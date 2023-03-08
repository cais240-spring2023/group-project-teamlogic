package edu.wsu.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageDisplayer {
    private JPanel messagePanel;
    private JLabel text;
    private JButton doneButton;
    private static boolean done;

    public MessageDisplayer(JFrame frame){
        doneButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                done = true;
                frame.dispose();
            }
        });
    }

    public static void launch(String name, String text){
        JFrame frame = new JFrame("Test");
        MessageDisplayer messageDisplayer = new MessageDisplayer(frame);
        messageDisplayer.messagePanel.setBorder(BorderFactory.createTitledBorder(name));
        frame.setContentPane(messageDisplayer.messagePanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        messageDisplayer.text.setText(text);
        done = false;
        frame.setVisible(true);
    }

    public static void display(String name, String text){
        text = "<html>"+text;
        text = text.replace("\n","<br>");
        launch(name,text);
        while(!done){
            System.out.print("");//necessary for some reason
        }
    }
}
