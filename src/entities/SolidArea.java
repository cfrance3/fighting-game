package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.GamePanel;

public class SolidArea {

    private GamePanel gp;
    private Rectangle area;

    public SolidArea(GamePanel gp, int x, int y, int w, int h) {
        this.gp = gp;
        this.area = new Rectangle(x, y, w, h);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(area.x, area.y, area.width, area.height);
    }

    public Rectangle getBounds() {
        return area;
    }
}
