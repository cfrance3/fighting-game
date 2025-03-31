package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.GamePanel;

public class Player {

    private GamePanel gp;

    private float x;
    private float y;
    private final int WIDTH = 10;
    private final int HEIGHT = 20;

    private final int SPEED = 200;

    private Rectangle hitbox;

    public Player(GamePanel gp) {
        this.gp = gp;
        this.x = 500;
        this.y = 300;
        this.hitbox = new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
    }

    public void update(double dt) {
        move(dt);
        updateHitbox();
    }

    private void move(double dt) {
        float tx = x;
        float ty = y;

        if (gp.getKeyboardInput().getWPressed()) {
            ty -= SPEED * dt;
        }
        if (gp.getKeyboardInput().getAPressed()) {
            tx -= SPEED * dt;
        }
        if (gp.getKeyboardInput().getSPressed()) {
            ty += SPEED * dt;
        }
        if (gp.getKeyboardInput().getDPressed()) {
            tx += SPEED * dt;
        }

        if (collidesWithSolidArea(tx, y) == false) {
            x = tx;
        }
        if (collidesWithSolidArea(x, ty) == false) {
            y = ty;
        }
    }

    private boolean collidesWithSolidArea(float tx, float ty) {
        for (SolidArea sa : gp.getSolidAreas()) {
            if (tx + WIDTH - sa.getBounds().x > 1 && tx <= sa.getBounds().x + sa.getBounds().width &&
                    ty + HEIGHT - sa.getBounds().y > 1 && ty <= sa.getBounds().y + sa.getBounds().height) {
                return true;
            }
        }
        return false;
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
