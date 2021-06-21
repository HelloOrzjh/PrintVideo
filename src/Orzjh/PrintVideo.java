package Orzjh;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

public class PrintVideo implements Runnable {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension d = toolkit.getScreenSize();
	int height = 1000;
	int width = 1920;
	
	String path;
	String picDir = "./img";
	Thread thread;
	
	PrintVideo(String path) {
		this.path = path;
		
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {		

		try {
//			prework();
			play();
		} catch (Exception e) {}
	}
	
	public void prework() throws Exception {
		FFmpegFrameGrabber grab = new FFmpegFrameGrabber(path);
		Frame frame = null;
		grab.start();
		int ftp = grab.getLengthInFrames();
//		System.out.println(ftp / grab.getFrameRate() / 60);
		
//		ftp = 500;
		
		int flag = 0;
		while(flag <= ftp) {
			frame = grab.grabImage();
			if(frame != null) {
				String fileName = picDir + "/img_" + String.valueOf(flag) + ".jpg";		
				File file = new File(fileName);
				BufferedImage buffer = new Java2DFrameConverter().getBufferedImage(frame);
				ImageIO.write(buffer, "jpg", file);
				String output = new TransformImage(fileName, flag).transform();
//				System.out.println(output);
//				System.out.println(flag);
			}
			flag++;
		}
		
		grab.stop();
	}
	
	public void play() throws Exception {
		
		MultiPlayer multiPlayer = new MultiPlayer(path);
		Thread[] playThread = new Thread[10];
		for(int i = 0; i < 10; i++) {
			playThread[i] = new Thread(multiPlayer);
			playThread[i].start();
		} 
	}
/*		
	public void play() throws Exception {
		jframe.setVisible(true);		
		jtextarea = new JTextArea(width * 2, height * 8);
//		System.out.println(width + " " + height);
		jtextarea.setSize(new Dimension(width, height));
		jpanel.setSize(new Dimension(width, height));
		jframe.setSize(new Dimension(width, height));
		jtextarea.setEditable(false);
		jtextarea.setFont(new Font("Consolas", Font.BOLD, 5));
		jpanel.add(jtextarea);
		jframe.setContentPane(jpanel);
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		FFmpegFrameGrabber grab = new FFmpegFrameGrabber(path);
		Frame frame = null;
		grab.start();
		int flag = 0;
		int ftp = grab.getLengthInFrames();
		
		long ta, tb;
		Long filelength;
		String encoding = "UTF-8";
		String fileName;
		String text;
		byte[] content;
		File file;
		FileInputStream in;
		
		while(flag <= ftp) {
			ta = System.currentTimeMillis();
			
			frame = grab.grabImage();
			if(frame != null) {	
				fileName = "./output/output_" + flag + ".txt";
				file = new File(fileName);
				filelength = file.length();
				content = new byte[filelength.intValue()];
				try {
					in = new FileInputStream(file);
					in.read(content);
					in.close();
					
					text = new String(content, encoding);
					jtextarea.setText(text);
				} catch (Exception e) {
					
				}
				
			}
			++flag;
			
			tb = System.currentTimeMillis();
			if(1000 / 24 - (tb - ta) > 0) Thread.sleep(1000 / 24 - (tb - ta));
			System.out.println(String.valueOf(System.currentTimeMillis() - ta));
		}
		
		grab.stop();
		jframe.setVisible(false);
	}
*/	
	public static void main(String[] args) {
		String path = "./video/ChaoJiMinGan.mp4";
		new PrintVideo(path);
	}
}
