package model.stadistics;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class Stadistics {

	private static int Opened=0;
	private static int NoOpened=0;
	private static int TimeNoCrosing=1;

public static void clear(){
					Opened=0;
					NoOpened=0;
					TimeNoCrosing=1;
					}
public static void addOpened(int entran){
	Opened=Opened+entran;
	}
public static void addClosed(int entran) {
	NoOpened=NoOpened+entran;
	
}

public static float geOVSNO()
{
if (Opened==0) return 0;
if (NoOpened==0) return Float.MAX_VALUE;
return ((float)Opened/((float)Opened+NoOpened))*100;	
}
public static void addTotal() {
	TimeNoCrosing++;
	
}

public static float getNoOpened() {
	return ((float)NoOpened);
}

public static float getOpened() {
	return ((float)Opened);
}
public static float getTotal() {
	return TimeNoCrosing;
}
public static float getUsed() {
	if (TimeNoCrosing==0) return Float.MAX_VALUE;
	return (TimeNoCrosing/(Opened+NoOpened));
}

}
