package com.devflow;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskManagerTest {

    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new TaskManager();
    }

    @Test
    void addAssignsIncrementalIds() {
        assertEquals(1, manager.add("Primera").id());
        assertEquals(2, manager.add("Segunda").id());
        assertEquals(2, manager.list().size());
    }

    @Test
    void addRejectsBlankTitle() {
        assertThrows(IllegalArgumentException.class, () -> manager.add("   "));
    }

    @Test
    void completeMarksTaskAsDone() {
        manager.add("Estudiar Git");
        assertTrue(manager.complete(1).orElseThrow().done());
        assertEquals(0, manager.pendingCount());
    }

    @Test
    void completeUnknownIdReturnsEmpty() {
        assertTrue(manager.complete(99).isEmpty());
    }

    @Test
    void deleteRemovesTask() {
        manager.add("Borrar esto");
        assertTrue(manager.delete(1));
        assertFalse(manager.delete(1));
        assertTrue(manager.list().isEmpty());
    }

    @Test
    void addAfterDeleteDoesNotReuseIds() {
        manager.add("Comprar pan");
        Task second = manager.add("Estudiar Git");
        manager.delete(1);
        Task third = manager.add("Preparar slides");

        assertNotEquals(second.id(), third.id());
    }

    @Test
    void clearRemovesAllTasks() {
        manager.add("Una");
        manager.add("Otra");
        assertEquals(2, manager.clear());
        assertTrue(manager.list().isEmpty());
    }
}
