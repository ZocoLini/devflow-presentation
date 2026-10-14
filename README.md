# Task CLI

Pequeña aplicación de consola en Java para gestionar tareas. Sirve de base para
la presentación sobre el flujo de trabajo de un desarrollador, Git hooks y CI/CD.

## Requisitos

- Java 21 o superior
- Maven 3.6.3 o superior

Si no los tienes, sigue la [guía de instalación](INSTALL_GUIDE.md).

## Uso

```bash
mvn test                                    # ejecutar los tests
mvn package                                 # compilar y generar el jar
java -jar target/task-cli-1.0.0.jar         # arrancar la aplicación
```

O con los scripts:

```bash
./scripts/run.sh    # compila y ejecuta la app (sin empaquetar)
./scripts/run.sh --debug      # igual, pero con las aserciones activadas (-ea)
./scripts/test.sh   # ejecuta los tests
./scripts/format.sh           # formatea el código (Spotless)
./scripts/format.sh --check   # solo comprueba el formato
./scripts/lint.sh             # pasa el linter (PMD)
```

## Comandos

| Comando        | Descripción                  |
|----------------|------------------------------|
| `add <título>` | Añade una tarea              |
| `list`         | Muestra todas las tareas     |
| `done <id>`    | Marca una tarea como hecha   |
| `delete <id>`  | Elimina una tarea            |
| `clear`        | Elimina todas las tareas     |
| `help`         | Muestra la ayuda             |
| `exit`         | Sale de la aplicación        |

## Estructura

```
src/main/java/com/devflow/
├── App.java          # Bucle de consola y parseo de comandos
├── Task.java         # Modelo (record inmutable)
└── TaskManager.java  # Lógica de negocio
src/test/java/com/devflow/
├── AppTest.java
└── TaskManagerTest.java
```
