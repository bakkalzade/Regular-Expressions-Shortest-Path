import java.util.List;

public class TestGetEarliestAllProjects {

    private static final int[][] expectedSchedules = {
            // CHANGE THE FIRST THREE
            {0, 5, 9, 13, 9, 14},
            {0, 23, 17, 28, 37, 17, 44, 28, 53, 58, 17, 62, 68},
            {0, 20, 10, 20, 10, 30, 30, 10, 20, 31, 35, 10, 36, 46},
            {0, 8, 8, 8, 21, 11, 16, 22, 8, 26, 23, 23, 26, 27, 35, 8, 8, 8, 8, 45, 35, 35, 45, 15, 17, 56, 47, 61, 52, 63}
    };

    public static void main(String[] args) {
        TestExplorationOutput.BANISH_OUTPUT();
        double total = 0;
        MissionGroundwork missionGroundwork = new MissionGroundwork();
        List<Project> projectList = missionGroundwork.readXML(TestGroundworkOutput.INPUT_FILENAME);

        for (int i = 0; i < projectList.size(); i++) {
            Project project = projectList.get(i);
            double correct = 0;
            int[] schedule = project.getEarliestSchedule();
            int minScheduleLength = Math.min(schedule.length, expectedSchedules[i].length);
            for (int j = 0; j < minScheduleLength; j++) {
                if (schedule[j] == expectedSchedules[i][j]) {
                    correct += 1;
                }
            }
            total += correct / expectedSchedules[i].length;
        }
        TestExplorationOutput.LET_ME_SAY_SOMETHING();
        System.out.println(total / expectedSchedules.length);
    }
}
