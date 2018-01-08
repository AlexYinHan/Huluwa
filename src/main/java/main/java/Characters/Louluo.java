/**
 * Louluo:小喽啰，是一种生物,没有名字，只有代号
 */
package main.java.Characters;

import main.java.Types.TianGan;

import java.util.Random;

public class Louluo extends Creature{

    private TianGan codeName;
    private int louluoKind;

    public Louluo(TianGan codeName){
        super();
        this.codeName = codeName;
        this.combatComponent.setAttackValue(10);
    }

    public Louluo(TianGan codeName, int louluokind) {
        super();
        this.codeName = codeName;
        this.combatComponent.setAttackValue(10);
        if (louluokind != 0 && louluokind != 1) {
            louluokind = 0;
        }
        this.louluoKind = louluokind;
    }
    @Override
    protected void prepareRenderComponent() {
        try {
            Random random = new Random();
            this.louluoKind = random.nextInt(2);
            renderComponent.prepare(RenderCreatureComponent.ImageType.MOVING, "Image/louluo" + louluoKind + "_moving.png", 6);
            renderComponent.prepare(RenderCreatureComponent.ImageType.HEALTH_BAR, "Image/healthBar.png", 1);
            renderComponent.prepare(RenderCreatureComponent.ImageType.HEALTH_BAR_FILL, "Image/healthBarFill.png", 1);
            renderComponent.prepare(RenderCreatureComponent.ImageType.TOMB, "Image/tomb.png", 1);
            renderComponent.prepare(RenderCreatureComponent.ImageType.IDLE, "Image/louluo" + louluoKind + "_idle.png", 1);
            renderComponent.prepare(RenderCreatureComponent.ImageType.ATTACKING, "Image/louluo" + louluoKind + "_attack.png", 3);
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
        try{
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
                    return "louluo" + louluoKind + "_moving";
                case ATTACKING:
                    return "louluo" + louluoKind + "_attack";
                case IDLE:
                default:
                    return "louluo" + louluoKind + "_idle";
            }
        }else {
            return "tomb";
        }
        //return "Louluo" + louluoKind;
//        return this.codeName.toString()
//                + " @"
//                + this.position.getX() + "," + this.position.getY()
//                + ";_ ";
    }

    @Override
    public String initInfo() {
        return "Louluo;" + codeName + ";" + louluoKind + ";";
    }
}


