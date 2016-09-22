import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

/**
 * Created by Sander on 22-9-2016.
 */
public class EllipticCurve {

    private final int a;
    private final int b;
    private int mod;

    public EllipticCurve(int a, int b, int mod) {
        this.a = a;
        this.b = b;
        this.mod = mod;
    }

    public Tuple<Double, Double> multiply(Tuple<Double, Double> p, int multiplier) throws Exception {
        int additionCounter = 0;
        List<Boolean> binary = getIntegerBinaryBooleans(multiplier);

        Tuple<Double, Double> first = new Tuple<>(1.0, 4.0);
        Tuple<Double, Double> second = new Tuple<>(3.0, 1.0);
        mod = 5;
        System.out.println("Book points: " + addPoints(first, second));

        List<Tuple<Double, Double>> tuples = new ArrayList<>();
        tuples.add(p);
        for (int i = 1; i < binary.size(); i++) {
            tuples.add(doublePoint(tuples.get(i - 1)));
        }

        List<Tuple<Double, Double>> toAdd = new ArrayList<>();
        for (int i = 0; i < binary.size(); i++) {
            if (binary.get(i)) {
                toAdd.add(tuples.get(tuples.size() - 1 - i));
            }
        }
        System.out.println("Multiplier: " + multiplier);
        System.out.println(binary);
        System.out.println(tuples);
        System.out.println(toAdd);

        Tuple<Double, Double> result = toAdd.stream()
                .reduce(this::addPoints)
                .orElseThrow(() -> new Exception("Shit happened"));
        return result;
    }

    private double getSamePointM(Tuple<Double, Double> p) {
        double first = (3 * pow(p.x, 2)) + a;
        double second = pow((2 * p.y), -1);
        return (first * second) % mod;
    }

    private double getDifferentPointM(Tuple<Double, Double> p1, Tuple<Double, Double> p2) {
        double first = p2.y - p1.y;
        double second = pow(p2.x - p1.x, -1);
        return (first * second) % mod;
    }

    private double x3(Tuple<Double, Double> p1, Tuple<Double, Double> p2, double m) {
        return (pow(m, 2) - p1.x - p2.x) % mod;
    }

    private double y3(Tuple<Double, Double> p1, double m, double x3) {
        return ((m * (p1.x - x3)) - p1.y) % mod;
    }

    private Tuple<Double, Double> doublePoint(Tuple<Double, Double> p) {
        double m = getSamePointM(p);
        double x3 = x3(p, p, m);
        double y3 = y3(p, m, x3);
        return new Tuple<>(x3, y3);
    }

    private Tuple<Double, Double> addPoints(Tuple<Double, Double> p1, Tuple<Double, Double> p2) {
        double m = getDifferentPointM(p1, p2);
        double x3 = x3(p1, p2, m);
        double y3 = y3(p1, m, x3);
        return new Tuple<>(x3, y3);
    }

    private List<Boolean> getIntegerBinaryBooleans(int value) {
        return Integer.toBinaryString(value)
                .chars()
                .mapToObj(e -> e == '1')
                .collect(Collectors.toList());
    }
}
