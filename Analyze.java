import java.util.Comparator;


public class Analyze implements Comparator<Node> {

    @Override
    public int compare(Node node1, Node node2) {
        if (node1.priority > node2.priority){
            return 1;
        }
        if (node1.priority < node2.priority){
            return -1;
        }
        return 0;
    }



}