package main.java.Characters;

import main.java.Base.Position;
import main.java.Types.Vector2;

import java.util.Collections;
import java.util.List;

/**
 * 实现了一些与寻路、获取战况有关的方法。
 * 以组合的方式添加到一个creature上。
 * 在creature初始化时，将其设置为这个寻路组件的creatureClient。
 */
public class NavigationCreatureComponent extends CreatureComponent {

    public NavigationCreatureComponent(Creature creature) {
        creatureClient = creature;
    }

    public List<Creature> getAliveCampCreature() {
        return creatureClient.troop.getAliveCreatures();
    }

    public List<Creature> getAliveHostileCreatures() {
        return creatureClient.troop.getAliveHostileCreatures();
    }

    public Position getPositionOfNearestAliveHostileCreature() {
        try {
            List<Creature> hostileCreatures = creatureClient.troop.getAliveHostileCreatures();
            if (hostileCreatures == null || hostileCreatures.size() == 0) {
                return null;
            }
            Creature nearestHostileCreature = Collections.min(hostileCreatures, (c1, c2) -> {
                int distanceToC1 = Vector2.ManhattanDistance(creatureClient.position.getCoordinate(), c1.position.getCoordinate());
                int distanceToC2 = Vector2.ManhattanDistance(creatureClient.position.getCoordinate(), c2.position.getCoordinate());
                return distanceToC1 - distanceToC2;
            });
            Position destination = null;
            if (nearestHostileCreature != null) {
                destination = nearestHostileCreature.position;
            } else {
                //System.out.println("葫芦娃：" + this.seniority + "寻路时找不到最近的敌人。");
            }
            return destination;
        } catch (NullPointerException e) {
            System.out.println("NavigationCreatureComponent.getPositionOfNearestAliveHostileCreature(): creatureClient可能未初始化。");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Vector2[] getPossibleNextPositionVectors(Position destination) {
        try {

            // 判断是否可以去
            if (destination == null) { // 一个无效的position，可能是坐标越界，或者找不到最近的敌人

                return null;
            }

            // 有了目的地后，根据目的地的方向确定下一个position的坐标
            Vector2 direction = destination.getCoordinate().minus(creatureClient.position.getCoordinate()).getDirection();
            int dx = direction.getX(), dy = direction.getY();
            if (dx == 0 && dy == 0) {
                return null;
            }
            int px = creatureClient.position.getX(), py = creatureClient.position.getY();
            //Vector2[] possibleNextPositionVectors;
            if (dx == 0) {
                return new Vector2[]{
                        new Vector2(px + 0, py + dy),
                        new Vector2(px + 1, py + dy),
                        new Vector2(px - 1, py + dy),
                        new Vector2(px + 1, py + 0),
                        new Vector2(px - 1, py + 0),
                        new Vector2(px + 1, py - dy),
                        new Vector2(px - 1, py - dy),
                        new Vector2(px + 0, py - dy),
                };
            } else if (dy == 0) {
                return new Vector2[]{
                        new Vector2(px + dx, py + 0),
                        new Vector2(px + dx, py + 1),
                        new Vector2(px + dx, py - 1),
                        new Vector2(px + 0, py + 1),
                        new Vector2(px + 0, py - 1),
                        new Vector2(px - dx, py + 1),
                        new Vector2(px - dx, py - 1),
                        new Vector2(px - dx, py + 0),
                };
            } else {
                return new Vector2[]{
                        new Vector2(px + dx, py + dy),
                        new Vector2(px + 0 , py + dy),
                        new Vector2(px + dx, py + 0),
                        new Vector2(px - dx, py + dy),
                        new Vector2(px + dx, py - dy),
                        new Vector2(px - dx, py + 0),
                        new Vector2(px + 0, py - dy),
                        new Vector2(px - dx, py - dy),
                };
            }
        } catch (NullPointerException e) {
            System.out.println("NavigationCreatureComponent.getPositionOfNearestAliveHostileCreature(): creatureClient可能未初始化。");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
