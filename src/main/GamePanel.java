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

    // INPUTS
    private KeyboardInput keyI = new KeyboardInput(this);;

    // ENTITY ARRAYS
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<SolidArea> solidAreas = new ArrayList<>();

    // GAME RUNNING
    private Thread gameThread;
    private boolean running = false;
    final int MAX_UPS = 100;
    final int MAX_FPS = 120;

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
        running = true;
    }

    private void initPlayer() {
        players.add(new Player(this));
    }

    private void initSolidAreas() {
        solidAreas.add(new SolidArea(this, 100, 800, 1000, 100));
    }

    public void update(double dt) {
        updatePlayers(dt);
    }

    public void updatePlayers(double dt) {
        for (Player p : players) {
            p.update(dt);
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

        final int U_OPTIMAL_TIME = 1000000000 / MAX_UPS;
        final int F_OPTIMAL_TIME = 1000000000 / MAX_FPS;

        double uDeltaTime = 0, fDeltatime = 0;
        int updates = 0, frames = 0;
        long startTime = System.nanoTime();
        long timer = System.currentTimeMillis();

        while (running) {

            long currentTime = System.nanoTime();
            uDeltaTime += (currentTime - startTime);
            fDeltatime += (currentTime - startTime);
            startTime = currentTime;

            if (uDeltaTime >= U_OPTIMAL_TIME) {
                update(uDeltaTime / 1000000000);
                updates++;
                uDeltaTime -= U_OPTIMAL_TIME;
            }
            if (fDeltatime >= F_OPTIMAL_TIME) {
                repaint();
                frames++;
                fDeltatime -= F_OPTIMAL_TIME;
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("UPS: " + updates);
                System.out.println("FPS: " + frames);
                updates = 0;
                frames = 0;
                timer += 1000;
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
