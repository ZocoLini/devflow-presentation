#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."

if [ "${1:-}" = "--check" ]; then
    echo "==> Comprobando formato..."
    mvn -q spotless:check
else
    echo "==> Formateando..."
    mvn -q spotless:apply
fi
