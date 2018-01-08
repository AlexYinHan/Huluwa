/**
 * ScorpionMonster是一种生物，有名字，暂时只会站着
 * 蝎子精属于这个类
 */
package main.java.Characters;

public class ScorpionMonster extends Creature {
    private String name;

    public ScorpionMonster(String name){
        super();
        this.name = name;

        this.maxHealth = 200;
        this.currentHealth = 200;
        this.combatComponent.setAttackValue(20);
    }
    @Override
    protected void prepareRenderComponent() {
        try {
            renderComponent.prepare(RenderCreatureComponent.ImageType.MOVING, "Image/xiezi_moving.png", 5);
            renderComponent.prepare(RenderCreatureComponent.ImageType.HEALTH_BAR, "Image/healthBar.png", 1);
            renderComponent.prepare(RenderCreatureComponent.ImageType.HEALTH_BAR_FILL, "Image/healthBarFill.png", 1);
            renderComponent.prepare(RenderCreatureComponent.ImageType.TOMB, "Image/tomb.png", 1);
            renderComponent.prepare(RenderCreatureComponent.ImageType.ATTACKING, "Image/xiezi_attack.png", 5);
            renderComponent.prepare(RenderCreatureComponent.ImageType.IDLE, "Image/xiezi_idle.png", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void report() {
        System.out.print(this.toString());
    }

    @Override
    protected void doThreadOperations() {
        try {
            doBattleOperations();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    public String toString(){
        if (alive) {
            switch (state) {
                case MOVING:
                    return "xiezi_moving";
                case ATTACKING:
                    return "xiezi_attack";
                case IDLE:
                default:
                    return "xiezi_idle";
            }
        } else {
            return "tomb";
        }
//        return this.name
//                + "@"
//                + this.position.getX() + "," + this.position.getY()
//                + ";";
    }

    @Override
    public String initInfo() {
        return "ScorpionMonster;" + name + ";";
    }
}
