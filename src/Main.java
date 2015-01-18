import java.sql.SQLException;
import java.util.ArrayList;

import dao.BusinessRuleDAOImpl;
import domain.businessrule.BusinessRule;


public class Main {

	public static void main(String[] args) throws SQLException {
		BusinessRuleDAOImpl dao = new BusinessRuleDAOImpl();
		ArrayList<BusinessRule> emptybusinessrules = dao.getEmptyUngeneratedBusinessRules();
		ArrayList<BusinessRule> businessrules = dao.getFilledBusinessRules(emptybusinessrules);
		for(BusinessRule b : businessrules){
			b.getGeneratedRule();
		}
	}

}
