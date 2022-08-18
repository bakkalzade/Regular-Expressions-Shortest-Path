import java.util.List;

public class TestMinimumCostNetwork1 {

    public static final double errorMargin = 0.000009;
    public static void main(String[] args) {
        runTest(1, 43.66272614333343);
    }

    public static void runTest(int testNumber, double expectedSum) {
        TestExplorationOutput.BANISH_OUTPUT();

        // UUT
        MissionNetworking missionNetworking = new MissionNetworking();
        MissionExploration missionExploration = new MissionExploration();
        Galaxy galaxy = missionExploration.readXML(TestExplorationOutput.INPUT_FILENAME + "_" + testNumber + TestExplorationOutput.INPUT_EXTENSION);
        List<SolarSystem> solarSystems = galaxy.exploreSolarSystems();

        SubspaceCommunicationNetwork networkUUT = missionNetworking.createNetwork(galaxy.getSolarSystems());

        // Begin testing
        List<HyperChannel> edgesUUT = networkUUT.getMinimumCostCommunicationNetwork();

        double sumUUT = 0;
        for (HyperChannel channel : edgesUUT) {
            sumUUT += channel.getWeight();
        }


        TestExplorationOutput.LET_ME_SAY_SOMETHING();
        if (Math.abs(expectedSum - sumUUT) < errorMargin) {
            System.out.println("1.0");
        } else {
            System.out.println("0.0");
        }
    }
}
