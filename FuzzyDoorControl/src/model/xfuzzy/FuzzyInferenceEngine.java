package model.xfuzzy;

public interface FuzzyInferenceEngine {
 public double[] crispInference(double[] input);
 public double[] crispInference(MembershipFunction[] input);
 public MembershipFunction[] fuzzyInference(double[] input);
 public MembershipFunction[] fuzzyInference(MembershipFunction[] input);
}
