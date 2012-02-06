package model.singleton;

import model.xfuzzy.FuzzyInferenceEngine;
import model.xfuzzy.MembershipFunction;

public class Singelton implements FuzzyInferenceEngine {

	private double espera=1;
	
	public Singelton() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public double[] crispInference(double[] input) {
		return new double[]{espera,espera,espera};
	}

	@Override
	public double[] crispInference(MembershipFunction[] input) {
		return new double[]{espera,espera,espera};
	}

	@Override
	public MembershipFunction[] fuzzyInference(double[] input) {
		return null;
	}

	@Override
	public MembershipFunction[] fuzzyInference(MembershipFunction[] input) {
		return null;
	}

	public void setEspera(double espera) {
		this.espera = espera;
	}
	
	public double getEspera() {
		return espera;
	}
}
