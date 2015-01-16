package domain;

public class Column implements Component {
	private String col;
	
	public Column(String c){
		this.col = c;
	}
	
	@Override
	public String getString(){
		return col;
	}
}
