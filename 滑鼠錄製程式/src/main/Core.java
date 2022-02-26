package main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

public class Core {

	private static boolean recording = false, playing = false;
	private static ArrayList<MouseData> script = new ArrayList<>();
	private static long lastTime;
	
	public static void main(String[] args) {
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			JOptionPane.showMessageDialog(null, "程式因不明原因出錯，請聯繫作者");

			System.exit(1);
		}

		MouseListener mouse_listener = new MouseListener();
		KeyListener key_listener = new KeyListener();

		GlobalScreen.addNativeMouseListener(mouse_listener);
		GlobalScreen.addNativeKeyListener(key_listener);
		
		JFrame frame = new JFrame();
		frame.setSize(200,120);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextArea label = new JTextArea("按ctrl+[開始錄製\n"
				+ "按ctrl+]停止錄製\n"
				+ "按ctrl+p播放/停止\n"
				+ "按ctrl+\\或直接打叉關閉程式");
		label.setEditable(false);
		frame.add(label);
		frame.setVisible(true);
		
		playScript();
	}
	
	private static void playScript(){
		int now_index = 0;
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e1) {}
		while(true) {
			if(playing) {
				MouseData data =script.get(now_index++%script.size());
				try {
					Thread.sleep(data.SleepTime);
				} catch (InterruptedException e) {}
				
				if(playing) {
					robot.mouseMove(data.X, data.Y);
					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
				}
			}
			else now_index=0;
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {}
		}
	}

	private static class KeyListener implements NativeKeyListener {
		public void nativeKeyTyped(NativeKeyEvent e) {
			if(e.getModifiers() == 2) {	//ctrl	
				switch(e.getKeyChar()) {
				case '[': //[
					recording = true;
					script.clear();
					lastTime = new Date().getTime();
					break;
				case ']': //]
					recording = false;
					break;
				case '\\': //\
//					JOptionPane.showMessageDialog(null, "已成功關閉程式");
					System.exit(0);
					break;
				case 'p': //p
					playing = !playing;
					break;
				}
			}
		}
	}

	private static class MouseListener implements NativeMouseInputListener {
		public void nativeMouseClicked(NativeMouseEvent e) {
			if(recording) {
				script.add(new MouseData(e.getX(), e.getY(), new Date().getTime()-lastTime));
				lastTime = new Date().getTime();
			}
		}
	}
	
	private static class MouseData {
		public final int X,Y;
		public final long SleepTime;
		
		public MouseData(int X, int Y, long SleepTime) {
			this.X=X;
			this.Y=Y;
			this.SleepTime=SleepTime;
		}
		
		public String toString() {
			return "[ "+X+", "+Y+", "+SleepTime+" ]";
		}
	}
}
