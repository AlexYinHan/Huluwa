package main.java.Layout;

import main.java.Types.FormationName;
import main.java.Types.Vector2;

import java.util.HashMap;

/**
 * 管理所有的阵型，包括每种阵型的默认Leader位置，默认Cheerer位置，阵型布局（二维布尔数组表示）
 * 暂时全用成员变量来存储阵型
 */
public class FormationManager {
    private HashMap<FormationName, boolean[][]> formationLayouts = new HashMap<>();
    private HashMap<FormationName, Vector2> leaderCoordinates = new HashMap<>();
    private HashMap<FormationName, Vector2> cheererCoordinates = new HashMap<>();


    public FormationManager(){

        /*录入阵型，领导者和加油者的位置*/

        for(int i = 0; i < FormationName.values().length; i++){
            this.formationLayouts.put(FormationName.values()[i], FormationsLayouts[i]);
            this.leaderCoordinates.put(FormationName.values()[i], LeaderCoordinates[i]);
            this.cheererCoordinates.put(FormationName.values()[i], CheererCoordinates[i]);
        }

    }

    public boolean[][] getFormationLayoutWithName(FormationName formationName){
        return formationLayouts.get(formationName);
    }
    public Vector2 getLeaderCoordinateWithName(FormationName formationName){
        return leaderCoordinates.get(formationName);
    }
    public Vector2 getCheerCoordinateWithName(FormationName formationName){
        return cheererCoordinates.get(formationName);
    }

    private static boolean[][][] FormationsLayouts={
            {//鹤翼
                    {false, false, false, false, false, false, false},
                    {true, false, false, false, false, false, true},
                    {false, true, false, false, false, true, false},
                    {false, false, true, false, true, false, false},
                    {false, false, false, true, false, false, false}
            },
            {//雁行
                    {false, false, false, false, true},
                    {false, false, false, true, false},
                    {false, false, true, false, false},
                    {false, true, false, false, false},
                    {true, false, false, false, false}
            },
            {//衡轭
                    {false, false, true, false, false},
                    {false, true, false, false, false},
                    {false, false, true, false, false},
                    {false, true, false, false, false},
                    {false, false, true, false, false}
            },
            {//长蛇
                    {false, false, true, false, false},
                    {false, false, true, false, false},
                    {false, false, true, false, false},
                    {false, false, true, false, false},
                    {false, false, true, false, false},
                    {false, false, true, false, false},
                    {false, false, true, false, false}
            },
            {//鱼鳞
                    {false, false, true, false, false},
                    {false, false, false, true, false},
                    {false, false, true, true, true},
                    {false, true, true, true, true},
                    {false, false, true, false, false}
            },
            {//方圆
                    {false, false, true, false, false},
                    {false, true, false, true, false},
                    {true, false, false, false, true},
                    {false, true, false, true, false},
                    {false, false, true, false, false}
            },
            {//偃月
                    {false, false, false, false, true},
                    {false, false, true, true, false},
                    {false, true, true, true, false},
                    {false, false, true, true, false},
                    {false, false, false, false, true}
            },
            {//锋矢
                    {false, false, true, false, false},
                    {false, true, true, true, false},
                    {true, false, true, false, true},
                    {false, false, true, false, false},
                    {false, false, true, false, false}
            }
    };

    private static Vector2[] LeaderCoordinates = {
            new Vector2(0, 0),
            new Vector2(0, 0),
            new Vector2(0, 0),
            new Vector2(0, 1),
            new Vector2(0, 0),
            new Vector2(0, 0),
            new Vector2(0, 0),
            new Vector2(0, 0)
    };
    private static Vector2[] CheererCoordinates = {
            new Vector2(2, 5),
            new Vector2(2, 5),
            new Vector2(2, 5),
            new Vector2(2, 3),
            new Vector2(2, 5),
            new Vector2(2, 5),
            new Vector2(2, 5),
            new Vector2(2, 5)
    };
}



