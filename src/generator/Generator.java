package generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;




import org.apache.commons.lang3.StringUtils;

import domain.BusinessRule;
import domain.Column;

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
		triggerString = triggerString.replaceAll("%declarations%", getDeclarationLine());
		triggerString = triggerString.replaceAll("%selectstatements%", getSelectLine());
		triggerString = triggerString.replaceAll("%comparison%", getComparisonLine());
		triggerString = triggerString.replaceAll("%tablename%", rule.getRestrictedTable());
		triggerString = triggerString.replaceAll("%businessrules%", triggerString);
		triggerString = triggerString.replaceAll("%triggername%", getTriggerName());
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
				triggerLine += "'" + triggerEvent + "'";
			}
			return triggerLine;
		} else{
			String triggerLine = "";
			
			for(int i = 0; i <= rule.getTriggerEvents().size() - 1; i++){
				triggerLine += "'" + rule.getTriggerEvents().get(i) + "'";
				if(i != rule.getTriggerEvents().size() - 1){
					triggerLine += ", ";
				}
			}
			
			return triggerLine;
		}
	}

	private String getDeclarationLine(){
		String declarationLine = "";
		for (Column c : rule.getColumns()){
			if(c.getType().equals("varchar2")){
				declarationLine += "l_" + c.getColumnName() + " " + c.getType() + "(4000);";
			}else if(c.getType().equals("number")){
				declarationLine += "l_" + c.getColumnName() + " " + c.getType() + "(38);";
			}else if(c.getType().equals("date")||c.getType().equals("boolean")){
				declarationLine += "l_" + c.getColumnName() + " " + c.getType();
			}
			
		}
		return declarationLine;
	}
	
	private String getSelectLine() {
		String selectLine = "";
		for (Column c : rule.getColumns()) {
			selectLine += "SELECT " + c.getColumnName();
			selectLine += " INTO l_"+ c.getColumnName();
			selectLine += " FROM " + c.getTableName();
			selectLine += " WHERE " + c.getColumnName() + " = ";
			if(c.getType().equals("varchar2") || c.getType().endsWith("varchar2")){
				selectLine += "'" + c.getUniqueValue() + "';";
			}
			else{
				selectLine += c.getUniqueValue() + ";";
			}
		}
		return selectLine;
	}
	
	private String getComparisonLine(){
		String template = rule.getTemplate();
		template = template.replaceFirst("%column%", ":new." + rule.getRestrictedColumn());
		template = template.replaceAll("%operator%", rule.getOperator());
		int countvalues = StringUtils.countMatches(template, "%literalvalue%");
		int countcolumns = StringUtils.countMatches(template, "%column%");
		
		for(int i = 0; i <= countvalues-1; i++){
			String value = rule.getValues().get(i).toString();		
			template = template.replaceFirst("%literalvalue%", value);
		}
		for(int i = 0; i <= countcolumns-1; i++){
			String columnString = "";
			columnString = "l_" + rule.getColumns().get(i).getColumnName();
			template = template.replaceFirst("%column%", columnString);
		}
		return template;
	}

	private String getTriggerName() {
		String triggerName = "BRG_";
		triggerName += rule.getRestrictedColumn().replaceAll("[aeiou]\\B", "").toUpperCase();
		triggerName += "_" + rule.getNameCode() + "_";
		triggerName += rule.getRuleId();
		return triggerName;
	}

}