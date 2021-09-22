package nl.inholland;

import java.util.Scanner;

public class Assignment3 {
    public Assignment3(){
        Start3();
    }

    public void Start3(){
        System.out.println("Assignment 3 has started!");
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the size of your group and press [ENTER] "); int groupSize = Integer.parseInt(input.nextLine());
        System.out.println("group size: " + groupSize);
        Student[] students = new Student[groupSize];

        for (int i = 0; i < students.length; i++) {
            System.out.println("Please enter the name of student #" + (i+1) + " and press [ENTER]");
            students[i] = new Student(input.nextLine());
        }
        System.out.println();

        for (int i = 0; i < students.length; i++) {
            System.out.println("Please enter the name of student #" + (i+1) + ": " + students[i].Name);
        }
        for (int i = 0; i < students.length; i++) {
            System.out.printf("Is student #%d(%s) present? [Y/N + ENTER]: ", i+1, students[i].Name);
            String presence = input.next();
            switch(presence) {
                case "y":
                    students[i].Present = true;
                    break;
                case "n":
                    students[i].Present = false;
                    break;
                default:
                    // code block
            }
        }
        System.out.println();
        for (int i = 0; i < students.length; i++) {
            System.out.printf("Student #%s: %-15s | Present: %s", i + 1, students[i].Name, students[i].Present);
            System.out.println();
        }
    }
}
