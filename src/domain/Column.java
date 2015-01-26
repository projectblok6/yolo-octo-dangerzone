package domain;

public class Column {
	private String tableName;
	private String columnName;
	private String uniqueValue;
	private String uniqueValueName;
	private String type;
	public Column(String columnName, String table, String uniqueValue, String uniqueValueName, String type){
		this.tableName = table;
		this.columnName = columnName;
		this.uniqueValueName = uniqueValueName;
		this.uniqueValue = uniqueValue;
		this.type = type;
	}
	public String getTableName() {
		return tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public String getUniqueValueName() {
		return uniqueValueName;
	}
	public String getUniqueValue() {
		return uniqueValue;
	}
	public String getType(){
		return type;
	}
}
