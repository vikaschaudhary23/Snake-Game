package SnakeGame;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Snakegame extends JFrame {
    Snakegame(){
        super("Snake Game");
        add(new Board());
        pack();

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Snakegame();
    }

}
