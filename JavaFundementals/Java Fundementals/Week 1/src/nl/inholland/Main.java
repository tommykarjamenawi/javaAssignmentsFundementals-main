package nl.inholland;

import  java.util.Scanner;

public class Main {

    public static void main(String[] args) {
       Scanner input = new Scanner(System.in);

       int whichAssignment;
       do {
           System.out.print("Which week 1 assignment do you want to run? ");
           whichAssignment = Integer.parseInt(input.next());
           if (whichAssignment < 1 || whichAssignment > 3){
               System.out.println("Please enter a correct assignment number :D");
           }
       }while(whichAssignment < 1 || whichAssignment > 3);
       Start(whichAssignment);
    }

    public static void Start(int assignment){
        switch(assignment) {
            case 1:
                Assignment1 as1 = new Assignment1();
                break;
            case 2:
                Assignment2 as2 = new Assignment2();
                break;
            case 3:
                Assignment3 as3 = new Assignment3();
                break;
            default:
                // code block
        }
    }
}
