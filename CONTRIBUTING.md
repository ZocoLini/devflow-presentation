# Cómo contribuir

## Instalar los git hooks

El repositorio incluye hooks en la carpeta `.githooks/`. Actívalos una sola vez después
de clonar:

```bash
git config core.hooksPath .githooks
```

| Hook         | Cuándo se ejecuta       | Qué comprueba                    |
|--------------|-------------------------|----------------------------------|
| `pre-commit` | Antes de cada commit    | Formato, linter y tests          |

Si alguna comprobación falla, el commit se cancela. Arregla el problema y vuelve a
intentarlo.

Para desactivarlos:

```bash
git config --unset core.hooksPath
```

## Flujo de trabajo

1. Crea una rama desde `master` actualizada:
   ```bash
   git checkout master
   git pull
   git checkout -b feature/nombre-corto
   ```
2. Haz tus cambios en commits pequeños y con sentido.
3. Comprueba que todo funciona antes de subir:
   ```bash
   ./scripts/test.sh
   ```
4. Sube la rama y abre un Pull Request hacia `master`.
5. Espera al menos una revisión aprobada antes de hacer merge.

## Nombres de ramas

| Prefijo     | Uso                              | Ejemplo                  |
|-------------|----------------------------------|--------------------------|
| `feature/`  | Nueva funcionalidad              | `feature/edit-command`   |
| `fix/`      | Corrección de un error           | `fix/empty-title`        |
| `docs/`     | Cambios en documentación         | `docs/readme-usage`      |
| `refactor/` | Mejora interna sin cambiar el comportamiento | `refactor/parser` |

## Mensajes de commit

Seguimos [Conventional Commits](https://www.conventionalcommits.org/es/):

```
<tipo>: <descripción en minúsculas y en imperativo>
```

Tipos permitidos: `feat`, `fix`, `docs`, `test`, `refactor`, `chore`.

```
feat: añadir comando edit
fix: evitar error con ids negativos
test: cubrir el comando delete
```

## Reglas básicas

- No se hace push directo a `master`: todo entra por Pull Request.
- Todos los tests deben pasar antes de abrir el PR.
- Cada funcionalidad nueva o corrección viene con su test.
- Un PR = un cambio. Si mezcla cosas distintas, divídelo.
- Mantén el estilo del código existente.
