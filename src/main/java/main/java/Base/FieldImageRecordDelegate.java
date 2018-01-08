package main.java.Base;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FieldImageRecordDelegate {
    private Field field;
    private List<ImageRecord> recordedImages = new LinkedList<>();

    public FieldImageRecordDelegate(Field field) {
        this.field = field;
    }

    void addOneRecord(ImageRecord imageRecord) {
        recordedImages.add(imageRecord);
    }

    public void saveToFile(String path) {
        File file =new File(path);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut=new ObjectOutputStream(out);
            objOut.writeObject(recordedImages);
            objOut.flush();
            objOut.close();
            System.out.println("save object success!");
        } catch (IOException e) {
            System.out.println("save object failed");
            e.printStackTrace();
        }

    }

    public List<ImageRecord> readFromFile(String path) {
        File file =new File(path);

        return readFromFile(file);
    }

    public List<ImageRecord> readFromFile(File file) {

        List<ImageRecord> records = null;
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn=new ObjectInputStream(in);
            records = (List<ImageRecord>)objIn.readObject();
            objIn.close();
            System.out.println("read object success!");
        } catch (IOException e) {
            System.out.println("read object failed");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return records;
    }

}

class ImageRecord implements Serializable{
    public long time;
    //private Image image;
    public String imageName;
    public int dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2;

    public ImageRecord(long time, String imageName, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
        this.time = time;
        this.imageName = imageName;
        this.dx1 = dx1;
        this.dy1 = dy1;
        this.dx2 = dx2;
        this.dy2 = dy2;
        this.sx1 = sx1;
        this.sy1 = sy1;
        this.sx2 = sx2;
        this.sy2 = sy2;
    }
}
