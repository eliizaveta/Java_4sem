package ru.spbstu.main.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

public class DatabaseModel {

	private static String username;
	private static String password;
	private static Integer port; 
	private static String URL;
	
	private static String nameBase;
	private static String tableName;
	
	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultSet;
	
	private static Integer prodid = 1;
	
	public DatabaseModel (String username, String password, Integer port, String nameBase, String tableName) throws SQLException {
		DatabaseModel.nameBase = nameBase;
		DatabaseModel.tableName = tableName;
		DatabaseModel.username = username;
		DatabaseModel.password = password;
		DatabaseModel.port = port;
		URL = "jdbc:mysql://localhost:" + port + "/" + nameBase + "?serverTimezone=UTC";
		connection = DriverManager.getConnection(URL, username, password);
		statement = connection.createStatement();
		try {
			statement.executeUpdate(queryCreateTable(tableName));
			
		}catch(SQLSyntaxErrorException e) {
			statement.executeUpdate(queryCleanTable(tableName));
		}
	}

	private String queryCleanTable(String tableName) {
		StringBuilder sb = new StringBuilder();
		sb.append("TRUNCATE TABLE ").append(tableName).append(";");
		return sb.toString();
	}
	
	private String queryCreateTable(String tableName) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE ").append(tableName);
		sb.append("(id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, ");
		sb.append("prodid INT(6) NOT NULL, ");
		sb.append("title VARCHAR(30) NOT NULL, ");
		sb.append("cost INT(6) UNSIGNED NOT NULL )");
		return sb.toString();
	}

	protected int add(String title, String cost) throws SQLException {
		StringBuilder sb = new StringBuilder();
		String query = "select title from " + nameBase + "." + tableName;
		resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			if(resultSet.getString(1).contentEquals(title)) {
				return 0;
			}
		}
		sb.append("insert into ").append(nameBase).append(".").append(tableName).append(" (prodid, title, cost)");
		sb.append("values (").append(prodid).append(", '").append(title).append("', ").append(cost);
		sb.append(");");
		statement.executeUpdate(sb.toString());
		prodid++;
		return 1;
	}
	
	protected int delete(String title) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ").append(nameBase).append(".").append(tableName);
		sb.append(" WHERE title='").append(title).append("';");
		return statement.executeUpdate(sb.toString());
	}
	
	protected String showAll() throws SQLException {
		String query = "select prodid, title, cost from " + nameBase + "." + tableName;
		resultSet = statement.executeQuery(query);
		StringBuilder sb = new StringBuilder();
		while(resultSet.next()) {
			sb.append("prodid: ").append(resultSet.getInt(1));
			sb.append(", title: ").append(resultSet.getString(2));
			sb.append(", cost: ").append(resultSet.getInt(3));
			sb.append("\n");
		}
		if(sb.length() == 0) return "empty";
		return sb.toString();
	}
	
	protected Integer price(String title) throws SQLException {
		String query = "select title, cost from " + nameBase + "." + tableName;
		resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			if(resultSet.getString(1).contentEquals(title)) {
				return resultSet.getInt(2);
			}
		}
		return -1;
	}
	
	protected Integer changePrice(String title, Integer newCost) throws SQLException {
		String query = "update " + tableName + " set cost=" + newCost + " where title='" + title + "';";
		return statement.executeUpdate(query);
	}
	
	protected String filterByPrice(Integer from, Integer to) throws SQLException {
		StringBuilder sb = new StringBuilder();
		String query = "select prodid, title, cost from " + tableName + " where cost between " + from + " and " + to + ";";
		resultSet = statement.executeQuery(query);
		while(resultSet.next()) {
			sb.append("prodid: ").append(resultSet.getInt(1));
			sb.append(", title: ").append(resultSet.getString(2));
			sb.append(", cost: ").append(resultSet.getInt(3));
			sb.append("\n");
		}
		if(sb.length() != 0) return sb.toString();
		
		return "No products found in this price range";
	}
	
	protected void close() throws SQLException {
		connection.close();
	}
	
	protected String getUsername() {
		return username;
	}

	protected void setUsername(String username) {
		DatabaseModel.username = username;
	}

	protected String getPassword() {
		return password;
	}

	protected void setPassword(String password) {
		DatabaseModel.password = password;
	}

	protected Integer getPort() {
		return port;
	}

	protected void setPort(Integer port) {
		DatabaseModel.port = port;
	}
}
