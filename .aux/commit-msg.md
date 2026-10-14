## .githooks/commit-msg

```bash
#!/usr/bin/env bash
set -euo pipefail

TYPES="feat|fix|docs|test|refactor|chore"
PATTERN="^($TYPES)(\([a-z0-9-]+\))?: .+"

MESSAGE=$(sed -e '/^#/d' -e 'q' "$1")

if [[ "$MESSAGE" =~ ^(Merge|Revert) ]]; then
    exit 0
fi

if ! [[ "$MESSAGE" =~ $PATTERN ]]; then
    echo "[commit-msg] Mensaje de commit no válido: \"$MESSAGE\""
    echo
    echo "Formato esperado:  <tipo>: <descripción>"
    echo "Tipos permitidos:  ${TYPES//|/, }"
    echo "Ejemplo:           fix: evitar ids duplicados tras borrar una tarea"
    exit 1
fi
```
