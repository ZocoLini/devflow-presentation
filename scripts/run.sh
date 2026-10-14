#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."

echo "==> Compilando..."
mvn -q compile

echo "==> Ejecutando"
echo
java -cp target/classes com.devflow.App
