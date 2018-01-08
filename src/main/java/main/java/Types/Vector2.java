package main.java.Types;

import static java.lang.Math.abs;

/**
 * 二维向量，用于表示位置坐标信息
 */
public class Vector2 {
    private int x, y;

    public Vector2(){
        this.x = -1;
        this.y = -1;
    }

    public Vector2(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vector2 add(Vector2 another){
        Vector2 result = new Vector2(this.x + another.getX(), this.y + another.getY());
        return result;
    }

    public Vector2 minus(Vector2 another) {
        Vector2 result = new Vector2(this.x - another.getX(), this.y - another.getY());
        return result;
    }

    public Vector2 getDirection() {
        int directionX, directionY;
        if (x == 0) {
            directionX = 0;
        } else {
            directionX = x > 0 ? 1 : -1;
        }
        if (y == 0) {
            directionY = 0;
        } else {
            directionY = y > 0 ? 1 : -1;
        }
        return new Vector2(directionX, directionY);
    }

    public boolean lessThan(Vector2 another){
        return (this.x < another.getX()) || ((this.x == another.getX()) && (this.y < another.getY()));
    }

    public static int ManhattanDistance(Vector2 v1, Vector2 v2) {
        return abs(v1.x-v2.x) + abs(v1.y-v2.y);
    }

    @Override
    public String toString(){
        return x + ";" + y;
    }
}
