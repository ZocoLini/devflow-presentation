package com.devflow;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AppTest {

    private final App app = new App();

    @Test
    void addAndListTasks() {
        app.handle("add Preparar la presentación");
        String output = app.handle("list");
        assertTrue(output.contains("1. [ ] Preparar la presentación"));
        assertTrue(output.contains("Pendientes: 1"));
    }

    @Test
    void invalidIdShowsError() {
        assertEquals("Error: 'abc' no es un id válido", app.handle("done abc"));
    }

    @Test
    void clearEmptiesTheList() {
        app.handle("add Una");
        app.handle("add Otra");
        assertEquals("Eliminadas 2 tareas", app.handle("clear"));
        assertEquals("No hay tareas.", app.handle("list"));
    }

    @Test
    void unknownCommandIsReported() {
        assertTrue(app.handle("foo").startsWith("Comando desconocido"));
    }
}
