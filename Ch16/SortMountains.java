import java.util.*;

class Mountain {
    
    private String name;
    private Integer height;

    Mountain(String n, int h) {
        name = n;
        height = h;
    }

    public String getName() {
        return name;
    }

    public Integer getHeight() {
        return height;
    }

    public String toString() {
        return name + " " + height;
    }
}

public class SortMountains {

    LinkedList<Mountain> mtn = new LinkedList<Mountain>();

    class NameCompare implements Comparator<Mountain> {

        public int compare(Mountain one, Mountain two) {
            return one.getName().compareTo(two.getName());
        }
    }

    class HeightCompare implements Comparator<Mountain> {

        public int compare(Mountain one, Mountain two) {
            return one.getHeight().compareTo(two.getHeight());
        }
    }

    public void go() {
        NameCompare nc = new NameCompare();
        HeightCompare hc = new HeightCompare();

        mtn.add(new Mountain("Longs", 14255));
        mtn.add(new Mountain("Elbert", 14433));
        mtn.add(new Mountain("Maroon", 14156));
        mtn.add(new Mountain("Castle", 14265));
        System.out.println("As entered: \n" + mtn);
        Collections.sort(mtn, nc);
        System.out.println("By name: \n" + mtn);
        Collections.sort(mtn, hc);
        System.out.println("By height: \n" + mtn);
    }

    public static void main(String[] args) {
        new SortMountains().go();
    }
}