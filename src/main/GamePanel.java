package main;

import javax.swing.JPanel;

import entities.Player;
import entities.SolidArea;
import inputs.KeyboardInput;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    // WINDOW SETTINGS
    private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final int WIDTH = (int) ge.getMaximumWindowBounds().getWidth();
    public final int HEIGHT = (int) ge.getMaximumWindowBounds().getHeight();
    public final int FPS = 120;

    // INPUTS
    private KeyboardInput keyI = new KeyboardInput(this);;

    private ArrayList<Player> players = new ArrayList<>();

    private ArrayList<SolidArea> solidAreas = new ArrayList<>();

    private Thread gameThread;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.GRAY);
        initPlayer();
        initSolidAreas();
        addKeyListener(keyI);
        setDoubleBuffered(true);
        setFocusable(true);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void initPlayer() {
        players.add(new Player(this));
    }

    private void initSolidAreas() {
        solidAreas.add(new SolidArea(this, 100, 800, 1000, 100));
    }

    public void update() {
        updatePlayers();
    }

    public void updatePlayers() {
        for (Player p : players) {
            p.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        drawPlayers(g2);
        drawSolidAreas(g2);
    }

    public void drawPlayers(Graphics2D g2) {
        for (Player p : players) {
            p.draw(g2);
        }
    }

    public void drawSolidAreas(Graphics2D g2) {
        for (SolidArea sa : solidAreas) {
            sa.draw(g2);
        }
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

    public KeyboardInput getKeyboardInput() {
        return keyI;
    }

    public ArrayList<SolidArea> getSolidAreas() {
        return solidAreas;
    }
}
