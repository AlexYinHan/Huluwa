import main.java.Base.Field;
import main.java.Base.Troop;
import main.java.Characters.Huluwa;
import main.java.Types.COLOR;
import main.java.Types.FormationName;
import main.java.Types.SENIORITY;
import main.java.Types.Vector2;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class testField {
    @Test
    public void testField() {

        //葫芦娃
        Huluwa[] HuluBrothers = new Huluwa[7];
        for (int i = 0; i < HuluBrothers.length; i++) {
            HuluBrothers[i] = new Huluwa(COLOR.values()[i], SENIORITY.values()[i]);
        }

        Field field = new Field(11);

        Troop powerOfHuluwa = new Troop("葫芦娃", 0, 0);

        field.addTroop(powerOfHuluwa);

        powerOfHuluwa.addCreatures(HuluBrothers);

        powerOfHuluwa.setFormation(FormationName.长蛇);

        Vector2[] possibleDes = {
                new Vector2(9, 9),
        };
        assertEquals(true, HuluBrothers[0].attemptToMoveTo(possibleDes));

        Vector2[] possibleDes2 = {
                new Vector2(-1, -1),
        };
        assertEquals(false, HuluBrothers[0].attemptToMoveTo(possibleDes2));

    }

}
