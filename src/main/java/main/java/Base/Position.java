package main.java.Base;

import main.java.Characters.Creature;
import main.java.Types.Vector2;

public class Position {

    private Vector2 coordinate;
    private Creature holder;

    private Field field = null;

    public Creature getHolder() {
        return holder;
    }

    public void setHolder(Creature holder) {
        this.holder = holder;
    }

    public int getX() {
        return coordinate.getX();
    }

    public int getY() {
        return coordinate.getY();
    }

    public Vector2 getCoordinate() {
        return coordinate;
    }

    public void setX(int x) {
        this.coordinate.setX(x);
    }

    public Position(int x){
        super();
        this.coordinate = new Vector2(x, 0);
    }

    public Position(int x, int y, Field field){ // 一个具体的position在一个field中，否则在虚空中（null）
        super();
        this.coordinate = new Vector2(x, y);
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Boolean isInSomeField() {//是否是在某个field中的具体position
        return this.field != null;
    }
}

