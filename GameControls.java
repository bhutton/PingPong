package PingPong;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

//import GrapherApp.PlotGraphics;

public class GameControls extends JApplet implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Container c = getContentPane();
	
	JLabel 			xLabel, yLabel, numLabel;
	JTextField 		destX, destY, destNum;
	JButton			showPlayAgain;
	
	public void init() {
		
		//showButton();
		
	}
	
	public void showButton() {
		final JFrame parent = new JFrame();
        JButton button = new JButton();

        button.setText("Click me to show dialog!");
        parent.add(button);
        parent.pack();
        parent.setVisible(true);

        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String name = JOptionPane.showInputDialog(parent,
                        "What is your name?", null);
            }
        });
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
