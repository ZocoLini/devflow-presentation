#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."

JAVA_OPTS=()
if [ "${1:-}" = "--debug" ]; then
    JAVA_OPTS+=(-ea)
fi

echo "==> Compilando..."
mvn -q compile

echo "==> Ejecutando"
echo
java "${JAVA_OPTS[@]}" -cp target/classes com.devflow.App
