package edu.wsu;


import javafx.application.Application;

import java.util.Scanner;

//This is for arbitrarily executing instructions
//Nothing substantive should happen here.
public class Sandbox {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        final int count = sc.nextInt();
        sc.close();
        for(int i = 0; i < count; i++){
            Application.launch(App.class);
        }
    }
}
