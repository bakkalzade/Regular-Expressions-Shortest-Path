public class TestGetEarliestProject2 {
    // CHANGE IT
    private static final int[] expectedSchedule =  {0, 23, 17, 28, 37, 17, 44, 28, 53, 58, 17, 62, 68};

    public static void main(String[] args) {
        TestGetEarliestProject1.testEarliestSchedule(expectedSchedule, "tests_io/input/groundwork_input_2.xml");
    }
}
