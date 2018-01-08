/**
 * Cheerer是一种生物，可以为自己所属的势力加油助威
 * 只能站在原地
 * 爷爷和蛇精属于这个类
 */
package main.java.Characters;

public class Cheerer extends Creature implements CheeringGroup {
    protected String name;
    protected String cheerUpWords = "";
    private boolean didAnnounceVictory = false;
    public Cheerer(String name){
        super();
        this.name = name;
    }


    @Override
    protected void prepareRenderComponent() {
        // 文字气泡
        renderComponent.prepare(RenderCreatureComponent.ImageType.TALK_BUBBLE, "Image/talk_bubble_white.png", 1);

    }

    @Override
    public void act(){
        cheerUp();
        System.out.println(cheerUpWords);
    }

    @Override
    public void cheerUp(){
        int remainFriendlyForceNum = navigationComponent.getAliveCampCreature().size();
        int remainHostileCreatureNum = navigationComponent.getAliveHostileCreatures().size();
        if (alive) {
            if (remainHostileCreatureNum == 0) {
                cheerUpWords = "胜利！";
                if (didAnnounceVictory == false) {
                    announceVictory();
                    didAnnounceVictory = true;
                }

            }else if (remainFriendlyForceNum <= 1) {
                cheerUpWords = "救命！";
            } else {
                cheerUpWords = "加油，还剩" + remainHostileCreatureNum + "个敌人。";
            }

        } else {
            cheerUpWords = "";
        }
        talk(cheerUpWords);

    }


    @Override
    public void report() {
        System.out.print(this.toString());
    }

    @Override
    public String initInfo() {
        return "Cheerer;";
    }

    @Override
    protected void doThreadOperations() {
        if (timerComponent.timesCount == 0) { // per second
            cheerUp();
        }
    }

    @Override
    public String toString(){
        return this.name
                + "  "
                + "@"
                + this.position.getX() + "," + this.position.getY()
                + ";";
    }

    protected void announceVictory() {
        troop.announceVictory();
    }
}
