package model.stadistics;

import java.util.Date;

import model.singleton.Singelton;
import model.xfuzzy.RAIFinalv14;
import controler.ConsoleThread;
import controler.Constantes;
import view.Main.ConsoleView;

public class TestMejoraResumidoResumido {
	
	private static boolean debug=false;
	private static Singelton S00S=new Singelton();
	private static Singelton S60S=new Singelton();
	private static Singelton S15S=new Singelton();
	private static RAIFinalv14 MyFZS=new RAIFinalv14();
	private static ConsoleView CV;
	
	public static void main(String[] args) {
		Constantes.setDebug(true);
		S60S.setEspera(30);
	S15S.setEspera(5);
	//	Date fecha = new Date();
		//System.out.println (fecha);
		
	for (int i = 0; i < 10; i++) {
		RunDifTemLow();
		//System.out.println("////////////////////////////////////////////////////////////////////////////");
		RunDifTemMidlle();
		//System.out.println("////////////////////////////////////////////////////////////////////////////");
		RunDifTemHigh();
		//System.out.println("////////////////////////////////////////////////////////////////////////////");
		
		System.out.println(" ");
		
	}
		
		
		
		
		//System.out.println("End");
		
		
		
		System.exit(0);
		
	}
	
	
private static void RunDifTemLow() {
		
//		System.out.println("");
//		System.out.println("////////////////////");
		//Diferencia de temperatura baja y poca gente (DIF:0 Stream:6/60->3/30)
		ConsoleThread.setDifTemp(0);
		ConsoleThread.setPxM(6);
//		System.out.println("Low Diference Of Temperature and Low Stream of People ");	
//		System.out.println("////////////////////");
		Ejecute(6,1);
		
		ConsoleThread.setDifTemp(12);
		ConsoleThread.setPxM(6);
//		System.out.println("Middle Diference Of Temperature and Low Stream of People ");	
//		System.out.println("////////////////////");
		Ejecute(6,12);
		
//		
//		System.out.println("");
//		System.out.println("////////////////////");
		//Diferencia de temperatura Alta y poca gente (DIF:12 Stream:6/60->3/30)
		ConsoleThread.setDifTemp(21);
		ConsoleThread.setPxM(6);
//		System.out.println("High Diference Of Temperature and Low Stream of People ");	
//		System.out.println("////////////////////");
		Ejecute(6,21);
		
//		
		
		
		
	}
private static void RunDifTemMidlle() {
//	System.out.println("");
//	System.out.println("////////////////////");
	//Diferencia de temperatura Media y poca gente (DIF:12 Stream:6/60->3/30)
	
	
//	System.out.println("////////////////////");
	//Diferencia de temperatura baja y media gente (DIF:0 Stream:30/60->15/30)
	ConsoleThread.setDifTemp(0);
	ConsoleThread.setPxM(30);
//	System.out.println("Low Diference Of Temperature and middle Stream of People ");	
//	System.out.println("////////////////////");
	Ejecute(30,1);
	
//	System.out.println("");
//	System.out.println("////////////////////");
	//Diferencia de temperatura Media y media gente (DIF:12 Stream:30/60->15/30)
	ConsoleThread.setDifTemp(12);
	ConsoleThread.setPxM(30);
//	System.out.println("Middle Diference Of Temperature and middle Stream of People ");	
//	System.out.println("////////////////////");
	Ejecute(30,12);
	
//	System.out.println("");
//	System.out.println("////////////////////");
	//Diferencia de temperatura Alta y media gente (DIF:21 Stream:30/60->15/30)
	ConsoleThread.setDifTemp(21);
	ConsoleThread.setPxM(30);
//	System.out.println("High Diference Of Temperature and middle Stream of People ");	
//	System.out.println("////////////////////");
	Ejecute(30,21);
	

}


	private static void RunDifTemHigh() {

//		System.out.println("////////////////////");
		//Diferencia de temperatura baja y Alta gente (DIF:0 Stream:70/60->35/30)
		ConsoleThread.setDifTemp(0);
		ConsoleThread.setPxM(70);
//		System.out.println("Low Diference Of Temperature and High Stream of People ");	
//		System.out.println("////////////////////");
		Ejecute(70,1);
		

//		System.out.println("");
//		System.out.println("////////////////////");
		//Diferencia de temperatura Media y Alta gente (DIF:12 Stream:70/60->35/30)
		ConsoleThread.setDifTemp(12);
		ConsoleThread.setPxM(70);
//		System.out.println("Middle Diference Of Temperature and High Stream of People ");	
//		System.out.println("////////////////////");
		Ejecute(70,12);
		
//		System.out.println("");
//		System.out.println("////////////////////");
		//Diferencia de temperatura Alta y Alta gente (DIF:21 Stream:70/60->35/30)
		ConsoleThread.setDifTemp(21);
		ConsoleThread.setPxM(70);
//		System.out.println("High Diference Of Temperature and High Stream of People ");	
//		System.out.println("////////////////////");
		Ejecute(70,21);
		
	}

	private static void Ejecute(int i, float j) {
		//System.out.print("Singleton Waiting Time 00s : ");
		Constantes.setFIE(S00S);
		Stadistics.clear();
		CV=new ConsoleView(10000);	
		//System.out.println(Stadistics.getOpened()/Stadistics.getTotal());
		if (debug) System.out.println(CV.getBuffer());
		
		
		
		
		
		//System.out.print("Singleton Waiting Time 15s : ");
		Constantes.setFIE(S15S);
		Stadistics.clear();
		CV=new ConsoleView(10000);	
		float S15SInt=getData(i,j);
		if (debug) System.out.println(CV.getBuffer());
		
		//System.out.print("Singleton Waiting Time 60s : ");
		Constantes.setFIE(S60S);
		Stadistics.clear();
		CV=new ConsoleView(10000);	
		float S60SInt=getData(i,j);;
		if (debug) System.out.println(CV.getBuffer());
		

		//System.out.print("My FuzzySystem             : ");
		Constantes.setFIE(MyFZS);
		Stadistics.clear();
		CV=new ConsoleView(10000);	
		float MyFZSInt=getData(i,j);;
		if (debug) System.out.println(CV.getBuffer());
//		System.out.println("////////////////////");
		
		
		System.out.print(S15SInt/MyFZSInt);
		System.out.print(" ");
		System.out.println(S60SInt/MyFZSInt);

		
	}


	private static float getData(int i, float j) {
		return ((Stadistics.getOpened()*i)/((Stadistics.getTotal()/j)));
	}

	

	

}
