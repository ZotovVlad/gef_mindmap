package com.itemis.gef.tutorial.mindmap.policies;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class HotKeysHandler extends Frame implements KeyListener, WindowListener {

	private int code_last;

	public void init() {
		this.addKeyListener(this);
		this.addWindowListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (code_last == 17 && e.getKeyCode() == 90) {
			// - Undo (Ctrl+z)
			System.out.println("// - Undo (Ctrl+z)");
		}
		if (code_last == 17 && e.getKeyCode() == 82) {
			// - Redo (Ctrl+r)
			System.out.println("// - Redo (Ctrl+r)");
		}
		if (code_last == 17 && e.getKeyCode() == 88) {
			// - New node (Ctrl + x)
			System.out.println("// - New node (Ctrl + x)");
		}
		if (code_last == 17 && e.getKeyCode() == 67) {
			// - New connection (Ctrl + c)
			System.out.println("// - New connection (Ctrl + c)");
		}
		if (e.getKeyCode() == 127) {
			// - Удаление выбранной Node (Delete)
			System.out.println("// - Удаление выбранной Node (Delete)");
		}
		System.out.println(e.getKeyCode());
		System.out.println(code_last);
		code_last = e.getKeyCode();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

}
