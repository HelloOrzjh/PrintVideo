package Orzjh;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class TransformImage {
	String path;
	int idx;
	
	TransformImage(String path, int idx) {
		this.path = path;
		this.idx = idx;
	}
	
	public String transform() {
		final String base = "M&$B%0eol1v!'=+;:.";
		try {
			File f = new File("./output/output_" + idx + ".txt");
			FileOutputStream fop = new FileOutputStream(f);
			OutputStreamWriter w = new OutputStreamWriter(fop, "UTF-16");
			
			final BufferedImage image = ImageIO.read(new File(path));
//			System.out.println(path + " " + image.getHeight() + " " + image.getWidth());
			for(int y = 0; y < image.getHeight(); y += 16) {
				for(int x = 0; x < image.getWidth(); x += 12) {
					final int pixel = image.getRGB(x, y);
					int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;					
					float gray = 0.299f * r + 0.578f * g + 0.114f * b;
					int idx = Math.round(gray * (base.length() + 1) / 255);
//					System.out.print(idx >= base.length() ? "." : String.valueOf(base.charAt(idx)));
					w.append(idx >= base.length() ? "." : String.valueOf(base.charAt(idx)));
				}
//				System.out.println("\n");
				w.append("\n");
			}
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "./output/output_" + idx + ".txt";
	}
	
}
