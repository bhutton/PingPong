package PingPong;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PingPong extends JFrame implements KeyListener,Runnable {
    JFrame f = new JFrame();
    JPanel jp;

	int appletHeight, appletWidth, incX=1, incY=1;
	private static int delay = 100;
	private Thread animatorThread;
	boolean frozen = false, called=false;
	private Thread currentThread = null;

	Ball pp = new Ball(true, true, 0, 0);
	Messages msg = new Messages();
	Background bg = new Background();
	final File basePath = new File(PingPongOld.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	Level level = new Level();

	String[] backGroundArray = new String[10];
	String backGroundImg = this.basePath + "/../src/PingPong/images/background-1.png";
	int currentBackGroundImage;

    public PingPong() {
        f.setTitle("PingPong");
        f.setSize(853, 600);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);

        jp = new GPanel();
        f.add(jp);
        f.setVisible(true);

        backGroundArray = this.bg.loadBackGrounds(this.basePath);
        this.currentBackGroundImage = 0;

        String 	ballImg = this.basePath + "/../src/PingPong/images/soccer-ball-clipart-no-background-clipart-panda-free-clipart-Ek7jBT-clipart.png",
                brickImg = this.basePath + "/../src/PingPong/images/brick.png",
                paddleImg = this.basePath + "/../src/PingPong/images/paddle.png",
                gameOverImg = this.basePath + "/../src/PingPong/images/free-game-wallpaper-9.jpg",
                gameOverMessage = this.basePath + "/../src/PingPong/images/game-over-png-22.png";

        setSize(853,600);

        Dimension appletSize = this.getSize();
        this.appletHeight = appletSize.height;
        this.appletWidth = appletSize.width;

        setFocusable(true);
        requestFocus();

        bg.setBackgroundImage(backGroundImg);
        bg.setGameOverImage(gameOverImg);
        msg.setGameOverImage(gameOverMessage);
        pp.setBallImage(ballImg);
        pp.setBrickImage(brickImg);
        pp.setPaddleImage(paddleImg);
        pp.setPaddleWidth(174);
        pp.setPaddleHeight(30);
        pp.initializeBrickArray();
        pp.setPaddleMoveAmount(30);
        pp.setPaddleLocation(appletHeight, appletWidth);
        addKeyListener(this);
    }

    public void keyPressed( KeyEvent e ) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) this.called = pp.movePaddleLeft();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) this.called = pp.movePaddleRight();
        if (e.getKeyCode() == KeyEvent.VK_ENTER) this.called = msg.setGameActive();
        if (e.getKeyCode() == KeyEvent.VK_SPACE) this.called = pp.ballSetStart();
    }
    public void keyReleased( KeyEvent e ) {	}
    public void keyTyped( KeyEvent e ) { }

    public void start() {
        if (animatorThread == null)
            animatorThread = new Thread(this);

        animatorThread.start();
    }

    public void startGameIfActive() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        this.currentThread = Thread.currentThread();
    }

    public Boolean testThread() {
        return this.currentThread == animatorThread;
    }


    public Thread getThread() {
        return this.currentThread;
    }

    public static void main(String[] args) {
        PingPong g1 = new PingPong();
        g1.setVisible(true);
    }

    public void run() {
        executeThread();
    }


    private void executeThread() {
        while (!testThread()) {
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                break;
            }
            jp.repaint();
        }
    }

    class GPanel extends JPanel {
        int x,y;
        Boolean up;
        public GPanel() {
            this.x = x;
            this.y = y;
            this.up = false;
            f.setPreferredSize(new Dimension(300, 300));
        }

        @Override
        public void paintComponent(Graphics g) {
            msg.setGameActive();
            startGameIfActive();
            pp.initializeBall();
            msg.setLevel(level.getLevel());
            msg.setLives(level.getLives());

            executeThread();
        }
    }
}

