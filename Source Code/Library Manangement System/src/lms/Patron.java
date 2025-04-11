/*
Diwani Walters - 2303848
Olivia McFarlane - 2301555
Javone-Anthony Gordon - 2206126
Kemone Laws - 2109446
*/

package lms;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

import javax.swing.JOptionPane;

public class Patron {
private String fname;
    private String lname;
    private int cardNum;
    private StackLinkedList checkedOutBooks; //list of books from part 1
    
    public Patron() //default constructor
    {
        this.fname = " ";
        this.lname = " ";
        this.cardNum = 0;
        this.checkedOutBooks = new StackLinkedList();
    }
    	
	    //primary constructor
 	// Constructor with a check for null
    public Patron(String fname, String lname, int cardNum, StackLinkedList books) {
        this.fname = fname;
        this.lname = lname;
        this.cardNum = cardNum;

        // Check if books is null and assign accordingly
        if (books != null) {
            this.checkedOutBooks = books;
        } else {
            this.checkedOutBooks = new StackLinkedList();  // Create a new empty list if null
        }
    }
    
    public Patron(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;

    }
	
    	 //Updated Copy Constructor
    public Patron(Patron p) {
        this.fname = p.fname;
        this.lname = p.lname;
        this.cardNum = p.cardNum;
        
        //Ensure we copy the book stack properly
        this.checkedOutBooks = new StackLinkedList(p.checkedOutBooks);
    }
    
    //geters and setters
    public String getFname() {
            return fname;
        }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }		
    
    public StackLinkedList getCheckedOutBooks() {
        return checkedOutBooks;
    }

    public void setCheckedOutBooks(StackLinkedList checkedOutBooks) {
        this.checkedOutBooks = checkedOutBooks;
    }

	public boolean patronLog(ArrayList<Password> pass, Scanner in, PatronLinkedList list) {
        int chance = 0;
        int searchNum = 0;
        String searchP;
        boolean found = false;
        
        //Validate Library Card Number
        do {
            try {
                System.out.printf("\n\nEnter your Library Card Number-> ");
                
                while (!in.hasNextInt()) { // Prevent invalid input
		        	Toolkit.getDefaultToolkit().beep();
		            System.err.print("Invalid input! Please enter a number: ");
		            in.next();
		        }
                
                searchNum = in.nextInt();
                in.nextLine(); // Consume the newline character

                // Search linked list for the patron by card number
                PatronNode curr = list.getHead();
                while (curr != null) {
                    if (curr.getPatronData().getCardNum() == searchNum) {
                        //Store the matching card number's details
                        this.cardNum = curr.getPatronData().getCardNum();
                        this.fname = curr.getPatronData().getFname();
                        this.lname = curr.getPatronData().getLname();
                        found = true;
                        break;
                    }
                    curr = curr.getNextNode();
                }

                if (!found) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,
                        "The Entered Library Card Number doesn't exist!\nYou have " + (4 - chance) + " more tries.",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);
                    chance++; //Increase failed attempt chances
                }
            } catch (InputMismatchException e) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null,
                    "Invalid input. Please enter a valid Library Card Number.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                in.nextLine(); // Clear the invalid input
                chance++; //Increase failed attempt count
            }
        } while (chance < 5 && !found);

        //If the entered card number isn't found you go back to the previous menu
        if (!found) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
                    "Returning to the previous menu.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Password verification
        chance = 0; //Failed attempt chances is reset
        do {
            System.out.printf("\nWelcome %s!!!\nEnter your Password-> ", this.fname);
            searchP = in.next();

            String hashedSearchP = Password.hashPassword(searchP); //The entered password is hashed

            //Check if the entered and stored hashed passwords are the same
            if (hashedSearchP.equals(pass.get(searchNum - 1001).getHashPassword())) {
                System.out.printf("Correct Password %s!!", this.fname);

                //Check if the password is the default password and prompt a change
                if (pass.get(searchNum - 1001).getDefaultP().equals(pass.get(searchNum - 1001).getHashPassword())) {
                    Toolkit.getDefaultToolkit().beep();
                    System.err.printf("\n\nYou still have your Default Password it needs to be changed\nEnter what you would like it to be changed to-> ");

                    String newPassword;
                    while (true) {
                        newPassword = in.next();
                        if (newPassword.length()< 8) {
        	                Toolkit.getDefaultToolkit().beep();
                            System.err.printf("\nYour new password must be at least 8 characters long.\nPlease enter a valid password-> ");
                        }
        	            //If old password did not have "admin" (which it shouldn't), new one cannot
                        else if (newPassword.contains("admin")) {
            	                Toolkit.getDefaultToolkit().beep();
            	                System.err.print("\nYour password cannot include 'admin'.\nPlease enter a valid password-> ");
                        }
                        // Validate against reusing the same password
        	            else if (Password.hashPassword(newPassword).equals(pass.get(this.cardNum - 1001).getHashPassword())) {
        	                Toolkit.getDefaultToolkit().beep();
        	                System.err.print("\nYour New Password cannot be the same as the previous one.\nPlease enter a valid password: ");
        	            } 
                        else {
                        	break; //Exit the loop because the password meets requirements
                        }
                    }

                    // The new password entered is hashed, and set as the hashed password in the specific patron's password index
                    pass.get(searchNum - 1001).setHashPassword(Password.hashPassword(newPassword));
                    System.out.printf("\nYour New Password has been set %s!!", this.fname);
                }

                return true; //Sucessful login
            } else {
            	//Incorrect password
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null,
                    "The Entered Password is WRONG!\nYou have " + (4 - chance) + " more tries.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
                chance++; //Increase failed attempt
            }
        } while (chance < 5);

        return false; //Failed login
    }	
	
	// Allows patron to change password
	public void changePassword(ArrayList<Password> pass, Scanner in) {
	    String oldPassword = null;

	    // Validate card number bounds (which it should be in by this point anyway)
	    if ((this.cardNum - 1001) < 0 || (this.cardNum - 1001) >= pass.size()) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
                    "Invalid card number. Password change failed.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    System.out.printf("\n\nEnter your Current Password: ");
	    oldPassword = in.next();

	    //Verify that the entered password matches the stored hashed password
	    if (!Password.hashPassword(oldPassword).equals(pass.get(this.cardNum - 1001).getHashPassword())) {
	        Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
                    "Incorrect current password. Password change failed.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
	        return; //Exit if it doesn't match
	    }
	    
	    System.out.printf("\n\nEnter your New Password (at least 8 characters): ");

	    while (true) {
	        try {
	            String newPassword = in.next();

	            // Validate password length
	            if (newPassword.length() < 8) {
	                Toolkit.getDefaultToolkit().beep();
	                System.err.print("Your New Password must be at least 8 characters long.\nPlease enter a valid password: ");
	            } 
	            //If old password had "admin", new one must too
	            //To make sure the admin patron can still carry out admin functionalities
	            else if (oldPassword.contains("admin") && !newPassword.contains("admin")) {
	                Toolkit.getDefaultToolkit().beep();
	                System.err.print("Your previous password contained 'admin'. Your new password must also include 'admin'.\nPlease enter a valid password: ");
	            }
	            //If old password did not have "admin", new one cannot
	            else if (!oldPassword.contains("admin") && newPassword.contains("admin")) {
	                Toolkit.getDefaultToolkit().beep();
	                System.err.print("Your previous password did not contain 'admin'. Your new password cannot include 'admin'.\nPlease enter a valid password: ");
	            }
	            // Validate against reusing the same password
	            else if (Password.hashPassword(newPassword).equals(pass.get(this.cardNum - 1001).getHashPassword())) {
	                Toolkit.getDefaultToolkit().beep();
	                System.err.print("Your New Password cannot be the same as the previous one.\nPlease enter a valid password: ");
	            } 
	            // If all checks pass, update the password in the ArrayList
	            else {
	                pass.get(this.cardNum - 1001).setHashPassword(Password.hashPassword(newPassword));
	                System.out.printf("\nYour New Password has been set, %s!!\n", this.fname);
	                break; // Exit loop after successful password change
	            }
	        } catch (InputMismatchException e) {
	            Toolkit.getDefaultToolkit().beep();
	            System.err.print("Invalid input. Please enter a valid password: ");
	            in.next(); // Clear the invalid input
	        }
	    }
	}
	
	public void checkout(Scanner in, PatronLinkedList list, BookBinarySearchTree bst, BookLinkedList books){
			
			int opt;
			Book temp = null;
			StackLinkedList tempStack = new StackLinkedList();
			StackLinkedList current = new StackLinkedList();
			PatronNode curr = list.getHead();
			BookNode bookCurr = books.getHead();
			boolean alreadyInQueue = false;
			
			// Find the current patron logged in
			while (curr != null) {
		        if (curr.getPatronData().getCardNum() == this.cardNum) {
		            tempStack = new StackLinkedList(curr.getPatronData().checkedOutBooks); // Load previous checkouts
		        	break;
		        }
		        curr = curr.getNextNode();
		    }
			
			//Checkout Menu
			do {
		        System.out.println("\nCheck-Out Menu:");
		        System.out.println("1. Add a book to your checkout list");
		        System.out.println("2. Remove last book from your current checkout list");
		        System.out.println("3. View the last book added and total books in your current checkout list");
		        System.out.println("4. Clear your current checkout list");
		        System.out.println("5. Confirm and Exit check-out system");
		        System.out.print("Enter your option: ");
	
		        while (!in.hasNextInt()) { // Prevent invalid input
		        	Toolkit.getDefaultToolkit().beep();
		            System.err.print("Invalid input! Please enter a number: ");
		            in.next();
		        }
		        opt = in.nextInt();
	            in.nextLine();
                LibraryManagementSystem.clearTerminal();
                
		        switch (opt) {
		        case 1: //Add book to checkout list
		            while (true) {
		                // Search for the book by ISBN
		                temp = bst.searchISBN(in);
		                if (temp == null) break; // Exit if book not found
	
		                curr = list.getHead();
		                while (curr != null) {
		                    if (curr.getPatronData().getCardNum() == this.cardNum) break;
		                    curr = curr.getNextNode();
		                }
	
		                StackLinkedList patronStack = curr.getPatronData().checkedOutBooks;
		                boolean alreadyCheckedOut = false;
		                StackLinkedList tempStorage = new StackLinkedList();
	
		                // Check if the book is already in their previous checkouts
		                while (!patronStack.isEmpty()) {
		                    String isbn = patronStack.pop();
		                    tempStorage.push(isbn);
		                    if (isbn.equals(temp.getISBN())) alreadyCheckedOut = true;
		                }
	
		                //Restore the stack
		                while (!tempStorage.isEmpty()) {
		                    patronStack.push(tempStorage.pop());
		                }
	
		                if (alreadyCheckedOut) {
		                    Toolkit.getDefaultToolkit().beep();
				            JOptionPane.showMessageDialog(null,
				                    "You have already checked out this book in a previous session.",
				                    "Checkout Error",
				                    JOptionPane.ERROR_MESSAGE);
		                } else if (books.searchReturn(temp.getISBN()).getAvailable()) {
		                    System.out.printf("\nBook added: %s by %s %s!!\n", temp.getTitle(), temp.getAuthorFName(), temp.getAuthorLName());
		                    patronStack.push(temp.getISBN());
		                    tempStack.push(temp.getISBN());
		                    current.push(temp.getISBN()); // Track only new additions
		                } else {
		                    Toolkit.getDefaultToolkit().beep();
		                    JOptionPane.showMessageDialog(null,
				                    "The book is not available for checkout.",
				                    "Checkout Error",
				                    JOptionPane.ERROR_MESSAGE);
		                }
	
		                //Ask for more additions
		                System.out.print("\nAdd another book? (1- Yes) otherwise enter another number: ");
		               	
		               	while (!in.hasNextInt()) { // Prevent invalid input
				        	Toolkit.getDefaultToolkit().beep();
				            System.err.print("Invalid input! Please enter a number: ");
				            in.next();
				        }
				        int response = in.nextInt();
			            in.nextLine();

		                if (response!= 1) break;
		            }
		            System.out.print("\nPress [Enter] to continue");
	                in.nextLine();
	                LibraryManagementSystem.clearTerminal();
	                
		            break;
		            	
		        case 2: //Remove last book from checkout list
		            if (current.isEmpty()) {
		                Toolkit.getDefaultToolkit().beep();
		                JOptionPane.showMessageDialog(null,
		                    "Your current checkout list is empty. Nothing to remove!!",
		                    "Checkout Error",
		                    JOptionPane.ERROR_MESSAGE);
		                break;
		            }

		            while (true) {
		                System.out.println("\nRemoving book: " + bst.searchPassedISBN(current.stackTop(), in).details());
		                System.out.print("\nConfirm removal? (Y - Yes, Any other key to cancel) -> ");
		                String res = in.next();

		                if (res.equalsIgnoreCase("Y")) {
		                    String removedISBN = current.pop(); //Remove from current session stack
		                    tempStack.pop(); //Remove from tempStack (session tracking)
		                    
		                    //Also remove from the patron's actual checkout stack
		                    curr = list.getHead();
		                    while (curr != null) {
		                        if (curr.getPatronData().getCardNum() == this.cardNum) {
		                            StackLinkedList newStack = new StackLinkedList(); // Temporary stack
		                            
		                            //Rebuild stack without removed ISBN
		                            while (!curr.getPatronData().checkedOutBooks.isEmpty()) {
		                                String isbn = curr.getPatronData().checkedOutBooks.pop();
		                                if (!isbn.equals(removedISBN)) {
		                                    newStack.push(isbn);
		                                }
		                            }
		                            
		                            //Restore original order
		                            while (!newStack.isEmpty()) {
		                                curr.getPatronData().checkedOutBooks.push(newStack.pop());
		                            }
		                            
		                            break; //Stop searching once the correct patron is updated
		                        }
		                        curr = curr.getNextNode();
		                    }

		                    System.out.println("\nBook successfully removed.");
		                } else {
		                    System.out.println("\nBook removal cancelled.");
		                    break;
		                }

		                if (current.isEmpty()) {
		                    Toolkit.getDefaultToolkit().beep();
		                    JOptionPane.showMessageDialog(null,
		                        "Your current checkout list is now empty.",
		                        "Checkout List Empty",
		                        JOptionPane.INFORMATION_MESSAGE);
		                    break;
		                }

		                System.out.print("\nRemove another book? (Y- Yes) otherwise enter anything else: ");
		                String response = in.next().trim();
		                if (!response.equalsIgnoreCase("Y")) break;
		            }
		            System.out.print("\nPress [Enter] to continue");
	                in.nextLine();
	                in.nextLine();
	                LibraryManagementSystem.clearTerminal();
	                
		            break;		                
		        case 3: //View last book added and count
		            if (current.isEmpty()) {
		                Toolkit.getDefaultToolkit().beep();
		                JOptionPane.showMessageDialog(null,
		                    "Your current session checkout list is empty. Nothing to view!!",
		                    "Checkout Error",
		                    JOptionPane.ERROR_MESSAGE);
		                break;
		            }
		            
		            //Gets the ISBN at the top of the person's stack and search for and displays its details from in the binary search tree
		            System.out.println("Last Book Added: " + bst.searchPassedISBN(current.stackTop(), in).details());
		            System.out.println("Total books in current session checkout list: " + current.stackCount());
		            
		            System.out.print("\nPress [Enter] to continue");
	                in.nextLine();
	                LibraryManagementSystem.clearTerminal();
		            break;
	
		        case 4: //Clear checkout list
		        	
		        	//Check if the current checkout list is empty
		            if (current.isEmpty()) {
		                Toolkit.getDefaultToolkit().beep();
		                JOptionPane.showMessageDialog(null,
		                    "Your current checkout list is empty. Nothing to clear!!",
		                    "Checkout Error",
		                    JOptionPane.ERROR_MESSAGE);
		                break;
		            }

		            System.out.print("\nAre you sure you want to clear your current checkout list? (Y - Yes, Any other key to cancel) -> ");
		            String confirm = in.next();

		            if (confirm.equalsIgnoreCase("Y")) {
		                curr = list.getHead();
		                while (curr != null) {
		                    if (curr.getPatronData().getCardNum() == this.cardNum) {
		                        StackLinkedList patronStack = curr.getPatronData().checkedOutBooks;
		                        StackLinkedList tempStorage = new StackLinkedList();

		                        //Remove only books that were added in this session
		                        while (!patronStack.isEmpty()) {
		                            String isbn = patronStack.pop();

		                            //Check if the book was from this session
		                            StackLinkedList sessionCheck = new StackLinkedList(current);
		                            boolean isSessionBook = false;

		                            while (!sessionCheck.isEmpty()) {
		                                if (sessionCheck.pop().equals(isbn)) {
		                                    isSessionBook = true;
		                                    break;
		                                }
		                            }

		                            if (!isSessionBook) {
		                                tempStorage.push(isbn); // Keep books from previous sessions
		                            }
		                        }

		                        //Restore non-session books back into patron stack in correct order
		                        while (!tempStorage.isEmpty()) {
		                            patronStack.push(tempStorage.pop());
		                        }

		                        break;
		                    }
		                    curr = curr.getNextNode();
		                }

		                //Clear session-specific stacks
		                current.destroyStack();
		                tempStack.destroyStack();

		                System.out.println("\nYour current checkout list has been successfully cleared!");
		            } else {
		                System.out.println("\nCheckout list clearing cancelled.");
		            }
		            System.out.print("\nPress [Enter] to continue");
	                in.nextLine();
	                in.nextLine();
	                LibraryManagementSystem.clearTerminal();
		            break;	
		        case 5: //Confirm checkout and exit
		            int iterations = current.stackCount();
	
		            if (iterations > 0) {
		            	for (int x = 0; x < iterations; x++) {
		            	    String isbn = current.pop();
		            	    bookCurr = books.getHead();
	         	        
		            	    while (bookCurr != null) {
		            	        if (bookCurr.getBookDetail().getISBN().equals(isbn)) {
		            	            PatronQueue tempQueue = new PatronQueue();
		            	            alreadyInQueue = false;
	
		            	            //Check if cardNum is in the queue without exiting main loop
		            	            while (!bookCurr.getBookDetail().getcheckedOutPatrons().isEmpty()) {
		            	                int queuedCard = bookCurr.getBookDetail().getcheckedOutPatrons().dequeue();
		            	                if (queuedCard == cardNum) alreadyInQueue = true;
		            	                tempQueue.enqueue(queuedCard);
		            	            }
	
		            	            //Restore the queue
		            	            while (!tempQueue.isEmpty()) {
		            	                bookCurr.getBookDetail().getcheckedOutPatrons().enqueue(tempQueue.dequeue());
		            	            }
	
		            	            //If already in the book's queue exit the loop
		            	            if (alreadyInQueue) break;
	
		            	            bookCurr.getBookDetail().getcheckedOutPatrons().enqueue(cardNum); //Library Card Number is added to the book's queue
		            	            bookCurr.getBookDetail().setQuantity(bookCurr.getBookDetail().getQuantity() - 1); //The book's quantity is decremented
		            	            if (bookCurr.getBookDetail().getQuantity() == 0) {
		            	                bookCurr.getBookDetail().setAvailable(false); //If the new quantity is 0 the availability is set to false
		            	            }
		            	            break;
		            	        }
		            	        bookCurr = bookCurr.getNextNode();
		            	    }
	
		            	    if (!alreadyInQueue) {
		            	        bst.ISBNRecursiveEnqueue(bst.getRoot(), isbn, this.cardNum);
		            	    }
		            	}
		            }
		            System.out.println("Checkout confirmed. Exiting system...");
		            System.out.print("\nPress [Enter] to continue");
	                in.nextLine();
	                LibraryManagementSystem.clearTerminal();
		            break;
	
		        default:
		            Toolkit.getDefaultToolkit().beep();
		            JOptionPane.showMessageDialog(null,
		                "Invalid option! Please enter a number between 1 and 5.",
		                "Input Error",
		                JOptionPane.ERROR_MESSAGE);
		        }
		    } while (opt != 5);
		}



	public void checkIn(Scanner in, PatronLinkedList list, BookBinarySearchTree bst, BookLinkedList books) {
	    //get the head of both linked lists
		PatronNode curr = list.getHead();
		BookNode bookCurr= books.getHead();

	    //Check for the patron in the list (which he should be)
	    while (curr != null) {
	        if (curr.getPatronData().getCardNum() == this.cardNum) {
	            break;
	        }
	        curr = curr.getNextNode();
	    }

	    if (curr == null) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
                "Error: Patron not found.",
                "Check-in Error",
                JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    //Stack to store returned books for undo functionality and user- friendly displays
	    StackLinkedList returned = new StackLinkedList();

	    //loop until the user exits
	    while (true) {
	        System.out.println("\nCheck-In Menu:");
	        System.out.println("1. Return a book");
	        System.out.println("2. Undo the last book returned");
	        System.out.println("3. View last book in your checked-out list & total books");
	        System.out.println("4. View last book you returned & total books checked in during the session");
	        System.out.println("5. Cancel check-in and restore original checked-out list");
	        System.out.println("6. Exit Check-In System");
	        System.out.print("Enter your option: ");

	        while (!in.hasNextInt()) { // Prevent invalid input
                Toolkit.getDefaultToolkit().beep();
	            System.err.print("Invalid input! Please enter a number: ");
	            in.next();
	        }
	        int opt = in.nextInt();
	        in.nextLine(); // Consume newline
	        
            LibraryManagementSystem.clearTerminal();
            
	        switch (opt) {
	            case 1: //Return a book
	            	while(true) {
	            		//Check if books are there to return
		                if (curr.getPatronData().checkedOutBooks.isEmpty()) {
			                Toolkit.getDefaultToolkit().beep();
			                JOptionPane.showMessageDialog(null,
			                    "No books to return. Your checkout list is empty.",
			                    "Check-in Error",
			                    JOptionPane.ERROR_MESSAGE);
		                    break;
		                }
		                
		                //Display the book being returned and ask if they wish to proceed
		                System.out.printf("\nThe book you are returning is %s\nAre you sure you wish to carry out this action? (Y- Yes) otherwise enter anything else: ", bst.searchPassedISBN(curr.getPatronData().checkedOutBooks.stackTop(), in).details());
		                String ans= in.next();
		                
		                if (!ans.equalsIgnoreCase("Y")) {
		                    break;
		                }
	
		                //search for the book in the booklist
		                bookCurr = books.getHead();
		            	
		            	while (bookCurr != null) {
		            		if (bookCurr.getBookDetail().getISBN().equals(curr.getPatronData().checkedOutBooks.stackTop())) {
		                        break;
		                    }
		                    bookCurr = bookCurr.getNextNode();
		                }
		                
		            	//Ensure the patron is first in line to return the book
		            	if(bookCurr.getBookDetail().getcheckedOutPatrons().peek()!= this.cardNum) {
			                Toolkit.getDefaultToolkit().beep();
			                JOptionPane.showMessageDialog(null,
			                    "You cannot return this book as yet, because you were not the first person to checkout the book.",
			                    "Check-in Error",
			                    JOptionPane.ERROR_MESSAGE);
		            		break;
		            	}
		            	
		            	//Transfer the book from checkout to returned stack
		                returned.push(curr.getPatronData().checkedOutBooks.pop());
	
		                System.out.println("\nYou Returned: " + bst.searchPassedISBN(returned.stackTop(), in).details());
		                System.out.print("\nWould you like to return another book? (Y- Yes) otherwise enter anything else: ");

		                
		                String res1 = in.next();
		                if (!res1.equalsIgnoreCase("Y")) {
		                    break;
		                }
	            	}
	            	System.out.print("\nPress [Enter] to continue");
	                in.nextLine();
	                in.nextLine();
	                LibraryManagementSystem.clearTerminal();
	                
	            	break;
	            case 2: //Undo last return
	               	                
	                while(true) {
	                	//Check if there is a book to undo
	                	if (returned.isEmpty()) {
		                    Toolkit.getDefaultToolkit().beep();
		                    JOptionPane.showMessageDialog(null,
				                    "No books to undo. You haven’t returned any books yet.",
				                    "Check-in Error",
				                    JOptionPane.ERROR_MESSAGE);
		                    break;
		                }
	                	
	                	//Confirm undo operation
		                System.out.printf("\nThe returned book you are undoing is %s,\nAre you sure you wish to carry out this action? (Y- Yes) otherwise enter anything else: ", bst.searchPassedISBN(returned.stackTop(), in).details());
		                String ans= in.next();
		                
		                if (!ans.equalsIgnoreCase("Y")) {
		                    break;
		                }
	
		                //Return the book to the checked-out stack
		                String undoBook = returned.pop();
		                curr.getPatronData().checkedOutBooks.push(undoBook);
		                
	
		                System.out.println("\nUndo successful! " +  bst.searchPassedISBN(undoBook, in).details() + " is back in your checked-out list.");
		                System.out.print("\nWould you like to undo another return? (Y- Yes) otherwise enter anything else: ");
	
		                String res2 = in.next();
		                if (!res2.equalsIgnoreCase("Y")) {
		                    break;
		                }
	                }
	                System.out.print("\nPress [Enter] to continue");
	                in.nextLine();
	                in.nextLine();
	                LibraryManagementSystem.clearTerminal();
	                
	                break;
	            case 3: //View last checked out book
	            	//Check if there are any checked out books lefts
	                if (curr.getPatronData().checkedOutBooks.isEmpty()) {
	                    Toolkit.getDefaultToolkit().beep();
	                    JOptionPane.showMessageDialog(null,
			                    "Your checkout list is empty.",
			                    "Check-in Error",
			                    JOptionPane.ERROR_MESSAGE);
	                } else {
	                    System.out.println("\nLast book in your checked out list: " + bst.searchPassedISBN(curr.getPatronData().checkedOutBooks.stackTop(), in).details());
	                    System.out.println("Total books in checked out list: " + curr.getPatronData().checkedOutBooks.stackCount());
	                }
	                
	                System.out.print("\nPress [Enter] to continue");
	                in.nextLine();
	                LibraryManagementSystem.clearTerminal();
	                
	                break;

	            case 4: //View last returned book
	            	//Check if there any books returned during this session
	            	if(!returned.isEmpty()) {
	            		System.out.println("\nLast book returned: " + bst.searchPassedISBN(returned.stackTop(), in).details());
		                System.out.println("Total books checked in this session: " + returned.stackCount());
	            	} 
	            	else {
	                    Toolkit.getDefaultToolkit().beep();
	                    JOptionPane.showMessageDialog(null,
			                    "Your return list is empty.",
			                    "Check-in Error",
			                    JOptionPane.ERROR_MESSAGE);
	            	}
		             
	            	System.out.print("\nPress [Enter] to continue");
	                in.nextLine();
	                LibraryManagementSystem.clearTerminal();
	                
	                break;
	            case 5: //Cancel checkin
	            	//Returned all books returned back to the checked out list
	                while (!returned.isEmpty()) {
	                    curr.getPatronData().checkedOutBooks.push(returned.pop()); // Move all returned books back
	                }
	                System.out.println("\nCheck-in canceled. All returned books have been restored.");
	                
	                System.out.print("\nPress [Enter] to continue");
	                in.nextLine();
	                LibraryManagementSystem.clearTerminal();
	                break;

	            case 6: 
	            	
	            	//Loops until all the ISBN's are gone from the returned stack
	            	while (!returned.isEmpty()) {
				        String isbn = returned.pop();
	            		bookCurr = books.getHead();
		            	
		            	while (bookCurr != null) {
		            		if (bookCurr.getBookDetail().getISBN().equals(isbn)) {
		                    	
		                        bookCurr.getBookDetail().getcheckedOutPatrons().dequeue(); //The person's card number is removed from the book's queue
		                        bookCurr.getBookDetail().setQuantity(bookCurr.getBookDetail().getQuantity()+ 1); //The book's quantity is incremented
		                        
		                        if(bookCurr.getBookDetail().getQuantity()> 0) {
		                        	bookCurr.getBookDetail().setAvailable(true); //The Book's availability is made true 
		                        }
		                        break;
		                    }
		                    bookCurr = bookCurr.getNextNode();
		                }
		            	
					    bst.ISBNRecursiveDequeue(bst.getRoot(), isbn, opt);	        					     
	            	}
	            	
	                System.out.println("Exiting Check-In System...");
	                System.out.print("\nPress [Enter] to continue");
	                in.nextLine();
	                LibraryManagementSystem.clearTerminal();
	                return;

	            default:
	                Toolkit.getDefaultToolkit().beep();
	                JOptionPane.showMessageDialog(null,
		                    "Invalid option! Please enter a number between 1 and 6.",
		                    "Input Error",
		                    JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
	
	//Total number of patrons with books checked out and the total number of books checked out
	public void viewCheckOutSummary(PatronLinkedList patronList){
        int totalPatrons = 0;
        int totalBooksCheckedOut = 0;
        
        PatronNode curr = patronList.getHead();
        while(curr != null){
            Patron patron = curr.getPatronData();
            
            //Check if the patron has checked-out books
            if(patron.getCheckedOutBooks() != null && !patron.getCheckedOutBooks().isEmpty()){
                totalPatrons++; //Increment count of patrons with books checked out
                totalBooksCheckedOut += patron.getCheckedOutBooks().stackCount(); //Add the amount of books the patron has to the count of books
            }
            
            curr = curr.getNextNode();
        }
        
        //Display to the user
        System.out.print("\nLibrary Checkout Statistics: ");
        System.out.print("\nTotal Patrons with Books Checked Out: " + totalPatrons);
        System.out.println("\nTotal Books Checked Out: " + totalBooksCheckedOut);
        
    }

    @Override
    public String toString() {
        return fname + "\t"+ lname + "\t" + cardNum + "\t"+ checkedOutBooks.toString();
    }	
    
  	public String detailedToString() {
        return "Patron Name: " + fname +" "+ lname + " | Library Card Number: " + cardNum + " | List of Checked out books: "+ checkedOutBooks+ "\n";
    }		


}
