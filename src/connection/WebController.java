package connection;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.RepositoryDAOImpl;
import dao.TargetDAOImpl;
import domain.TargetDatabase;
import domain.businessrule.BusinessRule;

public class WebController {
	private RepositoryDAOImpl dao;
	WebController(){
		try {
			dao = new RepositoryDAOImpl();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void generateBusinessRules(){
		try {
			ArrayList<BusinessRule> emptybusinessrules = dao.getEmptyUngeneratedBusinessRules();
			ArrayList<BusinessRule> businessrules = dao.getFilledBusinessRules(emptybusinessrules);
			for(BusinessRule b : businessrules){
				TargetDAOImpl targetDao = new TargetDAOImpl();
				TargetDatabase targetDatabase = b.getTargetDatabase();
				//targetDao.connectToTarget(targetDatabase.getUrl(), targetDatabase.getUsername(), targetDatabase.getPassword(), targetDatabase.getDatabaseType());
				//targetDao.executeGeneratedRule(b.getGeneratedRule());
				System.out.println(b.getGeneratedRule());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
