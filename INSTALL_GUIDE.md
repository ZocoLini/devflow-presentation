# Guía de instalación

Para trabajar en el proyecto necesitas:

- **Git**
- **JDK 21** o superior
- **Maven 3.6.3** o superior

Esta guía está pensada para Ubuntu.

## 1. Comprueba qué tienes

```bash
lsb_release -a    # versión de Ubuntu
git --version
java -version
mvn -v
```

Si `java -version` muestra 21 o superior y `mvn -v` funciona, salta al [paso 4](#4-comprueba-la-instalación).

## 2. Instalación con apt (Ubuntu 20.04 o posterior)

```bash
sudo apt update
sudo apt install -y git openjdk-21-jdk maven
```

Versiones que instala apt en cada Ubuntu (probadas con este proyecto):

| Ubuntu | JDK      | Maven |
|--------|----------|-------|
| 20.04  | 21.0.7   | 3.6.3 |
| 22.04  | 21.0.12  | 3.6.3 |
| 24.04  | 21.0.12  | 3.8.7 |

En todas ellas compila y pasan los tests.

Si ya tenías otra versión de Java instalada, elige la 21 como predeterminada:

```bash
sudo update-alternatives --config java
```

Si todo ha ido bien, salta al [paso 4](#4-comprueba-la-instalación).

## 3. Si lo anterior no funciona: SDKMAN

Usa esta opción si apt no encuentra el paquete `openjdk-21-jdk` o si las versiones
instaladas no son las correctas. SDKMAN instala Java y Maven en tu carpeta
personal, sin depender de la versión de Ubuntu.

Instala SDKMAN:

```bash
sudo apt update
sudo apt install -y curl zip unzip
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
```

Busca la versión 21 de Temurin:

```bash
sdk list java | grep -- "-tem"
```

Instálala copiando el identificador de la última columna tal y como te salga. Cambia con
el tiempo y también según el procesador del equipo:

| Procesador                    | Ejemplo de identificador |
|-------------------------------|--------------------------|
| Intel / AMD (x86_64)          | `21.0.12+1.1-tem`        |
| ARM (aarch64, p. ej. Mac M1+) | `21.0.12-tem`            |

Puedes ver tu procesador con `uname -m`. Después instala Java y Maven:

```bash
sdk install java 21.0.12+1.1-tem    # usa el identificador que te haya salido
sdk install maven
```

Abre una terminal nueva para que se apliquen los cambios.

## 4. Comprueba la instalación

```bash
java -version
mvn -v
```

- `java -version` debe mostrar **21** o superior.
- `mvn -v` debe mostrar Maven **3.6.3** o superior, y en la línea `Java version` también
  **21** o superior. Es el JDK con el que Maven compila el proyecto.

## 5. Prueba el proyecto

```bash
git clone <url-del-repositorio>
cd devflow-presentation
./scripts/test.sh    # deben pasar todos los tests
./scripts/run.sh     # arranca la aplicación
```

## Problemas frecuentes

**`error: release version 21 not supported`**
Maven está usando un JDK anterior al 21. Revisa la línea `Java version` de `mvn -v` y
cambia el JDK predeterminado con `sudo update-alternatives --config java`, o instala el
21 con SDKMAN.

**`mvn: command not found` o `sdk: command not found` después de usar SDKMAN**
Abre una terminal nueva, o ejecuta `source "$HOME/.sdkman/bin/sdkman-init.sh"`.

**Salen caracteres raros, como `?Hasta luego!` en lugar de `¡Hasta luego!`**
La terminal no está en UTF-8 (pasa sobre todo en contenedores Docker o servidores mínimos).
```bash
export LANG=C.UTF-8
```

**`Permission denied` al ejecutar un script**
```bash
chmod +x scripts/*.sh
```
