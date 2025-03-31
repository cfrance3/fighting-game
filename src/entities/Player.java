package entities;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Player {

    private GamePanel gp;

    private float x;
    private float y;
    private final int WIDTH = 10;
    private final int HEIGHT = 10;

    public Player(GamePanel gp) {
        this.gp = gp;
        this.x = 500;
        this.y = 300;
    }

    public void update() {
        move();
    }

    private void move() {
        float tx = x;
        float ty = y;

        if (gp.getKeyboardInput().getWPressed()) {
            ty -= 5;
        }
        if (gp.getKeyboardInput().getAPressed()) {
            tx -= 5;
        }
        if (gp.getKeyboardInput().getSPressed()) {
            ty += 5;
        }
        if (gp.getKeyboardInput().getDPressed()) {
            tx += 5;
        }

        x = tx;
        y = ty;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.fillRect((int) x, (int) y, WIDTH, HEIGHT);
    }
}
