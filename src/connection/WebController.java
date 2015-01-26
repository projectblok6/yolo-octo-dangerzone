package connection;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.RepositoryDAOImpl;
import dao.TargetDAOImpl;
import domain.BusinessRule;
import domain.TargetDatabase;

public class WebController {
	private RepositoryDAOImpl dao;
	WebController(){
		try {
			dao = new RepositoryDAOImpl();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public String generateBusinessRules(){
		String returnString = "";
		try {
			dao.connectToRepository();
			ArrayList<BusinessRule> businessRules = dao.getAllUngeneratedBusinessRules();
			for(BusinessRule b : businessRules){
				int businessRuleId = b.getRuleId();
				String businessRuleCode = b.getGeneratedRule();
				b.setGeneratedCode(businessRuleCode);
				dao.setGeneratedBusinessRules(businessRuleCode, businessRuleId);
				
				TargetDAOImpl targetDao = new TargetDAOImpl();
				TargetDatabase targetDatabase = b.getTargetDatabase();
				targetDao.connectToTarget(targetDatabase.getUrl(), targetDatabase.getUsername(), targetDatabase.getPassword(), targetDatabase.getDatabaseType());
				targetDao.executeGeneratedRule(businessRuleCode);
				returnString += businessRuleCode + "\n";
				dao.setStatus(businessRuleId, 2);
			}
		} catch (SQLException e) {
			returnString += "Oeps, er is iets fout gegaan!";
		}
		return returnString;
	}
}
