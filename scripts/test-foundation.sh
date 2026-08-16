#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
OUT="${TMPDIR:-/tmp}/pvnetwork-foundation-smoke.jar"

mapfile -t SOURCES < <(find "$ROOT/core/foundation/src/commonMain/kotlin" -type f -name '*.kt' | sort)
SOURCES+=("$ROOT/tools/foundation-smoke/Main.kt")

kotlinc "${SOURCES[@]}" -include-runtime -d "$OUT"
java -jar "$OUT"
