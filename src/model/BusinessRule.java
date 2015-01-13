package model;

public class BusinessRule {
	private int idnr = 3;
	double minValue, maxValue;
	private String ruletype, attribute, operator, compareWith, literalValue, attributeValue;


	public BusinessRule(int in, String rt, String att, String op, double minV, double maxV, String cW, String lV, String attV){
		idnr = in;
		ruletype = rt;
		attribute = att;
		operator = op;
		minValue = minV;
		maxValue = maxV;
		compareWith = cW;
		literalValue = lV;
		attributeValue = attV;
	}
	
	
	
	
	public String toString(){
		String s = idnr + " "+ ruletype +" "+ attribute +" "+ operator +" "+ minValue +" "+ maxValue +" "+ compareWith +" "+ literalValue +" "+ attributeValue;
		return s;
	}
}
