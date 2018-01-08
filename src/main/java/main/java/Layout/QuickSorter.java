package main.java.Layout;

import main.java.Characters.Comparable;
import main.java.Base.Position;

public class QuickSorter implements Sorter {
    @Override
    public void sort(Queue queue) {
        Position[] positions = queue.getPositions();
        quickSort(positions, 0, positions.length -1);
    }

    private void quickSort(Position[] positions, int left, int right){
        if(left < right){

            /*partition*/
            int low = left;
            int high = right;
            while(low < high){
                while(low < high && !((Comparable)(positions[left].getHolder())).biggerThan((Comparable)(positions[high].getHolder())) ){
                    high--;
                }
                while( low < high &&  !((Comparable)(positions[low].getHolder())).biggerThan((Comparable)(positions[left].getHolder())) ){
                    low++;
                }
                positions[high].getHolder().changePositionWith(positions[low].getHolder());
            }
            positions[low].getHolder().changePositionWith(positions[left].getHolder());

            /*递归排序*/
            quickSort(positions,left,low-1);
            quickSort(positions,low+1,right);
        }

    }
}
