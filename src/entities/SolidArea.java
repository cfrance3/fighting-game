package entities;

import java.awt.Rectangle;

import main.GamePanel;

public class SolidArea {

    private GamePanel gp;
    private Rectangle area;

    public SolidArea(GamePanel gp, int x, int y, int w, int h) {
        this.gp = gp;
        this.area = new Rectangle(x, y, w, h);
    }
}
