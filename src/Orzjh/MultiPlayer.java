package Orzjh;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

public class MultiPlayer implements Runnable {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension d = toolkit.getScreenSize();
	int height = 868;
	int width = 1624;
	int flag = 0;
	
	String path;
	String picDir = "./img";
	Thread thread;
	JFrame jframe = new JFrame();
	JPanel jpanel = new JPanel(new BorderLayout());
	JTextArea jtextarea = null;
	FFmpegFrameGrabber grab;
	Frame frame = null;
	
	MultiPlayer(String path) {
		this.path = path;
		
		jframe.setVisible(true);
		jtextarea = new JTextArea(width * 2, height * 8);
//		System.out.println(width + " " + height);
		jtextarea.setSize(new Dimension(width, height));
		jpanel.setSize(new Dimension(width, height));
		jframe.setSize(new Dimension(width, height));
		jtextarea.setEditable(false);
		jtextarea.setFont(new Font("Consolas", Font.BOLD, 9));
		jpanel.add(jtextarea);
		jframe.setContentPane(jpanel);
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		grab = new FFmpegFrameGrabber(path);
		try {
			grab.start();
		} catch (Exception e) {}
	}
	
	@Override
	public void run() {
		try {
			int ftp = grab.getLengthInFrames();
			
			long ta, tb;
			Long filelength;
			String encoding = "UTF-8";
			String fileName;
			String text;
			byte[] content;
			File file;
			FileInputStream in;
			BufferedInputStream buffer;
			
			while(flag <= ftp) {
				try {
					synchronized(this) {
						ta = System.currentTimeMillis();
						frame = grab.grabImage();
						if(frame != null) {	
							fileName = "./output/output_" + flag + ".txt";
							file = new File(fileName);
							filelength = file.length();
							content = new byte[filelength.intValue()];
							in = new FileInputStream(file);
							buffer = new BufferedInputStream(in);
							buffer.read(content);
							buffer.close();
							text = new String(content, encoding);
							jtextarea.setText(text);
						}
						++flag;	
						tb = System.currentTimeMillis();
						if(1000 / 24 - (tb - ta) > 0) Thread.sleep(1000 / 24 - (tb - ta));
						wait(300);
					}
				} catch (Exception e) {}
			}
			grab.stop();
			jframe.setVisible(false);
		} catch (Exception e) {}
	}
}
