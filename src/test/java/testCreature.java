import main.java.Characters.Creature;
import main.java.Characters.Huluwa;
import main.java.Types.COLOR;
import main.java.Types.SENIORITY;
import main.java.Types.Vector2;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class testCreature {
    @Test(expected = Exception.class)
    public void attemptToMoveTo() throws Exception {
        Creature creature = new Huluwa(COLOR.赤, SENIORITY.一);
        Vector2[] possibleDes = {
                new Vector2(0, 1),
        };
        assertEquals(false, creature.attemptToMoveTo(possibleDes));
        fail("空的position");
    }
}
