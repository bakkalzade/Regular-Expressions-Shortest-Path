import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

public class TestGroundworkReadXML {
    private static String getNodeValue(Element element, String field) {
        return element.getElementsByTagName(field).item(0).getTextContent();
    }

    private static boolean containsProject(List<Project> list, Project project){
        return list.stream().anyMatch(o -> o.equals(project));
    }

    public static List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();
        try {
            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            NodeList projectNodes = doc.getElementsByTagName("Project");

            // Parse projects
            for (int i = 0; i < projectNodes.getLength(); i++) {

                Node projectNode = projectNodes.item(i);
                Element projectElement = (Element) projectNode;

                List<Task> taskList = new ArrayList<>();
                String projectName = getNodeValue(projectElement, "Name");
                Project project = new Project(projectName, taskList);

                NodeList taskNodes = projectElement.getElementsByTagName("Task");

                // Parse tasks
                for (int j = 0; j < taskNodes.getLength(); j++) {
                    Node taskNode = taskNodes.item(j);
                    Element taskElement = (Element) taskNode;

                    List<Integer> dependencies = new ArrayList<>();
                    int taskId = Integer.parseInt(getNodeValue(taskElement, "TaskID"));
                    String description = getNodeValue(taskElement, "Description");
                    int duration = Integer.parseInt(getNodeValue(taskElement, "Duration"));

                    // Parse dependencies
                    NodeList dependencyNodes = taskElement.getElementsByTagName("DependsOnTaskID");
                    for (int k = 0; k < dependencyNodes.getLength(); k++) {
                        Node dependencyNode = dependencyNodes.item(k);
                        Element dependencyElement = (Element) dependencyNode;
                        Integer dependsOn = Integer.parseInt(dependencyElement.getTextContent());
                        dependencies.add(dependsOn);
                    }

                    // Add task to the project
                    taskList.add(new Task(
                            taskId,
                            description,
                            duration,
                            dependencies
                    ));
                }

                // Add project to the project list
                projectList.add(project);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectList;
    }

    public static void main(String[] args) {
        TestExplorationOutput.BANISH_OUTPUT();
        double correct = 0;
        List<Project> expectedList = readXML(TestGroundworkOutput.INPUT_FILENAME);

        MissionGroundwork groundwork = new MissionGroundwork();
        List<Project> testList = groundwork.readXML(TestGroundworkOutput.INPUT_FILENAME);
		
		if (expectedList.size() != testList.size()) {
			System.out.println("0.0");
			return;
		}
		
        for (Project testProject : testList) {
            if (containsProject(expectedList, testProject)) {
                correct += 1;
            }
        }
        TestExplorationOutput.LET_ME_SAY_SOMETHING();
        System.out.println(correct / expectedList.size());
    }
}
