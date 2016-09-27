import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
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

        int aM = 42;
        int bM = 57;

        int a = -4;
        int b = 25;
        int mod = 29;

        EllipticCurve curve = new EllipticCurve(a, b, mod);
        Tuple<Integer, Integer> p1 = new Tuple<>(23, 23);

        Tuple<Integer, Integer> aliceResult = curve.multiply(p1, aM);
        System.out.println("Alice: " + aliceResult + "\n");
//
//        Tuple<Integer, Integer> bobResult = curve.multiply(p1, bM);
//        System.out.println("Bob: " + bobResult + "\n");
//
//
//        Tuple<Integer, Integer> aliceBobResult = curve.multiply(aliceResult, bM);
//        Tuple<Integer, Integer> bobAliceResult = curve.multiply(bobResult, aM);
//
//        System.out.println(aliceBobResult);
//        System.out.println(bobAliceResult);
    }
}
