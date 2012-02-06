package view.Main;

import java.io.ObjectInputStream.GetField;


import controler.ConsoleThread;
import controler.Constantes;
import controler.SystemThread;

public class ConsoleView implements VisualInterface {
	

	private ConsoleView Yo;
	private boolean ShowEnd;
	private StringBuffer SB;
	
	public ConsoleView(int Ciclos) {
		SB=new StringBuffer();
		ShowEnd=Constantes.isDebug();
		Yo=this;
		Constantes.setSystem(Yo);
		ConsoleThread CT=new ConsoleThread(Ciclos);
		CT.run();
		
	}
	
	@Override
	public void addLineRed(String lineadd) {

		if (ShowEnd) SB.append("Error: "+ lineadd + "\n");
	}

	@Override
	public void addLineGreen(String lineadd) {

		if (ShowEnd) SB.append("Success: "+ lineadd + "\n");

	}

	@Override
	public void addLineBlack(String lineadd) {
		if (ShowEnd) SB.append(lineadd + "\n");
		
		

	}
	
	public String getBuffer()
	{
		String out=SB.toString();
		SB=new StringBuffer();
		return out;
	}

	public void cleanBuffer()
	{
	SB=new StringBuffer();	
	}
	
	@Override
	public boolean isVisible() {
		return true;
	}

	public static void main(String[] args) {
		Constantes.setDebug(true);
		ConsoleThread.setPxM(30);
		ConsoleThread.setDifTemp(0);
		ConsoleView CV=new ConsoleView(1);
		System.out.println(CV.getBuffer());
	}
}
