package connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.RepositoryDAOImpl;
import domain.businessrule.BusinessRule;


public class Main {

	public static void main(String[] args) throws SQLException {
		WebController webController = new WebController();;
		webController.generateBusinessRules();
	}

}
