package PingPong;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PingPong extends JFrame implements KeyListener,Runnable {
    JFrame f = new JFrame();
    JPanel jp;

    private Thread animatorThread;
    private Thread currentThread = null;

    boolean frozen = false, called=false;

    int x = 10, y = 10;

    public PingPong() {
        addKeyListener(this);
        f.setTitle("PingPong");
        f.setSize(853, 600);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);

        jp = new GPanel(this.x,this.y);
        f.add(jp);
        f.setVisible(true);

        //start();
    }

    public void keyPressed( KeyEvent e ) {
        /*if (e.getKeyCode() == KeyEvent.VK_LEFT) this.called = pp.movePaddleLeft();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) this.called = pp.movePaddleRight();
        if (e.getKeyCode() == KeyEvent.VK_ENTER) this.called = msg.setGameActive();*/
        if (e.getKeyCode() == KeyEvent.VK_SPACE) start();
        //this.called = pp.ballSetStart();
    }
    public void keyReleased( KeyEvent e ) {	}
    public void keyTyped( KeyEvent e ) { }

    private void start() {
        if (animatorThread == null)
            animatorThread = new Thread(this);

        animatorThread.start();
    }

    public static void main(String[] args) {
        PingPong g1 = new PingPong();
        g1.setVisible(true);
    }

    public void run() {
        executeThread();
    }

    public Boolean testThread() {
        return this.currentThread == animatorThread;
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
        public GPanel(int x,int y) {
            this.x = x;
            this.y = y;
            this.up = false;
            f.setPreferredSize(new Dimension(300, 300));
        }

        @Override
        public void paintComponent(Graphics g) {

            if(this.x == 200)
                this.up = true;
            if(this.x == 0)
                this.up = false;

            if (this.up) {
                this.x--;
                this.y--;
            }
            else {
                this.x++;
                this.y++;
            }

            g.fillOval(this.x, this.y, 100, 100);
        }
    }
}

