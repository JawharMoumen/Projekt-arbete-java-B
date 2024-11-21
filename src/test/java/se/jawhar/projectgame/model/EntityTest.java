package se.jawhar.projectgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {


    @org.junit.jupiter.api.Test
    void takeHit() {
    }

    @org.junit.jupiter.api.Test
    void isConscious() {
    }

    // Testmetod för att verifiera om hälsan minskar korrekt när takeHit anropas
    @Test
    void ifTakeHit() {
        Resident resident = new Resident("Resident", 12, 3);
        resident.takeHit(3);
        int expected = 9;
        assertEquals(expected, resident.getHealth()); // Verifiera att den faktiska hälsan är lika med den förväntade (9)
    }

    // Testmetod för att verifiera om en entitet fortfarande är medveten efter att ha tagit skada
    @Test
    void ifConscious() {
        Resident resident = new Resident("Resident", 12, 3); // Skapar en Resident och en Burglar med initiala värden för hälsa och skada
        Burglar burglar = new Burglar("Burglar", 12, 4);

        resident.punch(burglar);
        resident.takeHit(4);

        assertTrue(resident.isConscious());

    }

    @Test
    void ifnotConscious() {
        Resident resident = new Resident("Resident", 12, 12);
        Burglar burglar = new Burglar("Burglar", 12, 4);

        resident.punch(burglar);
        // Efter att ha tagit skada, bör Residenten vara medveten (hälsan är fortfarande > 0)

        // kolla att Resident fortfarande är medveten
        assertFalse(burglar.isConscious());


    }


}