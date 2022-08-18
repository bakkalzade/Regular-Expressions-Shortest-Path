import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TestNetworkingOutput {
    public static final String OUTPUT_FILENAME = "tests_io/output/networking_output";
    public static final String OUTPUT_EXTENSION = ".txt";
    public static final String BEGIN_STRING = "### MISSION NETWORKING START ###";
    public static final String END_STRING = "### MISSION NETWORKING END ###";

    public static void main(String[] args) throws IOException {
        double total = 0;
        for (int i = 0; i < TestExplorationOutput.NUMBER_OF_TEST_FILES; i++) {
            String inputFilename = TestExplorationOutput.INPUT_FILENAME +  "_" + (i+1) + TestExplorationOutput.INPUT_EXTENSION;
            String[] expectedOutput = Files.readAllLines(Paths.get(OUTPUT_FILENAME + "_" + (i + 1) + OUTPUT_EXTENSION)).toArray(new String[0]);
            String[] arguments = {"tests_io/input/groundwork_input_1.xml", inputFilename};
            total += checkOutput(arguments, BEGIN_STRING, END_STRING, expectedOutput);
        }
        System.out.println(total / (double) TestExplorationOutput.NUMBER_OF_TEST_FILES);
    }

    public static double checkOutput(String[] arguments,
                                     String beginString,
                                     String endString,
                                     String[] expectedOutput) throws IOException {
        double total = 0;

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

        // Get the last line
        String expectedLastLine = expectedOutput[expectedOutput.length-1];

        // Match the last line
        if (output.get(output.size()-1).trim().equals(expectedLastLine.trim())) {
            total += 0.5;
        }

        // Remove last lines from both
        output.remove(output.size()-1);
        List<String> expectedOutputList = new ArrayList<>(Arrays.asList(expectedOutput));
        expectedOutputList.remove(expectedOutputList.size()-1);

        int validSize = 0;

        for (String line : output) {
            if (line.startsWith("Hyperchannel between "))     {
                validSize++;
            }
        }

        if (expectedOutputList.size() != validSize) {
            int diff = Math.abs(expectedOutputList.size() - validSize);
            double score = 1 - (diff / (double) expectedOutputList.size());
            total += score * 0.5;
        } else {
            total += 0.5;
        }

        // Enable out
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        return Math.max(total, 0.0);
    }
}
