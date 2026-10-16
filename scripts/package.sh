#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."

echo "==> Empaquetando..."
mvn -q package -DskipTests

ls target/*.jar
