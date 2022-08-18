import org.w3c.dom.Element;

public class Util {
    public static String getNodeValue(Element element, String field) {
        return element.getElementsByTagName(field).item(0).getTextContent();
    }
    public static void printlnDash(int limit, char symbol) {
        for (int i = 0; i < limit; i++) System.out.print(symbol);
        System.out.println();
    }
}
