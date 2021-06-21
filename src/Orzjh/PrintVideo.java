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
			prework();
			play();
		} catch (Exception e) {}
	}
	
	public void prework() throws Exception {
		FFmpegFrameGrabber grab = new FFmpegFrameGrabber(path);
		Frame frame = null;
		grab.start();
		int ftp = grab.getLengthInFrames();
		
		int flag = 0;
		while(flag <= ftp) {
			frame = grab.grabImage();
			if(frame != null) {
				String fileName = picDir + "/img_" + String.valueOf(flag) + ".jpg";		
				File file = new File(fileName);
				BufferedImage buffer = new Java2DFrameConverter().getBufferedImage(frame);
				ImageIO.write(buffer, "jpg", file);
				String output = new TransformImage(fileName, flag).transform();
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

	public static void main(String[] args) {
		String path = "./video/ChaoJiMinGan.mp4";
		new PrintVideo(path);
	}
}
