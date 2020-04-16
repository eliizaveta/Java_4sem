package ru.spbstu.main.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

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
			
		} catch(SQLSyntaxErrorException e) {
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
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		String check = "SELECT * FROM products WHERE title = ?";
		preparedStatement = connection.prepareStatement(check);
		preparedStatement.setString(1, title);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			return 0;
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
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		String check = "SELECT * FROM products WHERE title = ?";
		preparedStatement = connection.prepareStatement(check);
		preparedStatement.setString(1, title);
		resultSet = preparedStatement.executeQuery();
		if (!resultSet.next()) {
			return -1;
		}
		return resultSet.getInt("cost");
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
	
	protected ArrayList <String> getTitles() throws SQLException {
		ArrayList <String> result = new ArrayList();
		String query = "select title from " + tableName + ";";
		resultSet = statement.executeQuery(query);
		while(resultSet.next()) {
			result.add(resultSet.getString(1));
		}
		if(result.size() != 0) return result;
		
		return null;
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
	
	public void generateProducts(int N) throws SQLException {
		Random random = new Random();
		String product = "product";
		for(int i = 0; i < N; i++) {
			add(product + i, String.valueOf(random.nextInt(10000)));
		}
	}
}
