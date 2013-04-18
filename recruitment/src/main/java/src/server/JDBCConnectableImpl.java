package src.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import src.client.JDBCConnectable;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class JDBCConnectableImpl extends RemoteServiceServlet implements JDBCConnectable {

	private Connection con;
	private Object lock = new Object();
	private final String protocol = "jdbc:mysql://";
	private final String host = "instance43799.db.xeround.com";
	private final String database = "/recruitment?";
	private final String port = ":9123";
	private String username = "user=";
	private String password = "password=";
	private final String jdbc_driver = "com.mysql.jdbc.Driver";

	public List executeQuery(String sql, String username, String password) {
		List result = new ArrayList<>();
		result.add("");
		result.add("");
		result.add("");
		synchronized (lock) {
			if (con == null) {
				this.username = this.username + username;
				this.password = this.password + password;
				try {
					initConnection();
					ResultSet resultSet = con.createStatement().executeQuery(sql);

					System.out.println("\n\n resultSet: " + resultSet + "\n\n");

				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (!con.isClosed()) {
							con.close();
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		return result;
	}

	private void initConnection() throws InstantiationException, IllegalAccessException, SQLException {
		try {
			Class.forName(jdbc_driver).newInstance();
			String url = protocol + host + port + database + username + "&" + password;
			con = DriverManager.getConnection(url);
		} catch (ClassNotFoundException ex) {
			System.err.println("\n\nWhere is your MySQL JDBC Driver?\n\n");
			ex.printStackTrace();
		}
	}
}
