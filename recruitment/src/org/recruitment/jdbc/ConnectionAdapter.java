package org.recruitment.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import android.util.Log;

public class ConnectionAdapter {

	private Connection con;
	private Object lock = new Object();
	private final String protocol = "jdbc:mysql://";
	private final String host = "instance43799.db.xeround.com";
	private final String database = "/recruitment?";
	private final String port = ":9123";
	private final String username = "user=";
	private final String password = "password=";
	private final String jdbc_driver = "com.mysql.jdbc.Driver";

	public ConnectionAdapter() {
		synchronized (lock) {
			if (con == null) {
				initConnection();
			}
		}
	}

	public void close() {
		try {
			if (!con.isClosed()) {
				con.close();
			}
		} catch (Exception e) {
			Log.e(e.getClass().getName(), e.getMessage());
		}
	}

	private void initConnection() {
		try {
			Class.forName(jdbc_driver).newInstance();
			String url = protocol + host + port + database + username + "&" + password;
			con = DriverManager.getConnection(url);
		} catch (SQLException ex) {
			Log.e(ex.getClass().getName(), ex.getMessage());
		} catch (ClassNotFoundException ex) {
			Log.e(ex.getClass().getName(), ex.getMessage() + "\nWhere is your MySQL JDBC Driver?");
		} catch (InstantiationException ex) {
			Log.e(ex.getClass().getName(), ex.getMessage());
		} catch (IllegalAccessException ex) {
			Log.e(ex.getClass().getName(), ex.getMessage());
		} 
//		finally {
//			close();
//		}
	}

	public Statement createStatement() {
		Statement st = null;
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
	}

	private void selectAllFromAgent() {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from agent");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			int agentID = 0;
			String agentFirsrName = null, agentLastName = null, agentExperience = null;
			String line = "----------------";

			while (rs.next()) {
				for (int inx = 1; inx <= columnsNumber; inx++) {
					if (inx == 1) {
						agentID = rs.getInt(inx);
					} else if (inx == 2) {
						agentFirsrName = rs.getString(inx);
					} else if (inx == 3) {
						agentLastName = rs.getString(inx);
					} else if (inx == 4) {
						agentExperience = rs.getString(inx);
					}
				}
				Log.d("agentID", agentID + "");
				Log.d("agentFirsrName", agentFirsrName);
				Log.d("agentLastName", agentLastName);
				Log.d("agentExperience", agentExperience);
				Log.d(line, line);
			}
		} catch (SQLException e) {
			Log.e(e.getClass().getName(), e.getMessage());
		}
	}
}
