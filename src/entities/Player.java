package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import utils.LoadSave;
import utils.UtilityTool;

public class Player {

    private GamePanel gp;

    private float x;
    private float y;
    private float xVel = 0;
    private float yVel = 0;
    private final int WIDTH = 64;
    private final int HEIGHT = 128;

    // DIRECTION
    private final int RIGHT = 0;
    private final int LEFT = 1;
    private int direction = RIGHT;

    // PLAYER STATE
    PlayerState state = PlayerState.IDLE;

    // MOVE ATTRIBUTES
    private final int SPEED = 400;
    private final int FALL_SPEED = 1;
    private final int TERMINAL_VEL = 15;

    // ANIMATIONS
    BufferedImage[][] animations;
    private final int IMAGE_OFFSET = WIDTH / 2;

    private Rectangle hitbox;

    public Player(GamePanel gp) {
        this.gp = gp;
        this.x = 500;
        this.y = 300;
        this.hitbox = new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
        loadAnimations();
    }

    private void loadAnimations() {
        animations = new BufferedImage[2][3];
        animations[0][0] = UtilityTool.scaleImage(LoadSave.getSpriteAtlas(LoadSave.PLAYER_IDLE_RIGHT), 128, 128);
        animations[0][1] = UtilityTool.scaleImage(LoadSave.getSpriteAtlas(LoadSave.PLAYER_FALLING_RIGHT), 128, 128);
        animations[0][2] = UtilityTool.scaleImage(LoadSave.getSpriteAtlas(LoadSave.PLAYER_JUMPING_RIGHT), 128, 128);
        animations[1][0] = UtilityTool.scaleImage(LoadSave.getSpriteAtlas(LoadSave.PLAYER_IDLE_LEFT), 128, 128);
        animations[1][1] = UtilityTool.scaleImage(LoadSave.getSpriteAtlas(LoadSave.PLAYER_FALLING_LEFT), 128, 128);
        animations[1][2] = UtilityTool.scaleImage(LoadSave.getSpriteAtlas(LoadSave.PLAYER_JUMPING_LEFT), 128, 128);
    }

    public void update(double dt) {
        move(dt);
        updateHitbox();
        updateState();
    }

    private void move(double dt) {
        float tx;
        float ty;

        xVel = calculateXVel(dt);
        yVel = calculateYVel(dt);

        updateDirection(xVel);

        tx = x + xVel;
        ty = y + yVel;

        if (!collidesWithSolidArea((int) tx, (int) y)) {
            x = tx;
        }
        if (!collidesWithSolidArea((int) x, (int) ty)) {
            y = ty;
        } else if (isFloating((int) x, (int) y)) {
            System.out.println("floating");
        } else {
            yVel = 0;
        }
    }

    private void updateDirection(float xVel) {
        if (xVel > 0) {
            direction = RIGHT;
        }
        if (xVel < 0) {
            direction = LEFT;
        }
    }

    private void updateState() {
        System.out.println(yVel);
        if (isInAir((int) x, (int) y) && yVel > 0) {
            state = PlayerState.FALL;
        } else if (isInAir((int) x, (int) y) && yVel <= 6) {
            state = PlayerState.JUMP;
        } else {
            state = PlayerState.IDLE;
        }
    }

    private boolean collidesWithSolidArea(int x, int y) {
        int c = 0;
        for (SolidArea sa : gp.getSolidAreas()) {
            if (x + WIDTH < sa.getX() || x > sa.getX() + sa.getWidth() || y + HEIGHT - 1 < sa.getY()
                    || y > sa.getY() + sa.getHeight()) {
                c++;
            }
        }
        if (c == gp.getSolidAreas().size()) {
            return false;
        }
        return true;
    }

    private boolean isInAir(int x, int y) {
        for (SolidArea sa : gp.getSolidAreas()) {
            if (sa.getBounds().contains(new Point(x, y + HEIGHT))
                    || sa.getBounds().contains(new Point(x + WIDTH, y + HEIGHT))) {
                return false;
            }
        }
        return true;
    }

    private float calculateXVel(double dt) {
        xVel = 0;
        if (gp.getKeyboardInput().getAPressed()) {
            xVel -= 1;
        }
        if (gp.getKeyboardInput().getDPressed()) {
            xVel += 1;
        }
        return (float) (xVel * SPEED * dt);
    }

    private float calculateYVel(double dt) {
        if (!isInAir((int) x, (int) y)) {
            if (gp.getKeyboardInput().getSpacePressed()) {
                return -25;
            } else {
                return 0;
            }
        } else if (yVel >= TERMINAL_VEL) {
            return TERMINAL_VEL;
        }
        return yVel + FALL_SPEED;
    }

    private boolean isFloating(int x, int y) {
        for (int i = 1; i >= TERMINAL_VEL; i++) {
            for (SolidArea sa : gp.getSolidAreas()) {
                if ((sa.getBounds().contains(new Point(x, y + HEIGHT + i))
                        || sa.getBounds().contains(new Point(x + WIDTH, y + HEIGHT + i)))) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateHitbox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    private void drawHitbox(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.drawRect((int) x, (int) y, WIDTH, HEIGHT);
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(animations[direction][state.getValue()], (int) (x - IMAGE_OFFSET), (int) y, null);
        drawHitbox(g2);
    }
}
