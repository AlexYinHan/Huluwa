import main.java.Base.Field;
import main.java.Base.Troop;
import main.java.Characters.Huluwa;
import main.java.Layout.Queue;
import main.java.Layout.QuickSorter;
import main.java.Types.COLOR;
import main.java.Types.SENIORITY;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class testSorter {
    @Test
    public void testSort() {
        //葫芦娃
        Huluwa[] HuluBrothers = new Huluwa[7];
        for (int i = 0; i < HuluBrothers.length; i++) {
            HuluBrothers[i] = new Huluwa(COLOR.values()[i], SENIORITY.values()[i]);
        }

        Field field = new Field(11);

        Troop powerOfHuluwa = new Troop("葫芦娃", 0, 0);

        field.addTroop(powerOfHuluwa);

        powerOfHuluwa.addCreatures(HuluBrothers);

        /* 使用queue管理待排序的成员 */
        Queue queue = new Queue(HuluBrothers);

        queue.shuffle();    //  打乱

        new QuickSorter().sort(queue);

        for (int i = 0; i < HuluBrothers.length - 1; i++) {
            assertEquals(false, HuluBrothers[i+1].getPosition().getCoordinate().lessThan(HuluBrothers[i].getPosition().getCoordinate()));
        }
    }
}
