package model.xfuzzy;

//++++++++++++++++++++++++++++++++++++++++++++++++++++++//
//     Fuzzy Inference Engine RAIFinalv14      //
//++++++++++++++++++++++++++++++++++++++++++++++++++++++//

public class RAIFinalv14 implements FuzzyInferenceEngine {

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //      Membership function of an input variable       //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private abstract class InnerMembershipFunction {
  double min, max, step;
  abstract double param(int i);
  double center() { return 0; }
  double basis() { return 0; }
  abstract double isEqual(double x);

  double isSmallerOrEqual(double x) {
   double degree=0, mu;
   for(double y=max; y>=x ; y-=step) if((mu = isEqual(y))>degree) degree=mu;
   return degree;
  }

  double isGreaterOrEqual(double x) {
   double degree=0, mu;
   for(double y=min; y<=x ; y+=step) if((mu = isEqual(y))>degree) degree=mu;
   return degree;
  }

  double isEqual(MembershipFunction mf) {
   if(mf instanceof FuzzySingleton)
    { return isEqual( ((FuzzySingleton) mf).getValue()); }
   if((mf instanceof OutputMembershipFunction) &&
      ((OutputMembershipFunction) mf).isDiscrete() ) {
    double[][] val = ((OutputMembershipFunction) mf).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = isEqual(val[i][0]);
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }
   double mu1,mu2,minmu,degree=0;
   for(double x=min; x<=max; x+=step){
    mu1 = mf.compute(x);
    mu2 = isEqual(x);
    minmu = (mu1<mu2 ? mu1 : mu2);
    if( degree<minmu ) degree = minmu;
   }
   return degree;
  }

  double isGreaterOrEqual(MembershipFunction mf) {
   if(mf instanceof FuzzySingleton)
    { return isGreaterOrEqual( ((FuzzySingleton) mf).getValue()); }
   if((mf instanceof OutputMembershipFunction) &&
      ((OutputMembershipFunction) mf).isDiscrete() ) {
    double[][] val = ((OutputMembershipFunction) mf).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = isGreaterOrEqual(val[i][0]);
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }
   double mu1,mu2,minmu,degree=0,greq=0;
   for(double x=min; x<=max; x+=step){
    mu1 = mf.compute(x);
    mu2 = isEqual(x);
    if( mu2>greq ) greq = mu2;
    if( mu1<greq ) minmu = mu1; else minmu = greq;
    if( degree<minmu ) degree = minmu;
   }
   return degree;
  }

  double isSmallerOrEqual(MembershipFunction mf) {
   if(mf instanceof FuzzySingleton)
    { return isSmallerOrEqual( ((FuzzySingleton) mf).getValue()); }
   if((mf instanceof OutputMembershipFunction) &&
      ((OutputMembershipFunction) mf).isDiscrete() ) {
    double[][] val = ((OutputMembershipFunction) mf).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = isSmallerOrEqual(val[i][0]);
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }
   double mu1,mu2,minmu,degree=0,smeq=0;
   for(double x=max; x>=min; x-=step){
    mu1 = mf.compute(x);
    mu2 = isEqual(x);
    if( mu2>smeq ) smeq = mu2;
    if( mu1<smeq ) minmu = mu1; else minmu = smeq;
    if( degree<minmu ) degree = minmu;
   }
   return degree;
  }

  double isGreater(MembershipFunction mf, InnerOperatorset op) {
   if(mf instanceof FuzzySingleton)
    { return op.not(isSmallerOrEqual( ((FuzzySingleton) mf).getValue())); }
   if((mf instanceof OutputMembershipFunction) &&
      ((OutputMembershipFunction) mf).isDiscrete() ) {
    double[][] val = ((OutputMembershipFunction) mf).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = op.not(isSmallerOrEqual(val[i][0]));
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }
   double mu1,mu2,minmu,gr,degree=0,smeq=0;
   for(double x=max; x>=min; x-=step){
    mu1 = mf.compute(x);
    mu2 = isEqual(x);
    if( mu2>smeq ) smeq = mu2;
    gr = op.not(smeq);
    minmu = ( mu1<gr ? mu1 : gr);
    if( degree<minmu ) degree = minmu;
   }
   return degree;
  }

  double isSmaller(MembershipFunction mf, InnerOperatorset op) {
   if(mf instanceof FuzzySingleton)
    { return op.not(isGreaterOrEqual( ((FuzzySingleton) mf).getValue())); }
   if((mf instanceof OutputMembershipFunction) &&
      ((OutputMembershipFunction) mf).isDiscrete() ) {
    double[][] val = ((OutputMembershipFunction) mf).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = op.not(isGreaterOrEqual(val[i][0]));
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }
   double mu1,mu2,minmu,sm,degree=0,greq=0;
   for(double x=min; x<=max; x+=step){
    mu1 = mf.compute(x);
    mu2 = isEqual(x);
    if( mu2>greq ) greq = mu2;
    sm = op.not(greq);
    minmu = ( mu1<sm ? mu1 : sm);
    if( degree<minmu ) degree = minmu;
   }
   return degree;
  }

  double isNotEqual(MembershipFunction mf, InnerOperatorset op) {
   if(mf instanceof FuzzySingleton)
    { return op.not(isEqual( ((FuzzySingleton) mf).getValue())); }
   if((mf instanceof OutputMembershipFunction) &&
      ((OutputMembershipFunction) mf).isDiscrete() ) {
    double[][] val = ((OutputMembershipFunction) mf).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = op.not(isEqual(val[i][0]));
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }
   double mu1,mu2,minmu,degree=0;
   for(double x=min; x<=max; x+=step){
    mu1 = mf.compute(x);
    mu2 = op.not(isEqual(x));
    minmu = (mu1<mu2 ? mu1 : mu2);
    if( degree<minmu ) degree = minmu;
   }
   return degree;
  }

  double isApproxEqual(MembershipFunction mf, InnerOperatorset op) {
   if(mf instanceof FuzzySingleton)
    { return op.moreorless(isEqual( ((FuzzySingleton) mf).getValue())); }
   if((mf instanceof OutputMembershipFunction) &&
      ((OutputMembershipFunction) mf).isDiscrete() ) {
    double[][] val = ((OutputMembershipFunction) mf).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = op.moreorless(isEqual(val[i][0]));
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }
   double mu1,mu2,minmu,degree=0;
   for(double x=min; x<=max; x+=step){
    mu1 = mf.compute(x);
    mu2 = op.moreorless(isEqual(x));
    minmu = (mu1<mu2 ? mu1 : mu2);
    if( degree<minmu ) degree = minmu;
   }
   return degree;
  }

  double isVeryEqual(MembershipFunction mf, InnerOperatorset op) {
   if(mf instanceof FuzzySingleton)
    { return op.very(isEqual( ((FuzzySingleton) mf).getValue())); }
   if((mf instanceof OutputMembershipFunction) &&
      ((OutputMembershipFunction) mf).isDiscrete() ) {
    double[][] val = ((OutputMembershipFunction) mf).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = op.very(isEqual(val[i][0]));
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }
   double mu1,mu2,minmu,degree=0;
   for(double x=min; x<=max; x+=step){
    mu1 = mf.compute(x);
    mu2 = op.very(isEqual(x));
    minmu = (mu1<mu2 ? mu1 : mu2);
    if( degree<minmu ) degree = minmu;
   }
   return degree;
  }

  double isSlightlyEqual(MembershipFunction mf, InnerOperatorset op) {
   if(mf instanceof FuzzySingleton)
    { return op.slightly(isEqual( ((FuzzySingleton) mf).getValue())); }
   if((mf instanceof OutputMembershipFunction) &&
      ((OutputMembershipFunction) mf).isDiscrete() ) {
    double[][] val = ((OutputMembershipFunction) mf).getDiscreteValues();
    double deg = 0;
    for(int i=0; i<val.length; i++){
     double mu = op.slightly(isEqual(val[i][0]));
     double minmu = (mu<val[i][1] ? mu : val[i][1]);
     if( deg<minmu ) deg = minmu;
    }
    return deg;
   }
   double mu1,mu2,minmu,degree=0;
   for(double x=min; x<=max; x+=step){
    mu1 = mf.compute(x);
    mu2 = op.slightly(isEqual(x));
    minmu = (mu1<mu2 ? mu1 : mu2);
    if( degree<minmu ) degree = minmu;
   }
   return degree;
  }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //          Abstract class of an operator set          //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private abstract class InnerOperatorset {
  abstract double and(double a, double b);
  abstract double or(double a, double b);
  abstract double also(double a, double b);
  abstract double imp(double a, double b);
  abstract double not(double a);
  abstract double very(double a);
  abstract double moreorless(double a);
  abstract double slightly(double a);
  abstract double defuz(OutputMembershipFunction mf);
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //      Class for the conclusion of a fuzzy rule       //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private class InnerConclusion {
  private double degree;
  private InnerMembershipFunction mf;
  private InnerOperatorset op;

  InnerConclusion(double degree, InnerMembershipFunction mf, InnerOperatorset op) {
   this.op = op;
   this.degree = degree;
   this.mf = mf;
  }

  public double degree() { return degree; }
  public double compute(double x) { return op.imp(degree,mf.isEqual(x)); }
  public double center() { return mf.center(); }
  public double basis() { return mf.basis(); }
  public double param(int i) { return mf.param(i); }
  public double min() { return mf.min; }
  public double max() { return mf.max; }
  public double step() { return mf.step; }
  public boolean isSingleton() { 
   return mf.getClass().getName().endsWith("MF_xfl_singleton");
  }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //      Membership function of an output variable      //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private class OutputMembershipFunction implements MembershipFunction {
  public InnerConclusion[] conc;
  public double[] input;
  private InnerOperatorset op;

  OutputMembershipFunction() {
   this.conc = new InnerConclusion[0];
  }

  public void set(int size, InnerOperatorset op, double[] input) {
   this.input = input;
   this.op = op;
   this.conc = new InnerConclusion[size];
  }

  public void set(int pos, double dg, InnerMembershipFunction imf) {
   this.conc[pos] = new InnerConclusion(dg,imf,op);
  }

  public double compute(double x) {
   double dom = conc[0].compute(x);
   for(int i=1; i<conc.length; i++) dom = op.also(dom,conc[i].compute(x));
   return dom;
  }

  public double defuzzify() {
   return op.defuz(this);
  }

  public double min() {
   return conc[0].min();
  }

  public double max() {
   return conc[0].max();
  }

  public double step() {
   return conc[0].step();
  }

  public boolean isDiscrete() {
   for(int i=0; i<conc.length; i++) if(!conc[i].isSingleton()) return false;
   return true;
  }
 
  public double[][] getDiscreteValues() {
   double[][] value = new double[conc.length][2];
   for(int i=0; i<conc.length; i++) {
    value[i][0] = conc[i].param(0);
    value[i][1] = conc[i].degree();
   }
   return value;
  }

 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //      Membership function MF_xfl_bell      //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

  private class MF_xfl_bell extends InnerMembershipFunction {
   double a;
   double b;

   MF_xfl_bell( double min, double max, double step, double param[]){
    super.min = min;
    super.max = max;
    super.step = step;
    this.a = param[0];
    this.b = param[1];
   }
   double param(int _i) {
    switch(_i) {
     case 0: return a;
     case 1: return b;
     default: return 0;
    }
   }
   double isEqual(double x) {
    return Math.exp( -(a-x)*(a-x)/(b*b) ); 
   }
   double isGreaterOrEqual(double x) {
    if(x>a) return 1; return Math.exp( - (x-a)*(x-a)/(b*b) ); 
   }
   double isSmallerOrEqual(double x) {
    if(x<a) return 1; return Math.exp( - (x-a)*(x-a)/(b*b) ); 
   }
   double center() {
    return a; 
   }
   double basis() {
    return b; 
   }
  }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //      Membership function MF_xfl_trapezoid      //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

  private class MF_xfl_trapezoid extends InnerMembershipFunction {
   double a;
   double b;
   double c;
   double d;

   MF_xfl_trapezoid( double min, double max, double step, double param[]){
    super.min = min;
    super.max = max;
    super.step = step;
    this.a = param[0];
    this.b = param[1];
    this.c = param[2];
    this.d = param[3];
   }
   double param(int _i) {
    switch(_i) {
     case 0: return a;
     case 1: return b;
     case 2: return c;
     case 3: return d;
     default: return 0;
    }
   }
   double isEqual(double x) {
    return (x<a || x>d? 0: (x<b? (x-a)/(b-a) : (x<c?1 : (d-x)/(d-c)))); 
   }
   double isGreaterOrEqual(double x) {
    return (x<a? 0 : (x>b? 1 : (x-a)/(b-a) )); 
   }
   double isSmallerOrEqual(double x) {
    return (x<c? 1 : (x>d? 0 : (d-x)/(d-c) )); 
   }
   double center() {
    return (b+c)/2; 
   }
   double basis() {
    return (d-a); 
   }
  }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //     Operator set OP_Minimum      //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private class OP_Minimum extends InnerOperatorset {
  double and(double a, double b) {
    return (a<b? a : b); 
  }
  double or(double a, double b) {
    return (a>b? a : b); 
  }
  double also(double a, double b) {
    return (a>b? a : b); 
  }
  double imp(double a, double b) {
    return (a<b? a : b); 
  }
  double not(double a) {
    return 1-a; 
  }
  double very(double a) {
   double w = 2.0;
    return Math.pow(a,w); 
  }
  double moreorless(double a) {
   double w = 0.5;
    return Math.pow(a,w); 
  }
  double slightly(double a) {
    return 4*a*(1-a); 
  }
 double defuz(OutputMembershipFunction mf) {
   double min = mf.min();
   double max = mf.max();
   double step = mf.step();
  double num=0, denom=0;
  for(double x=min; x<=max; x+=step) {
   double m = mf.compute(x);
   num += x*m;
   denom += m;
  }
  if(denom==0) return (min+max)/2;
  return num/denom;
  }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //     Operator set OP_Product      //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private class OP_Product extends InnerOperatorset {
  double and(double a, double b) {
    return a*b; 
  }
  double or(double a, double b) {
    return a+b-a*b; 
  }
  double also(double a, double b) {
    return (a>b? a : b); 
  }
  double imp(double a, double b) {
    return (a<b? a : b); 
  }
  double not(double a) {
    return 1-a; 
  }
  double very(double a) {
   double w = 2.0;
    return Math.pow(a,w); 
  }
  double moreorless(double a) {
   double w = 0.5;
    return Math.pow(a,w); 
  }
  double slightly(double a) {
    return 4*a*(1-a); 
  }
 double defuz(OutputMembershipFunction mf) {
   double min = mf.min();
   double max = mf.max();
   double step = mf.step();
  double num=0, denom=0;
  for(double x=min; x<=max; x+=step) {
   double m = mf.compute(x);
   num += x*m;
   denom += m;
  }
  if(denom==0) return (min+max)/2;
  return num/denom;
  }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //     Operator set OP_Lukasiewicz      //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private class OP_Lukasiewicz extends InnerOperatorset {
  double and(double a, double b) {
    return (b<a? 1-a+b : 1); 
  }
  double or(double a, double b) {
    return (b<a? 1-a+b : 1); 
  }
  double also(double a, double b) {
    return (a>b? a : b); 
  }
  double imp(double a, double b) {
    return (a<b? a : b); 
  }
  double not(double a) {
    return 1-a; 
  }
  double very(double a) {
   double w = 2.0;
    return Math.pow(a,w); 
  }
  double moreorless(double a) {
   double w = 0.5;
    return Math.pow(a,w); 
  }
  double slightly(double a) {
    return 4*a*(1-a); 
  }
 double defuz(OutputMembershipFunction mf) {
   double min = mf.min();
   double max = mf.max();
   double step = mf.step();
  double num=0, denom=0;
  for(double x=min; x<=max; x+=step) {
   double m = mf.compute(x);
   num += x*m;
   denom += m;
  }
  if(denom==0) return (min+max)/2;
  return num/denom;
  }
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //  Type TP_DifferenceOfTemperatureDegrees  //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private class TP_DifferenceOfTemperatureDegrees {
  private double min = 0.0;
  private double max = 30.0;
  private double step = 0.11764705882352941;
  double _p_Low[] = { 0.0,5.0 };
  double _p_MLow[] = { 8.0,5.0 };
  double _p_MHigh[] = { 18.0,6.0 };
  double _p_High[] = { 20.0,27.0,30.0,31.0 };
  MF_xfl_bell Low = new MF_xfl_bell(min,max,step,_p_Low);
  MF_xfl_bell MLow = new MF_xfl_bell(min,max,step,_p_MLow);
  MF_xfl_bell MHigh = new MF_xfl_bell(min,max,step,_p_MHigh);
  MF_xfl_trapezoid High = new MF_xfl_trapezoid(min,max,step,_p_High);
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //  Type TP_StreamOfPeople  //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private class TP_StreamOfPeople {
  private double min = 0.0;
  private double max = 15.0;
  private double step = 0.2542372881355932;
  double _p_Low[] = { 0.0,2.0 };
  double _p_MLow[] = { 3.0,3.0 };
  double _p_MHigh[] = { 8.0,3.0 };
  double _p_High[] = { 7.0,11.0,15.0,30.0 };
  MF_xfl_bell Low = new MF_xfl_bell(min,max,step,_p_Low);
  MF_xfl_bell MLow = new MF_xfl_bell(min,max,step,_p_MLow);
  MF_xfl_bell MHigh = new MF_xfl_bell(min,max,step,_p_MHigh);
  MF_xfl_trapezoid High = new MF_xfl_trapezoid(min,max,step,_p_High);
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //  Type TP_WaitingTime  //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private class TP_WaitingTime {
  private double min = 0.0;
  private double max = 30.0;
  private double step = 0.11764705882352941;
  double _p_Min[] = { -1.0,0.0,1.0,4.0 };
  double _p_Low[] = { 3.5,2.0 };
  double _p_MediumLow[] = { 6.0,2.0 };
  double _p_MediumHigh[] = { 6.0,9.0,15.0,18.0 };
  double _p_High[] = { 15.0,17.0,23.0,25.0 };
  double _p_Max[] = { 22.0,25.0,30.0,40.0 };
  MF_xfl_trapezoid Min = new MF_xfl_trapezoid(min,max,step,_p_Min);
  MF_xfl_bell Low = new MF_xfl_bell(min,max,step,_p_Low);
  MF_xfl_bell MediumLow = new MF_xfl_bell(min,max,step,_p_MediumLow);
  MF_xfl_trapezoid MediumHigh = new MF_xfl_trapezoid(min,max,step,_p_MediumHigh);
  MF_xfl_trapezoid High = new MF_xfl_trapezoid(min,max,step,_p_High);
  MF_xfl_trapezoid Max = new MF_xfl_trapezoid(min,max,step,_p_Max);
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //  Rulebase RL_Min  //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private MembershipFunction[] RL_Min(MembershipFunction SPeople, MembershipFunction DifTemp) {
  double _rl;
  double _input[] = new double[2];
  if(SPeople instanceof FuzzySingleton)
   _input[0] = ((FuzzySingleton) SPeople).getValue();
  if(DifTemp instanceof FuzzySingleton)
   _input[1] = ((FuzzySingleton) DifTemp).getValue();
  OP_Minimum _op = new OP_Minimum();
  OutputMembershipFunction TE = new OutputMembershipFunction();
  TE.set(16,_op,_input);
  TP_StreamOfPeople _t_SPeople = new TP_StreamOfPeople();
  TP_DifferenceOfTemperatureDegrees _t_DifTemp = new TP_DifferenceOfTemperatureDegrees();
  TP_WaitingTime _t_TE = new TP_WaitingTime();
  int _i_TE=0;
  _rl = _op.and(_t_SPeople.Low.isEqual(SPeople),_t_DifTemp.Low.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumHigh); _i_TE++;
  _rl = _op.and(_t_SPeople.Low.isEqual(SPeople),_t_DifTemp.MLow.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumLow); _i_TE++;
  _rl = _op.and(_t_SPeople.Low.isEqual(SPeople),_t_DifTemp.MHigh.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Low); _i_TE++;
  _rl = _op.and(_t_SPeople.Low.isEqual(SPeople),_t_DifTemp.High.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Min); _i_TE++;
  _rl = _op.and(_t_SPeople.MLow.isEqual(SPeople),_t_DifTemp.Low.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumHigh); _i_TE++;
  _rl = _op.and(_t_SPeople.MLow.isEqual(SPeople),_t_DifTemp.MLow.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumLow); _i_TE++;
  _rl = _op.and(_t_SPeople.MLow.isEqual(SPeople),_t_DifTemp.MHigh.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Low); _i_TE++;
  _rl = _op.and(_t_SPeople.MLow.isEqual(SPeople),_t_DifTemp.High.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Min); _i_TE++;
  _rl = _op.and(_t_SPeople.MHigh.isEqual(SPeople),_t_DifTemp.Low.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.High); _i_TE++;
  _rl = _op.and(_t_SPeople.MHigh.isEqual(SPeople),_t_DifTemp.MLow.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumHigh); _i_TE++;
  _rl = _op.and(_t_SPeople.MHigh.isEqual(SPeople),_t_DifTemp.MHigh.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumLow); _i_TE++;
  _rl = _op.and(_t_SPeople.MHigh.isEqual(SPeople),_t_DifTemp.High.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Low); _i_TE++;
  _rl = _op.and(_t_SPeople.High.isEqual(SPeople),_t_DifTemp.Low.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Max); _i_TE++;
  _rl = _op.and(_t_SPeople.High.isEqual(SPeople),_t_DifTemp.MLow.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.High); _i_TE++;
  _rl = _op.and(_t_SPeople.High.isEqual(SPeople),_t_DifTemp.MHigh.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumHigh); _i_TE++;
  _rl = _op.and(_t_SPeople.High.isEqual(SPeople),_t_DifTemp.High.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumLow); _i_TE++;
  MembershipFunction[] _output = new MembershipFunction[1];
  _output[0] = TE;
  return _output;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //  Rulebase RL_Pro  //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private MembershipFunction[] RL_Pro(MembershipFunction SPeople, MembershipFunction DifTemp) {
  double _rl;
  double _input[] = new double[2];
  if(SPeople instanceof FuzzySingleton)
   _input[0] = ((FuzzySingleton) SPeople).getValue();
  if(DifTemp instanceof FuzzySingleton)
   _input[1] = ((FuzzySingleton) DifTemp).getValue();
  OP_Product _op = new OP_Product();
  OutputMembershipFunction TE = new OutputMembershipFunction();
  TE.set(16,_op,_input);
  TP_StreamOfPeople _t_SPeople = new TP_StreamOfPeople();
  TP_DifferenceOfTemperatureDegrees _t_DifTemp = new TP_DifferenceOfTemperatureDegrees();
  TP_WaitingTime _t_TE = new TP_WaitingTime();
  int _i_TE=0;
  _rl = _op.and(_t_SPeople.Low.isEqual(SPeople),_t_DifTemp.Low.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumHigh); _i_TE++;
  _rl = _op.and(_t_SPeople.Low.isEqual(SPeople),_t_DifTemp.MLow.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumLow); _i_TE++;
  _rl = _op.and(_t_SPeople.Low.isEqual(SPeople),_t_DifTemp.MHigh.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Low); _i_TE++;
  _rl = _op.and(_t_SPeople.Low.isEqual(SPeople),_t_DifTemp.High.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Min); _i_TE++;
  _rl = _op.and(_t_SPeople.MLow.isEqual(SPeople),_t_DifTemp.Low.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumHigh); _i_TE++;
  _rl = _op.and(_t_SPeople.MLow.isEqual(SPeople),_t_DifTemp.MLow.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumLow); _i_TE++;
  _rl = _op.and(_t_SPeople.MLow.isEqual(SPeople),_t_DifTemp.MHigh.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Low); _i_TE++;
  _rl = _op.and(_t_SPeople.MLow.isEqual(SPeople),_t_DifTemp.High.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Min); _i_TE++;
  _rl = _op.and(_t_SPeople.MHigh.isEqual(SPeople),_t_DifTemp.Low.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.High); _i_TE++;
  _rl = _op.and(_t_SPeople.MHigh.isEqual(SPeople),_t_DifTemp.MLow.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumHigh); _i_TE++;
  _rl = _op.and(_t_SPeople.MHigh.isEqual(SPeople),_t_DifTemp.MHigh.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumLow); _i_TE++;
  _rl = _op.and(_t_SPeople.MHigh.isEqual(SPeople),_t_DifTemp.High.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Low); _i_TE++;
  _rl = _op.and(_t_SPeople.High.isEqual(SPeople),_t_DifTemp.Low.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Max); _i_TE++;
  _rl = _op.and(_t_SPeople.High.isEqual(SPeople),_t_DifTemp.MLow.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.High); _i_TE++;
  _rl = _op.and(_t_SPeople.High.isEqual(SPeople),_t_DifTemp.MHigh.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumHigh); _i_TE++;
  _rl = _op.and(_t_SPeople.High.isEqual(SPeople),_t_DifTemp.High.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumLow); _i_TE++;
  MembershipFunction[] _output = new MembershipFunction[1];
  _output[0] = TE;
  return _output;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //  Rulebase RL_Lukasiewicz  //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private MembershipFunction[] RL_Lukasiewicz(MembershipFunction SPeople, MembershipFunction DifTemp) {
  double _rl;
  double _input[] = new double[2];
  if(SPeople instanceof FuzzySingleton)
   _input[0] = ((FuzzySingleton) SPeople).getValue();
  if(DifTemp instanceof FuzzySingleton)
   _input[1] = ((FuzzySingleton) DifTemp).getValue();
  OP_Lukasiewicz _op = new OP_Lukasiewicz();
  OutputMembershipFunction TE = new OutputMembershipFunction();
  TE.set(16,_op,_input);
  TP_StreamOfPeople _t_SPeople = new TP_StreamOfPeople();
  TP_DifferenceOfTemperatureDegrees _t_DifTemp = new TP_DifferenceOfTemperatureDegrees();
  TP_WaitingTime _t_TE = new TP_WaitingTime();
  int _i_TE=0;
  _rl = _op.and(_t_SPeople.Low.isEqual(SPeople),_t_DifTemp.Low.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumHigh); _i_TE++;
  _rl = _op.and(_t_SPeople.Low.isEqual(SPeople),_t_DifTemp.MLow.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumLow); _i_TE++;
  _rl = _op.and(_t_SPeople.Low.isEqual(SPeople),_t_DifTemp.MHigh.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Low); _i_TE++;
  _rl = _op.and(_t_SPeople.Low.isEqual(SPeople),_t_DifTemp.High.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Min); _i_TE++;
  _rl = _op.and(_t_SPeople.MLow.isEqual(SPeople),_t_DifTemp.Low.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumHigh); _i_TE++;
  _rl = _op.and(_t_SPeople.MLow.isEqual(SPeople),_t_DifTemp.MLow.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumLow); _i_TE++;
  _rl = _op.and(_t_SPeople.MLow.isEqual(SPeople),_t_DifTemp.MHigh.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Low); _i_TE++;
  _rl = _op.and(_t_SPeople.MLow.isEqual(SPeople),_t_DifTemp.High.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Min); _i_TE++;
  _rl = _op.and(_t_SPeople.MHigh.isEqual(SPeople),_t_DifTemp.Low.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.High); _i_TE++;
  _rl = _op.and(_t_SPeople.MHigh.isEqual(SPeople),_t_DifTemp.MLow.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumHigh); _i_TE++;
  _rl = _op.and(_t_SPeople.MHigh.isEqual(SPeople),_t_DifTemp.MHigh.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumLow); _i_TE++;
  _rl = _op.and(_t_SPeople.MHigh.isEqual(SPeople),_t_DifTemp.High.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Low); _i_TE++;
  _rl = _op.and(_t_SPeople.High.isEqual(SPeople),_t_DifTemp.Low.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.Max); _i_TE++;
  _rl = _op.and(_t_SPeople.High.isEqual(SPeople),_t_DifTemp.MLow.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.High); _i_TE++;
  _rl = _op.and(_t_SPeople.High.isEqual(SPeople),_t_DifTemp.MHigh.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumHigh); _i_TE++;
  _rl = _op.and(_t_SPeople.High.isEqual(SPeople),_t_DifTemp.High.isEqual(DifTemp));
  TE.set(_i_TE,_rl, _t_TE.MediumLow); _i_TE++;
  MembershipFunction[] _output = new MembershipFunction[1];
  _output[0] = TE;
  return _output;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //               Fuzzy Inference Engine                //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public double[] crispInference(double[] _input) {
  MembershipFunction DifferenceOfTemperatureDegrees = new FuzzySingleton(_input[0]);
  MembershipFunction StreamOfPeople = new FuzzySingleton(_input[1]);
  MembershipFunction ProductOutputWaitingTime;
  MembershipFunction MinimumOutputWaitingTime;
  MembershipFunction LuckasiewiczOutputWaitingTime;
  MembershipFunction[] _call;
  _call = RL_Pro(StreamOfPeople,DifferenceOfTemperatureDegrees); ProductOutputWaitingTime=_call[0];
  _call = RL_Min(StreamOfPeople,DifferenceOfTemperatureDegrees); MinimumOutputWaitingTime=_call[0];
  _call = RL_Lukasiewicz(StreamOfPeople,DifferenceOfTemperatureDegrees); LuckasiewiczOutputWaitingTime=_call[0];
  double _output[] = new double[3];
  if(ProductOutputWaitingTime instanceof FuzzySingleton)
   _output[0] = ((FuzzySingleton) ProductOutputWaitingTime).getValue();
  else _output[0] = ((OutputMembershipFunction) ProductOutputWaitingTime).defuzzify();
  if(MinimumOutputWaitingTime instanceof FuzzySingleton)
   _output[1] = ((FuzzySingleton) MinimumOutputWaitingTime).getValue();
  else _output[1] = ((OutputMembershipFunction) MinimumOutputWaitingTime).defuzzify();
  if(LuckasiewiczOutputWaitingTime instanceof FuzzySingleton)
   _output[2] = ((FuzzySingleton) LuckasiewiczOutputWaitingTime).getValue();
  else _output[2] = ((OutputMembershipFunction) LuckasiewiczOutputWaitingTime).defuzzify();
  return _output;
 }

 public double[] crispInference(MembershipFunction[] _input) {
  MembershipFunction DifferenceOfTemperatureDegrees = _input[0];
  MembershipFunction StreamOfPeople = _input[1];
  MembershipFunction ProductOutputWaitingTime;
  MembershipFunction MinimumOutputWaitingTime;
  MembershipFunction LuckasiewiczOutputWaitingTime;
  MembershipFunction[] _call;
  _call = RL_Pro(StreamOfPeople,DifferenceOfTemperatureDegrees); ProductOutputWaitingTime=_call[0];
  _call = RL_Min(StreamOfPeople,DifferenceOfTemperatureDegrees); MinimumOutputWaitingTime=_call[0];
  _call = RL_Lukasiewicz(StreamOfPeople,DifferenceOfTemperatureDegrees); LuckasiewiczOutputWaitingTime=_call[0];
  double _output[] = new double[3];
  if(ProductOutputWaitingTime instanceof FuzzySingleton)
   _output[0] = ((FuzzySingleton) ProductOutputWaitingTime).getValue();
  else _output[0] = ((OutputMembershipFunction) ProductOutputWaitingTime).defuzzify();
  if(MinimumOutputWaitingTime instanceof FuzzySingleton)
   _output[1] = ((FuzzySingleton) MinimumOutputWaitingTime).getValue();
  else _output[1] = ((OutputMembershipFunction) MinimumOutputWaitingTime).defuzzify();
  if(LuckasiewiczOutputWaitingTime instanceof FuzzySingleton)
   _output[2] = ((FuzzySingleton) LuckasiewiczOutputWaitingTime).getValue();
  else _output[2] = ((OutputMembershipFunction) LuckasiewiczOutputWaitingTime).defuzzify();
  return _output;
 }

 public MembershipFunction[] fuzzyInference(double[] _input) {
  MembershipFunction DifferenceOfTemperatureDegrees = new FuzzySingleton(_input[0]);
  MembershipFunction StreamOfPeople = new FuzzySingleton(_input[1]);
  MembershipFunction ProductOutputWaitingTime;
  MembershipFunction MinimumOutputWaitingTime;
  MembershipFunction LuckasiewiczOutputWaitingTime;
  MembershipFunction[] _call;
  _call = RL_Pro(StreamOfPeople,DifferenceOfTemperatureDegrees); ProductOutputWaitingTime=_call[0];
  _call = RL_Min(StreamOfPeople,DifferenceOfTemperatureDegrees); MinimumOutputWaitingTime=_call[0];
  _call = RL_Lukasiewicz(StreamOfPeople,DifferenceOfTemperatureDegrees); LuckasiewiczOutputWaitingTime=_call[0];
  MembershipFunction _output[] = new MembershipFunction[3];
  _output[0] = ProductOutputWaitingTime;
  _output[1] = MinimumOutputWaitingTime;
  _output[2] = LuckasiewiczOutputWaitingTime;
  return _output;
 }

 public MembershipFunction[] fuzzyInference(MembershipFunction[] _input) {
  MembershipFunction DifferenceOfTemperatureDegrees = _input[0];
  MembershipFunction StreamOfPeople = _input[1];
  MembershipFunction ProductOutputWaitingTime;
  MembershipFunction MinimumOutputWaitingTime;
  MembershipFunction LuckasiewiczOutputWaitingTime;
  MembershipFunction[] _call;
  _call = RL_Pro(StreamOfPeople,DifferenceOfTemperatureDegrees); ProductOutputWaitingTime=_call[0];
  _call = RL_Min(StreamOfPeople,DifferenceOfTemperatureDegrees); MinimumOutputWaitingTime=_call[0];
  _call = RL_Lukasiewicz(StreamOfPeople,DifferenceOfTemperatureDegrees); LuckasiewiczOutputWaitingTime=_call[0];
  MembershipFunction _output[] = new MembershipFunction[3];
  _output[0] = ProductOutputWaitingTime;
  _output[1] = MinimumOutputWaitingTime;
  _output[2] = LuckasiewiczOutputWaitingTime;
  return _output;
 }

}
