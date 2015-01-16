package domain;

public class Operator {
	private String operatorName;
	private String logicalOperator;
	public Operator(String operatorName, String logicalOperator){
		this.operatorName = operatorName;
		this.logicalOperator = logicalOperator;
	}
	public String toString(){
		return logicalOperator;
	}
}
