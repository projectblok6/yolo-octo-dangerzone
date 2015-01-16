package domain.businessrule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import domain.Component;
import domain.Operator;

public class RangeRule implements BusinessRule {
	private String ruleName;
	private ArrayList<String> triggerEvents;
	private Operator operator;
	private ArrayList<Component> components;
	private String errorMessage;

	public RangeRule(ArrayList<String> triggerEvents, String errorMessage, ArrayList<Component> components, Operator operator) {
		this.triggerEvents = triggerEvents;
		this.errorMessage = errorMessage;
		this.components = components;
		this.operator = operator;
	}

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
	
	private String getTriggerLine(){
		String triggerLine = "";
		for (String triggerEvent : triggerEvents) {
			triggerLine += triggerEvent + ", ";
		}
		return triggerLine.substring(0, triggerLine.length() - 2);
	}
	
	private String getDeclarationsLine(){
		String declarationLine = "";
		for(Component component : components){
			//declarationLine += component.getDeclarationLine();
		}
		return declarationLine;
	}
	
	private String getSelectLine(){
		String declarationLine = "";
		for(Component component : components){
			//declarationLine += component.getSelectStatement();
		}
		return declarationLine;
	}
	
	private String getComparisonLine(){
		String comparison = "";
		comparison += components.get(0).getString() + " " + operator.toString()
				+ " "  + components.get(1).getString() + " and " 
				+ components.get(2).getString();
		return comparison;
	}
	
}
