public class TestGetEarliestProject4 {
    private static final int[] expectedSchedule =  {0, 8, 8, 8, 21, 11, 16, 22, 8, 26, 23, 23, 26, 27, 35, 8, 8, 8, 8, 45, 35, 35, 45, 15, 17, 56, 47, 61, 52, 63};

    public static void main(String[] args) {
        TestGetEarliestProject1.testEarliestSchedule(expectedSchedule, "tests_io/input/groundwork_input_4.xml");
    }
}
