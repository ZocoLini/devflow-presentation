#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."

echo "==> Comprobando la arquitectura..."
if grep -rnE 'System\.(out|err|in)|Scanner|Console' src/main/java --include='*.java' --exclude=App.java; then
    echo "Solo App.java puede usar la consola. Mueve esa entrada o salida a App."
    exit 1
fi
