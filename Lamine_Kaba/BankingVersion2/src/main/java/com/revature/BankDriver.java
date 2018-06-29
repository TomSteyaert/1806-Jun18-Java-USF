package com.revature;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.Scanner;



public class BankDriver {
	static Scanner userInput = new Scanner(System.in);
	private static User user = null;
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		
		mainMenu();

	}

	private static void mainMenu() {

		String userInput;

		System.out.println("\n    +--------------MAIN MENU--------------+");
		System.out.println("          Welcome To Your Bank");
		System.out.println("Please choose an option");
		

		System.out.println("[1] - Login");
		System.out.println("[2] - Register");
		System.out.print("Selection: ");

		try {
			userInput = br.readLine();

			switch(userInput) {
			case "1":
				System.out.println("Navigating to Login Menu...");
				login();
				break;
			case "2":
				System.out.println("Navigating to Registration Menu...");
				register();
				break;
			default:
				System.out.println("Invalid selection, please try again.\n");
				mainMenu();
			}

		} catch (IOException e) {
			System.out.println("[LOG] - Error while reading from console ");
			e.printStackTrace();
		}

	}
	
	private static void register() {
		
		String firstName, lastName, username, password, email;
		
		System.out.println("\n    +--------------REGISTRATION--------------+");
		
		
		try {
			
			System.out.print("First name: ");
			firstName = br.readLine();
			
			System.out.print("Last name: ");
			lastName = br.readLine();
			
			System.out.print("Username: ");
			username = br.readLine();
			
			System.out.print("Password: ");
			password = br.readLine();
			
			System.out.print("Email Address: ");
			email = br.readLine();
			
			user = new User(firstName, lastName, username, password, email);
			System.out.println("Checking username availability..." );
			
			if(usernameAvailable(user)) {
				System.out.println("Username " + user.getUsername() + " Created successfully!");
				//System.out.println("Creating new user, " + user.getUsername() + "...\n");
				serializeUser(user);
				login();
			} else {
				System.out.println("Username is not available. Please try again...");
				register();
			}
			
			
		} catch (IOException e) {
			System.out.println("[LOG] - Error while reading from console");
			e.printStackTrace();
		}
		
		
	}
	
	private static void serializeUser(User u) {
		
		String fileName = u.getUsername() + ".ser";
		
		/*
		 * Try-with-Resources block
		 * 
		 * Syntactically it is a try block with a set of parenthesis that is used to declared and initialize any
		 * variables whose data type implement the AutoCloseable interface. Any variables declared here will be 
		 * automatically closed after the try/catch execution.
		 */
		try (FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			
			/*
			 * Write the specified object to the ObjectOutputStream. The class of the object, the signature of
			 * the class, and the values of the non-transient and non-static fields of the class and all of its
			 * supertypes are written.
			 */
			oos.writeObject(u);
			
			
		} catch (FileNotFoundException e) {
			System.out.println("[LOG] - An error occurred while accessing the file 1111111");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("[LOG] - An error occurred while writing the file 222222");
			e.printStackTrace();
		}
	}
	
	private static boolean usernameAvailable(User u) {
		
		String fileName = u.getUsername() + ".ser";
		File file = new File(fileName);
		
		if(file.exists()) {
			return false;
		} else {
			return true;
		}
	}
	
	private static void login() {
		
		String username, password;
		
		System.out.println("\n    +--------------LOGIN--------------+");
		
		try {
			
			System.out.print("Username: ");
			username = br.readLine();
			
			System.out.print("Password: ");
			password = br.readLine();
			
			if(credentialsValid(username, password)) {
				System.out.println("Login successful!");
				activities();
			} else {
				System.out.println("User not found!");
				System.out.println("First time user, please select option 2\n");
				mainMenu();
			}
			
		} catch (IOException e) {
			System.out.println("User not fund! \nFirst time user, please select option 2");
			System.out.println("[LOG] - Error while reading from console");
			e.printStackTrace();
		}
	}
	
	private static void activities() {
		
		System.out.println("\n    +--------------Activities--------------+");
		
		System.out.println("What you would like to do?");
		
		System.out.println("[1] - Deposit");
		System.out.println("[2] - Withdraw");
		System.out.println("[3] - View balance");
		System.out.println("[4] - LogOut");
		System.out.print("Selection: ");
		
		String userInput;
		
		try {
			userInput = br.readLine();

			switch(userInput) {
			case "1":
				System.out.println("Navigating to deposit...");
				deposit();
				break;
			case "2":
				System.out.println("Navigating to Withdraw...");
				withdraw();
				break;
			case "3":
				System.out.println("Navigating to Balance...");
				checkBance();
				break;
			case "4":
				System.out.println(" ");
				exitProgram();
				break;
			default:
				System.out.println("Invalid selection, please try again.");
				activities();
			}

		} catch (IOException e) {
			System.out.println("[LOG] - Error while reading from console ");
			e.printStackTrace();
		}
		
	}
	
	private static void deposit() {
		
		System.out.println("\n    +--------------Deposit--------------+");
		
		System.out.println("Please enter the amount you will like to deposit");
		double depoAmount = 0;
		
		depoAmount = userInput.nextDouble();
		
		if(depoAmount <= 0) {
			System.out.println("Error! you can't deposit a negative value");
			System.out.println("Please try again");
			deposit();
		}
		else {
			user.setBalance(user.getBalance() + trimedUserInput(depoAmount));
			serializeUser(user);
			System.out.println("Deposit successful...");
			System.out.println("Do you want to check you balance?\nPress 'y' to make another transaction or any key to Logout");
		}
		
		String yesNo;
		try {
			yesNo = br.readLine();
			if(yesNo.toLowerCase().equals("y")) {
				System.out.println("Your balance is: " + showAmount());
				System.out.println("Press 'y' to make another transaction or any key to Logout");
				//String yesNo;
				try {
					yesNo = br.readLine();
					if(yesNo.toLowerCase().equals("y")) {
						activities();
					}
					else {
						logout();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				logout();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void withdraw() {
		
		System.out.println("\n    +--------------Withdraw--------------+");
		
		System.out.println("Please enter the amount you will like to withdraw");
		double withdAmount = 0;
		withdAmount = userInput.nextDouble();
		
		if(withdAmount < 0) {
			System.out.println("Error! you can't withdraw a negative value");
			System.out.println("Please try again");
			withdraw();
		}
		else if(withdAmount > user.getBalance()) {
			System.out.println("No sufficient found for the requested amount");
			System.out.println("Your balance is: " + showAmount());
			System.out.println("Please try again ");
			withdraw();
		}
		else {
			user.setBalance(user.getBalance() - trimedUserInput(withdAmount));
			serializeUser(user);
			System.out.println("Withdraw successful...");
			System.out.println("Your balance is: " + showAmount());
			System.out.println("Press 'y' to make another transaction or any key to Logout");
		}
		
		String yesNo;
		try {
			yesNo = br.readLine();
			if(yesNo.toLowerCase().equals("y")) {
				activities();
			}
			else {
				logout();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void checkBance() {
		
		System.out.println("\n    +--------------CheckBanlance--------------+");
		
		System.out.println("Your balance is: " + showAmount());
		System.out.println("Press 'y' to make another transaction or any key to Logout");
		
		String yesNo;
		try {
			yesNo = br.readLine();
			if(yesNo.toLowerCase().equals("y")) {
				activities();
			}
			else {
				logout();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private static void logout() {
		System.out.println("Thank you for your business!\nGoodbye!!");
		System.exit(0);
	}
	
	private static double trimedUserInput(double userInput) {
		
		double receivedInput = userInput;
		
		// Trim input to 2 decimal place string
		String trimedInput;
		trimedInput = new DecimalFormat("#.00").format(receivedInput);
		
		// Parse the trimed value back to double
		double parsedDouble;
		parsedDouble = Double.parseDouble(trimedInput);
		
		 return parsedDouble;
	}
	
	private static String showAmount() {
		
		return  new DecimalFormat("#.00").format(user.getBalance());
	}
	
	private static void exitProgram() {
		System.out.println("Thank you for your visiting!\nGoodbye!!");
		System.exit(0);
	}
	
	private static boolean credentialsValid(String username, String password) {
		
		String fileName = username + ".ser";
		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
			
			user = (User) ois.readObject();
			
			if(user != null) {
				
				if(username.equals(user.getUsername()) & password.equals(user.getPassword())) {
					return true;
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(" ");//[LOG] - An error occurred while accessing the file 3333");
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("[LOG] - An error occurred while writing the file 44444");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("[LOG] - An error occurred while deserializing the object");
			e.printStackTrace();
		}
		
		return false;
	}

}


