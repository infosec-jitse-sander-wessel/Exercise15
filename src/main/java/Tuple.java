/**
 * Created by Sander on 22-9-2016.
 */
public class Tuple<X extends Number, Y extends Number> {

    final X x;
    final Y y;

    Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
