package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import domain.BusinessRule;

public interface RepositoryDAO {
	public ArrayList<BusinessRule> getAllUngeneratedBusinessRules() throws SQLException;
	public void setGeneratedBusinessRules(String businessRule, int businessRuleId) throws SQLException;
	public void setStatus(int businessRuleId, int Status) throws SQLException;
}
