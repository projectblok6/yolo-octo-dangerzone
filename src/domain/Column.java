package domain;

public class Column {
	private String tableName;
	private String columnName;
	private String uniqueValue;
	private String type;
	public Column(String columnName, String table, String uniqueValue, String type){
		this.tableName = table;
		this.columnName = columnName;
		this.uniqueValue = uniqueValue;
		this.type = type;
	}
	public String getTableName() {
		return tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public String getUniqueValue() {
		return uniqueValue;
	}
	public String getType(){
		return type;
	}
}
