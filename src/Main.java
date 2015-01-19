import java.sql.SQLException;
import java.util.ArrayList;

import dao.RepositoryDAOImpl;
import domain.businessrule.BusinessRule;


public class Main {

	public static void main(String[] args) throws SQLException {
		RepositoryDAOImpl dao = new RepositoryDAOImpl();
		ArrayList<BusinessRule> emptybusinessrules = dao.getEmptyUngeneratedBusinessRules();
		ArrayList<BusinessRule> businessrules = dao.getFilledBusinessRules(emptybusinessrules);
		for(BusinessRule b : businessrules){
			System.out.println(b.getGeneratedRule());
		}
	}

}
