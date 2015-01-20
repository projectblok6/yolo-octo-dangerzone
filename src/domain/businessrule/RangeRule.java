package domain.businessrule;

import generator.Generator;

import java.util.ArrayList;

import domain.Component;
import domain.Operator;
import domain.TargetDatabase;

public class RangeRule implements BusinessRule {
	private int ruleId;
	private Operator operator;
	private String restrictedTable;
	private String restrictedColumn;
	private ArrayList<String> triggerEvents;
	private ArrayList<Component> components;
	private String errorMessage;
	private TargetDatabase targetDatabase;

	public RangeRule() {
	};

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public void setTriggerEvents(ArrayList<String> triggerEvents) {
		this.triggerEvents = triggerEvents;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public void setComponents(ArrayList<Component> components) {
		this.components = components;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setRestrictedTable(String restrictedTable) {
		this.restrictedTable = restrictedTable;
	}
	
	public void setRestrictedColumn(String restrictedColumn) {
		this.restrictedColumn = restrictedColumn;
	}

	public void setTargetDatabase(TargetDatabase targetDatabase) {
		this.targetDatabase = targetDatabase;
	}

	public int getRuleId() {
		return ruleId;
	}

	public TargetDatabase getTargetDatabase() {
		return this.targetDatabase;
	}

	@Override
	public String getGeneratedRule() {
		Generator gen = new Generator(this);
		return gen.getGeneratedRule();
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public String getRestrictedTable() {
		return restrictedTable;
	}

	@Override
	public String getRestrictedColumn() {
		return restrictedColumn;
	}

	@Override
	public ArrayList<String> getTriggerEvents() {
		return triggerEvents;
	}

	@Override
	public ArrayList<Component> getComponents() {
		return components;
	}

	@Override
	public Operator getOperator() {
		return operator;
	}
	
	
}
