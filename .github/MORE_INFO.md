# CI/CD: más información

## Caché y artefactos

- **Caché**: lo que se descarga y cambia poco (dependencias en `.m2/repository`). Si no
  está, se vuelve a descargar.
- **Artefactos**: resultados que pasan de un job a otro o se descargan (informes, el jar).
- No se cachea `target/`: la CI debe compilar desde cero para no arrastrar restos antiguos.
- En Rust sí se cachea `target/` porque ahí están las dependencias **compiladas**, que es lo
  lento. Es la misma regla: cachear lo caro de obtener que cambia poco.
- `cache: maven` en `setup-java` es un valor que entiende la acción: sabe qué carpeta
  guardar y cuándo invalidarla (cuando cambia el `pom.xml`).

## CD: publicar versiones

- El CD se dispara con un tag: `git tag v1.0.0 && git push origin v1.0.0`.
- Reutiliza la CI completa antes de empaquetar: si algo falla, no se publica nada.
- **Artefacto**: adjunto a la ejecución, solo para quien tiene acceso al repo, se borra a
  los 90 días.
- **Release**: visible en la portada del repo, permanente, descargable por cualquiera.
- `softprops/action-gh-release` crea la Release; `actions/create-release` está archivada y
  no se debe usar.
- `generate_release_notes` escribe las notas a partir de los commits y PRs: otro motivo para
  cuidar los mensajes de commit.
- La versión del jar sale del `pom.xml`, no del tag: hay que mantenerlos de acuerdo.

## Seguridad

- **Permisos mínimos**: dar solo lo necesario (`permissions: contents: write` solo en el job
  que crea la Release).
- **Fijar acciones por hash** en lugar de `@v3`: en 2025, las etiquetas de
  `tj-actions/changed-files` se movieron a código malicioso que expuso secretos de miles de
  repositorios en los logs.
- **Secretos** en *Settings → Secrets*: nunca en el código ni en el YAML.

## Otras ideas

- **Protección de rama**: exigir CI en verde y una revisión antes del merge. Convierte las
  reglas de `CONTRIBUTING.md` en obligatorias.
- **Dependabot**: abre PRs cuando hay versiones nuevas de dependencias o acciones; la CI dice
  si rompen algo.
- **`concurrency`**: cancelar la ejecución anterior si llega un push nuevo.
- **`paths-ignore`**: no ejecutar la CI si solo cambian archivos `.md`.
- **`timeout-minutes`**: evitar jobs colgados.
- **`schedule`**: ejecutar la CI cada noche para detectar roturas por dependencias.
- **Badge** en el README con el estado de la CI.
- **Cobertura** con JaCoCo: 100 % de cobertura no significa sin bugs.

## CI e IA

- Hay acciones que revisan cada PR con IA y comentan posibles bugs (por ejemplo, la GitHub
  Action de Claude Code).
- La IA es un revisor más: no sustituye a la CI ni a la revisión humana.
