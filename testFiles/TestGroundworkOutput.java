import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TestGroundworkOutput {
    public static final String INPUT_FILENAME = "tests_io/input/groundwork_input_all.xml";
    private static final String OUTPUT_FILENAME = "tests_io/output/groundwork_output_all.txt";

    private static final String BEGIN_STRING = "### MISSION GROUNDWORK START ###";
    private static final String END_STRING = "### MISSION GROUNDWORK END ###";

    public static void main(String[] args) throws IOException {
        String[] arguments = {INPUT_FILENAME, "tests_io/input/exploration_input_1.xml"};
        String[] expectedOutput = Files.readAllLines(Paths.get(OUTPUT_FILENAME)).toArray(new String[0]);
        System.out.println(checkOutput(arguments, BEGIN_STRING, END_STRING, expectedOutput));
    }

    private static double checkOutput(String[] arguments,
                                      String beginString,
                                      String endString,
                                      String[] expectedOutput) throws IOException {
        int correct = 0;

        // Suppress out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        // Run and record
        Main.main(arguments);
        String recordedOutput = baos.toString().trim();
        String[] outputLines = recordedOutput.split(System.lineSeparator());

        ArrayList<String> output = new ArrayList<>();

        boolean flag = false;

        for (int i = 0; i < outputLines.length; i++) {
            String line = outputLines[i].trim();

            if (line.contains(beginString)) {
                flag = true;
                continue;
            }
            if (line.contains(endString)) {
                flag = false;
            }

            if (flag) output.add(line);
        }

        // No ArrayOutOfBounds Exception
        int minLength = Math.min(expectedOutput.length, output.size());

        for (int i = 0; i < minLength; i++) {
            if (output.get(i).trim().equals(expectedOutput[i].trim())) {
                correct++;
            }
        }

        // Enable out
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        return correct / (double) expectedOutput.length;
    }
}
