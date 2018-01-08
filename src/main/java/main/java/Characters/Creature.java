package main.java.Characters;
import main.java.Base.Troop;
import main.java.Base.Position;
import main.java.Types.Vector2;

import java.awt.*;
import java.io.Serializable;

enum Direction {
    LEFT, RIGHT, UP, DOWN
}
public abstract class Creature implements Paintable, Runnable, Serializable{

    protected Position position;

    private String talkMessage;
    protected Troop troop; //  每个生物有其所属的阵营
    //m
    public RenderCreatureComponent renderComponent;
    protected NavigationCreatureComponent navigationComponent;// 寻路组件，在需要寻路时向其索要目的地等信息
    protected TimerCreatureComponent timerComponent;
    public CombatCreatureComponent combatComponent;

    //m
    public int remainActionAnimationClockCircles = 0;
    //m
    public CreatureState state = CreatureState.IDLE;
    public boolean alive = true;
    public int currentHealth = 100, maxHealth = 100;
    Direction faceDirection = Direction.RIGHT;

    public abstract void report();
    public abstract String initInfo();
    public void act(){}

    public Creature() {

        position = new Position(0, 0, null);
        renderComponent = new RenderCreatureComponent(this);
        prepareRenderComponent();
        navigationComponent = new NavigationCreatureComponent(this);
        timerComponent = new TimerCreatureComponent(this);
        combatComponent = new CombatCreatureComponent(this);
    }

    protected abstract void prepareRenderComponent();

    public void moveTo(Position position) {

        if (!this.position.isInSomeField()) {//只有具体的position上的生物才能移动
            return;
        }
        if(this.position != null){
            this.position.setHolder(null);
        }
        if(position == null){
            // 这个生物将被移出所在的field
            // 在线程安全的前提下，不会有两个生物同时在一个position中，也就不会进入这个语句块
            // 让进入错误位置的生物被标记，并且可以继续显示出来，用于及时显示出线程不安全的问题
            this.position = new Position(this.position.getX(), this.position.getY(), null);
            return;
        }
        if(position.getHolder() != null){ //  将目标位置上原有的占有者移走
            position.getHolder().moveTo((Position) null);
        }

        if (this.position != null && position !=null) {
            faceDirection = (position.getY() - this.position.getY()) > 0 ? Direction.RIGHT : Direction.LEFT;
        }
        this.position = position;
        position.setHolder(this);
    }

    public void moveTo(Vector2 positionCoordinate) {
        try {
            Position position = troop.getPositionAt(positionCoordinate.getX(), positionCoordinate.getY());
            if(position == null){
                throw new NullPointerException();
            }
            this.moveTo(position);
        } catch (NullPointerException e){
            System.out.println("Creature.moveTo(Vector2 ):错误的position坐标。");
            System.out.println("positionCoordinate: " + positionCoordinate);
        }

    }

    public boolean attemptToMoveTo(Vector2[] positionCoordinates) {
        /*
        尝试移动到某一组坐标上的position，只有找到一个没有被占据的position后，才真的会移动，否则不动。
         */
        if (positionCoordinates == null) {
            return false;
        }
        synchronized (Creature.class) {
            for(Vector2 positionCoordinate: positionCoordinates) {
                Position position = troop.getPositionAt(positionCoordinate.getX(), positionCoordinate.getY());
                if (position != null && position.getHolder() == null) { // 候选的position没有被占据，可以移动
                    moveTo(position);
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public final void run() { //  template method
        try {
            while (!Thread.interrupted()) {
                doThreadOperations();
                updatePaintings();
                timerComponent.tick();
            }
        } catch (NullPointerException e) {
            System.out.println("Creature.startActing(): NullPointerException.");
            e.printStackTrace();
            System.out.println(troop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected final void doBattleOperations() {
        if (alive) {
            if (timerComponent.timesCount == 0) { // per second
                // 先判断是否有敌人可以攻击，若没有，则寻路
                if (state == CreatureState.IDLE && combatComponent != null) {
                    Position destination = navigationComponent.getPositionOfNearestAliveHostileCreature();
                    if (destination != null && Vector2.ManhattanDistance(destination.getCoordinate(), this.position.getCoordinate()) == 1) {
                        if (destination.getHolder() != null && destination.getHolder().alive) {
                            combatComponent.attack(destination.getHolder());
                            state = CreatureState.ATTACKING;
                            remainActionAnimationClockCircles = 10; // 攻击动画持续500毫秒
                            Vector2 currentCoordinate = this.position.getCoordinate();
                            renderComponent.startAnimationProgressWithDuration(remainActionAnimationClockCircles, RenderCreatureComponent.ImageType.ATTACKING, currentCoordinate);

                        }
                    }
                }
                if (state == CreatureState.IDLE && navigationComponent != null) {
                    // 寻路
                    // 先得到要去的position
                    Position destination = navigationComponent.getPositionOfNearestAliveHostileCreature();
                    // 然后尝试往这个方向移动
                    Vector2 currentCoordinate = this.position.getCoordinate();
                    boolean isMoving = attemptToMoveTo(navigationComponent.getPossibleNextPositionVectors(destination));
                    if (isMoving) {
                        state = CreatureState.MOVING;
                        remainActionAnimationClockCircles = 10; // 移动动画持续500毫秒
                        renderComponent.startAnimationProgressWithDuration(remainActionAnimationClockCircles, RenderCreatureComponent.ImageType.MOVING, currentCoordinate);

                    }

                }

            }
        }
        if (timerComponent.timesCount%2 == 0) {
            renderComponent.changeToNextGesture();
//            if (state == CreatureState.MOVING) {
//                renderComponent.changeToNextGesture();
//            }
        }
    }
    protected abstract void doThreadOperations();
    public final void updatePaintings() {
        if (remainActionAnimationClockCircles > 0) {
            remainActionAnimationClockCircles--;
            renderComponent.updateAnimationProgress();
        }
        if (remainActionAnimationClockCircles <= 0) {
            state = CreatureState.IDLE;
            renderComponent.resetAnimationProgress();
        }

        //troop.askFieldToRepaint();
    }

    public final void paintInGraphics(Graphics g, int positionWidth, int positionHeight) {
        if (renderComponent != null) {
            renderComponent.paintInGraphics(g, positionWidth, positionHeight);
        }

    }

    public void setPosition(Position position) {   //  只改变position的值，但是不会处理相应的holder
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void changePositionWith(Creature another){

        Position tempPosition = this.position;
        this.moveTo(another.getPosition());
        another.moveTo(tempPosition);
    }

    public String getCampName() {
        if(troop == null)
            return "";
        else
            return troop.getCampName();
    }

    public void setTroop(Troop troop) {
        this.troop = troop;
    }


    public String getTalkMessage() {
        return talkMessage;
    }

    public void talk(String talkMessage) {
        this.talkMessage = talkMessage;
    }
}
