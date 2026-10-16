package com.devflow;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class App {
    private static final String HELP = """
            Comandos disponibles:
              add <título>   Añade una tarea
              list           Muestra todas las tareas
              done <id>      Marca una tarea como hecha
              delete <id>    Elimina una tarea
              clear          Elimina todas las tareas
              help           Muestra esta ayuda
              exit           Sale de la aplicación
            """;

    private final TaskManager manager = new TaskManager();

    public static void main(String[] args) {
        System.out.println("------- Chando-TODO -------");
        new App().run(new Scanner(System.in, StandardCharsets.UTF_8));
    }

    void run(Scanner in) {
        while (true) {
            System.out.print("> ");

            if (!in.hasNextLine()) {
                return;
            }

            String line = in.nextLine().strip();

            if ("exit".equals(line)) {
                System.out.println("¡Hasta luego!");
                return;
            }

            if (!line.isEmpty()) {
                System.out.println(handle(line));
            }
        }
    }

    String handle(String line) {
        String[] parts = line.split("\\s+", 2);
        String command = parts[0].toLowerCase(Locale.ROOT);
        String argument = parts.length > 1 ? parts[1] : "";

        try {
            return switch (command) {
                case "add" -> "Añadida: " + manager.add(argument);
                case "list" -> formatList();
                case "done" -> manager.complete(parseId(argument))
                        .map(task -> "Completada: " + task)
                        .orElse("No existe la tarea " + argument);
                case "delete" -> manager.delete(parseId(argument))
                        ? "Eliminada la tarea " + argument
                        : "No existe la tarea " + argument;
                case "clear" -> "Eliminadas " + manager.clear() + " tareas";
                case "help" -> HELP;
                default -> "Comando desconocido: '" + command + "'. Escribe 'help'.";
            };
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    private String formatList() {
        if (manager.list().isEmpty()) {
            return "No hay tareas.";
        }

        StringBuilder sb = new StringBuilder();
        manager.list().forEach(task -> sb.append(task).append('\n'));
        sb.append("Pendientes: ").append(manager.pendingCount());

        return sb.toString();
    }

    private static int parseId(String argument) {
        try {
            return Integer.parseInt(argument.strip());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("'" + argument + "' no es un id válido", e);
        }
    }
}
