//Solves 8 puzzle using A*

import java.util.*;

public class Main {
    public static void main(String args[]){


        //Node array for possible directions
        Node[] states = new Node[4];

        //Goal node
        Node goalNode = new Node();
        goalNode = null;

        //Store correct nodes and allow display
        Stack stack = new Stack();

        //Current node being expanded
        Node current = new Node();

        //Closed List
        LinkedList<ArrayList<?>> closed = new LinkedList<ArrayList<?>>();
        int count = 0;

        //Starting grid
        Node start = new Node();
        ArrayList<Integer> startArray = new ArrayList<>();
        startArray.add(2);
        startArray.add(8);
        startArray.add(3);
        startArray.add(1);
        startArray.add(6);
        startArray.add(4);
        startArray.add(7);
        startArray.add(0);
        startArray.add(5);

        System.out.print("Start:");
        for (int i = 0; i < 9; i = i + 3){
            System.out.println();
            for (int j = 0; j < 3; j++)
                System.out.print(startArray.get(i+j) + " ");
        }
        System.out.println();
        System.out.println();

        start.state = startArray;
        start.parent = null;
        start.move = null;
        start.priority = 0;
        start.distance = -1;

        //Goal State
        Node goal = new Node();
        ArrayList<Integer> goalArray = new ArrayList<>();
        goalArray.add(1);
        goalArray.add(2);
        goalArray.add(3);
        goalArray.add(8);
        goalArray.add(0);
        goalArray.add(4);
        goalArray.add(7);
        goalArray.add(6);
        goalArray.add(5);

        //Display goal array
        System.out.print("Goal:");
        for (int i = 0; i < 9; i = i + 3){
            System.out.println();
            for (int j = 0; j < 3; j++)
                System.out.print(goalArray.get(i+j) + " ");
        }


        System.out.println("\n----------------------\n");
        System.out.println("Movements: ");


        //Creating goal state and setting it equal to goalArray
        goal.state = goalArray;
        goal.parent = null;
        goal.distance = -1;
        goal.move = null;

        //Instantiate open list (priority queue) and comparator for priorities
        Comparator<Node> comparator = new Analyze();
        PriorityQueue<Node> open = new PriorityQueue<Node>(100,comparator);

        //Add start node to priority queue, and add visited states to closed arrayList
        open.add(start);
        closed.add(start.state);

        //While open list is not empty, increment count and set current to first element of open
        //Find all possible results from movement using findStates
        while(!open.isEmpty()){
            count++;
            current = open.remove();
            states = findStates(current);

            //If states is not null and current state equals goal state, mission complete.
            //Else calculate F cost and keep going
            for(int i = 0; i<=3; i++){
                if(states[i] != null){
                    if (states[i].state.equals(goal.state)){
                        goalNode = states[i];
                        break;
                    }
                    else{
                        if(!closed.contains(states[i].state)){
                            states[i].distance = current.distance + 1;
                            closed.add(states[i].state);
                            states[i].priority = cost(states[i],goal);
                            open.add(states[i]);
                        }
                    }
                }
            }
            if(goalNode != null)
                break;

        }



        while (goalNode.parent != null){
            if(goalNode.move != null){
                stack.push(goalNode.move);
            }
            goalNode = goalNode.parent;
        }
        Node temp;

        //Outputs changes to arrayList
        while(!stack.isEmpty()){
            System.out.println(stack.pop());
        }

    }

    //Find cost of movement and returns priority of node
    private static int cost(Node node, Node goal) {

        int priority;
        int count = 0;

        for(int i=0; i<9; i++){
            if(node.state.get(i) != goal.state.get(i)){
                count++;
            }
        }

        priority = node.distance + count;
        return priority;
    }




    private static Node[] findStates(Node state) {
        Node state1,state2,state3,state4;

        state1 = moveU(state);
        state2 = moveD(state);
        state3 = moveL(state);
        state4 = moveR(state);

        Node[] states = {state1, state2, state3, state4};

        return states;
    }


    //Method to move 0  right and shift accordingly
    private static Node moveR(Node node) {
        int space = node.state.indexOf(0);
        ArrayList<Integer> child;
        int temp;
        Node childNode = new Node();

        //If space = 2, 5, or 8 : It cannot shift right
        if (space != 2 && space != 5 && space != 8) {
            child = (ArrayList<Integer>) node.state.clone();

            //Swapping blank
            temp = child.get(space+1);
            child.set(space+1,0);
            child.set(space,temp);
            childNode.state = child;
            childNode.parent = node;
            childNode.distance = node.distance + 1;

            //String for output
            childNode.move =  childNode.state.get(0) + " " + childNode.state.get(1) + " " + childNode.state.get(2) +
                    "\n" + childNode.state.get(3) + " " + childNode.state.get(4) + " " + childNode.state.get(5) +
                    "\n" + childNode.state.get(6) + " " + childNode.state.get(7) + " " + childNode.state.get(8)+ "\n" + "\n";
            return childNode;
        }
        else{
            return null;
        }
    }

    //Method to move 0 left and shift accordingly
    private static Node moveL(Node node) {
        int space = node.state.indexOf(0);
        ArrayList<Integer> child;
        int temp;
        Node childNode = new Node();

        //If space = 0, 3, or 6 : Cannot shift left
        if (space != 0 && space != 3 && space != 6) {
            child = (ArrayList<Integer>) node.state.clone();

            //Swapping blank
            temp = child.get(space-1);
            child.set(space-1,0);
            child.set(space,temp);
            childNode.state = child;
            childNode.parent = node;
            childNode.distance = node.distance + 1;

            //String for output
            childNode.move =  childNode.state.get(0) + " " + childNode.state.get(1) + " " + childNode.state.get(2) +
                    "\n" + childNode.state.get(3) + " " + childNode.state.get(4) + " " + childNode.state.get(5) +
                    "\n" + childNode.state.get(6) + " " + childNode.state.get(7) + " " + childNode.state.get(8)+ "\n" + "\n";
            return childNode;
        }
        else{
            return null;
        }
    }

    //Method to move 0 down and shift accordingly
    private static Node moveD(Node node) {
        int space = node.state.indexOf(0);
        ArrayList<Integer> child;
        int temp;
        Node childNode = new Node();

        //If space > 5 cannot shift down
        if (space <= 5) {
            child = (ArrayList<Integer>) node.state.clone();

            //Swapping blank
            temp = child.get(space+3);
            child.set(space+3,0);
            child.set(space,temp);
            childNode.state = child;
            childNode.parent = node;
            childNode.distance = node.distance + 1;

            //String for output
            childNode.move =  childNode.state.get(0) + " " + childNode.state.get(1) + " " + childNode.state.get(2) +
                    "\n" + childNode.state.get(3) + " " + childNode.state.get(4) + " " + childNode.state.get(5) +
                    "\n" + childNode.state.get(6) + " " + childNode.state.get(7) + " " + childNode.state.get(8)+ "\n" + "\n";
            return  childNode;
        }
        else{
            return null;
        }
    }

    //Method to move 0 up and shift accordingly
    private static Node moveU(Node node) {
        int space = node.state.indexOf(0);
        ArrayList<Integer> child;
        int temp;
        Node childNode = new Node();

        //If space is <= 2, cannot shift up
        if (space > 2) {
            child = (ArrayList<Integer>) node.state.clone();

            //Swapping blank
            temp = child.get(space-3);
            child.set(space-3,0);
            child.set(space,temp);
            childNode.state = child;
            childNode.parent = node;
            childNode.distance = node.distance + 1;

            //String for output
            childNode.move = childNode.state.get(0) + " " + childNode.state.get(1) + " " + childNode.state.get(2) +
                    "\n" + childNode.state.get(3) + " " + childNode.state.get(4) + " " + childNode.state.get(5) +
                    "\n" + childNode.state.get(6) + " " + childNode.state.get(7) + " " + childNode.state.get(8)+ "\n" + "\n";
            return childNode;
        }
        else{
            return null;
        }
    }

}