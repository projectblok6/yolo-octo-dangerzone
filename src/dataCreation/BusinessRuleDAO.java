package dataCreation;

import java.util.ArrayList;
import domain.businessrule.BusinessRule;

public interface BusinessRuleDAO {

	public void createBusinessRules();
	public void deleteBusinessRule();
	public ArrayList<BusinessRule> getAllBusinessRules();
	
}
