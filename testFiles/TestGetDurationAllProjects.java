import java.util.List;

public class TestGetDurationAllProjects {
    private static final int[] expectedDurations = {17, 78, 49, 72};

    public static void main(String[] args) {
        TestExplorationOutput.BANISH_OUTPUT();
        double correct = 0;
        MissionGroundwork missionGroundwork = new MissionGroundwork();
        List<Project> projectList = missionGroundwork.readXML(TestGroundworkOutput.INPUT_FILENAME);

        for (int i = 0; i < projectList.size(); i++) {
            Project project = projectList.get(i);
            int duration = project.getProjectDuration();
            if (duration == expectedDurations[i]) {
                correct += 1;
            }
        }
        TestExplorationOutput.LET_ME_SAY_SOMETHING();
        System.out.println(correct / expectedDurations.length);
    }
}
