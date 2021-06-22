package example;

import java.awt.GraphicsConfiguration;
import javax.swing.JFrame;

public class JFrameExample {
	public static void demo(int x, int y) {
		System.out.println("x= " + x + " y=" + y);

	}

	static GraphicsConfiguration gc;

	public static void main(String[] args) {
		int x = 10;
		int y = 20;
		demo(x, y);
		JFrame frame = new JFrame(gc);
		frame.setTitle("Welcome to JavaTutorial.net");
		gc.getBounds();
		gc.getDevice();
		frame.setVisible(true);
	}
}