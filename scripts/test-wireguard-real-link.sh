#!/usr/bin/env bash
set -euo pipefail

for cmd in ip wg ping awk; do
  command -v "$cmd" >/dev/null || { echo "missing required command: $cmd" >&2; exit 2; }
done

NS_A="pvwg-a-$$"
NS_B="pvwg-b-$$"
VETH_A="pva$$"
VETH_B="pvb$$"
TMP="$(mktemp -d)"

cleanup() {
  ip netns del "$NS_A" 2>/dev/null || true
  ip netns del "$NS_B" 2>/dev/null || true
  rm -rf "$TMP"
}
trap cleanup EXIT

umask 077
wg genkey > "$TMP/a.key"
wg pubkey < "$TMP/a.key" > "$TMP/a.pub"
wg genkey > "$TMP/b.key"
wg pubkey < "$TMP/b.key" > "$TMP/b.pub"
PUB_A="$(cat "$TMP/a.pub")"
PUB_B="$(cat "$TMP/b.pub")"

ip netns add "$NS_A"
ip netns add "$NS_B"
ip link add "$VETH_A" type veth peer name "$VETH_B"
ip link set "$VETH_A" netns "$NS_A"
ip link set "$VETH_B" netns "$NS_B"

ip -n "$NS_A" link set lo up
ip -n "$NS_B" link set lo up
ip -n "$NS_A" addr add 192.0.2.1/24 dev "$VETH_A"
ip -n "$NS_B" addr add 192.0.2.2/24 dev "$VETH_B"
ip -n "$NS_A" link set "$VETH_A" up
ip -n "$NS_B" link set "$VETH_B" up

ip -n "$NS_A" link add wg0 type wireguard
ip -n "$NS_B" link add wg0 type wireguard
ip -n "$NS_A" addr add 10.203.0.1/24 dev wg0
ip -n "$NS_B" addr add 10.203.0.2/24 dev wg0

ip netns exec "$NS_A" wg set wg0 \
  private-key "$TMP/a.key" \
  listen-port 51821 \
  peer "$PUB_B" \
  endpoint 192.0.2.2:51822 \
  allowed-ips 10.203.0.2/32

ip netns exec "$NS_B" wg set wg0 \
  private-key "$TMP/b.key" \
  listen-port 51822 \
  peer "$PUB_A" \
  endpoint 192.0.2.1:51821 \
  allowed-ips 10.203.0.1/32

ip -n "$NS_A" link set wg0 up
ip -n "$NS_B" link set wg0 up

ip netns exec "$NS_A" ping -c 3 -W 3 10.203.0.2

HANDSHAKE_A="$(ip netns exec "$NS_A" wg show wg0 latest-handshakes | awk 'NR==1 {print $2}')"
HANDSHAKE_B="$(ip netns exec "$NS_B" wg show wg0 latest-handshakes | awk 'NR==1 {print $2}')"

if [[ ! "$HANDSHAKE_A" =~ ^[0-9]+$ || "$HANDSHAKE_A" -le 0 ]]; then
  echo "WireGuard peer A did not record a real handshake" >&2
  exit 1
fi
if [[ ! "$HANDSHAKE_B" =~ ^[0-9]+$ || "$HANDSHAKE_B" -le 0 ]]; then
  echo "WireGuard peer B did not record a real handshake" >&2
  exit 1
fi

TRANSFER_A="$(ip netns exec "$NS_A" wg show wg0 transfer | awk 'NR==1 {print $2" "$3}')"
TRANSFER_B="$(ip netns exec "$NS_B" wg show wg0 transfer | awk 'NR==1 {print $2" "$3}')"

echo "WireGuard A transfer bytes: $TRANSFER_A"
echo "WireGuard B transfer bytes: $TRANSFER_B"
echo "PVNetwork WireGuard real-link: PASS"
