package org.recruitment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class App {
	private String username = "dob";
	private String password = "password";

	private App() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-module.xml");
		DriverManagerDataSource dmds = (DriverManagerDataSource) context.getBean("dataSource");

		dmds.setPassword(password);
		dmds.setUsername(username);

		try {
			Connection con = dmds.getConnection();
			Statement statement = con.createStatement();
			String sql = "select * from agent";
			ResultSet rs = statement.executeQuery(sql);

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
				System.out.println("agentID:\t" + agentID + "");
				System.out.println("agentFirsrName:\t" + agentFirsrName);
				System.out.println("agentLastName:\t" + agentLastName);
				System.out.println("agentExperience:" + agentExperience);
				System.out.println(line);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new App();
	}
}
