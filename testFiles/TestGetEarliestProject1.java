import java.util.List;

public class TestGetEarliestProject1 {
    // CHANGE IT
    private static final int[] expectedSchedule = {0, 5, 9, 13, 9, 14};

    public static void main(String[] args) {
        testEarliestSchedule(expectedSchedule, "tests_io/input/groundwork_input_1.xml");
    }

    public static void testEarliestSchedule(int[] expectedSchedule, String inputFile) {
        TestExplorationOutput.BANISH_OUTPUT();
        MissionGroundwork missionGroundwork = new MissionGroundwork();
        List<Project> projectList = missionGroundwork.readXML(inputFile);
        if (projectList.size() != 1) {
            System.out.println("0.0");
            return;
        }

        Project project = projectList.get(0);
        double correct = 0;
        int[] schedule = project.getEarliestSchedule();
        int minScheduleLength = Math.min(schedule.length, expectedSchedule.length);
        for (int j = 0; j < minScheduleLength; j++) {
            if (schedule[j] == expectedSchedule[j]) {
                correct += 1;
            }
        }
        TestExplorationOutput.LET_ME_SAY_SOMETHING();
        System.out.println(correct / expectedSchedule.length);
    }
}
