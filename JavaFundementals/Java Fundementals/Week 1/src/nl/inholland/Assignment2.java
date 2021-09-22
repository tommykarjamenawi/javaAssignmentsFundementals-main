package nl.inholland;

import java.util.Scanner;

public class Assignment2 {
    public Assignment2(){
        Start2();
    }

    public void Start2(){
        System.out.println("Assignment 2 has started!");
        Scanner input = new Scanner(System.in);
        System.out.print("Enter course name: ");
        String name = input.next();
        System.out.print("Enter number of students: ");
        int amountOfStudents = Integer.parseInt(input.next());
        System.out.println();

        String[] names = new String[amountOfStudents];
        int[] grades = new int[amountOfStudents];

        for (int i = 0; i < names.length; i++) {
            System.out.print("enter name of student " + i + ": ");
            names[i] = input.next();
        }
        System.out.println();
        for (int i = 0; i < grades.length; i++) {
            System.out.print("enter grade of " + names[i] + ": ");
            grades[i] = Integer.parseInt(input.next());
        }
        System.out.println();
        double average = 0;
        int maxGrade = 0;
        String maxStudent = names[0];

        for (int i = 0; i < grades.length; i++) {
            average += grades[i];
            if (grades[i] > maxGrade){
                maxGrade = grades[i];
                maxStudent = names[i];
            }
        }
        average = average / 3;

        System.out.println("Average grade: " + String.format("%,.1f", average));
        System.out.println("Student " + maxStudent + " has maximum grade: " + maxGrade);
        System.out.println();
        for (int i = 0; i < grades.length; i++) {
            System.out.println("Grade for student " + names[i] + " (course " + name + "): " + grades[i]);
        }
    }
}