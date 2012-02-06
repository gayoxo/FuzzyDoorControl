package controler;

import model.xfuzzy.RAIFinalv14;
import view.Main.VisualInterface;

public class Constantes {

	public static int GranularityTime=1000;
	private static VisualInterface System;
	private static boolean debug=false;
	protected static model.xfuzzy.FuzzyInferenceEngine FIE= new RAIFinalv14();
	
	
	public static void setGranularityTime(int granularityTime) {
		GranularityTime = granularityTime;
	}
	
	public static int getGranularityTime() {
		return GranularityTime;
	}
	
	public static VisualInterface getSystem() {
		return System;
	}
	
	public static void setSystem(VisualInterface system) {
		System = system;
	}
	
	public static boolean isDebug() {
		return debug;
	}
	
	public static void setDebug(boolean debug) {
		Constantes.debug = debug;
	}
	
	public static void setFIE(model.xfuzzy.FuzzyInferenceEngine fIE) {
		FIE = fIE;
	}
	
	public static model.xfuzzy.FuzzyInferenceEngine getFIE() {
		return FIE;
	}
}
