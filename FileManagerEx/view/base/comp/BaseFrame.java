package base.comp;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import base.IClose;
import base.IDesign;

public abstract class BaseFrame extends JFrame implements IDesign, IClose {

	public void setFrame(int w, int h, String title) {
		super.setSize(w, h);
		super.setTitle(title);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLocationRelativeTo(null);
		super.setLayout(new BorderLayout());
		setComp();
		setDesign();
		setAction();
		setList();
	}

	String gettile() {
		return super.getTitle();
	}

	public void settitle(String title) {
		super.setTitle(title);
	}
}
