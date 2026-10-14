## scripts/package.sh

```bash
#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."

echo "==> Empaquetando..."
mvn -q package -DskipTests

ls target/*.jar
```

## .github/workflows/cd.yml

```yaml
name: CD

on:
  push:
    tags: ['v*']

jobs:
  ci:
    uses: ./.github/workflows/ci.yml

  package:
    name: Empaquetar
    needs: ci
    runs-on: ubuntu-latest
    permissions:
      contents: write
    steps:
      - uses: actions/checkout@v7
      - uses: ./.github/actions/setup-java
      - run: ./scripts/package.sh
      - uses: softprops/action-gh-release@v3
        with:
          files: target/task-cli-*.jar
          fail_on_unmatched_files: true
          generate_release_notes: true
```
