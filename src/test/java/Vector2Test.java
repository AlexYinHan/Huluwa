import main.java.Types.Vector2;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Vector2Test {
    @Test
    public void testGetDirection() throws Exception {
        Vector2 testVector = new Vector2(3, -5);
        assertEquals(1, testVector.getDirection().getX());
        assertEquals(-1, testVector.getDirection().getY());

        testVector = new Vector2(3, 0);
        assertEquals(1, testVector.getDirection().getX());
        assertEquals(0, testVector.getDirection().getY());
    }

    @Test
    public void testManhattanDistance() throws Exception {
        Vector2 v1 = new Vector2(8, 9), v2 = new Vector2(1, 1);
        assertEquals(15, Vector2.ManhattanDistance(v1, v2));
    }

}
