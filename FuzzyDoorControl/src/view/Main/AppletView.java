package view.Main;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.DropMode;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.util.logging.LoggingPermission;

import javax.swing.JApplet;
import javax.swing.JEditorPane;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JPanel;

import view.ControlPanel;
import view.LogPanel;

import controler.Constantes;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AppletView extends JApplet implements VisualInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1495835692375411981L;
	private static AppletView Yo=null;
	private static StringBuffer SB;
	private LogPanel Log;
	private ControlPanel AP;

	public AppletView() {
		
		Yo=this;
		Constantes.setSystem(Yo);
		Log=new LogPanel();
		SB=new StringBuffer();
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		AP = new ControlPanel();
		getContentPane().add(AP);
		Log.setMinimumSize(new Dimension(200, 348));
		getContentPane().add(Log);
		setMinimumSize(new Dimension(500+200, 348+80));
	//	pack();
	}


	

	
	public static AppletView getYo() {
		return Yo;
	}
	
	public static void setYo(AppletView yo) {
		Yo = yo;
	}
	
	public void addLineRed(String lineadd)
	{
		if (SB!=null)
			SB.insert( 0, "<font size=\"3\" color=\"red\">"+lineadd+"</font><br>");
		if ((Yo!=null)) 
		{
		Yo.Log.getLogPane().setText(SB.toString());
		}
	}
	
	public void addLineGreen(String lineadd)
	{
		if (SB!=null)
			SB.insert( 0,"<font size=\"3\" color=\"green\">"+lineadd+"</font><br>");
		if ((Yo!=null)) 
		{
		Yo.Log.getLogPane().setText(SB.toString());
		}
	}
	
	public void addLineBlack(String lineadd)
	{

	if (SB!=null)	
		SB.insert( 0,"<font size=\"3\" color=\"black\">"+lineadd+"</font><br>");
	if ((Yo!=null)) 
		{	
		Yo.Log.getLogPane().setText(SB.toString());
		}
	}
	
	
	@Override
	public void init() {

			super.init();
			setSize(new Dimension(500+200, 348+80));
			setMinimumSize(new Dimension(500+200, 348+80));
	}

}
