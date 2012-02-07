package model.stadistics;

import java.util.Date;

import model.singleton.Singelton;
import model.xfuzzy.RAIFinalv14;
import controler.ConsoleThread;
import controler.Constantes;
import view.Main.ConsoleView;

public class TestMejoraResumido {
	
	private static boolean debug=false;
	private static Singelton S00S=new Singelton();
	private static Singelton S60S=new Singelton();
	private static Singelton S15S=new Singelton();
	private static RAIFinalv14 MyFZS=new RAIFinalv14();
	private static ConsoleView CV;
	
	public static void main(String[] args) {
		Constantes.setDebug(true);
		S60S.setEspera(60);
	S15S.setEspera(15);
		Date fecha = new Date();
		System.out.println (fecha);
		
		RunDifTemLow();
		System.out.println("////////////////////////////////////////////////////////////////////////////");
		RunDifTemMidlle();
		System.out.println("////////////////////////////////////////////////////////////////////////////");
		RunDifTemHigh();
		System.out.println("////////////////////////////////////////////////////////////////////////////");
		
		
		
		
		
		System.out.println("End");
		
		
		
		System.exit(0);
		
	}
	
	
private static void RunDifTemLow() {
		
		System.out.println("");
		System.out.println("////////////////////");
		//Diferencia de temperatura baja y poca gente (DIF:0 Stream:6/60->3/30)
		ConsoleThread.setDifTemp(0);
		ConsoleThread.setPxM(6);
		System.out.println("Low Diference Of Temperature and Low Stream of People ");	
		System.out.println("////////////////////");
		Ejecute();
		
		
		
		System.out.println("");
		System.out.println("////////////////////");
		//Diferencia de temperatura baja y media gente (DIF:0 Stream:30/60->15/30)
		ConsoleThread.setDifTemp(0);
		ConsoleThread.setPxM(30);
		System.out.println("Low Diference Of Temperature and middle Stream of People ");	
		System.out.println("////////////////////");
		Ejecute();
		
		
		System.out.println("");
		System.out.println("////////////////////");
		//Diferencia de temperatura baja y Alta gente (DIF:0 Stream:70/60->35/30)
		ConsoleThread.setDifTemp(0);
		ConsoleThread.setPxM(70);
		System.out.println("Low Diference Of Temperature and High Stream of People ");	
		System.out.println("////////////////////");
		Ejecute();
		
		
		
	}
private static void RunDifTemMidlle() {
	System.out.println("");
	System.out.println("////////////////////");
	//Diferencia de temperatura Media y poca gente (DIF:12 Stream:6/60->3/30)
	ConsoleThread.setDifTemp(12);
	ConsoleThread.setPxM(6);
	System.out.println("Middle Diference Of Temperature and Low Stream of People ");	
	System.out.println("////////////////////");
	Ejecute();
	
	
	
	System.out.println("");
	System.out.println("////////////////////");
	//Diferencia de temperatura Media y media gente (DIF:12 Stream:30/60->15/30)
	ConsoleThread.setDifTemp(12);
	ConsoleThread.setPxM(30);
	System.out.println("Middle Diference Of Temperature and middle Stream of People ");	
	System.out.println("////////////////////");
	Ejecute();
	
	
	System.out.println("");
	System.out.println("////////////////////");
	//Diferencia de temperatura Media y Alta gente (DIF:12 Stream:70/60->35/30)
	ConsoleThread.setDifTemp(12);
	ConsoleThread.setPxM(70);
	System.out.println("Middle Diference Of Temperature and High Stream of People ");	
	System.out.println("////////////////////");
	Ejecute();
}


	private static void RunDifTemHigh() {
		System.out.println("");
		System.out.println("////////////////////");
		//Diferencia de temperatura Alta y poca gente (DIF:12 Stream:6/60->3/30)
		ConsoleThread.setDifTemp(21);
		ConsoleThread.setPxM(6);
		System.out.println("High Diference Of Temperature and Low Stream of People ");	
		System.out.println("////////////////////");
		Ejecute();
			
		
		System.out.println("");
		System.out.println("////////////////////");
		//Diferencia de temperatura Alta y media gente (DIF:21 Stream:30/60->15/30)
		ConsoleThread.setDifTemp(21);
		ConsoleThread.setPxM(30);
		System.out.println("High Diference Of Temperature and middle Stream of People ");	
		System.out.println("////////////////////");
		Ejecute();
		
		
		System.out.println("");
		System.out.println("////////////////////");
		//Diferencia de temperatura Alta y Alta gente (DIF:21 Stream:70/60->35/30)
		ConsoleThread.setDifTemp(21);
		ConsoleThread.setPxM(70);
		System.out.println("High Diference Of Temperature and High Stream of People ");	
		System.out.println("////////////////////");
		Ejecute();
		
	}

	private static void Ejecute() {
		System.out.print("Singleton Waiting Time 00s : ");
		Constantes.setFIE(S00S);
		Stadistics.clear();
		CV=new ConsoleView(10000);	
		System.out.println(Stadistics.geOVSNO()/Stadistics.getTotal());
		if (debug) System.out.println(CV.getBuffer());
		
		
		
		
		System.out.print("Singleton Waiting Time 15s : ");
		Constantes.setFIE(S15S);
		Stadistics.clear();
		CV=new ConsoleView(10000);	
		System.out.println(Stadistics.geOVSNO()/Stadistics.getTotal());
		if (debug) System.out.println(CV.getBuffer());
		
		
		System.out.print("Singleton Waiting Time 60s : ");
		Constantes.setFIE(S60S);
		Stadistics.clear();
		CV=new ConsoleView(10000);	
		System.out.println(Stadistics.geOVSNO()/Stadistics.getTotal());
		if (debug) System.out.println(CV.getBuffer());
		

		
		System.out.print("My FuzzySystem             : ");
		Constantes.setFIE(MyFZS);
		Stadistics.clear();
		CV=new ConsoleView(10000);	
		System.out.println(Stadistics.geOVSNO()/Stadistics.getTotal());
		if (debug) System.out.println(CV.getBuffer());
		System.out.println("////////////////////");
		
	}

	

	

}
