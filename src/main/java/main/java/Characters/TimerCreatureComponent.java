package main.java.Characters;

public class TimerCreatureComponent extends CreatureComponent {

    private static final int timeInterval = 50;
    private int millisecondCount = 0;
    protected int timesCount = 0;

    public TimerCreatureComponent(Creature creature) {
        creatureClient = creature;
    }

    public void tick() throws InterruptedException{
        // 若timeInterval = 50ms;
        // 每个生物对应的动画刷新率应该在20fps左右
        // 这样每个线程在每50毫秒都有机会选择是否要做一些事情，根据timeCount可以控制行为的频率
        millisecondCount = (millisecondCount + timeInterval) % 1000;
        timesCount = millisecondCount/timeInterval;
        Thread.sleep(timeInterval);
    }
}
