#!/usr/bin/env bash
set -euo pipefail

if [[ ${EUID:-$(id -u)} -ne 0 ]]; then
  exec sudo -E bash "$0" "$@"
fi

for command_name in openvpn openssl ip ping grep; do
  command -v "$command_name" >/dev/null 2>&1 || {
    echo "missing required command: $command_name" >&2
    exit 2
  }
done

workdir="$(mktemp -d)"
server_ns="pvn-ovpn-s-$$"
client_ns="pvn-ovpn-c-$$"
server_if="ovs$$"
client_if="ovc$$"

cleanup() {
  set +e
  ip netns pids "$client_ns" 2>/dev/null | xargs -r kill >/dev/null 2>&1
  ip netns pids "$server_ns" 2>/dev/null | xargs -r kill >/dev/null 2>&1
  ip netns del "$client_ns" >/dev/null 2>&1
  ip netns del "$server_ns" >/dev/null 2>&1
  rm -rf "$workdir"
}
trap cleanup EXIT INT TERM

openvpn --version | head -n 1

openssl req -x509 -newkey rsa:2048 -nodes \
  -keyout "$workdir/ca.key" \
  -out "$workdir/ca.crt" \
  -days 1 -sha256 \
  -subj "/CN=PVNetwork OpenVPN CI CA" >/dev/null 2>&1

openssl req -newkey rsa:2048 -nodes \
  -keyout "$workdir/server.key" \
  -out "$workdir/server.csr" \
  -subj "/CN=pvnetwork-openvpn-server" >/dev/null 2>&1
cat >"$workdir/server.ext" <<'EOF'
basicConstraints=CA:FALSE
keyUsage=digitalSignature,keyEncipherment
extendedKeyUsage=serverAuth
subjectAltName=DNS:pvnetwork-openvpn-server
EOF
openssl x509 -req -in "$workdir/server.csr" \
  -CA "$workdir/ca.crt" -CAkey "$workdir/ca.key" -CAcreateserial \
  -out "$workdir/server.crt" -days 1 -sha256 \
  -extfile "$workdir/server.ext" >/dev/null 2>&1

openssl req -newkey rsa:2048 -nodes \
  -keyout "$workdir/client.key" \
  -out "$workdir/client.csr" \
  -subj "/CN=pvnetwork-openvpn-client" >/dev/null 2>&1
cat >"$workdir/client.ext" <<'EOF'
basicConstraints=CA:FALSE
keyUsage=digitalSignature,keyEncipherment
extendedKeyUsage=clientAuth
EOF
openssl x509 -req -in "$workdir/client.csr" \
  -CA "$workdir/ca.crt" -CAkey "$workdir/ca.key" -CAcreateserial \
  -out "$workdir/client.crt" -days 1 -sha256 \
  -extfile "$workdir/client.ext" >/dev/null 2>&1
chmod 600 "$workdir"/*.key

ip netns add "$server_ns"
ip netns add "$client_ns"
ip link add "$server_if" type veth peer name "$client_if"
ip link set "$server_if" netns "$server_ns"
ip link set "$client_if" netns "$client_ns"
ip -n "$server_ns" link set lo up
ip -n "$client_ns" link set lo up
ip -n "$server_ns" addr add 192.0.2.1/30 dev "$server_if"
ip -n "$client_ns" addr add 192.0.2.2/30 dev "$client_if"
ip -n "$server_ns" link set "$server_if" up
ip -n "$client_ns" link set "$client_if" up

cat >"$workdir/server.conf" <<EOF
client-to-client
dev tun
proto udp
local 192.0.2.1
port 11940
ifconfig 10.77.1.1 10.77.1.2
tls-server
ca $workdir/ca.crt
cert $workdir/server.crt
key $workdir/server.key
dh none
remote-cert-tls client
data-ciphers AES-256-GCM:AES-128-GCM
auth SHA256
ping 2
ping-restart 10
verb 3
EOF

cat >"$workdir/client.conf" <<EOF
dev tun
proto udp
remote 192.0.2.1 11940
nobind
ifconfig 10.77.1.2 10.77.1.1
tls-client
ca $workdir/ca.crt
cert $workdir/client.crt
key $workdir/client.key
remote-cert-tls server
data-ciphers AES-256-GCM:AES-128-GCM
auth SHA256
connect-retry-max 3
connect-timeout 10
verb 3
EOF

ip netns exec "$server_ns" openvpn \
  --config "$workdir/server.conf" \
  --daemon \
  --writepid "$workdir/server.pid" \
  --log "$workdir/server.log"

ip netns exec "$client_ns" openvpn \
  --config "$workdir/client.conf" \
  --daemon \
  --writepid "$workdir/client.pid" \
  --log "$workdir/client.log"

for _ in $(seq 1 40); do
  if grep -q "Initialization Sequence Completed" "$workdir/server.log" 2>/dev/null && \
     grep -q "Initialization Sequence Completed" "$workdir/client.log" 2>/dev/null; then
    break
  fi
  if ! ip netns pids "$server_ns" | grep -q . || ! ip netns pids "$client_ns" | grep -q .; then
    echo "OpenVPN process exited before tunnel initialization" >&2
    echo "--- server log ---" >&2
    cat "$workdir/server.log" >&2 || true
    echo "--- client log ---" >&2
    cat "$workdir/client.log" >&2 || true
    exit 1
  fi
  sleep 1
done

if ! grep -q "Initialization Sequence Completed" "$workdir/server.log" || \
   ! grep -q "Initialization Sequence Completed" "$workdir/client.log"; then
  echo "OpenVPN tunnel initialization timed out" >&2
  echo "--- server log ---" >&2
  cat "$workdir/server.log" >&2 || true
  echo "--- client log ---" >&2
  cat "$workdir/client.log" >&2 || true
  exit 1
fi

ip netns exec "$client_ns" ping -c 3 -W 2 10.77.1.1
ip netns exec "$server_ns" ping -c 3 -W 2 10.77.1.2

echo "PVNetwork OpenVPN real-link: PASS"
echo "Scope: ephemeral Ubuntu CI namespaces using the runner's system OpenVPN package; no OpenVPN binary is bundled into PVNetwork."
