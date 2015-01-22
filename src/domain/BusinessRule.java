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

	public String getGeneratedRule() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRuleId(int ruleId) {
		// TODO Auto-generated method stub

	}

	public void setTriggerEvents(ArrayList<String> triggerEvents) {
		// TODO Auto-generated method stub

	}

	public void setErrorMessage(String errorMessage) {
		// TODO Auto-generated method stub

	}

	public void setRestrictedTable(String restrictedTable) {
		// TODO Auto-generated method stub

	}

	public void setRestrictedColumn(String restrictedColumn) {
		// TODO Auto-generated method stub

	}

	public void setTargetDatabase(TargetDatabase targetDatabase) {
		// TODO Auto-generated method stub

	}

	public TargetDatabase getTargetDatabase() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRuleId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRestrictedTable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRestrictedColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<String> getTriggerEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Component> getComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	public Operator getOperator() {
		// TODO Auto-generated method stub
		return null;
	}

}
