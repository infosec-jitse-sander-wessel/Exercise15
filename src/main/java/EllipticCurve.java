import static java.lang.Math.pow;

/**
 * Created by Sander on 22-9-2016.
 */
public class EllipticCurve {

    private final int a;
    private final int b;
    private final int mod;

    public EllipticCurve(int a, int b, int mod) {
        this.a = a;
        this.b = b;
        this.mod = mod;
    }

    public Tuple<Long, Long> multiply(Tuple<Number, Number> p, int multiplier) {
        int additionCounter = 0;
        System.out.println(Integer.toBinaryString(multiplier));
        return new Tuple<Long, Long>(1l, 2l);
    }

    private double getSamePointM(Tuple<Double, Double> p, int a, int mod) {
        return ((3 * pow(p.x, 2) + a) * pow((2 * p.y), -1)) % mod;
    }

    private double getDifferentPointM(Tuple<Double, Double> p1, Tuple<Double, Double> p2, int mod) {
        return ((p2.y - p1.y) * pow((p2.x - p1.x), -1)) % mod;
    }

    private double x3(Tuple<Double, Double> p1, Tuple<Double, Double> p2, double m, int mod) {
        return (pow(m, 2) - p1.x - p2.x) % mod;
    }

    private double y3(Tuple<Double, Double> p1, double m, double x3, int mod) {
        return ((m * (p1.x - x3)) - p1.y) % mod;
    }

    private Tuple<Double, Double> doublePoint(Tuple<Double, Double> p, int a, int mod) {
        double m = getSamePointM(p, a, mod);
        double x3 = x3(p, p, m, mod);
        double y3 = y3(p, m, x3, mod);
        return new Tuple<>(x3, y3);
    }

    private Tuple<Double, Double> addPoints(Tuple<Double, Double> p1, Tuple<Double, Double> p2, int a, int mod) {
        double m = getDifferentPointM(p1, p2, mod);
        double x3 = x3(p1, p2, m, mod);
        double y3 = y3(p1, m, x3, mod);
        return new Tuple<>(x3, y3);
    }
}
