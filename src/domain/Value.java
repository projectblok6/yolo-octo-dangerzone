package domain;

public class Value implements Component {
	private String val;
	
	public Value(String s){
		this.val = s;
	}
	
	@Override
	public String toString(){
		return val;
	}
}
