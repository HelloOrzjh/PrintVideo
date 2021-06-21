package Orzjh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.bytedeco.*;
import org.bytedeco.javacv.*;

public class GUI extends JFrame {
	String path;
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension d = toolkit.getScreenSize();
	int height = d.height;
	int width = d.width;
	
	JPanel panel = new JPanel(new BorderLayout());
	JButton startButton = new JButton("¿ªÊ¼");
	
	GUI(String path) {
		super("Orzjh");
		
//		System.out.println(height + " " + width);
		
		this.path = path;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width / 3, height / 3);
		panel.setSize(width / 3, height / 3);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		setStartButton();
		this.setContentPane(panel);
	}
	
	public void setStartButton() {
		startButton.setSize(new Dimension(width / 4, height / 4));
		startButton.setFont(new Font("ËÎÌå", Font.PLAIN, 60));
		
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PrintVideo(path);
			}
		});
		
		panel.add(startButton, BorderLayout.CENTER);
		
	}
	
	public static void main(String[] args) {
		String path = "./video/ChaoJiMinGan.mp4";
		new GUI(path);
	}
	
	
}
