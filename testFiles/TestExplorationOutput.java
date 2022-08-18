import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestExplorationOutput {
    public static final String INPUT_FILENAME = "tests_io/input/exploration_input";
    public static final String INPUT_EXTENSION = ".xml";
    public static final String OUTPUT_FILENAME = "tests_io/output/exploration_output";
    public static final String OUTPUT_EXTENSION = ".txt";
    public static final int NUMBER_OF_TEST_FILES = 4;
    private static final String BEGIN_STRING = "### MISSION EXPLORATION START ###";
    private static final String END_STRING = "### MISSION EXPLORATION END ###";

    public static void LET_ME_SAY_SOMETHING() {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    public static void BANISH_OUTPUT() {
        System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
            @Override public void write(int b) {}
        }) {
            @Override public void flush() {}
            @Override public void close() {}
            @Override public void write(int b) {}
            @Override public void write(byte[] b) {}
            @Override public void write(byte[] buf, int off, int len) {}
            @Override public void print(boolean b) {}
            @Override public void print(char c) {}
            @Override public void print(int i) {}
            @Override public void print(long l) {}
            @Override public void print(float f) {}
            @Override public void print(double d) {}
            @Override public void print(char[] s) {}
            @Override public void print(String s) {}
            @Override public void print(Object obj) {}
            @Override public void println() {}
            @Override public void println(boolean x) {}
            @Override public void println(char x) {}
            @Override public void println(int x) {}
            @Override public void println(long x) {}
            @Override public void println(float x) {}
            @Override public void println(double x) {}
            @Override public void println(char[] x) {}
            @Override public void println(String x) {}
            @Override public void println(Object x) {}
            @Override public java.io.PrintStream printf(String format, Object... args) { return this; }
            @Override public java.io.PrintStream printf(java.util.Locale l, String format, Object... args) { return this; }
            @Override public java.io.PrintStream format(String format, Object... args) { return this; }
            @Override public java.io.PrintStream format(java.util.Locale l, String format, Object... args) { return this; }
            @Override public java.io.PrintStream append(CharSequence csq) { return this; }
            @Override public java.io.PrintStream append(CharSequence csq, int start, int end) { return this; }
            @Override public java.io.PrintStream append(char c) { return this; }
        });
    }

    public static void main(String[] args) throws IOException {
        double total = 0;
        for (int i = 0; i < NUMBER_OF_TEST_FILES; i++) {
            String inputFilename = INPUT_FILENAME + "_" + (i+1) + INPUT_EXTENSION;
            String[] expectedOutput = Files.readAllLines(Paths.get(OUTPUT_FILENAME + "_" + (i + 1) + OUTPUT_EXTENSION)).toArray(new String[0]);
            String[] arguments = {"tests_io/input/groundwork_input_1.xml", inputFilename};
            total += checkOutput(arguments, BEGIN_STRING, END_STRING, expectedOutput);
        }
        System.out.println(total / (double) NUMBER_OF_TEST_FILES);
    }


    public static double checkOutput(String[] arguments,
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

        if (output.size() != expectedOutput.length) {
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            return 0.0;
        }

        // Get the first line
        String expectedFirstLine = expectedOutput[0];

        // Match the first line
        if (output.get(0).trim().equals(expectedFirstLine.trim())) {
            correct += 1;
        }

        output.remove(0);

        // Trim the output
        for (int k = 0; k < output.size(); k++) {
            output.set(k, output.get(k).split(":")[1].trim());
        }

        Set<String> testOutputSet = new HashSet<>(output);

        String[] expectedOutputSliced = Arrays.copyOfRange(expectedOutput, 1, expectedOutput.length);

        for (int k = 0; k < expectedOutputSliced.length; k++) {

            String planetString = expectedOutputSliced[k].split(":")[1].trim();
            if (testOutputSet.contains(planetString)) {
                correct += 1;
            }
        }

        // Enable out
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        return correct / (double) expectedOutput.length;
    }
}
