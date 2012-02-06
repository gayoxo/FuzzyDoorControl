package controler;

import javax.swing.ImageIcon;

public class PuertaAnimacion {

	private final static ImageIcon[] Imagenes={new ImageIcon(view.ControlPanel.class.getResource("/icons/Close.gif")),
		new ImageIcon(view.ControlPanel.class.getResource("/icons/CloseOpen0.gif")),
		new ImageIcon(view.ControlPanel.class.getResource("/icons/CloseOpen1.gif")),
		new ImageIcon(view.ControlPanel.class.getResource("/icons/CloseOpen2.gif")),
		//new ImageIcon(view.Window.class.getResource("/icons/CloseOpen2.gif")),
		new ImageIcon(view.ControlPanel.class.getResource("/icons/Open.gif"))};
	
	private static int actual = 0;

	public static ImageIcon Closing()
	{
		if (actual!=0) actual--;
		return Imagenes[actual];
		 
	}
	
	public static ImageIcon Opening()
	{
		if (actual<Imagenes.length-1) actual=((actual+1)*2)-1;
		if (actual>Imagenes.length-1) actual=Imagenes.length-1;
		return Imagenes[actual];
		 
	}
	
	public static boolean final_()
	{
		return actual==Imagenes.length;
	}

	public static int lenght() {
		return Imagenes.length;
	}
}
