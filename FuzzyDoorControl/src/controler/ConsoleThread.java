package controler;

import java.util.Date;
import java.util.Random;


import model.stadistics.Stadistics;

public class ConsoleThread {

	
	private static int clock=59;
	private static int[] PxS=new int[60];
	private static int DifTemp=0;
	private static int[]PxSAcum=new int[60];
	private static model.xfuzzy.FuzzyInferenceEngine FIE=Constantes.getFIE();
	private long TimeMil;
	private enum Estado{Cerrada,Abierta,Cerrandose};
	private static Estado EstadoActial;
	private static int Timer=0;
	//Como no cuento las imagenes...
	private int Estado_de_cierre=10;
	
	private int Ciclos;

	public ConsoleThread(int CiclosIn) {
		Ciclos=CiclosIn;
	}
	
	public void run()
	{
		Date fecha = new Date();
		Constantes.getSystem().addLineBlack("System Start At: ");
		Constantes.getSystem().addLineBlack(fecha.toString());
		TimeMil=System.currentTimeMillis();
		EstadoActial=Estado.Cerrada;
	for (int i = 0; i < Ciclos; i++) {
		step();
	}	
	}
	
	private void addPosiciones() {
		int pos=clock;
		PxSAcum[pos]=PxS[pos];
		
	}
	
	private float SumaPosiciones() {
		float sol=0;
		StringBuffer SB=new StringBuffer();
		if (Constantes.isDebug())SB.append("[");
		int count=0;
		int act=clock;
		while (count<30)
		{
			if (act>=60) act=0;
			int data=PxSAcum[act];
			sol=sol+data;
			if (Constantes.isDebug())SB.append(data +" ");
			act++;
			count++;
		}
		if (Constantes.isDebug())SB.append("]");
		Constantes.getSystem().addLineBlack(SB.toString());
		return sol;
	}
	
	
	public void step()
	{
		
		
		
		
		//////////////////PUERTA
		if (Timer!=0)
		{
		Timer--;
		}
	else {
		if (EstadoActial==Estado.Abierta)
		{
			EstadoActial=Estado.Cerrandose;
			Estado_de_cierre=PuertaAnimacion.lenght()-2;
		
		}
		else 
			if (EstadoActial==Estado.Cerrandose)
			{
				if (Estado_de_cierre==0)
				EstadoActial=Estado.Cerrada;
				else Estado_de_cierre--;	
			}else
		
			EstadoActial=Estado.Cerrada;
			
		
		}
		
	
		///////////SISTEMA
	
		float DifTempFloat=DifTemp<0 ? -DifTemp: DifTemp;
		addPosiciones();
		float Gentef=SumaPosiciones();
		
		if (Constantes.isDebug())Constantes.getSystem().addLineBlack("Dif :" + DifTempFloat + " People :" + Gentef);
		double[] a ={DifTempFloat,Gentef}; 
		//Ejecutamos las funciones de xfuzzy.
		FIE=Constantes.getFIE();
		double[] b = FIE.crispInference(a);

		int BDouble=(int)(b[0]*10000);
		Double CInt=(double) BDouble;
		b[0]=CInt/10000;
		
		BDouble=(int)(b[1]*10000);
		CInt=(double) BDouble;
		b[1]=CInt/10000;
		
		BDouble=(int)(b[2]*10000);
		CInt=(double) BDouble;
		b[2]=CInt/10000;
		
		
		
		int can=PxSAcum[clock];
		if (can>0)
			{
				
			Long A=System.currentTimeMillis();
			A=A-TimeMil;
			int B=(int)(A*100);
			Long C=new Long(B);
			A=C/100;
			if (EstadoActial!=Estado.Abierta){
				Timer=((int)b[0]);
				if (can==1) 
					Constantes.getSystem().addLineRed("A Person: " + A + "s." );
				else Constantes.getSystem().addLineRed(can +" Persons: " + A + "s."  );
				Stadistics.addClosed(can);
			}
			else {
				if (can==1) 
					Constantes.getSystem().addLineGreen("A Person: " + A + "s." );
				else Constantes.getSystem().addLineGreen(can +" Persons: " + A + "s."  );
				Stadistics.addOpened(can);
			}	
			
			OpenMandatori();	
			
			}
		else {
			if (EstadoActial==Estado.Abierta) 	
				Stadistics.addTotal();
		}
	
		
		if (Constantes.isDebug())Constantes.getSystem().addLineBlack("Pot: " + b[0] + " Min: " + b[1] +" Luck:" + b[2]);
		
		clock--;
		if (clock==-1) clock=59;
	
	

		
		
		
		
	
	
	}

	private void OpenMandatori() {
		if (EstadoActial!=Estado.Abierta)
			{
			EstadoActial=Estado.Abierta;	
			
			}
	}

	public static void setPxM(int VpxS) {
		int[] tempPXS=new int[60];
		for (int i = 0; i < tempPXS.length; i++) {
			tempPXS[i]=0;
		}
		Random R=new Random();
		for (int i = 0; i < VpxS; i++) {
			int posNewPerson=R.nextInt(60);
			tempPXS[posNewPerson]++;
			
		}
		PxS=tempPXS;
		
	}

	public static void setDifTemp(int i) {
		DifTemp=i;
		
	}
	
	
}
