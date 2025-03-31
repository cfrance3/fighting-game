import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;

public class GamePanel extends JPanel implements Runnable {

    // WINDOW SETTINGS
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final int WIDTH = (int) ge.getMaximumWindowBounds().getWidth();
    public final int HEIGHT = (int) ge.getMaximumWindowBounds().getHeight();
    public final int FPS = 120;

    Thread gameThread;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.GRAY);
        setDoubleBuffered(true);
        setFocusable(true);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
}
