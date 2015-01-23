package generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import domain.BusinessRule;

public class Generator {
	private BusinessRule rule;

	public Generator(BusinessRule r) {
		rule = r;
	}

	public String getGeneratedRule() {
		String triggerString = getTemplate("src/triggertemplate.txt");
		//String ruleString = getTemplate("src/ruletemplate.txt");
		triggerString = triggerString.replaceAll("%errormessage%", rule.getErrorMessage());
		triggerString = triggerString.replaceAll("%triggerevents%", getTriggerLine());
		triggerString = triggerString.replaceAll("%declarations%",
				getDeclarationsLine());
		triggerString = triggerString.replaceAll("%selectstatements%",
				rule.getTemplate());
		//ruleString = ruleString.replaceAll("%comparison%", getComparisonLine());
		triggerString = triggerString
				.replaceAll("%tablename%", rule.getRestrictedTable());
		//triggerString = triggerString.replaceAll("%businessrules%", triggerString);
		triggerString = triggerString.replaceAll("%triggername%",
				getTriggerName());
		return triggerString;
	}

	private String getTemplate(String location) {
		// template inlezen
		File file = new File(location);
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner sc = new Scanner(bf);
		String ruleString = "";
		while (sc.hasNext()) {
			String line = sc.nextLine();
			ruleString += line + "\n";
		}
		return ruleString;
	}

	private String getTriggerLine() {
		// if no trigger events
		if (rule.getTriggerEvents().size() == 0) {
			return null;
			// TODO create exception: no trigger events
		} else if(rule.getTriggerEvents().size() == 1) {
			String triggerLine = "";
			for (String triggerEvent : rule.getTriggerEvents()) {
				triggerLine += triggerEvent;
			}
			return triggerLine;
		} else{
			String triggerLine = "";
			for (String triggerEvent : rule.getTriggerEvents()) {
				triggerLine += triggerEvent + ", ";
			}
			
			return triggerLine;
		}
	}

	private String getDeclarationsLine() {
		String declarationLine = "";
//		for (Component component : rule.getComponents()) {
//			// declarationLine += component.getDeclarationLine();
//		}
		return declarationLine;
	}

//	private String getSelectLine() {
//		String declarationLine = "";
//		for (Component component : rule.getComponents()) {
//			// declarationLine += component.getSelectStatement();
//		}
//		return declarationLine;
//	}

	private String getTriggerName() {
		String triggerName = "BRG_";
		triggerName += "CNS_";
		triggerName += rule.getRestrictedColumn().replaceAll("[aeiou]\\B", "")
				.toUpperCase();
		triggerName += "_RNGR";
		return triggerName;
	}

}