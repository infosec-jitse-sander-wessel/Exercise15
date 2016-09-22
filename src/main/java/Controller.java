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

//        if (arguments.size() != 3) {
//            throw new ParseException("Invalid number of integer arguments.");
//        }

        new EllipticCurve(10, 21, 41).multiply(new Tuple<>(3, 6), 1);

    }
}
