package domain.businessrule;

import java.util.ArrayList;

import domain.Component;
import domain.Operator;

public interface BusinessRule {
	public String getGeneratedRule();
	
	public void setRuleId(int ruleId);

	public void setRuleName(String ruleName);

	public void setTriggerEvents(ArrayList<String> triggerEvents);

	public void setOperator(Operator operator);

	public void setComponents(ArrayList<Component> components);
	
	public void setErrorMessage(String errorMessage);
	
	public int getRuleId();
}