import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public void run() throws Exception {
        if (commandLine.hasOption("h")) {
            printHelpPage();
            return;
        }
        List<Integer> arguments = Arrays.stream(commandLine.getArgs())
                .filter(StringUtils::isNumeric)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

//        if (arguments.size() != 3) {
//            throw new ParseException("Invalid number of integer arguments.");
//        }


        int aM = 15;
        int bM = 22;

        int a = 11;
        int b = 19;
        int mod = 167;

        EllipticCurve curve = new EllipticCurve(a, b, mod);
        Tuple<Double, Double> p1 = new Tuple<>(2.0, 7.0);

        Tuple<Double, Double> aliceResult = curve.multiply(p1, aM);
        System.out.println(aliceResult + "\n");

        Tuple<Double, Double> bobResult = curve.multiply(p1, bM);
        System.out.println(bobResult + "\n");


//        Tuple<Double, Double> aliceBobResult = curve.multiply(aliceResult, bM);
//        Tuple<Double, Double> bobAliceResult = curve.multiply(bobResult, aM);

//        System.out.println(aliceBobResult);
//        System.out.println(bobAliceResult);
    }

    private Tuple<Long, Long> round(Tuple<Double, Double> input) {
        return new Tuple<>(Math.round(input.x), Math.round(input.y));
    }
}
