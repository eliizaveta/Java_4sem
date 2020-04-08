package ru.spbstu.main;

import ru.spbstu.main.database.DatabaseController;
import ru.spbstu.main.database.DatabaseModel;

public class Main {
	final static String username = "root";
	final static String password = "1234";
	final static Integer port = 3306;
	
	final static String baseName = "shop";
	final static String tableName = "products";
	
	public static void main(String [] args) {
		try {
			if(args.length != 1) {
				throw new IllegalArgumentException();
			}
			int N = Integer.parseInt(args[0]);
			if (N > 0) {
					DatabaseModel dm = new DatabaseModel(username, password, port, baseName, tableName);
					DatabaseController.generateProducts(N, dm);
					DatabaseController.menu(dm);
			}
		} catch(Exception e) {
			System.out.println("Invalid parameters or your database is incorrect");
			e.printStackTrace();
		}
	}
}
