package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.DropMode;
import java.awt.SystemColor;
import javax.swing.ScrollPaneConstants;

public class LogPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6298119336554564926L;
	private JEditorPane LogPane;

	public LogPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		
		LogPane = new JEditorPane();
		LogPane.setBackground(SystemColor.control);
		LogPane.setDropMode(DropMode.INSERT);
		LogPane.setContentType("text/html");
		LogPane.setEditable(false);
		LogPane.setMinimumSize(new Dimension(200, 348));
		scrollPane.setViewportView(LogPane);
		scrollPane.setMinimumSize(new Dimension(200, 348));
		setMinimumSize(new Dimension(200, 348));
	}
	
	public JEditorPane getLogPane() {
		return LogPane;
	}
	
	

}
