package domain.businessrule;

import java.util.ArrayList;

import domain.Component;
import domain.Operator;
import domain.TargetDatabase;

public class BusinessRuleImpl implements BusinessRule {
	
	private int noLiteralValues;
	private int noColumnValues;
	private int ruleId;
	private Operator operator;
	private String restrictedTable;
	private String restrictedColumn;
	private ArrayList<String> triggerEvents;
	private ArrayList<Component> components;
	private String errorMessage;
	private TargetDatabase targetDatabase;
	
	public BusinessRuleImpl(){
		
	}

	@Override
	public String getGeneratedRule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRuleId(int ruleId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTriggerEvents(ArrayList<String> triggerEvents) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOperator(Operator operator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setComponents(ArrayList<Component> components) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setErrorMessage(String errorMessage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRestrictedTable(String restrictedTable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRestrictedColumn(String restrictedColumn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTargetDatabase(TargetDatabase targetDatabase) {
		// TODO Auto-generated method stub

	}

	@Override
	public TargetDatabase getTargetDatabase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRuleId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRestrictedTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRestrictedColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getTriggerEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Component> getComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Operator getOperator() {
		// TODO Auto-generated method stub
		return null;
	}

}
