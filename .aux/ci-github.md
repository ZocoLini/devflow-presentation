## .github/actions/setup-java/action.yml

```yaml
name: Preparar Java
description: Instala Temurin 21 con caché de Maven

runs:
  using: composite
  steps:
    - uses: actions/setup-java@v6
      with:
        distribution: temurin
        java-version: 21
        cache: maven
```

## .github/workflows/ci.yml

```yaml
name: CI

on:
  push:
    branches: [master]
  pull_request:
    branches: [master]

jobs:
  format:
    uses: ./.github/workflows/format.yml

  lint:
    uses: ./.github/workflows/lint.yml

  test:
    needs: [format, lint]
    uses: ./.github/workflows/test.yml
```

## .github/workflows/format.yml

```yaml
name: Formato

on:
  workflow_call:

jobs:
  format:
    name: Formato
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v7
      - uses: ./.github/actions/setup-java
      - run: ./scripts/format.sh --check
```

## .github/workflows/lint.yml

```yaml
name: Linter

on:
  workflow_call:

jobs:
  lint:
    name: Linter
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v7
      - uses: ./.github/actions/setup-java
      - run: ./scripts/lint.sh
```

## .github/workflows/test.yml

```yaml
name: Tests

on:
  workflow_call:

jobs:
  test:
    name: Tests
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v7
      - uses: ./.github/actions/setup-java
      - run: ./scripts/test.sh
      - if: failure()
        uses: actions/upload-artifact@v7
        with:
          name: test-reports
          path: target/surefire-reports/
```
