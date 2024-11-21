package se.jawhar.projectgame.model;

public abstract class Entity {

    // Variabler för entitetens roll (tex. Boende, inbrottstjuv), hälsa och skada
    private String role;

    private int health;

    private int damage;


    public Entity(String role, int health, int damage) {
        this.role = role;
        this.health = health;
        this.damage = damage;
    }


    // Metod som låter en entitet (den aktuella instansen) slå en annan entitet.
    void punch(Entity toPunch) {
        toPunch.takeHit(damage);
    }

    // Metod som tar emot skada. Hälsan minskar med det belopp som skadan är.
    void takeHit(int damage) {
        health -= damage;
        System.out.println(role + " Tog " + damage + " Skada. Återstående liv " + health);
    }

    // Metod för att lägga till extra skada till entiteten.
    public void addDamage(int damage) {
        this.damage += damage;

    }

    // Metod för att kontrollera om entiteten fortfarande är medveten
    boolean isConscious() {
        if (health > 0) {
            return true;
        }
        return false;
    }

    public String getRole() {
        return role;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }
}
