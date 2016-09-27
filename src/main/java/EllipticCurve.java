import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

/**
 * Created by Sander on 22-9-2016.
 */
public class EllipticCurve {

    private int a;
    private int b;
    private int mod;

    public EllipticCurve(int a, int b, int mod) {
        this.a = a;
        this.b = b;
        this.mod = mod;
    }

    public Tuple<Integer, Integer> multiply(Tuple<Integer, Integer> p, int multiplier) throws Exception {
        int additionCounter = 0;
        List<Boolean> binary = getIntegerBinaryBooleans(multiplier);
        System.out.println(binary.size());
        List<Tuple<Integer, Integer>> tuples = new ArrayList<>();
        tuples.add(p);
        for (int i = 1; i < binary.size(); i++) {
            tuples.add(doublePoint(tuples.get(i - 1)));
        }

        List<Tuple<Integer, Integer>> toAdd = new ArrayList<>();
        for (int i = 0; i < binary.size(); i++) {
            if (binary.get(i)) {
                toAdd.add(tuples.get(tuples.size() - 1 - i));
            }
        }

        while (toAdd.size() > 1) {
            toAdd.add(addPoints(toAdd.remove(0), toAdd.remove(0)));
            additionCounter++;
        }

        System.out.println("Additions done: " + additionCounter);

//        Tuple<Integer, Integer> result = toAdd.stream()
//                .reduce(this::addPoints)
//                .orElseThrow(() -> new Exception("Shit happened"));
        return toAdd.get(0);
    }

    private int getSamePointM(Tuple<Integer, Integer> p) {
        int first = (3 * (int) pow(p.x, 2)) + a;
        int second = modInverse(2 * p.y, mod);
        return mod((first * second), mod);
    }

    private int getDifferentPointM(Tuple<Integer, Integer> p1, Tuple<Integer, Integer> p2) {
        int first = p2.y - p1.y;
        int second = modInverse(p2.x - p1.x, mod);
        return mod((first * second), mod);
    }

    private int x3(Tuple<Integer, Integer> p1, Tuple<Integer, Integer> p2, int m) {
        return mod((int) (pow(m, 2) - p1.x - p2.x), mod);
    }

    private int y3(Tuple<Integer, Integer> p1, int m, int x3) {
        return mod(((m * (p1.x - x3)) - p1.y), mod);
    }

    private Tuple<Integer, Integer> doublePoint(Tuple<Integer, Integer> p) {
        int m = getSamePointM(p);
        int x3 = x3(p, p, m);
        int y3 = y3(p, m, x3);
        return new Tuple<>(x3, y3);
    }

    private Tuple<Integer, Integer> addPoints(Tuple<Integer, Integer> p1, Tuple<Integer, Integer> p2) {
        int m = getDifferentPointM(p1, p2);
        int x3 = x3(p1, p2, m);
        int y3 = y3(p1, m, x3);
        return new Tuple<>(x3, y3);
    }

    private List<Boolean> getIntegerBinaryBooleans(int value) {

        return Integer.toBinaryString(value)
                .chars()
                .mapToObj(e -> e == '1')
                .collect(Collectors.toList());
    }

    private int mod(int n, int m) {
        int result = n % m;
        if (result < 0) {
            result = m + result;
        }
        return result;
    }

    private int modInverse(int n, int m) throws ArithmeticException {
        if (m < 0) {
            throw new ArithmeticException("Can't do modular inverse for negative values");
        }

        int saveN = mod(n, m);
        if (saveN == 0) {
            return 1;
        }
        int[] modInverse = new int[saveN + 1];
        modInverse[1] = 1;
        for (int i = 2; i <= saveN; i++) {
            int inverse = (-(m / i) * modInverse[m % i]) % m + m;
            modInverse[i] = inverse;
        }
        return modInverse[saveN];
    }
}
