package main.java.Base;


import main.java.Characters.*;
import main.java.Layout.Queue;
import main.java.Types.*;
import main.java.Layout.*;
import main.java.Types.FormationName;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.List;

public class Field extends JPanel{
    private int fieldWidth, fieldHeight, positionRowCount, positionColCount;
    private Position[][] positions;

    private final static String[] imagesNames = {
         "background.png",
         "grandpa_idle.png",
         "healthBar.png", "healthBarFill.png",
         "hero_attack.png", "hero_idle.png", "hero_moving.png",
         "louluo0_attack.png", "louluo0_idle.png", "louluo0_moving.png",
         "louluo1_attack.png", "louluo1_idle.png", "louluo1_moving.png",
         "none.png",
         "shejing_idle.png",
         "square.png",
         "talk_bubble_blue.png", "talk_bubble_white.png",
         "tomb.png",
         "xiezi_attack.png", "xiezi_idle.png", "xiezi_moving.png"
    };
    Map<String, Image> imageMap = new HashMap<String, Image>() {{
        for (String imageName: imagesNames) {
            put(imageName, new ImageIcon(this.getClass().getClassLoader().getResource("Image/" + imageName)).getImage());
        }
    }};

    List<Troop> troops = new ArrayList<>();

    private Image backgroundImage;
    private Image positionSquareImage;

    private boolean isGridViewVisible = true;

    private FieldImageRecordDelegate fieldImageRecordDelegate = new FieldImageRecordDelegate(this);

    private long startTime = 0;
    private double replaySlowRation = 0.7;
    private boolean isReplaying = false;
    private boolean gameStarted  = false;
    private List<ImageRecord> recordsInFile = null;

    public Field(int fieldWidth, int fieldHeight, int positionRowCount, int positionColCount) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.positionRowCount = positionRowCount;
        this.positionColCount = positionColCount;

        positions = new Position[positionRowCount][positionColCount];
        for(int i = 0; i < positionRowCount; i++) {
            for (int j = 0; j < positionColCount; j++) {
                positions[i][j] = new Position(i, j, this);
            }
        }

        URL loc1 = this.getClass().getClassLoader().getResource("Image/background.png");
        backgroundImage = new ImageIcon(loc1).getImage();

        URL loc2 = this.getClass().getClassLoader().getResource("Image/square.png");
        positionSquareImage = new ImageIcon(loc2).getImage();

        addKeyListener(new TAdapter());
        setFocusable(true);

        Timer repaintTimer = new Timer(50, (a)->{
            repaint();
        });
        repaintTimer.start();
    }
    public Field(int fieldWidth){
        this.fieldWidth = fieldWidth;

        positions = new Position[fieldWidth][fieldWidth];

        for(int i = 0; i < fieldWidth; i++){
            for(int j = 0; j < fieldWidth; j++)
                positions[i][j] = new Position(i, j, this);
        }
    }

    public Position[][] getPositions(){
        return this.positions;
    }
    public void addTroop(Troop newTroop){
        newTroop.enterField(this);
        troops.add(newTroop);
    }

    public void removeAllTroop() {
        troops.clear();
    }

    public int getBoardWidth() {
        return this.fieldWidth;
    }

    public int getBoardHeight() {
        return this.fieldHeight;
    }

    private void buildWorld(Graphics g) {
        // background
        g.drawImage(backgroundImage, 0, 0, this.getBoardWidth(), this.getBoardHeight(), this);

        if (isReplaying) {
            int positionWidth = fieldWidth / positionColCount, positionHeight = fieldHeight / positionRowCount;
            if (isGridViewVisible) {
                for (int i = 0; i < positionRowCount; i++) {
                    for (int j = 0; j < positionColCount; j++) {
                        g.drawImage(positionSquareImage,
                                j * positionWidth, i * positionHeight,
                                positionWidth, positionHeight,
                                this);
                    }
                }

            }
            long time = System.currentTimeMillis() - startTime;
            List<ImageRecord> temp = new LinkedList<>();
            temp.addAll(recordsInFile);
            for (ImageRecord imageRecord: recordsInFile) {
                if (time >= imageRecord.time*replaySlowRation) { //该显示这个图像了
                    Image image = imageMap.get(imageRecord.imageName);
                    if (image == null) { // 显示文字
                        g.drawString(
                                imageRecord.imageName,
                                imageRecord.dx1, imageRecord.dy1
                        );
                    } else {
                        g.drawImage(
                                imageMap.get(imageRecord.imageName),
                                imageRecord.dx1, imageRecord.dy1,
                                imageRecord.dx2, imageRecord.dy2,
                                imageRecord.sx1, imageRecord.sy1,
                                imageRecord.sx2, imageRecord.sy2,
                                null
                        );
                    }
                    temp.remove(imageRecord);
//                    System.out.println(imageRecord.imageName);
//                    System.out.println(imageRecord.dx1 + "'" + imageRecord.dy1);
//                    System.out.println(imageRecord.dx2 + "'" + imageRecord.dy2);
//                    System.out.println(imageRecord.sx1 + "'" + imageRecord.sy1);
//                    System.out.println(imageRecord.sx2 + "'" + imageRecord.sy2);
                }
            }
            recordsInFile.clear();
            recordsInFile.addAll(temp);
        } else {
            int positionWidth = fieldWidth / positionColCount, positionHeight = fieldHeight / positionRowCount;
            if (isGridViewVisible) {
                for (int i = 0; i < positionRowCount; i++) {
                    for (int j = 0; j < positionColCount; j++) {
                        g.drawImage(positionSquareImage,
                                j * positionWidth, i * positionHeight,
                                positionWidth, positionHeight,
                                this);
                    }
                }

            }
            for (Troop troop : troops) {
                troop.paintInGraphics(g, positionWidth, positionHeight);
            }
        }

    }


    public void addOneRecord(long time, String imageName, int dx1, int dy1, int dx2 ,int dy2, int sx1, int sy1, int sx2, int sy2) {
        if (gameStarted) {
            fieldImageRecordDelegate.addOneRecord(new ImageRecord(time - startTime, imageName, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2));
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        buildWorld(g);
    }
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            switch (key) {
                case KeyEvent.VK_L:
                    if (gameStarted) {
                        break;
                    }
                    JFileChooser jfc=new JFileChooser();
                    jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
                    jfc.showDialog(new JLabel(), "选择");
                    File file=jfc.getSelectedFile();
                    if (file != null) {

                        recordsInFile = fieldImageRecordDelegate.readFromFile(file);
                        if (recordsInFile != null) {
                            isReplaying = true;
                            startTime = System.currentTimeMillis();
                        } else {
                            isReplaying = false;
                            System.out.println("文件格式错误。");
                        }

                    } else {
                        System.out.println("未选择文件。");
                    }

                    break;
                case KeyEvent.VK_SPACE:
                    if (gameStarted == false) {
                        for (Troop troop : troops) {
                            troop.setFormation(FormationName.锋矢);
                        }

//                    Timer timer1 = new Timer(50, (a)->{
//                        repaint();
//                    });
//                    timer1.start();
                        for (Troop troop : troops) {
                            troop.startActing();
                        }
                        startTime = System.currentTimeMillis();
                        gameStarted = true;
                    }
                    break;
//                case KeyEvent.VK_T:
//                    for (Troop troop : troops) {
//                        troop.startActing();
//                    }
//                    break;
//                case KeyEvent.VK_E:
//                    for(Troop troop: troops) {
//                        troop.pauseActing();
//                    }
//                    break;
//                case KeyEvent.VK_R:
//                    saveRecord();
//                    break;
//
//                case KeyEvent.VK_A:
//
//                    recordsInFile = fieldImageRecordDelegate.readFromFile("record.txt");
//                    if (recordsInFile != null) {
//                        isReplaying = true;
//                        startTime = System.currentTimeMillis();
//                    } else {
//                        isReplaying = false;
//                        System.out.println("文件格式错误。");
//                    }
//
//                    break;
            }

            //repaint();
        }
    }

    public void rawShow(){ //  依次显示每个position
        System.out.println();
        for(int i = 0; i < fieldWidth; i++){
            for(int j = 0; j < fieldWidth; j++){
                if(positions[i][j].getHolder() == null){
                    System.out.print("_________ ");
                }else{
                    System.out.print(positions[i][j].getHolder().toString());
                }
            }
            System.out.println();
        }
        System.out.println();

        act();
    }

    public void act(){
        for(int i = 0 ; i < troops.size() ; i++) {
            troops.get(i).act();
        }
    }

    public void victory() {
        gameStarted = false;
        saveRecord();
        JOptionPane.showMessageDialog(null, "战斗结束，回放已保存至 record.txt");
    }
    public void saveRecord() {
        fieldImageRecordDelegate.saveToFile("record.txt");
    }

    public static void main(String[] args) {
        /* 初始化所有生物 */
        //葫芦娃
        Huluwa[] HuluBrothers = new Huluwa[7];
        for (int i = 0; i < HuluBrothers.length; i++) {
            HuluBrothers[i] = new Huluwa(COLOR.values()[i], SENIORITY.values()[i]);
        }
        // 爷爷
        Cheerer YeYe = new Cheerer("爷爷");
        // 蛇精
        Cheerer SheJing = new Cheerer("蛇精");
        // 小喽啰
        Louluo[] lackeys = new Louluo[10];
        for (int i = 0; i < lackeys.length; i++) {
            lackeys[i] = new Louluo(TianGan.values()[i]);
        }
        // 蝎子精
        ScorpionMonster XieZiJing = new ScorpionMonster("蝎子精");

        /* 初始化场地，11*11方阵，可容纳2方势力 */
        Field field = new Field(11);

        /* 初始化各方势力 */
        Troop powerOfHuluwa = new Troop("葫芦娃", 0, -1);
        Troop powerOfYaojing = new Troop("妖精", 0, 4);

        /* 各方势力登场 */
        field.addTroop(powerOfHuluwa);
        field.addTroop(powerOfYaojing);


        /* 所有人物加入势力 */
        powerOfHuluwa.addCreatures(HuluBrothers);
        powerOfHuluwa.addOneCreature(YeYe);

        powerOfYaojing.addCreatures(lackeys);
        powerOfYaojing.addOneCreature(SheJing);
        powerOfYaojing.addOneCreature(XieZiJing);


        /* 各方势力布阵 */
        powerOfHuluwa.setFormation(FormationName.长蛇);
        powerOfYaojing.setFormation(FormationName.锋矢);

        /* 使用queue管理待排序的成员 */
        Queue queue = new Queue(HuluBrothers);

        queue.shuffle();    //  打乱
        field.rawShow();

        new QuickSorter().sort(queue); //  快速排序
        field.rawShow();

        /* 妖精变换阵型 */
        powerOfYaojing.setFormation(FormationName.偃月);

        field.rawShow();

    }


}
