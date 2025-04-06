package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class LoadSave {

    public static final String PLAYER_IDLE_RIGHT = "res/blankFighterIdleRight.png";
    public static final String PLAYER_FALLING_RIGHT = "res/blankFighterFallingRight.png";
    public static final String PLAYER_JUMPING_RIGHT = "res/blankFighterJumpingRight.png";
    public static final String PLAYER_IDLE_LEFT = "res/blankFighterIdleLeft.png";
    public static final String PLAYER_FALLING_LEFT = "res/blankFighterFallingLeft.png";
    public static final String PLAYER_JUMPING_LEFT = "res/blankFighterJumpingLeft.png";

    public static BufferedImage getSpriteAtlas(String fileName) {

        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);

        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }
}
