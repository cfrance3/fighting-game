package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player {

    private float x;
    private float y;
    private final int WIDTH = 10;
    private final int HEIGHT = 10;

    public Player(GamePanel gp) {
        this.x = 500;
        this.y = 300;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.fillRect((int) x, (int) y, WIDTH, HEIGHT);
    }
}
