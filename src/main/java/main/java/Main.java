package main.java;

import main.java.Characters.*;
import main.java.Base.Troop;
import main.java.Base.Field;
import main.java.Types.COLOR;
import main.java.Types.SENIORITY;
import main.java.Types.TianGan;


public class Main {
    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow(1000, 850);

        Field field = new Field(1000, 800, 15, 12);

        //葫芦娃
        Huluwa[] HuluBrothers = new Huluwa[7];
        for (int i = 0; i < HuluBrothers.length; i++) {
            HuluBrothers[i] = new Huluwa(COLOR.values()[i], SENIORITY.values()[i]);
        }
        // 小喽啰
        Louluo[] lackeys = new Louluo[7];
        for (int i = 0; i < lackeys.length; i++) {
            lackeys[i] = new Louluo(TianGan.values()[i%TianGan.values().length]);
        }
        // 爷爷
        Grandpa YeYe = new Grandpa("爷爷");
        // 蛇精
        SnakeMonster SheJing = new SnakeMonster("蛇精");
        // 蝎子精
        ScorpionMonster XieZiJing = new ScorpionMonster("蝎子精");

        /* 初始化各方势力 */
        Troop powerOfHuluwa = new Troop("葫芦娃", 2, 0);
        Troop powerOfYaojing = new Troop("妖精", 8, 6);
        /* 各方势力登场 */
        field.addTroop(powerOfHuluwa);
        field.addTroop(powerOfYaojing);

        /* 所有人物加入势力 */
        powerOfHuluwa.addCreatures(HuluBrothers);
        powerOfHuluwa.addOneCreature(YeYe);
        powerOfYaojing.addCreatures(lackeys);
        powerOfYaojing.addOneCreature(SheJing);
        powerOfYaojing.addOneCreature(XieZiJing);

        /* 各方势力宣战 */
        powerOfHuluwa.declareWarTo(powerOfYaojing);
        powerOfYaojing.declareWarTo(powerOfHuluwa);

        gameWindow.add(field);
        gameWindow.setVisible(true);
        gameWindow.setResizable(false);

    }
}
