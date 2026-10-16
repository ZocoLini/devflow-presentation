# Git hooks: más información

## Los hooks ayudan, pero no garantizan nada

- `git commit --no-verify` y `git push --no-verify` se saltan los hooks.
- Git no activa los hooks al clonar, por seguridad: clonar un repo no debe ejecutar código
  en tu máquina. Cada persona tiene que activarlos (`git config core.hooksPath .githooks`).
- Los hooks avisan pronto; la **CI** es la que de verdad impide que entre código roto.

## Qué comprobar en cada sitio

- Un hook lento acaba saltándose con `--no-verify`: lo rápido va en local, lo lento en la CI.
- `pre-commit` (segundos): formato y linter.
- `commit-msg` (instantáneo): formato del mensaje.
- `pre-push` (hasta un minuto): tests.
- CI (sin límite): todo, en un entorno limpio.

## Detectar secretos

- Subir una contraseña o API key por error es uno de los fallos más habituales y caros.
- Aunque se borre después, sigue en el historial de Git: hay que invalidar la clave.
- Herramientas como **gitleaks** lo detectan en el `pre-commit` y cancelan el commit.
- Los asistentes de IA a veces escriben claves directamente en el código.
- Ver el ejemplo de abajo.

## Gestores de hooks

- `core.hooksPath` sirve para aprender; en proyectos reales se usa una herramienta.
- **Husky**: proyectos JavaScript.
- **pre-commit**: nació en Python, pero vale para cualquier lenguaje y tiene cientos de hooks
  ya hechos (gitleaks incluido).
- **Lefthook**: multilenguaje, rápido, ejecuta las comprobaciones en paralelo.
- En Java hay plugins de Maven que instalan los hooks al compilar.

## Revisar solo lo que se va a commitear

- Nuestros hooks revisan los archivos tal como están en disco.
- Las herramientas profesionales (lint-staged, pre-commit) revisan solo lo añadido con
  `git add`: es más correcto y más rápido en proyectos grandes.

## Hooks e IA

- Los hooks también se ejecutan cuando el commit lo hace un asistente de IA: son una red de
  seguridad para el código generado.
- Las propias herramientas de IA copian la idea: Claude Code, por ejemplo, tiene hooks para
  ejecutar comprobaciones antes o después de que la IA edite un archivo.

## Ejemplo: detectar una clave falsa

El script [`examples/pre-commit-secrets`](examples/pre-commit-secrets) busca en los archivos
del commit patrones típicos de secretos: claves de AWS, tokens de GitHub, claves de APIs,
claves privadas y asignaciones como `password = "..."`. No necesita instalar nada.

Actívalo como `pre-commit` (sustituye al actual mientras haces la prueba):

```bash
cp .githooks/pre-commit .githooks/pre-commit.bak
cp .githooks/examples/pre-commit-secrets .githooks/pre-commit
```

Crea un archivo con una clave falsa (es la clave de ejemplo que publica AWS en su
documentación, no funciona) e intenta hacer el commit:

```bash
cat > src/main/java/com/devflow/Config.java <<'JAVA'
package com.devflow;

public class Config {
    static final String AWS_KEY = "AKIAIOSFODNN7EXAMPLE";
}
JAVA

git add src/main/java/com/devflow/Config.java
git commit -m "feat: añadir configuración"
```

Resultado:

```
==> [pre-commit] Buscando secretos en los archivos del commit
[pre-commit] Posible secreto en src/main/java/com/devflow/Config.java:
    línea 4:    static final String AWS_KEY = "AKIAIOSFODNN7EXAMPLE";

[pre-commit] Commit cancelado. Quita el secreto y usa una variable de entorno.
```

La forma correcta es leer la clave de una variable de entorno:

```java
static final String AWS_KEY = System.getenv("AWS_KEY");
```

Para dejarlo todo como estaba:

```bash
git restore --staged src/main/java/com/devflow/Config.java
rm src/main/java/com/devflow/Config.java
mv .githooks/pre-commit.bak .githooks/pre-commit
```
