package domain.businessrule;

import java.util.ArrayList;

import domain.Component;
import domain.Operator;
import domain.TargetDatabase;

public interface BusinessRule {
	public String getGeneratedRule();
	
	public void setRuleId(int ruleId);

	public void setTriggerEvents(ArrayList<String> triggerEvents);

	public void setOperator(Operator operator);

	public void setComponents(ArrayList<Component> components);
	
	public void setErrorMessage(String errorMessage);
	
	public void setRestrictedTable(String restrictedTable);
	
	public void setRestrictedColumn(String restrictedColumn);
	
	public void setTargetDatabase(TargetDatabase targetDatabase);
	
	public TargetDatabase getTargetDatabase();
	
	public int getRuleId();
}