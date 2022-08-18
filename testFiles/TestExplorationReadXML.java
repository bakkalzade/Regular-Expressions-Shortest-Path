import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.*;

public class TestExplorationReadXML {

    private static boolean containsPlanet(List<Planet> list, Planet planet){
        return list.stream().anyMatch(o -> o.equals(planet));
    }

    public static Galaxy readXML(String filename) {
        List<Planet> planetList = new ArrayList<>();

        try {
            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            NodeList planetNodes = doc.getElementsByTagName("Planet");

            for (int i = 0; i < planetNodes.getLength(); i++) {
                Node planetNode = planetNodes.item(i);
                Element planetElement = (Element) planetNode;
                List<String> neighbors = new ArrayList<>();

                // Parse neighbors
                NodeList neighborNodes = planetElement.getElementsByTagName("PlanetID");
                for (int j = 0; j < neighborNodes.getLength(); j++) {
                    Node neighbor = neighborNodes.item(j);
                    Element neighborElement = (Element) neighbor;
                    neighbors.add(neighborElement.getTextContent());
                }

                Planet planet = new Planet(
                        Util.getNodeValue(planetElement, "ID"),
                        Integer.parseInt(Util.getNodeValue(planetElement, "TechnologyLevel")),
                        neighbors);

                planetList.add(planet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Galaxy(planetList);
    }


    public static void main(String[] args) {
        TestExplorationOutput.BANISH_OUTPUT();
        double total = 0;

        for (int i = 0; i < TestExplorationOutput.NUMBER_OF_TEST_FILES; i++) {
            double correct = 0;
            String inputFilename = TestExplorationOutput.INPUT_FILENAME + "_" + (i+1) + TestExplorationOutput.INPUT_EXTENSION;
            Galaxy expectedGalaxy = readXML(inputFilename);
            List<Planet> expectedList = expectedGalaxy.getPlanets();

            MissionExploration exploration = new MissionExploration();
            Galaxy testGalaxy = exploration.readXML(inputFilename);
            List<Planet> testPlanetList = testGalaxy.getPlanets();

			if (testPlanetList.size() != expectedList.size()) {
				System.out.println("0.0");
				return;
			}

            for (Planet testPlanet : testPlanetList) {
                if (containsPlanet(expectedList, testPlanet)) {
                    correct += 1;
                }
            }
            total += correct / (double) expectedList.size();
        }
        TestExplorationOutput.LET_ME_SAY_SOMETHING();
        System.out.println(total / (double) TestExplorationOutput.NUMBER_OF_TEST_FILES);
    }
}
