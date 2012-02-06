package controler;

import java.util.Date;
import java.util.Random;

import view.Main.WindowView;

import model.stadistics.Stadistics;
import model.xfuzzy.RAIFinalv14;


public class SystemThread implements Runnable {

	public class IntegerPointer
	{
		private Integer clock=10;
		
		public IntegerPointer(int i) {
			clock=i;
		}

		public Integer getClock() {
			return clock;
		}
		
		public void setClock(Integer clock) {
			this.clock = clock;
		}
	}
	
	public class IntegerArrayPointer
	{
		private Integer[] Array= new Integer[60];
		private final int MAX=60;
		
		public IntegerArrayPointer() {
			Array= new Integer[60];
			for (int i = 0; i < Array.length; i++) {
				Array[i]=0;
			}
		}

public Integer[] getArray() {
	return Array;
}

public void setArray(Integer[] array) {
	Array = array;
}

public int getMAX() {
	return MAX;
}


	}
	
	private static IntegerPointer clock;
	private static IntegerArrayPointer PxS;
	private static IntegerPointer DifTemp;
	private static IntegerArrayPointer PxSAcum;
	protected model.xfuzzy.FuzzyInferenceEngine FIE;
	private long TimeMil;
	
	public SystemThread() {
		clock = new IntegerPointer(59);
		PxS=new IntegerArrayPointer();
		DifTemp=new IntegerPointer(0);
		PxSAcum=new IntegerArrayPointer();
		FIE=Constantes.getFIE();
	}
	
	
	@Override
	public void run() {
		
		while (view.ControlPanel.WindowAct==null);
		Date fecha = new Date();
		if (Constantes.isDebug())System.out.println (fecha);
		
		while (!Constantes.getSystem().isVisible()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		Constantes.getSystem().addLineBlack("System Start At: ");
		Constantes.getSystem().addLineBlack(fecha.toString());
		
		TimeMil=System.currentTimeMillis()/1000;
		
		while (true)
		{	
		try {
			Thread.sleep(Constantes.getGranularityTime());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		float DifTempFloat=DifTemp.getClock()<0 ? -DifTemp.getClock(): DifTemp.getClock();
		addPosiciones();
		float Gentef=SumaPosiciones();
		
		if (Constantes.isDebug())System.out.println("Dif :" + DifTempFloat + " People :" + Gentef);
		double[] a ={DifTempFloat,Gentef}; 
		//Ejecutamos las funciones de xfuzzy.
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
		
		((view.ControlPanel)view.ControlPanel.WindowAct).setProductLabel(Double.toString(b[0]));
		((view.ControlPanel)view.ControlPanel.WindowAct).setMinimumLabel(Double.toString(b[1]));
		((view.ControlPanel)view.ControlPanel.WindowAct).setLukasiewiczLabel(Double.toString(b[2]));
		
		int can=getPxSAcum().getArray()[getClock().getClock()];
		if (can>0)
			{
			Long A=System.currentTimeMillis()/1000;
			A=A-TimeMil;
			int B=(int)(A*100);
			Long C=new Long(B);
			A=C/100;
			if (!PuertaThread.isOpen()){
				PuertaThread.setTimer((int)b[0]);
				if (can==1) 
					Constantes.getSystem().addLineRed("A Person: " + A + "s." );
				else Constantes.getSystem().addLineRed(can +" Persons: " + A + "s."  );
			}
			else {
				if (can==1) 
					Constantes.getSystem().addLineGreen("A Person: " + A + "s." );
				else Constantes.getSystem().addLineGreen(can +" Persons: " + A + "s."  );
			}	
			PuertaThread.OpenMandatori();			
			
			}
		
		
		if (Constantes.isDebug())System.out.println("Pot: " + b[0] + " Min: " + b[1] +" Luck:" + b[2]);
		
		if (getClock().getClock()!=0) clock.setClock(clock.getClock()-1);
		else setClock(59);
		}

	}

	
	private void addPosiciones() {
		int pos=clock.getClock();
		getPxSAcum().getArray()[pos]=getPxS().getArray()[pos];
		
	}


	private float SumaPosiciones() {
		float sol=0;
		if (Constantes.isDebug())System.out.print("[");
		int count=0;
		int act=getClock().getClock();
		while (count<30)
		{
			if (act>=60) act=0;
			int data=getPxSAcum().getArray()[act];
			sol=sol+data;
			if (Constantes.isDebug())System.out.print(data +" ");
			act++;
			count++;
		}
		if (Constantes.isDebug())System.out.println("]");
		return sol;
	}


	public static void setClock(Integer clock) {
		SystemThread.getClock().setClock(clock);
	}
	
	public static synchronized IntegerPointer getClock() {
		return clock;
	}
	
	
	public static void setPxM(int VpxS) {
		Integer[] tempPXS=new Integer[60];
		for (int i = 0; i < tempPXS.length; i++) {
			tempPXS[i]=0;
		}
		Random R=new Random();
		for (int i = 0; i < VpxS; i++) {
			int posNewPerson=R.nextInt(60);
			tempPXS[posNewPerson]++;
			
		}
		getPxS().setArray(tempPXS);
	}
	
	
	
	public static synchronized IntegerArrayPointer getPxS() {
		return PxS;
	}
	
	public static synchronized IntegerPointer getDifTemp() {
		return DifTemp;
	}
	
	public static void setDifTemp(int difTemp) {
		getDifTemp().setClock(difTemp);
	}
	
	public static synchronized IntegerArrayPointer getPxSAcum() {
		return PxSAcum;
	}
	
	public void setFIE(model.xfuzzy.FuzzyInferenceEngine fIE) {
		FIE = fIE;
	}

}
