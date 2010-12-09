package test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import console.Console;

public class GUIMain extends JFrame {
	private static final long serialVersionUID = -8838655167255886843L;
	private Console console;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new GUIMain().setVisible(true);
	}

	public GUIMain() {
		super("Console Emulation Test");
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				dispose();
				System.exit(0);
			}
		});

		console = new Console(20, 80);
		add(console, BorderLayout.CENTER);
//		console.reset();
		pack();
	}
}
