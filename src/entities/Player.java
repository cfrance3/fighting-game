package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import main.GamePanel;

public class Player {

    private GamePanel gp;

    private float x;
    private float y;
    private final int WIDTH = 10;
    private final int HEIGHT = 20;

    private Rectangle hitbox;

    public Player(GamePanel gp) {
        this.gp = gp;
        this.x = 500;
        this.y = 300;
        this.hitbox = new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
    }

    public void update() {
        move();
        updateHitbox();
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

    private void checkSolidAreaCollisions(float tx, float ty) {

    }

    private void updateHitbox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.fillRect((int) x, (int) y, WIDTH, HEIGHT);
    }
}
