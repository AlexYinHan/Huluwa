package main.java.Characters;

public class CombatCreatureComponent extends CreatureComponent {
    private int attackValue = 0;
    public CombatCreatureComponent(Creature creature) {
        creatureClient = creature;
    }
    public void beAttacked(int attackValue) {
        if (creatureClient != null) {
            creatureClient.currentHealth -= attackValue;
            if (creatureClient.currentHealth <= 0) {
                creatureClient.currentHealth = 0;

                creatureClient.alive = false;
            }
        }
    }

    public void attack(Creature creature) {
        if (creature != null && creature.combatComponent != null && creatureClient != null) {
            creature.combatComponent.beAttacked(this.attackValue);
            creatureClient.faceDirection = (creature.position.getY() - creatureClient.position.getY()) > 0 ? Direction.RIGHT : Direction.LEFT;
        }
    }

    public void attemptToAttack(Creature creature) {

    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }
}
