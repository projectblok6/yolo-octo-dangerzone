package domain;

import generator.Generator;

import java.util.ArrayList;

public class BusinessRule {
	
	private int ruleId;
	private int ruleTypeId;
	private String nameCode;
	private String operatorName;
	private String restrictedTable;
	private String restrictedColumn;
	private String errorMessage;
	private String template;
	private String operatorTemplate;
	private String generatedCode;
	public int targetAppId;
	private ArrayList<String> triggerEvents;
	private ArrayList<String> lValues;
	private ArrayList<Column> columns;
	private TargetDatabase targetDatabase;
	
	public BusinessRule(int id, int ruleTypeId, String name, String operName, String tabId, String colId, String error, String template, String operatorTemplate, int targetApp, ArrayList<String> trigs, ArrayList<String> vals, ArrayList<Column> columns, TargetDatabase db){
		this.ruleId = id;
		this.ruleTypeId = ruleTypeId;
		this.nameCode = name;
		this.operatorName = operName;
		this.restrictedTable = tabId;
		this.restrictedColumn = colId;
		this.errorMessage = error;
		this.targetAppId = targetApp;
		this.template = template;
		this.operatorTemplate = operatorTemplate;
		this.triggerEvents = trigs;
		this.lValues = vals;
		this.columns = columns;
		this.targetDatabase = db;
	}
	
	public String getGeneratedRule(){
		Generator gen = new Generator(this);
		return gen.getGeneratedRule();
	}

	public int getRuleId() {
		return ruleId;
	}

	public int getRuleTypeId() {
		return ruleTypeId;
	}

	public String getNameCode() {
		return nameCode;
	}

	public String getOperator() {
		return operatorName;
	}

	public String getRestrictedTable() {
		return restrictedTable;
	}

	public String getRestrictedColumn() {
		return restrictedColumn;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public int getTargetAppId() {
		return targetAppId;
	}
	
	public String getTemplate(){
		return this.template;
	}
	
	public String getOperatorTemplate(){
		return this.operatorTemplate;
	}

	public ArrayList<String> getTriggerEvents() {
		return triggerEvents;
	}

	public TargetDatabase getTargetDatabase() {
		return targetDatabase;
	}
	
	public ArrayList<String> getValues(){
		return lValues;
	}
	
	public ArrayList<Column> getColumns(){
		return columns;
	}

	public String getGeneratedCode() {
		return generatedCode;
	}

	public void setGeneratedCode(String generatedCode) {
		this.generatedCode = generatedCode;
	}
}
