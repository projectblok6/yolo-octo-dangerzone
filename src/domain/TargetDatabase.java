package domain;

public class TargetDatabase {
	private String username;
	private String password;
	private String host;
	private int port;
	private String SSID;
	private String databaseType;
	public TargetDatabase(String username, String password, String host, int port, String SSID, String databaseType) {
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
		this.SSID = SSID;
		this.databaseType = databaseType;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public String getSSID() {
		return SSID;
	}
	public String getDatabaseType() {
		return databaseType;
	}
}
