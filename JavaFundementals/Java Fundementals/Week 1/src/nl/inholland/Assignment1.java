package nl.inholland;

import java.util.Scanner;

public class Assignment1 {
    public Assignment1(){
        Start1();
    }

    public void Start1(){
        System.out.println("Assignment 1 has started!");
        Scanner input = new Scanner(System.in);
        String name = input.nextLine();
        int age = Integer.parseInt(input.nextLine());
        System.out.println("name: " + name + " age: " + age);
    }
}
