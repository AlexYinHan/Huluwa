package main.java.Characters;

public class Grandpa extends Cheerer {
    public Grandpa(String name){
        super(name);
    }
    @Override
    public void prepareRenderComponent() {
        // Cheerer不参与战斗，不去设置attack
        renderComponent.prepare(RenderCreatureComponent.ImageType.HEALTH_BAR_FILL, "Image/healthBarFill.png", 1);
        renderComponent.prepare(RenderCreatureComponent.ImageType.IDLE, "Image/grandpa_idle.png", 1);
        renderComponent.prepare(RenderCreatureComponent.ImageType.TOMB, "Image/tomb.png", 1);
        renderComponent.prepare(RenderCreatureComponent.ImageType.HEALTH_BAR, "Image/healthBar.png", 1);
        // 文字气泡
        renderComponent.prepare(RenderCreatureComponent.ImageType.TALK_BUBBLE, "Image/talk_bubble_blue.png", 1);

    }

    @Override
    public String initInfo() {
        return "Grandpa;" + name + ";";
    }

    @Override
    public String toString() {
        if (alive) {
            return "grandpa_idle";
        } else {
            return "tomb";
        }
    }
}
