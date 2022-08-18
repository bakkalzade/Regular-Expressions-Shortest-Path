import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class TestExploreSolarSystems1 {

    public static void main(String[] args) throws IOException {
        runTest(1);
    }

    public static void runTest(int testNumber) throws IOException {
        TestExplorationOutput.BANISH_OUTPUT();
        // Read the expected solar systems
        List<String> lines = Files.readAllLines(Paths.get(TestExplorationOutput.OUTPUT_FILENAME + "_" + testNumber +
                TestExplorationOutput.OUTPUT_EXTENSION));
        List<String> solarSystemLines = lines.subList(1, lines.size());
        Set<String> solarSystemSetExpected = new HashSet<>();

        for (String line : solarSystemLines) {
            solarSystemSetExpected.add(line.split("[\\[\\]]")[1]);
        }

        // Read the input galaxy
        MissionExploration missionExploration = new MissionExploration();
        Galaxy galaxy = missionExploration.readXML(TestExplorationOutput.INPUT_FILENAME + "_" + testNumber + TestExplorationOutput.INPUT_EXTENSION);

        // Begin the test
        List<SolarSystem> solarSystemsUUT = galaxy.exploreSolarSystems();

        double correct = 0;

        for (SolarSystem system : solarSystemsUUT) {
            // Check if solar system is in the expected
            List<Planet> planets = new ArrayList<>(system.getPlanets());
            Collections.sort(planets);

            List<String> planetsUUT = planets.stream().map(Planet::getId).collect(Collectors.toList());
            String uut = String.join(", ", planetsUUT);
            if (solarSystemSetExpected.contains(uut)) {
                correct += 1;
            }
        }
        TestExplorationOutput.LET_ME_SAY_SOMETHING();
        System.out.println(correct / solarSystemSetExpected.size());
    }
}
