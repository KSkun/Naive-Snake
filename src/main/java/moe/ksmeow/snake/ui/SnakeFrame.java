package moe.ksmeow.snake.ui;

import moe.ksmeow.snake.NaiveSnake;
import moe.ksmeow.snake.client.SnakeClient;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeFrame extends JFrame implements KeyListener {

    private final SnakePanel panel;

    public SnakeFrame(int[][] map) {
        super(NaiveSnake.APP_NAME + " " + NaiveSnake.VERSION);
        this.setSize(SnakePanel.RECT_SIZE * NaiveSnake.MAP_SIZE,
                SnakePanel.RECT_SIZE * NaiveSnake.MAP_SIZE + 50); // needs larger height for magic reason
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);

        panel = new SnakePanel(map);
        this.add(panel);
    }

    public void updateMap(int[][] map) {
        panel.setMap(map);
        panel.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (!NaiveSnake.MANUAL || !NaiveSnake.gameRunning) return;
        try {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP: NaiveSnake.client.move(SnakeClient.Direction.UP); break;
                case KeyEvent.VK_DOWN: NaiveSnake.client.move(SnakeClient.Direction.DOWN); break;
                case KeyEvent.VK_LEFT: NaiveSnake.client.move(SnakeClient.Direction.LEFT); break;
                case KeyEvent.VK_RIGHT: NaiveSnake.client.move(SnakeClient.Direction.RIGHT); break;
            }
            Thread.sleep(10);
            updateMap(NaiveSnake.dump.getMap());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}
