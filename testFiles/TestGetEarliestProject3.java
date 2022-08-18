public class TestGetEarliestProject3 {
    // CHANGE IT
    private static final int[] expectedSchedule =  {0, 20, 10, 20, 10, 30, 30, 10, 20, 31, 35, 10, 36, 46};

    public static void main(String[] args) {
        TestGetEarliestProject1.testEarliestSchedule(expectedSchedule, "tests_io/input/groundwork_input_3.xml");
    }
}
