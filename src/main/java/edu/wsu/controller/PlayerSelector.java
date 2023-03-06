package edu.wsu.controller;

import edu.wsu.model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerSelector {
    private JButton P7;
    private JButton P2;
    private JButton P3;
    private JButton P4;
    private JButton P5;
    private JButton P6;
    private JButton P8;
    private JButton P9;
    private JButton P10;
    private JButton P11;
    private JButton P12;
    private JButton P1;
    private JButton skipButton;
    private JPanel playerSelector;

    private static Player toReturn;
    private static Player[] selections;
    private static boolean done;
    private JButton[] buttons = {P1,P2,P3,P4,P5,P6,P7,P8,P9,P10,P11,P12};

    public PlayerSelector(JFrame frame) {
        P1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toReturn = null;
                if(selections.length >= 1) toReturn = selections[0];
                if(toReturn != null) {
                    done = true;
                    frame.dispose();
                }
            }
        });
        P2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toReturn = null;
                if(selections.length >= 2) toReturn = selections[1];
                if(toReturn != null) {
                    done = true;
                    frame.dispose();
                }
            }
        });
        P3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toReturn = null;
                if(selections.length >= 3) toReturn = selections[2];
                if(toReturn != null) {
                    done = true;
                    frame.dispose();
                }
            }
        });
        P4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toReturn = null;
                if(selections.length >= 4) toReturn = selections[3];
                if(toReturn != null) {
                    done = true;
                    frame.dispose();
                }
            }
        });
        P5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toReturn = null;
                if(selections.length >= 5) toReturn = selections[4];
                if(toReturn != null) {
                    done = true;
                    frame.dispose();
                }
            }
        });
        P6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toReturn = null;
                if(selections.length >= 6) toReturn = selections[5];
                if(toReturn != null) {
                    done = true;
                    frame.dispose();
                }
            }
        });
        P7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toReturn = null;
                if(selections.length >= 7) toReturn = selections[6];
                if(toReturn != null) {
                    done = true;
                    frame.dispose();
                }
            }
        });
        P8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toReturn = null;
                if(selections.length >= 8) toReturn = selections[7];
                if(toReturn != null) {
                    done = true;
                    frame.dispose();
                }
            }
        });
        P9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toReturn = null;
                if(selections.length >= 9) toReturn = selections[8];
                if(toReturn != null) {
                    done = true;
                    frame.dispose();
                }
            }
        });
        P10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toReturn = null;
                if(selections.length >= 10) toReturn = selections[9];
                if(toReturn != null) {
                    done = true;
                    frame.dispose();
                }
            }
        });
        P11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toReturn = null;
                if(selections.length >= 11) toReturn = selections[10];
                if(toReturn != null) {
                    done = true;
                    frame.dispose();
                }
            }
        });
        P12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selections.length >= 11) toReturn = selections[11];
                if(toReturn != null) {
                    done = true;
                    frame.dispose();
                }
            }
        });
        skipButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                toReturn = null;
                done = true;
                frame.dispose();
            }
        });
    }

    public static void launch(String name, String purpose, boolean onlyCheckTheLiving){
        JFrame frame = new JFrame("Test");
        PlayerSelector playerSelector = new PlayerSelector(frame);
        playerSelector.playerSelector.setBorder(BorderFactory.createTitledBorder(name + ", select a player to " + purpose + "..."));
        frame.setContentPane(playerSelector.playerSelector);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < selections.length; i++){
            if(selections[i] != null && (selections[i].isAlive() || !onlyCheckTheLiving)) playerSelector.buttons[i].setText(selections[i].getName());
            else playerSelector.buttons[i].setVisible(false);
        }
        for(int i = selections.length; i < 12; i++){
            playerSelector.buttons[i].setVisible(false);
        }
        frame.setVisible(true);
    }

    public static Player selectPlayer(Player[] players, String name, String purpose, boolean onlyCheckTheLiving){
        if(players.length > 12){
            return null;
        }
        selections = players;
        toReturn = null;
        done = false;
        launch(name, purpose, onlyCheckTheLiving);
        while(!done){
            System.out.print("");//I don't know why but this is completely necessary
        }
        return toReturn;
    }
}
