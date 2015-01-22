package domain;

import java.util.ArrayList;

public class BusinessRule {
	
	private int ruleId;
	private int ruleTypeId;
	private String nameCode;
	private int operatorId;
	private int restrictedTableId;
	private int restrictedColumnId;
	private String errorMessage;
	public int targetAppId;
	private ArrayList<String> triggerEvents;
	private TargetDatabase targetDatabase;
	
	public BusinessRule(int id, int ruleTypeId, String name, int opId, int tabId, int colId, String error, int targetApp){
		ruleId = id;
		this.ruleTypeId = ruleTypeId;
		nameCode = name;
		operatorId = opId;
		restrictedTableId = tabId;
		restrictedColumnId = colId;
		errorMessage = error;
		targetAppId = targetApp;
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

	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public int getRestrictedTableId() {
		return restrictedTableId;
	}

	public void setRestrictedTableId(int restrictedTableId) {
		this.restrictedTableId = restrictedTableId;
	}

	public int getRestrictedColumnId() {
		return restrictedColumnId;
	}

	public void setRestrictedColumnId(int restrictedColumnId) {
		this.restrictedColumnId = restrictedColumnId;
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
