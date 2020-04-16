package ru.spbstu.main.database;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.Border;

public class DatabaseView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	final static String username = "root";
	final static String password = "1234";
	
	final static Integer port = 3306;
	
	final static String baseName = "shop";
	final static String tableName = "products";
	
	private static DatabaseModel dm;
	
	private static JFrame window = new JFrame("Product management");
	private static JTextArea databaseInfo;
	private static JScrollPane databaseScroll;
	private static JTextArea logs;
	private static JScrollPane logsScroll;
	private static JPanel buttonsPanel;
	private static JButton addButton = new JButton("Add product");
	private static JButton delButton = new JButton("Delete product");
	private static JButton showButton = new JButton("Show database");
	private static JButton byPriceButton = new JButton("Get product price");
	private static JButton changePriceButton = new JButton("Change price");
	private static JButton filterButton = new JButton("Filter by range");
	
	public DatabaseView() {
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);
		Border infoCompound = BorderFactory.createTitledBorder(blackline, "Database Info");
		Border logsCompound = BorderFactory.createTitledBorder(blackline, "Logs");
		
		databaseInfo = new JTextArea();
		databaseInfo.setBackground(new Color(200, 200, 240));
		databaseInfo.setBorder(infoCompound);
		databaseInfo.setEditable(false);
		
		databaseScroll = new JScrollPane(databaseInfo);
	
		logs = new JTextArea();
		logs.setBackground(new Color(200, 200, 240));
		logs.setBorder(logsCompound);
		logs.setEditable(false);
		logsScroll = new JScrollPane(logs);
		
		buttonsPanel = new JPanel();
		buttonsPanel = new JPanel(new GridLayout(3,2));
		buttonsPanel.add(addButton);
		buttonsPanel.add(delButton);
		buttonsPanel.add(showButton);
		buttonsPanel.add(byPriceButton);
		buttonsPanel.add(changePriceButton);
		buttonsPanel.add(filterButton);
		buttonsPanel.setSize(640,480);
		addListeners();
	
		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(new GridLayout(1, 2));
		outputPanel.add(databaseScroll);
		outputPanel.add(logsScroll);
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 1));
		p.add(outputPanel);
		p.add(buttonsPanel);
	
		setLayout(new BorderLayout());	
		add(p);
		validate();
	}

	public void addListeners() {
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object result = JOptionPane.showInputDialog(window,
                        new String[] {"To add product write:", 
                                      "\"title price\""}, 
                                      "New product", 
                                      JOptionPane.QUESTION_MESSAGE);
				if (result != null) {
					String[] lines = ((String) result).split(" ");
					try {
						if(lines.length != 2) throw new InvalidParameterException();
						int sqlresult = dm.add(lines[0], lines[1]);
						if(sqlresult == 0) 
							logs.append("Error:Product with this title already exists\n");
						else
							logs.append("Product \""+lines[0]+"\" was added successfully\n");
					} catch(Exception ex) {
						logs.append("Error: To add product write: title cost (cost > 0)\n");
					}
				}
				
			}
		});
		
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList <String> list = new ArrayList();
				try {
					if (dm.getTitles() == null) {
						logs.append("Error:Product database is empty\n");
						return;
					}
					list.addAll(dm.getTitles());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				JComboBox jcb = new JComboBox(list.toArray());
				jcb.setEditable(true);
				int reply = JOptionPane.showConfirmDialog(window,jcb,
                        			"Delete product",
                                      JOptionPane.DEFAULT_OPTION);
				if(reply == JOptionPane.CLOSED_OPTION) {
					return;
				}
				Object result = jcb.getSelectedItem();
				if (result != null) {
					String[] lines = ((String) result).split(" ");
					try {
						if(lines.length != 1) throw new InvalidParameterException();
						int sqlresult = dm.delete(lines[0]);
						if(sqlresult == 0) 
							logs.append("Error:Can't delete product,make sure the title is correct\n");
						else
							logs.append("Product \""+lines[0]+"\" was deleted successfully\n");
					} catch(Exception ex) {
						logs.append("Error:Can't delete product,make sure the title is correct\n");
					}
				}
				
			}
		});
		
		showButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					databaseInfo.setText(dm.showAll());
					window.repaint();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		byPriceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList <String> list = new ArrayList();
				try {
					if (dm.getTitles() == null){
						logs.append("Error:Product database is empty\n");
						return;
					}
					list.addAll(dm.getTitles());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				JComboBox jcb = new JComboBox(list.toArray());
				jcb.setEditable(true);
				int reply = JOptionPane.showConfirmDialog(window,jcb,
                        			"Get price of a product",
                                      JOptionPane.DEFAULT_OPTION);
				if(reply == JOptionPane.CLOSED_OPTION) {
					return;
				}
				Object result = jcb.getSelectedItem();
				if (result != null) {
					String[] lines = ((String) result).split(" ");
					try {
						if(lines.length != 1) throw new InvalidParameterException();
						Integer sqlresult = dm.price(lines[0]);
						if(sqlresult == -1) 
							logs.append("Error:You can't see the price of a product with this name.\n");
						else logs.append("Price of "+lines[0]+" is "+sqlresult.toString()+"\n");
						window.repaint();
					} catch(Exception ex) {
						logs.append("To get product's price choose the product's title\n");
					}
				}
			}
		});	
		
		changePriceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList <String> list = new ArrayList();
				try {
					if (dm.getTitles() == null){
						logs.append("Error:Product database is empty\n");
						return;
					}
					list.addAll(dm.getTitles());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				JComboBox jcb = new JComboBox(list.toArray());
				jcb.setEditable(true);
				Object result = JOptionPane.showInputDialog(window,jcb,
									  "Choose product and enter new price",
                                      JOptionPane.QUESTION_MESSAGE);
				String title = (String) jcb.getSelectedItem();
				if (result != null) {
					String price = ((String) result).split(" ")[0];
					System.out.print(price + " " + title);
					try {
						int sqlresult = dm.changePrice(title, Integer.parseInt(price));
						if(sqlresult == 0) 
							logs.append("Error:You can't change price of a product with this title.\n");
						else
							logs.append("Price of \""+title+"\" has been changed to "+price+" successfully\n");
					} catch(Exception ex) {
						logs.append("Error: Invalid product name or price\n");
					}
				}
				
			}
		});
		
		filterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object result = JOptionPane.showInputDialog(window,
                        new String[] {"To get products from range", 
                                      "write 2 positive numbers"}, 
                                      "Get products by range", 
                                      JOptionPane.QUESTION_MESSAGE);
				if (result != null) {
					String[] lines = ((String) result).split(" ");
					try {
						if(lines.length != 2) throw new InvalidParameterException();
						String sqlresult = dm.filterByPrice(Integer.parseInt(lines[0]), Integer.parseInt(lines[1]));
						logs.append(sqlresult);
						window.repaint();
					} catch(Exception ex) {
						logs.append("Error:To get products from range write 2 positive numbers");
					}
				}
			}
		});	
	}
	
	public static void main(String args[]){

		try {
			System.out.println("Enter count of products: ");
		    Scanner scanner = new Scanner(System.in);
			int N = scanner.nextInt();
			if (N > 0) {
		 	dm = new DatabaseModel(username, password, port, baseName, tableName);
			dm.generateProducts(N);
			window.setSize(840, 480);
			window.setVisible(true);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.add(new DatabaseView());
			window.validate();
			}
		} catch(Exception e) {
			System.out.println("Invalid parameters or your database is incorrect");
			e.printStackTrace();
		}
	 }
}
