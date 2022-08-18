import java.io.Serializable;
import java.util.*;

public class RoutingTable implements Serializable {

    static final long serialVersionUID = 99L;
    private final Router router;
    private final Network network;
    private List<RoutingTableEntry> entryList;
    private ArrayList<Link> remainingLinks = new ArrayList<>();
    private HashMap<String,ArrayList<Link>> routerMap = new HashMap<>();
    private ArrayList<String> routerArray = new ArrayList<>();

    public RoutingTable(Router router) {
        this.router = router;
        this.network = router.getNetwork();
        this.entryList = new ArrayList<>();
    }

    private String getTheOther(Link link, String id){
        String other;
        if (link.getIpAddress1().equals(id))
            other = link.getIpAddress2();
        else
            other = link.getIpAddress1();
        return other;
    }

    /**
     * updateTable() should calculate routing information and then instantiate RoutingTableEntry objects, and finally add
     * them to RoutingTable object’s entryList.
     */
    public void updateTable() {
        // TODO: YOUR CODE HERE

        remainingLinks = new ArrayList<>();
        routerMap = new HashMap<>();
        routerArray = new ArrayList<>();
        remainingLinks.addAll(network.getLinks());
        entryList = new ArrayList<>();

        Link link;
        for (Router router:network.getRouters()){
            if (router.isDown()){
                while ((link = findLink(router.getIpAddress())) != null){
                    remainingLinks.remove(link);
                }
            }
        }

        while ((link = findLink(router.getIpAddress())) != null){

            String visited = getTheOther(link,router.getIpAddress());
            add(visited,link,-61);
        }

        int counter=0;
        while (!remainingLinks.isEmpty() && !routerArray.isEmpty()){

            while ((link = findLink(routerArray.get(counter)))!=null){

                String visited=getTheOther(link,routerArray.get(counter));
                if (!routerArray.contains(visited)){

                    add(visited,link,counter);
                }
                else {

                    int prev =routerArray.indexOf(visited);
                    if(getPathCost(prev) > getPathCost(counter)+link.getCost()){

                        routerMap.put(routerArray.get(prev),getNewPath(link,counter));
                    }
                }
                remainingLinks.remove(link);
            }
            counter++;
        }

        for (Router router:network.getRouters()){
            if (!this.router.equals(router) && !routerArray.contains(router.getIpAddress())){
                routerArray.add(router.getIpAddress());
                routerMap.put(router.getIpAddress(),new ArrayList<>());
            }
        }

        for (int i=0;i<network.getRouters().size()-1;i++)
           entryList.add(new RoutingTableEntry(router.getIpAddress(),routerArray.get(i),pathTo(routerArray.get(i))));
    }

    /**
     * pathTo(Router destination) should return a Stack<Link> object which contains a stack of Link objects,
     * which represents a valid path from the owner Router to the destination Router.
     *
     * @param destination Destination router
     * @return Stack of links on the path to the destination router
     */
    public Stack<Link> pathTo(String destination) {
        // TODO: YOUR CODE

        Stack<Link> linkStack = new Stack<>();
        ArrayList<Link> pathsList = routerMap.get(destination);
        linkStack.addAll(pathsList);

        return linkStack;
    }
    private void add(String visited, Link link, int counter){

        routerArray.add(visited);
        routerMap.put(visited,getNewPath(link,counter));
        remainingLinks.remove(link);

    }
    private ArrayList<Link> getNewPath(Link link, int index){

        if (index==-61){
            return getNewPath(link);
        }
        ArrayList<Link> linkStack = new ArrayList<>(routerMap.get(routerArray.get(index)));
        linkStack.add(link);
        return linkStack;

    }
    private ArrayList<Link> getNewPath(Link link){

        ArrayList<Link> linkStack = new ArrayList<>();
        linkStack.add(link);
        return linkStack;

    }


    private Link findLink(String routerID){
        for (Link link:remainingLinks){
            if (link.getIpAddress1().equals(routerID))
                return link;
            if (link.getIpAddress2().equals(routerID))
                return link;
        }
        return null;
    }
    private double getPathCost(int index){
        double total = 0;

        if (routerMap.get(routerArray.get(index)).size()==0)
            return Double.POSITIVE_INFINITY;

        for (Link link : routerMap.get(routerArray.get(index))){
            total+= link.getCost();
        }
        return total;


    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutingTable that = (RoutingTable) o;
        return router.equals(that.router) && entryList.equals(that.entryList);
    }

    public List<RoutingTableEntry> getEntryList() {
        return entryList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Router getRouter() {
        return router;
    }

    public Network getNetwork() {
        return network;
    }

}