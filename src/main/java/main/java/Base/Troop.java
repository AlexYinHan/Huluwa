/**
 * Troop:表示一方势力的队伍，包括campMember，ScorpionMonster，Cheerer
 */
package main.java.Base;

import main.java.Characters.Cheerer;
import main.java.Characters.Creature;
import main.java.Characters.ScorpionMonster;
import main.java.Layout.FormationManager;
import main.java.Types.FormationName;
import main.java.Types.Vector2;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Troop {
    private String campName;

    private List<Creature> creatures = new ArrayList<>();
    private List<Troop> hostileTroops = new ArrayList<>();

    private List<Vector2> memberCoordinates = new ArrayList<>();
    private Vector2 anchorCoordinate;
    private Vector2 leaderCoordinate, cheererCoordinate;

    private FormationManager formationManager = new FormationManager();

    private Field field = null;
    //private Position[][] battleField;

    public Troop(){

        this.campName = null;
        this.memberCoordinates = null;
        this.leaderCoordinate = new Vector2();
        this.cheererCoordinate = new Vector2();
        this.anchorCoordinate = new Vector2(0,0);
    }

    public Troop(String campName, int anchorX, int anchorY){

        this.campName = campName;
        this.leaderCoordinate = new Vector2();
        this.cheererCoordinate = new Vector2();
        this.anchorCoordinate = new Vector2(anchorX, anchorY);
    }

    public void setAnchorCoordinate(Vector2 anchorCoordinate) {
        this.anchorCoordinate = anchorCoordinate;
    }

    public void setLeaderCoordinate(Vector2 leaderCoordinate) {
        this.leaderCoordinate = leaderCoordinate;
    }

    public void setCheererCoordinate(Vector2 cheererCoordinate) {
        this.cheererCoordinate = cheererCoordinate;
    }


    public void enterField(Field field) {
        this.field  = field;
    }

    public void paintInGraphics(Graphics g, int positionWidth, int positionHeight) {
        for(Creature creature: creatures) {
            creature.paintInGraphics(g, positionWidth, positionHeight);
        }
    }
    private void setMemberCoordinates(boolean[][] layout){
        memberCoordinates.clear();  //  变换阵型前，先将已有阵型清空

        int rowNum = layout.length;
        int colNum = layout[0].length;
        for(int i = 0; i < rowNum; i++){
            for(int j = 0; j < colNum; j++){
                if(layout[i][j]){
                    memberCoordinates.add(new Vector2(i, j));
                }
            }
        }
    }

    public void setFormation(FormationName formationName){
        boolean[][] layout = formationManager.getFormationLayoutWithName(formationName);
        this.leaderCoordinate = formationManager.getLeaderCoordinateWithName(formationName);
        this.cheererCoordinate = formationManager.getCheerCoordinateWithName(formationName);
        setMemberCoordinates(layout);
        arrange();
    }

    public Position getPositionAt(int x, int y) {
        try {
            return field.getPositions()[x][y];
        } catch (IndexOutOfBoundsException e) {
            return null;
        } catch (NullPointerException e) {
            System.out.println("Troop.getPositionAt(int, int): 该Troop未加入Field.");
            return null;
        }
    }
    public void addCreatures(Creature[] newCreatures){

        int num = newCreatures.length;
        if(num == 0)
            return;
        //IntStream.range(0, num).forEach(i -> newCreatures[i].setCampName(this.campName));
        for(Creature creature: newCreatures) {
            creature.setTroop(this);
            creature.setPosition(this.field.getPositions()[creature.getPosition().getX()][creature.getPosition().getY()]);
        }
        creatures.addAll(Arrays.asList(newCreatures));
    }

    public void addOneCreature(Creature someCreature){
        creatures.add(someCreature);
        someCreature.setPosition(this.field.getPositions()[someCreature.getPosition().getX()][someCreature.getPosition().getY()]);
        someCreature.setTroop(this);
    }

    public void act(){
        for(int i = 0 ; i < creatures.size() ; i++) {
            creatures.get(i).act();
        }
    }

    List<Thread> threads = new LinkedList<>();
    public void startActing() {
        for(Creature creature: creatures) {
            Thread thread = new Thread(creature);
            threads.add(thread);
            thread.start();
        }

    }

    public void pauseActing() {
        for(Thread thread: threads) {
            thread.interrupt();
        }
    }
    private void arrange(){
        if(this.field == null) {
            System.out.println("Troop.arrange(): 该Troop未加入Field.");
            return;
        }

        int indexOfMemCoor = 0;
        for(int i = 0 ; i < creatures.size() ; i++) {

            Creature someCreature = creatures.get(i);

            Vector2 coordinateInField = new Vector2();
            if(someCreature instanceof ScorpionMonster){
                coordinateInField = this.leaderCoordinate.add(this.anchorCoordinate);
            }else if(someCreature instanceof Cheerer){
                coordinateInField = this.cheererCoordinate.add(this.anchorCoordinate);
            }else { //  campMember
                coordinateInField = this.memberCoordinates.get(indexOfMemCoor).add(this.anchorCoordinate);

                if(indexOfMemCoor < memberCoordinates.size() - 1)
                    indexOfMemCoor ++;
                else
                    indexOfMemCoor = 0;
            }

            someCreature.moveTo(this.field.getPositions()[coordinateInField.getX()][coordinateInField.getY()]);
        }
    }

    public String getCampName() {
        return campName;
    }

    public void askFieldToRepaint() {
        try {
            field.repaint();
        } catch (NullPointerException e) {
            System.out.println("Troop.askFieldToRepaint(): 该Troop未加入Field.");
        }
    }

    public void declareWarTo(Troop troop) {
        hostileTroops.add(troop);
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public List<Creature> getAliveCreatures() {
        return creatures.stream().filter(
                (creature) -> creature.alive
        ).collect(Collectors.toList());
    }

    public List<Creature> getAliveHostileCreatures() {
        List<Creature> hostileCreatures = new LinkedList<>();
        for(Troop troop: hostileTroops) {
            hostileCreatures.addAll(
                    troop.getCreatures().stream().filter(
                            (creature) -> creature.alive
                    ).collect(Collectors.toList()));
        }
        return hostileCreatures;
    }

//    public void askFieldToRecord(long time, Creature creature, Vector2 target, CreatureState actionType, int duration) {
//        if (field != null) {
//            field.addOneRecord(time, creature, target, actionType, duration);
//        }
//    }

    public void askFieldToRecord(long time, String imageName, int dx1, int dy1, int dx2 ,int dy2, int sx1, int sy1, int sx2, int sy2) {
        if (field != null) {
            field.addOneRecord(time, imageName, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2);
        }
    }

    public void announceVictory() {
        field.victory();
    }
}
