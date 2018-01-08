package main.java.Layout;

import main.java.Characters.Comparable;
import main.java.Base.Position;

public class BubbleSorter implements Sorter {
    @Override
    public void sort(Queue queue) {
        Position[] positions = queue.getPositions();

        for (int i = 0; i < positions.length - 1; i++) {
            for (int j = 0; j < positions.length - 1 - i; j++) {
                if (((Comparable) (positions[j].getHolder())).biggerThan((Comparable) (positions[j + 1].getHolder()))) {
                    positions[j].getHolder().changePositionWith(positions[j+1].getHolder());
                }
            }
        }
    }
}
