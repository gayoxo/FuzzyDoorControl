package model.xfuzzy;

public class FuzzySingleton implements MembershipFunction {
 private double value;

 public FuzzySingleton(double value) { this.value = value; }
 public double getValue() { return this.value; }
 public double compute(double x) { return (x==value? 1.0: 0.0); }
}
