/*
Diwani Walters - 2303848
Olivia McFarlane - 2301555
Javone-Anthony Gordon - 2206126
Kemone Laws - 2109446
*/

package lms;

import java.awt.Toolkit;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;

public class LibraryManagementSystem {

    public static void main(String[] args) {
        //Creating and initializing objects
        ArrayList<Password> pass= new ArrayList<>();
        Patron user= new Patron();
        PatronLinkedList list= new PatronLinkedList();
        BookLinkedList bookList= new BookLinkedList();
        BookBinarySearchTree bookBST= new BookBinarySearchTree();
        Scanner input= new Scanner(System.in);
		String updateISBN = "";
        int quantityUpdateOpt = 0, deleteBookOpt = 0, adminChoice= 0, bookChoice= 0, 
        		patronChoice= 0, passwordChoice= 0, patronMenuChoice= 0,
        				quantityUpdate= 0;

        // Load Passwords
        try (Scanner fileScanner= new Scanner(new File("PasswordCollection.txt"))) {
            Password.loadPasswords(pass, fileScanner);
        } catch (FileNotFoundException e) {
            Toolkit.getDefaultToolkit().beep();
            System.err.println("The Password File hasn't been created yet. There are no passwords to retrieve");
        }

        // Load Books
        try (Scanner fileScanner= new Scanner(new File("BookCollection.txt"))) {
            bookList.readBooksFromFile(bookBST, fileScanner);
        } catch (FileNotFoundException e) {
            Toolkit.getDefaultToolkit().beep();
            System.err.println("The Book File hasn't been created yet. There are no book records to retrieve");
        }
        
        // Load Patrons
        try (Scanner fileScanner= new Scanner(new File("PatronCollection.txt"))) {
            list.readPatronsFile(fileScanner);
        } catch (FileNotFoundException e) {
            Toolkit.getDefaultToolkit().beep();
            System.err.print("The Patrons' File hasn't been created yet. There are no patron records to retrieve");
            
        }
              
        System.out.print("\nPress [Enter] to continue");
        input.nextLine();
        clearTerminal();
        
        while (true)     //Main loop for the library to keep the program running until exit
        {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("Which do you wish to use?\n1. Administrator Functionalities");
            System.out.println("2. Patron Functionalities");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            while (!input.hasNextInt())     //Input validation to ensure user enters a valid integer
            {
                Toolkit.getDefaultToolkit().beep();
                System.err.print("Invalid input! Please enter a number: ");
                input.next();
            }
            int choice = input.nextInt();
            input.nextLine();

           clearTerminal();          //Clears the console for better readability
            
            if (choice == 1)      //If Admin functionalities are selected
            {
                if (!adminLogin(input, pass))      //Calls admin login function, continues if login fails
                {
                    continue;
                }
                
                clearTerminal();

                adminChoice = 0;
                while (adminChoice != 3)     // Loop for administrator menu
                {
                    System.out.println("\n===== ADMINISTRATOR MENU =====");
                    System.out.println("1. Book Functionalities");
                    System.out.println("2. Patron Functionalities");
                    System.out.println("3. Log Out");
                    System.out.println("4. Terminate Program");
                    System.out.print("Select an option: ");

					//Input validation for admin choice
                    while (!input.hasNextInt()) 
                    {
                        Toolkit.getDefaultToolkit().beep();
                        System.err.print("Invalid input! Please enter a number: ");
                        input.next();
                    }
                    adminChoice = input.nextInt();
                    input.nextLine();
                    
                    clearTerminal();

                    if (adminChoice == 1)     //If "Book Functionalities" is selected
                    {
                        bookChoice = 0;
                        while (bookChoice != 9) {
                            System.out.println("\n===== ADMIN- BOOK FUNCTIONALITIES =====");
                            System.out.println("1. Add a Book");
                            System.out.println("2. Update Book Quantity");
                            System.out.println("3. View Book Detail by ISBN");
                            System.out.println("4. View Book Detail by Author Name");
                            System.out.println("5. View Book Detail by Title");
                            System.out.println("6. View Total Number of Books");
                            System.out.println("7. View Total Number of Patrons with Books Checked Out");
                            System.out.println("8. Delete Book");
                            System.out.println("9. Previous Menu");
                            System.out.println("10. Terminate Program");
                            System.out.print("Select an option: ");

                            while (!input.hasNextInt())         //Input validation for book choice
                            {
                                Toolkit.getDefaultToolkit().beep();
                                System.err.print("Invalid input! Please enter a number: ");
                                input.next();
                            }
                            bookChoice = input.nextInt();
                            input.nextLine();

                            clearTerminal();
                            
                            if (bookChoice == 1)        //Option to add a new book
                            {
                            	//Add book
                                System.out.println("\n===== ADD BOOKS =====");

                            	int addBookOpt= 0;
                                do {
                                    String title, authorFName, authorLName, ISBN;
                                    int quantity;
                                    
                                    //Ensures book title is not empty
                                    do{
                                        System.out.print("\nEnter Book Title: ");
                                    	title= input.nextLine();
                                    	if(title.isEmpty()){
                                            Toolkit.getDefaultToolkit().beep();
                                            JOptionPane.showMessageDialog(null, "Book title cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
                                        } 
                                    }while(title.isEmpty());
                        			
                        			//Ensures author's first name is not empty
                        			do{
                                        System.out.print("\nEnter Author First Name: ");
                                    	authorFName= input.nextLine().trim();
                                    	if(authorFName.isEmpty()){
                                        	Toolkit.getDefaultToolkit().beep();
                                            JOptionPane.showMessageDialog(null, "Author first name cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }while(authorFName.isEmpty());
                                    
                                    // Ensures author's last name is not empty
                                    do{
                                        System.out.print("\nEnter Author Last Name: ");
                                    	authorLName= input.nextLine().trim();
                                    	if(authorLName.isEmpty()){
                                        	Toolkit.getDefaultToolkit().beep();
                                            JOptionPane.showMessageDialog(null, "Author last name cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }while(authorLName.isEmpty());
                        			
                                    //Validate ISBN Format
                                    while(true){
                                        try {
                        	                System.out.print("\nEnter ISBN: ");
                        	                ISBN= input.next().trim();
                        	                
                        	                String errorMessage= "";
                        	                
                        	                if (ISBN.length() != 17)     //Ensure ISBN has exactly 17 characters
                        	                {
                        	                    errorMessage += "ISBN must be exactly 17 characters long.\n";
                        	                }
                        	                
                        	                if (ISBN.charAt(0)== '-')    //Ensure ISBN doesn't start with a dash
                        	                {
                        	                    errorMessage += "ISBN must not start with a dash (-).\n";
                        	                }
                        	                
                        	                if (ISBN.charAt(ISBN.length() - 1)== '-')  //Ensure ISBN doesn't end with a dash
                        	                {
                        	                    errorMessage += "ISBN must not end with a dash (-). \n";
                        	                }
                        	                
                        	                if (countDigits(ISBN) != 13)     // Ensure ISBN contains exactly 13 digits
                        	                {
                        	                    errorMessage += "ISBN must contain exactly 13 digits.\n";
                        	                }
                        	                
                        	                if (countDashes(ISBN) != 4)      // Ensure ISBN contains exactly 4 dashes
                        	                {
                        	                    errorMessage += "ISBN must contain exactly 4 dashes.\n";
                        	                }
                        	                
                        	                if(bookList.search(ISBN))           // Check if ISBN already exists in system
                        	                {
                                                errorMessage += "This ISBN already exists in the system.\n";
                                            }
                        	                
                        	                if (!errorMessage.isEmpty())        // If there are errors, throw exception
                        	                {
                        	                    throw new InputMismatchException(errorMessage.trim());
                        	                }
                        	                break;
                        	            } catch (InputMismatchException e) {
                        	                Toolkit.getDefaultToolkit().beep();
                        	                JOptionPane.showMessageDialog(null, "Invalid ISBN:\n" + e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                        	            }
                                    }
                                    
                                    //Ensures book quantity is a valid positive number
                        			do{
                                        System.out.print("\nEnter Book Quantity: ");
                                        
                                        while (!input.hasNextInt()) { // Prevent invalid input
								        	Toolkit.getDefaultToolkit().beep();
								            System.err.print("Invalid input! Please enter a number: ");
								            input.next();
								        }
                                        
                                    	quantity= input.nextInt();
                                    	input.nextLine();
                                    	
                                    	if(quantity <= 0){
                                            Toolkit.getDefaultToolkit().beep();
                                            System.err.print("Error: Quantity must be greater than zero!");
                                        }
                                    }while(quantity <= 0);

                                    Book newBook= new Book(title, authorFName, authorLName, ISBN, quantity);     // Create a new Book object and add it to the system
									
									try{
                                        // Insert the book into both linked list and BST for storage
                                        bookList.insertAtBack(newBook);
                                    	bookBST.insertBookBSTNode(newBook);
                                    	System.out.print("\nBook successfully added!");
                                    }catch(Exception e){
                                        Toolkit.getDefaultToolkit().beep();
                    	                JOptionPane.showMessageDialog(null, "Failed to add book: " + e.getMessage(), "Adding Book Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                    
                                    System.out.print("\n1. Add new book\nAny other value to return to the previous menu\nOption: ");       // Ask the user if they want to add another book
                                    
                                    while (!input.hasNextInt()) { // Prevent invalid input
							        	Toolkit.getDefaultToolkit().beep();
							            System.err.print("Invalid input! Please enter a number: ");
							            input.next();
							        }
                                    
                                    addBookOpt= input.nextInt();
                                    input.nextLine(); 
                                     
                                }while(addBookOpt== 1);      // Continue if the user selects "1" to add another book
                                
                                System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                            	                           	
                            }else if(bookChoice == 2)   //Option to update book quantity
                            {
                                System.out.println("\n===== UPDATE BOOK QUANTITY =====");
                            	
                            	do{
                                    while(true)   // Loop to validate ISBN input
                                    {
                                        try {
                        	                System.out.print("Enter the ISBN of the book: ");
                                    		updateISBN = input.next().trim();
                        	                
                        	                String errorMessage= "";
                        	                
                        	                if (updateISBN.length() != 17)        // Validate ISBN length
                        	                {
                        	                    errorMessage += "ISBN must be exactly 17 characters long.\n";
                        	                }
                        	                
                        	                if (updateISBN.charAt(0)== '-')      // Ensure ISBN does not start with a dash
                        	                {
                        	                    errorMessage += "ISBN must not start with a dash (-).\n";
                        	                }
                        	                
                        	                if (updateISBN.charAt(updateISBN.length() - 1)== '-')       // Ensure ISBN does not end with a dash
                        	                {
                        	                    errorMessage += "ISBN must not end with a dash (-). \n";
                        	                }
                        	                
                        	                if (countDigits(updateISBN) != 13)      // Ensure ISBN contains exactly 13 digits
                        	                {
                        	                    errorMessage += "ISBN must contain exactly 13 digits.\n";
                        	                }
                        	                
                        	                if (countDashes(updateISBN) != 4)      // Ensure ISBN contains exactly 4 dashes
                        	                {
                        	                    errorMessage += "ISBN must contain exactly 4 dashes.\n";
                        	                }
                        	                
                        	                if (!errorMessage.isEmpty())        // If any validation errors exist, throw an exception
                        	                {
                        	                    throw new InputMismatchException(errorMessage.trim());
                        	                }
                        	                break;          // Valid ISBN entered, exit loop
                        	            } catch (InputMismatchException e) {
                        	                Toolkit.getDefaultToolkit().beep();
                        	                JOptionPane.showMessageDialog(null, "Invalid ISBN:\n" + e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                        	            }
                                    }
                                    
                                    do       // Loop to accept and validate new quantity input
                                    {
                                        System.out.print("Enter new quantity: ");
                                        
                                        while (!input.hasNextInt()) { // Ensure input is a number
								        	Toolkit.getDefaultToolkit().beep();
								            System.err.print("Invalid input! Please enter a number: ");
								            input.next();
								        }
                                        
                                    	quantityUpdate = input.nextInt();
                                    	input.nextLine();
                                    	
                                    	if(quantityUpdate <= 0)     // Ensure quantity is positive
                                    	{
                                            Toolkit.getDefaultToolkit().beep();
                        	                JOptionPane.showMessageDialog(null, "Quantity must be greater than zero!", "Input Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }while(quantityUpdate <= 0);
                                    
                                    boolean updated = updateBookQuantity(updateISBN, quantityUpdate, bookList, bookBST);       // Call function to update book quantity
                                    
                                     // Display appropriate message based on update status
                                    if(updated){
                                        System.out.print("\nBook quantity updated successfully.");
                                    }else{
                                        Toolkit.getDefaultToolkit().beep();
                    	                JOptionPane.showMessageDialog(null, "Book ISBN not found.", "Quantity Update Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                           
                                    // Prompt user to update another book or return to the menu                                         
                                    System.out.print("\n\n1. Update another book\nAny other value to return to the previous menu\nOption: ");

                                    
                                    while (!input.hasNextInt())        // Ensure input is a number
                                    { 
							        	Toolkit.getDefaultToolkit().beep();
							            System.err.print("Invalid input! Please enter a number: ");
							            input.next();
							        }
                                    
                                    quantityUpdateOpt = input.nextInt();
                                    input.nextLine(); 
                               
                                }while(quantityUpdateOpt == 1);          // Repeat if user wants to update another book
                            	
                            	System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                            	
                            }else if(bookChoice == 3) {
                            	// Search for a book by ISBN and display details
                                System.out.println("\n===== BOOK DETAILS BY ISBN =====");

                                bookList.adminSearchByISBN(input, list);
                                
                                System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                            	
                            }else if(bookChoice == 4) {
                            	// Search for a book by Author Name and display details
                                System.out.println("\n===== BOOK DETAILS BY AUTHOR NAME =====");
                                
                                bookList.adminSearchByAuthor(input, list);   
                                
                                System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                                
                            }else if(bookChoice == 5) {
                                // Search for a book by Title and display details
                            	// View Book Detail by Title
                                System.out.println("\n===== BOOK DETAILS BY TITLE =====");
                                
                                bookList.adminSearchByTitle(input, list);    
                                
                                System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                                
                            }else if(bookChoice == 6) {
                                // Display the total number of books
                            	System.out.println("\n===== TOTAL NUMBER OF BOOKS =====");

                                bookList.countNodesTS();  
                                
                                System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                                
                            }else if(bookChoice == 7) {
                                // Display the total number of checked-out books
                            	System.out.println("\n===== TOTAL NUMBER OF CHECKED-OUT BOOKS =====");

                                Patron total= new Patron();
                                
                                total.viewCheckOutSummary(list);                                
                                
                                System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                            	
                            } else if(bookChoice == 8)      // Delete a book based on ISBN
                            {
                            	// Delete Book
                                System.out.println("\n===== DELETE BOOKS =====");
								String deleteISBN = "";
                            	do{
                                    while(true)   // Validate ISBN input
                                    {
                                        try {
                        	                System.out.print("Enter the ISBN of the book you want to delete: ");
                                    		deleteISBN = input.next().trim();
                        	                                                    	                
                        	                String errorMessage= "";
                        	                
                        	                if (deleteISBN.length() != 17)   //Ensure ISBN has exactly 17 characters
                        	                {
                        	                    errorMessage += "ISBN must be exactly 17 characters long.\n";
                        	                }
                        	                
                        	                if (deleteISBN.charAt(0)== '-')   //Ensure ISBN doesn't start with a dash
                        	                {
                        	                    errorMessage += "ISBN must not start with a dash (-).\n";
                        	                }
                        	                
                        	                if (deleteISBN.charAt(deleteISBN.length() - 1)== '-')     //Ensure ISBN doesn't end with a dash
                        	                {
                        	                    errorMessage += "ISBN must not end with a dash (-). \n";
                        	                }
                        	                
                        	                if (countDigits(deleteISBN) != 13)   // Ensure ISBN contains exactly 13 digits
                        	                {
                        	                    errorMessage += "ISBN must contain exactly 13 digits.\n";
                        	                }
                        	                
                        	                if (countDashes(deleteISBN) != 4)     // Ensure ISBN contains exactly 4 dashes
                        	                {
                        	                    errorMessage += "ISBN must contain exactly 4 dashes.\n";
                        	                }
                        	                
                        	                if (!errorMessage.isEmpty())      // If there are errors, throw exception
                        	                {
                        	                    throw new InputMismatchException(errorMessage.trim());
                        	                }
                        	                
                        	                break;
                        	            } catch (InputMismatchException e) {
                        	                Toolkit.getDefaultToolkit().beep();
                        	                JOptionPane.showMessageDialog(null, "Invalid ISBN:\n" + e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                        	            }
                                    }
                                    
                                    // Check if book exists before attempting deletion
                                    if (!bookList.search(deleteISBN))     
                                    {  
									    Toolkit.getDefaultToolkit().beep();
                    	                JOptionPane.showMessageDialog(null, "Delete unsuccessful. Book doesn't exist.", "Book Deletion Error", JOptionPane.ERROR_MESSAGE);
									    break;
									}
                                    
                                    if(bookList.deleteCheck(deleteISBN))  // Verify if book can be deleted
                                    {
                                        break;
                                    }
                                    
                                    quantityUpdate = 0;       // Set book quantity to 0 for deletion
                                    
                                    boolean delete = updateBookQuantity(deleteISBN, quantityUpdate, bookList, bookBST);
                                    
                                    if(delete){
                                        System.out.print("\nBook quantity updated successfully.");
                                    }else{
                                        Toolkit.getDefaultToolkit().beep();
                    	                JOptionPane.showMessageDialog(null, "Book ISBN not found.", "Book Deletion Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                    
                                    try{
                                        bookList.delete(deleteISBN);         // Delete book from system
                                        //System.out.print("\nBook deleted successfully");
                                    }catch(Exception e){
                                        Toolkit.getDefaultToolkit().beep();
                    	                JOptionPane.showMessageDialog(null, "Failed to delete book: " + e.getMessage(), "Book Deletion Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                    
                                    System.out.print("\n\n1. Delete another book\nAny other value to return to the previous menu\nOption: ");                               
                                    
                                    while (!input.hasNextInt())    // Ensure valid number input
                                    { 
                        	        	Toolkit.getDefaultToolkit().beep();
                        	            System.err.print("Invalid input! Please enter a number: ");
                        	            input.next();
                        	        }
                                    
                                    deleteBookOpt = input.nextInt();
                                    input.nextLine();
                                }while(deleteBookOpt == 1);
                            	
                            	System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                            	
                            } else if (bookChoice == 9)    // Return to the previous menu
                            {
                                System.out.println("Returning to previous menu...");
                                System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                                
                            } else if (bookChoice == 10)     //Save data to files before exiting  
                            {
                            	bookList.writeBooksToFile();
                                list.writePatronsToFile();
                                Password.storePasswords(pass);
                                
                                System.out.println("Exiting the program..."); // Inform the user that the program is exiting  

                                System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                                
                                input.close();       // Closes the Scanner to free resources  
                                return;
                            } else      // If the user enters an invalid option (not between 1 and 10), play an error beep and show a message dialog 
                            {
                            	Toolkit.getDefaultToolkit().beep();
                            	JOptionPane.showMessageDialog(null,
            		                    "Invalid option! Please enter a number between 1 and 10.",
            		                    "Input Error",
            		                    JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else if (adminChoice == 2)  // Handling admin functionalities when adminChoice == 2  
                    {
                        patronChoice = 0;
                        while (patronChoice != 7)  // Loop until the user selects option 7 to return to the previous menu  
                        {
                            System.out.println("\n===== ADMIN- PATRON FUNCTIONALITIES ====="); //Admin Patron Functionalities menu 
                            System.out.println("1. Add a Patron");
                            System.out.println("2. View Patron Details");
                            System.out.println("3. View Total Number of Patrons");
                            System.out.println("4. Make a Patron an Administrator");
                            System.out.println("5. Change Patron Password");
                            System.out.println("6. Delete Patron");
                            System.out.println("7. Previous Menu");
                            System.out.println("8. Terminate Program");
                            System.out.print("Select an option: ");

                            while (!input.hasNextInt())  // Validate user input (ensure it's an integer)  
                            {
                                Toolkit.getDefaultToolkit().beep();
                                System.err.print("Invalid input! Please enter a number: ");
                                input.next();
                            }
                            patronChoice = input.nextInt();
                            input.nextLine();

                            clearTerminal();
                            
                            if(patronChoice == 1)  // Add a new patron 
                            {
                            	// Add a Patron
                                System.out.println("\n===== ADD PATRON =====");
                                
                                // Prompt for and validate the first name  
                                System.out.print("\nEnter the Patron's First Name-> ");
                                String firstName;
                                do {
                                    firstName = input.nextLine().trim();
                                    if (firstName.isEmpty()) {
                                        Toolkit.getDefaultToolkit().beep();
                                        JOptionPane.showMessageDialog(null, "First name cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } while (firstName.isEmpty());
                                user.setFname(firstName);

								// Prompt for and validate the last name  
                                System.out.print("\nEnter the Patron's Last Name-> ");
                                String lastName;
                                do {
                                    lastName = input.nextLine().trim();
                                    if (lastName.isEmpty()) {
                                        Toolkit.getDefaultToolkit().beep();
                                        JOptionPane.showMessageDialog(null, "Last name cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } while (lastName.isEmpty());
                                user.setLname(lastName);

                                
                                list.addPatron(user, pass);     // Add the patron to the system  
                                
                                System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                            	
                            } else if(patronChoice == 2)  // View details of a specific patron 
                            {
                            	// View Patron Details
                                System.out.println("\n===== VIEW A PATRON'S DETAIL =====");

                                System.out.print("Enter the library card number of the patron's details you wish to view: ");
                                
                                while (!input.hasNextInt())     // Validate input to ensure it's a number  
                                { 
                    	        	Toolkit.getDefaultToolkit().beep();
                    	            System.err.print("Invalid input! Please enter a number: ");
                    	            input.next();
                    	        }
                                int libNum = input.nextInt();
                                input.nextLine();

                                list.viewPatronDetails(libNum, bookList);    // Retrieve and display the patron's details  
                                
                                System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                            	
                            } else if(patronChoice == 3)     // View total number of patrons  
                            {
                            	// View Total Patrons
                                System.out.println("\n===== VIEW TOTAL PATRONS =====");
                                
                                System.out.println("The total number of patrons in the system is "+ list.countPatrons());
                                
                                System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                            	
                            }else if(patronChoice == 4)       // Make a patron an administrator  
                            {
                            	// Make a Patron an Administrator
                                System.out.println("\n===== CHANGE A PATRON TO A ADMIN PATRON =====");

                                makeAdmin(input, list, pass);
                                
                                System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                input.nextLine();
                                clearTerminal();
                            	
                            }else if(patronChoice == 5)     // Submenu for changing patron passwords  
                            {
                            	passwordChoice= 0;
                            	
                            	while (passwordChoice!= 3) {
                                    System.out.println("\n===== CHANGE PATRON PASSWORD =====");
                                    System.out.println("1. Change Normal Patron Password");
                                    System.out.println("2. Change Patron Administrator Password");
                                    System.out.println("3. Previous Menu");
                                    System.out.print("Select an option: ");
                                    
                                    while (!input.hasNextInt())        // Validate input  
                                    { 
                        	        	Toolkit.getDefaultToolkit().beep();
                        	            System.err.print("Invalid input! Please enter a number: ");
                        	            input.next();
                        	        }
                                    passwordChoice = input.nextInt();
                                    input.nextLine();                                    
                                    
                                    clearTerminal();
                                    
                                    if (passwordChoice== 1)       // Change a normal patron's password  
                                    {
                                    	// Change a Patron Password
                                        System.out.println("\n===== CHANGE A *NORMAL* PATRON'S PASSWORD =====");

                                    	adminChangePassword(input, list, pass);
                                    	
                                    	System.out.print("\nPress [Enter] to continue");
                                        input.nextLine();
                                        clearTerminal();
                                        
                                    } else if (passwordChoice== 2)      // Change an administrator's password  
                                    {
                                    	// Change a Patron Administrator's Password
                                    	System.out.println("\n===== CHANGE ADMIN PATRON'S PASSWORD =====");

                                    	adminChangeAdminPassword(input, list, pass);
                                    	
                                    	System.out.print("\nPress [Enter] to continue");
                                        input.nextLine();
                                        clearTerminal();
                                    } else if(passwordChoice== 3)       // Return to the previous menu  
                                    {
                                    	System.out.println("Returning to previous menu...");
                                    	System.out.print("\nPress [Enter] to continue");
                                        input.nextLine();
                                        clearTerminal();
                                    } 
                                    else      // Handle invalid options  
                                    {
                                    	Toolkit.getDefaultToolkit().beep();
                                    	JOptionPane.showMessageDialog(null,
                    		                    "Invalid option! Please enter a number between 1 and 3.",
                    		                    "Input Error",
                    		                    JOptionPane.ERROR_MESSAGE);
                                    }
                                    
                            	}
                            	
                            }else if(patronChoice == 6)  // Delete a patron
                            {
                            	System.out.println("\n===== DELETE A PATRON FROM THE SYSTEM =====");
                                
                                System.out.print("Enter the library card number of the patron you would like to remove-> ");
                                
                                while (!input.hasNextInt())    // Validate input 
                                { 
                    	        	Toolkit.getDefaultToolkit().beep();
                    	            System.err.print("Invalid input! Please enter a number: ");
                    	            input.next();
                    	        }
                    	        int card = input.nextInt();
                    	        input.nextLine();
                    	        
                    	        list.removePatron(card, pass);
                    	        
                    	        System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                            	
                            }else if (patronChoice == 7)  // Return to the previous menu  
                            {
                                System.out.println("Returning to previous menu...");
                                System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                            } else if (patronChoice == 8)    // Save the current state of books, patrons, and passwords before exiting
                            {
                            	bookList.writeBooksToFile();
                                list.writePatronsToFile();
                                Password.storePasswords(pass);
                                
                                System.out.println("Exiting the program...");
                                System.out.print("\nPress [Enter] to continue");
                                input.nextLine();
                                clearTerminal();
                                
                                input.close(); // Close the input stream and exit the program
                                return;
                            } else {
                            	Toolkit.getDefaultToolkit().beep();
                            	JOptionPane.showMessageDialog(null,
            		                    "Invalid option! Please enter a number between 1 and 8.",
            		                    "Input Error",
            		                    JOptionPane.ERROR_MESSAGE); // Play an error sound and show an error dialog if invalid choice is made
                            }
                        }
                    } else if (adminChoice == 3)   // Admin menu option for logging out
                    {
                        System.out.println("Logging Out...");
                        System.out.print("\nPress [Enter] to continue");
                        input.nextLine();
                        clearTerminal();
                        
                    } else if (adminChoice == 4)  // Save the current state of books, patrons, and passwords before exiting
                    {
                    	bookList.writeBooksToFile();
                        list.writePatronsToFile();
                        Password.storePasswords(pass);
                        
                        System.out.println("Exiting the program...");
                        System.out.print("\nPress [Enter] to continue");
                        input.nextLine();
                        clearTerminal();
                        input.close();
                        
                        return;
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null,
                                "Invalid option! Please enter a number between 1 and 4.",
                                "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (choice == 2)        // Patron login
            {
                if (user.patronLog(pass, input, list))     // Attempt to log in as patron using the provided password and input
                {
                    System.out.println("\nYou have been successfully logged in");       // If login is successful, display success message             
                    
                    clearTerminal();
                    
                    patronMenuChoice = 0;
                    while (patronMenuChoice != 7)    //Patron Menu
                     {
                        System.out.println("\n===== PATRON MENU =====");
                        System.out.println("1. View Book Detail by ISBN");
                        System.out.println("2. View Book Detail by Author Name");
                        System.out.println("3. View Book Detail by Title");
                        System.out.println("4. Check-Out Books");
                        System.out.println("5. Return Books");
                        System.out.println("6. Change Password");
                        System.out.println("7. Log Out");
                        System.out.println("8. Terminate Program");
                        System.out.print("Select an option: ");

                        while (!input.hasNextInt())      // Handle invalid input for patron menu choice
                        {
                            Toolkit.getDefaultToolkit().beep();
                            System.err.print("Invalid input! Please enter a number: ");
                            input.next();
                        }
                        patronMenuChoice = input.nextInt();
                        input.nextLine();

                        clearTerminal();
                        
                        if (patronMenuChoice == 1)  //Search using the ISBN and display book details by ISBN and s
                        {
                            System.out.println("\n===== BOOK DETAILS BY ISBN =====");
                            
                            bookBST.searchByISBN(input);
                            
                            System.out.print("\nPress [Enter] to continue");
                            input.nextLine();
                            clearTerminal();
                            
                        } else if (patronMenuChoice == 2)     //Search using author name and display book details by author and s
                        {
                            System.out.println("\n===== BOOK DETAILS BY AUTHOR NAME =====");
                            
                            bookBST.searchByAuthor(input);
                            
                            System.out.print("\nPress [Enter] to continue");
                            input.nextLine();
                            clearTerminal();
                            
                        } else if (patronMenuChoice == 3)       //Search using the title and display book details by title
                        {
                            System.out.println("\n===== BOOK DETAILS BY TITLE =====");
                            
                            bookBST.searchByTitle(input);
                            
                            System.out.print("\nPress [Enter] to continue");
                            input.nextLine();
                            clearTerminal();
                            
                        } else if (patronMenuChoice == 4)   // Handle book checkout
                        {
                            System.out.println("\n===== CHECK-OUT BOOKS =====");
                            user.checkout(input, list, bookBST, bookList);
                            
                                                        
                        } else if (patronMenuChoice == 5)  // Handle book return
                        {
                            System.out.println("\n===== RETURN BOOKS =====");
                            
                            user.checkIn(input, list, bookBST, bookList);
                            
                                                        
                        } else if (patronMenuChoice == 6)    // Allow patron to change their password
                        {
                            System.out.println("\n===== CHANGE YOUR PASSWORD =====");
                            
                            user.changePassword(pass, input);
                            
                            System.out.print("\nPress [Enter] to continue");
                            input.nextLine();
                            input.nextLine();
                            clearTerminal();
                            
                        } else if (patronMenuChoice == 7)       // Log out the patron and return to the previous menu
                        {
                            System.out.println("Logging Out...");
                            System.out.print("\nPress [Enter] to continue");
                            input.nextLine();
                            clearTerminal();
                        } else if (patronMenuChoice == 8)     // Exit the program, saving all data before exiting
                        {
                        	bookList.writeBooksToFile();
                            list.writePatronsToFile();
                            Password.storePasswords(pass);
                            
                            // Print exit message and close program
                            System.out.println("Exiting the program...");
                            System.out.print("\nPress [Enter] to continue");
                            input.nextLine();
                            clearTerminal();
                            input.close();
                            return;
                        } else {
                            Toolkit.getDefaultToolkit().beep();
                            JOptionPane.showMessageDialog(null,
                                    "Invalid option! Please enter a number between 1 and 8.",
                                    "Input Error", JOptionPane.ERROR_MESSAGE);        // Play error sound and show dialog for invalid patron menu choice
                        }
                    }

                } else        // Show error message if login fails and return to the previous menu
                {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Login failed. Returning to the previous menu.", "Login Error", JOptionPane.WARNING_MESSAGE);
                    System.out.print("\nPress [Enter] to continue");
                    input.nextLine();
                    clearTerminal();                    
                }
            } else if (choice == 3)    // Save all data before exiting if the user selects to exit
            {
            	bookList.writeBooksToFile();
                list.writePatronsToFile();
                Password.storePasswords(pass);
                
                // Print exit message and close program
                System.out.println("Exiting the program...");
                System.out.print("\nPress [Enter] to continue");
                input.nextLine();
                clearTerminal();
                input.close();
                return;
            } else {
                // Show error message for invalid main menu choice
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid option! Please enter a number between 1 and 3.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    //Admin login Method
    public static boolean adminLogin(Scanner input, ArrayList<Password> pass) {
        int chance = 0;
        boolean logged = false;
        String userName;
        String password;
        int patronUserName = 0;
        boolean isAdminPatron = false;

        do {
        	//prompt the admin for a user and the patron admin for their library card number
            System.out.printf("\n\nWelcome Admin!!!!\nEnter your username (or library card number)-> ");

            if (input.hasNextInt()) {
                patronUserName = input.nextInt(); //library card number is stored if an integer was entered
                input.nextLine();
                userName = "__"; //Placeholder username because a number was entered
            } else {
                userName = input.next(); //read the username if a string was entered
                patronUserName = 1001; //Placeholder username because a string was entered
            }

            System.out.printf("Enter your password-> ");
            password = input.next();

            // Check for the main admin login
            boolean isAdmin = "admin".equals(userName) && "admin".equals(password) && patronUserName == 1001;

            //Ensure the library card number is within
            if (patronUserName - 1001 >= 0 && patronUserName - 1001 < pass.size()) {
                //Check if "admin" is in the password and if the password matches
            	isAdminPatron = password.contains("admin") &&
                        pass.get(patronUserName - 1001).getHashPassword().equals(Password.hashPassword(password));
            }

            //Checks if either of the admin possibilities were correct
            if (isAdmin || isAdminPatron) {
                logged = true; //Login successfull
                break; //Exit Loop
            } else {
            	//Error message displays the number of chances left
                Toolkit.getDefaultToolkit().beep();
                int remainingTries = 2 - chance;
                if (remainingTries > 0) {
                    JOptionPane.showMessageDialog(null,
                            "The Entered Username or Password is WRONG!\nYou have " + remainingTries + " more tries.",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            chance++; //Login attempt is incremented
        } while (chance < 3);

        //If the log in fails, a final error message is displayed
        if (!logged) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Login failed. Returning to the previous menu.", "Login Error", JOptionPane.WARNING_MESSAGE);
        }

        return logged; //login status is returned
    }
        
    //Count the number of characters in a given string
	public static int countDigits(String input) {
	    int digitCount= 0;
	    for (int i= 0; i < input.length(); i++) {
	        if (Character.isDigit(input.charAt(i))) {
	            digitCount++;
	        }
	    }
	    return digitCount;
	}
	
	//Count the number of dashes ('-') in a given string
	public static int countDashes(String input) {
        int dashCount= 0;
        for (int i= 0; i < input.length(); i++) {
            if (input.charAt(i)== '-') {
                dashCount++;
            }
        }
        return dashCount;
    }
    
    //Unified Method to Update Both Linked List and BST
    public static boolean updateBookQuantity(String ISBN, int newQuantity, BookLinkedList bookList, BookBinarySearchTree bookBST) {
        boolean updatedInList = bookList.updateBookQuantityInList(ISBN, newQuantity);
        boolean updatedInBST = bookBST.updateBookQuantityInBST(ISBN, newQuantity);

        return updatedInList && updatedInBST; // Return true if updated in both
    }
    
    //Make a patron an admin by modifying their password
	public static void makeAdmin(Scanner in, PatronLinkedList pat, ArrayList<Password> pass){
		int tempCard;
		String tempPass;
		
		System.out.print("Enter the Library Card Number of the Patron you would like to become an ADMIN-> ");
		
		while (!in.hasNextInt()) { // Prevent invalid input
        	Toolkit.getDefaultToolkit().beep();
            System.err.print("Invalid input! Please enter a number: ");
            in.next();
        }
		
		tempCard= in.nextInt();
		in.nextLine();
		
		//Check if the card number is in the system
		if(pat.searchPatron(tempCard)) {
			//The patron has to enter their password to confirm
			System.out.print("The Patron needs to enter their password to prove they consent to this action->	");
			tempPass= in.next();
			
			//Verify that the hashed entered and stored password match
			if(Password.hashPassword(tempPass).equals(pass.get(tempCard - 1001).getHashPassword())){
				//Admin is added to the password
				tempPass= "admin"+ tempPass;
				
				//The new password is hashed and stored for the patron
				pass.get(tempCard - 1001).setHashPassword(Password.hashPassword(tempPass));
				
				System.out.println("Your new password for all logins is now: " + tempPass);
				System.out.println("REMEMBER IT!!\nIf you change your password be sure to include \"admin\" in the new password or you will no longer have admin privileges");
			} else {
	            Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null,
	                    "The Entered Password is Incorrect\nTry Again Later",
	                    "Incorrect Password",
	                    JOptionPane.ERROR_MESSAGE);
				return;
			}
			
		}
		else {
            Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null,
                    "The Entered Library Card Number doesn't exist in the system",
                    "Make Administrator Error: Non-Existent Record",
                    JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	//Admin resets a normal patron's password
	public static void adminChangePassword(Scanner in, PatronLinkedList pat, ArrayList<Password> pass) {
		int tempCard;
		String tempPass;
		
		System.out.print("Enter the Library Card Number of the Patron you are changing the password for-> ");
		
		while (!in.hasNextInt()) { // Prevent invalid input
        	Toolkit.getDefaultToolkit().beep();
            System.err.print("Invalid input! Please enter a number: ");
            in.next();
        }
		
		tempCard= in.nextInt();
		in.nextLine();
		
		//Check if the library card number exist in the system
		if(pat.searchPatron(tempCard)) {
			tempPass= Password.generateDefaultPassword(); //generate a new password for the patron
							
			//The generated password is hashed and stored as the person's new password
			pass.get(tempCard - 1001).setHashPassword(Password.hashPassword(tempPass));
				
			System.out.println("The Patron's new password is now: " + tempPass);
			System.out.println("REMEMBER IT!!");	
		}
		else {
            Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null,
                    "The Entered Library Card Number doesn't exist in the system",
                    "Admin Change Password Error: Non-Existent Record",
                    JOptionPane.ERROR_MESSAGE);
			return;
		}		
	}
	
	//Admin resets a patron admin's password
	public static void adminChangeAdminPassword(Scanner in, PatronLinkedList pat, ArrayList<Password> pass) {
		int tempCard;
		String tempPass;
		
		System.out.print("Enter the Library Card Number of the Patron Admin you are changing the password for-> ");
		
		while (!in.hasNextInt()) { // Prevent invalid input
        	Toolkit.getDefaultToolkit().beep();
            System.err.print("Invalid input! Please enter a number: ");
            in.next();
        }
		
		tempCard= in.nextInt();
		in.nextLine();
		
		//Check if the library card number exist in the system
		if(pat.searchPatron(tempCard)) {
			//Generate a new password and concat "admin" to the start of the new password
			tempPass= Password.generateDefaultPassword();
							
			tempPass= "admin"+ tempPass;

			//The generated and modified password is hashed and stored as the person's new password
			pass.get(tempCard - 1001).setHashPassword(Password.hashPassword(tempPass));
			
			System.out.println("The Patron Admin's password for all logins is now: " + tempPass);
			System.out.println("REMEMBER IT!!\nIf you change your password be sure to include \"admin\" in the new password or you will no longer have admin privileges");	
		}
		else {
            Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null,
                    "The Entered Library Card Number doesn't exist in the system",
                    "Admin Change Password Error: Non-Existent Record",
                    JOptionPane.ERROR_MESSAGE);
			return;
		}		
	}

	// Method to clear the console/terminal
	public static void clearTerminal() {
        try {
            // Get the operating system name
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                // If the OS is Windows, run the 'cls' command using cmd
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO() // Inherit input/output streams to execute in the same console
                        .start()     // Start the process
                        .waitFor();  // Wait for it to complete
            } else {
                // For Linux/macOS, use ANSI escape codes to clear the screen
                System.out.print("\033[H\033[2J"); // Moves cursor to the top-left and clears the screen
                System.out.flush(); // Flush the output stream to apply changes
            }
        } catch (IOException | InterruptedException e) {
            // Print any exceptions that occur during execution
            e.printStackTrace();
        }
    }

    
}
