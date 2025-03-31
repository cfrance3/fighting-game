package main;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;

public class GamePanel extends JPanel implements Runnable {

    // WINDOW SETTINGS
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final int WIDTH = (int) ge.getMaximumWindowBounds().getWidth();
    public final int HEIGHT = (int) ge.getMaximumWindowBounds().getHeight();
    public final int FPS = 120;

    Player player;

    Thread gameThread;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.GRAY);
        initPlayer();
        setDoubleBuffered(true);
        setFocusable(true);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void initPlayer() {
        player = new Player(this);
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        player.draw(g2);
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }
}
