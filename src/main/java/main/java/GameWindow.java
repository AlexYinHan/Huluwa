package main.java;

import javax.swing.JFrame;

public final class GameWindow extends JFrame{
    public GameWindow(int width, int height) {
        setSize(width, height);
        InitUI();
    }

    public void InitUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setTitle("葫芦娃");
    }
}
