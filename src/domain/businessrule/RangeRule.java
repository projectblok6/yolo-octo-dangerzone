package domain.businessrule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Scanner;

import domain.Component;
import domain.Operator;
import domain.TargetDatabase;

public class RangeRule implements BusinessRule {
	private int ruleId;
	private String ruleName;
	private Operator operator;
	private String restrictedColumn;
	private ArrayList<String> triggerEvents;
	private ArrayList<Component> components;
	private String errorMessage;
	private TargetDatabase targetDatabase;

	public RangeRule() {
	};

	public String getGeneratedRule() {
		String ruleString = getTemplate("src/ruletemplate.txt");
		ruleString = ruleString.replaceAll("%errormessage%", errorMessage);
		ruleString = ruleString.replaceAll("%triggerevents%", getTriggerLine());
		ruleString = ruleString.replaceAll("%declarations%", getDeclarationsLine());
		ruleString = ruleString.replaceAll("%selectstatements%", getSelectLine());
		ruleString = ruleString.replaceAll("%comparison%", getComparisonLine());
		return ruleString;
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
		if (triggerEvents.size() == 0) {
			return null;
			// TODO create exception: no trigger events
		} else {
			String triggerLine = "";
			for (String triggerEvent : triggerEvents) {
				triggerLine += triggerEvent + ", ";
			}
			return triggerLine.substring(0, triggerLine.length() - 2);
		}
	}

	private String getDeclarationsLine() {
		String declarationLine = "";
		for (Component component : components) {
			// declarationLine += component.getDeclarationLine();
		}
		return declarationLine;
	}

	private String getSelectLine() {
		String declarationLine = "";
		for (Component component : components) {
			// declarationLine += component.getSelectStatement();
		}
		return declarationLine;
	}

	private String getComparisonLine() {
		if (components.size() < 2) {
			return null;
			// TODO create exception: not enough components
		} else {
			String comparison = "";
			comparison += restrictedColumn + " " + operator.toString() + " "
					+ components.get(0).toString() + " and "
					+ components.get(1).toString();
			System.out.println(comparison);
			return comparison;
		}
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public void setTriggerEvents(ArrayList<String> triggerEvents) {
		this.triggerEvents = triggerEvents;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public void setComponents(ArrayList<Component> components) {
		this.components = components;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setRestrictedColumn(String restrictedColumn) {
		this.restrictedColumn = restrictedColumn;
	}

	public void setTargetDatabase(TargetDatabase targetDatabase) {
		this.targetDatabase = targetDatabase;
	}

	public int getRuleId() {
		return ruleId;
	}

	public TargetDatabase getTargetDatabase() {
		return this.targetDatabase;
	}
}
