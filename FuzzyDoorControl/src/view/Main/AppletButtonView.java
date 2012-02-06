package view.Main;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.DropMode;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JButton;


import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AppletButtonView extends JApplet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4142691666698094597L;




	public AppletButtonView() {
		
		JButton btnNewButton = new JButton("Window Mode");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WindowView.main(new String[0]);
			}
		});
		getContentPane().add(btnNewButton, BorderLayout.CENTER);
		
	}
	
	
	
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		setMinimumSize(new Dimension(150,30));
		setSize(new Dimension(150,30) );

	}
}
