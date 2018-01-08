package main.java.Base;

import main.java.Characters.*;
import main.java.Types.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FieldRecordDelegate {
    private static int count = 0;
    private Field field;
static class RecordableAction implements Serializable {
        long time;                  // 在time毫秒后
        Creature creature;          // 场景中的一个生物
        Vector2 targetCoor;         // 朝着这个坐标
        CreatureState actionType;   // 执行了某一个动作
        int duration;               // 动画维持了duration个周期

        public RecordableAction(long time, Creature creature, Vector2 targetCoor, CreatureState actionType, int duration) {
            this.time = time;
            this.creature = creature;
            this.targetCoor = targetCoor;
            this.actionType = actionType;
            this.duration = duration;
        }
}

    FieldRecordDelegate(Field field) {
        this.field = field;
    }
    private List<RecordableAction> recordedActions = new ArrayList<>();
    void addOneRecord(RecordableAction recordableAction) {
        recordedActions.add(recordableAction);
    }

    public void saveToFile(String path) {
//        File file = new File(path);
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            //FileOutputStream in = new FileOutputStream(file);
            List<Creature> creatures = new LinkedList<>();
            for (Troop troop: field.troops) {
                creatures.addAll(troop.getCreatures());
            }

            try {
                FileWriter fileWriter = new FileWriter(path, false);
                fileWriter.write(""); //先将已有文件清空
                fileWriter.close();
                fileWriter = new FileWriter(path, true);
                for (Creature creature: creatures) {
                    String content = "*;";
                    content += creature.initInfo();
                    content += "\n";
                    System.out.print(content);
                    fileWriter.write(content);
                }
                for (RecordableAction recordableAction: recordedActions) {
                    if (recordableAction != null) {
                        String content = "+;";
                        content += recordableAction.time + ";";
                        content += creatures.indexOf(recordableAction.creature) + ";";
                        content += recordableAction.targetCoor + ";";
                        content += recordableAction.actionType.name() + ";";
                        content += recordableAction.duration + ";";
                        content += "\n";
                        System.out.print(content);
                        fileWriter.write(content);
                    }

                }
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<RecordableAction> readFromFile(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            List<Creature> creatures = new LinkedList<>();
            List<RecordableAction> actionsInFile = new LinkedList<>();

            Troop powerOfHuluwa = new Troop("葫芦娃", 2, 0);
            Troop powerOfYaojing = new Troop("妖精", 8, 6);
            field.removeAllTroop();
            field.addTroop(powerOfHuluwa);
            field.addTroop(powerOfYaojing);

            String content;
            while ((content = bufferedReader.readLine()) != null) {
                String[] info = content.split(";");
                switch (info[0]) {
                    case "*":
                        Creature creature;
                        switch (info[1]) {
                            case "Huluwa":
                                creature = new Huluwa(COLOR.valueOf(info[2]), SENIORITY.valueOf(info[3]));
                                powerOfHuluwa.addOneCreature(creature);
                                break;
                            case "Louluo":
                                creature = new Louluo(TianGan.valueOf(info[2]), Integer.parseInt(info[3]));
                                powerOfYaojing.addOneCreature(creature);
                                break;
                            case "Grandpa":
                                creature = new Grandpa("爷爷");
                                powerOfHuluwa.addOneCreature(creature);
                                break;
                            case "SnakeMonster":
                                creature = new SnakeMonster("蛇精");
                                powerOfYaojing.addOneCreature(creature);
                                break;
                            case "ScorpionMonster":
                                creature = new ScorpionMonster("蝎子精");
                                powerOfYaojing.addOneCreature(creature);
                                break;
                            default:
                                creature = null;
                                break;
                        }
                        if (creature != null) {
                            creatures.add(creature);
                        }
                        break;
                    case "+":
                        actionsInFile.add(
                                new RecordableAction(
                                        Long.parseLong(info[1]),
                                        creatures.get(Integer.parseInt(info[2])),
                                        new Vector2(Integer.parseInt(info[3]), Integer.parseInt(info[4])),
                                        CreatureState.valueOf(info[5]),
                                        Integer.parseInt(info[6])
                                )
                        );
                        break;
                    default: break;
                }
            }

            powerOfHuluwa.setFormation(FormationName.锋矢);
            powerOfYaojing.setFormation(FormationName.锋矢);
            fileReader.close();


            return actionsInFile;
        }   catch (FileNotFoundException e) {
            System.out.println("找不到文件。");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //File file = new File("");
}
