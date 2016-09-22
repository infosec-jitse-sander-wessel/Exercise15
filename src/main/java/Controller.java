import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

/**
 * Created by Sander on 21-9-2016.
 */
public class Controller {

    private CommandLine commandLine;
    private final Options options;

    Controller(String[] args) throws Exception {
        this.options = getOptions();
        CommandLineParser parser = new BasicParser();
        try {
            this.commandLine = parser.parse(this.options, args);
        } catch (ParseException e) {
            System.out.println("Incorrect arguments: " + e.getMessage());
            printHelpPage();
            throw new Exception("Incorrect input");
        }
    }

    private static Options getOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "Display this help page");
        return options;
    }

    private void printHelpPage() {
        System.out.println("Usage: <multiplier> <x> <y>");
    }

    public void run() throws ParseException {
        if (commandLine.hasOption("h")) {
            printHelpPage();
            return;
        }
        List<Integer> arguments = Arrays.stream(commandLine.getArgs())
                .filter(StringUtils::isNumeric)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        if (arguments.size() != 3) {
            throw new ParseException("Invalid number of integer arguments.");
        }

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

    private class Tuple<X, Y> {

        final X x;
        final Y y;

        Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }

    }

}
