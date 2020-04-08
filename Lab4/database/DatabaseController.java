package ru.spbstu.main.database;

import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class DatabaseController {
	public static void menu(DatabaseModel dm) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Input your query in format: /query ...");
		String line;
		int result;

		while(!(line = readNewLine(scanner)).contentEquals("/exit")) {
			String [] lines = line.split(" ");
			switch(lines[0]) {
			case "/add":
				try {
					if(lines.length != 3) throw new InvalidParameterException();
					result = dm.add(lines[1], lines[2]);
					if(result == 0) System.out.println("Product with this title already exists");
					else if(result == 1) System.out.println("Product has been added successfully");
					else System.out.println("Unknown error");
				}catch(Exception e) {
					System.out.println("To add product use: /add title cost (cost > 0)");
				}
				continue;
				
			case "/delete":
				try {
					if(lines.length != 2) throw new InvalidParameterException();
					result =  dm.delete(lines[1]);
					if(result == 1) System.out.println("Product has been deleted successfully");
					else if(result == 0) System.out.println("Can't delete product,make sure the title is correct");
					else System.out.println("Unknown error");
				} catch(Exception e) {
					System.out.println("To delete a product use: /delete title");
				}
				continue;
				
			case "/show_all":
				try {
					if(lines.length != 1) throw new InvalidParameterException();
					System.out.println(dm.showAll());
				} catch(Exception e) {
					System.out.println("To show all products use: /show_all");
				}
				continue;
				
			case "/price":
				try {
					if(lines.length != 2) throw new InvalidParameterException();
					result = dm.price(lines[1]);
					if(result == -1) System.out.println("You can't see the price of a product with this name.");
					else System.out.println("Price: " + result);
				} catch(Exception e) {
					System.out.println("To see the price of a product use: /price title");
				}
				continue;
				
			case "/change_price":
				try {
					if(lines.length != 3) throw new InvalidParameterException();
					 result = dm.changePrice(lines[1], Integer.parseInt(lines[2]));
					 if(result == 1) System.out.println("Price has been changed successfully");
					 else if(result == 0) System.out.println("You can't change price of product with this title");
					 else  System.out.println("Unknown error");
				} catch(Exception e) {
					System.out.println("To change product's price use: /change_price title cost(cost >0)");
				}
				continue;
				
			case "/filter_by_price":
				try {
					if(lines.length != 3) throw new InvalidParameterException();
					System.out.println(dm.filterByPrice(Integer.parseInt(lines[1]), Integer.parseInt(lines[2])));
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("To see products in a specific price range: /filter_by_price range_begin range_end");
				}
				continue;
				
			case "/help":
				System.out.println("To add product use: /add title cost (cost > 0)");
				System.out.println("To delete a product use: /delete title");
				System.out.println("To show all products use: /show_all");
				System.out.println("To see the price of a product use: /price title");
				System.out.println("To change product's price use: /change_price title cost(cost >0)");
				System.out.println("To see products in a specific price range: /filter_by_price range_begin range_end");
				System.out.println("To see this message use: /help");
				continue;
			}
			System.out.println("Incorrect command. Use /help to see all commands.");
		}
		scanner.close();
		dm.close();
	}
	
	public static void generateProducts(int N, DatabaseModel dm) throws SQLException {
		Random random = new Random();
		String product = "product";
		for(int i = 0; i < N; i++) {
			dm.add(product + i, String.valueOf(random.nextInt(10000)));
		}
	}
	
	private static String readNewLine(Scanner scanner) {
		System.out.print("> ");
		return scanner.nextLine().trim();
	}
}
