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
	public int targetAppId;
	private ArrayList<String> triggerEvents;
	private TargetDatabase targetDatabase;
	
	public BusinessRule(int id, int ruleTypeId, String name, String operName, String tabId, String colId, String error, String template, int targetApp, ArrayList<String> trigs){
		ruleId = id;
		this.ruleTypeId = ruleTypeId;
		nameCode = name;
		operatorName = operName;
		restrictedTable = tabId;
		restrictedColumn = colId;
		errorMessage = error;
		targetAppId = targetApp;
		this.template = template;
		triggerEvents = trigs;
	}
	
	public String getGeneratedRule(){
		Generator gen = new Generator(this);
		return gen.getGeneratedRule();
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public int getRuleTypeId() {
		return ruleTypeId;
	}

	public void setRuleTypeId(int ruleTypeId) {
		this.ruleTypeId = ruleTypeId;
	}

	public String getNameCode() {
		return nameCode;
	}

	public void setNameCode(String nameCode) {
		this.nameCode = nameCode;
	}

	public String getOperator() {
		return operatorName;
	}

	public void setOperatorId(String operatorId) {
		this.operatorName = operatorId;
	}

	public String getRestrictedTable() {
		return restrictedTable;
	}

	public void setRestrictedTable(String restrictedTableId) {
		this.restrictedTable = restrictedTableId;
	}

	public String getRestrictedColumn() {
		return restrictedColumn;
	}

	public void setRestrictedColumn(String restrictedColumnId) {
		this.restrictedColumn = restrictedColumnId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getTargetAppId() {
		return targetAppId;
	}
	
	public String getTemplate(){
		return this.template;
	}

	public void setTargetAppId(int targetAppId) {
		this.targetAppId = targetAppId;
	}

	public ArrayList<String> getTriggerEvents() {
		return triggerEvents;
	}

	public void setTriggerEvents(ArrayList<String> triggerEvents) {
		this.triggerEvents = triggerEvents;
	}

	public TargetDatabase getTargetDatabase() {
		return targetDatabase;
	}

	public void setTargetDatabase(TargetDatabase targetDatabase) {
		this.targetDatabase = targetDatabase;
	}

}
