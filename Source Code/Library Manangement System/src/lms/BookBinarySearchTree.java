/*
Diwani Walters - 2303848
Olivia McFarlane - 2301555
Javone-Anthony Gordon - 2206126
Kemone Laws - 2109446
*/

package lms;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.InputMismatchException;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

public class BookBinarySearchTree 
{
	private BookBSTNode root;
	
	//Constructor
	public BookBinarySearchTree(){
		root = null;
	}
	
	//Accessor
	public BookBSTNode getRoot() {
		return root;
	}

	//Mutator
	public void setRoot(BookBSTNode root) {
		this.root = root;
	}
	
	//Insert Book into and sort Binary Search Tree while doing so
	public void insertBookBSTNode(Book book){
		BookBSTNode temp = new BookBSTNode(book);
		
		//Check if tree is empty, if so make the node you are trying to insert the first node(root)
		if(this.root == null)         
		{
			this.root = temp;
		}
		else
		{
			BookBSTNode tempRoot = this.root;
			while(true){
				//Sort books in Binary Search Tree by Title
				if(book.getTitle().compareTo(tempRoot.getData().getTitle()) < 0)   //If the book title is smaller go to the left sub tree
				{
					if(tempRoot.getLeftSubTree() == null)            // If left child is empty, insert here
					{
						tempRoot.setLeftSubTree(temp);               //Otherwise, move to the left child and continue searching for an empty spot
						break;
					}
					else{
						tempRoot = tempRoot.getLeftSubTree();
					}
				}
				else{
					if(tempRoot.getRightSubTree() == null)           //If the book title is larger go to the right sub tree
					{
						tempRoot.setRightSubTree(temp);                 //If right child is empty, insert here
						break;
					}
					else{
						tempRoot = tempRoot.getRightSubTree();         //Otherwise, move to the right child and continue searching for an empty spot
					}
				}
			}
		}
	}
	
	//Display method
	public void display() {
	    if (root == null) {
	        System.out.println("The library has no books.");
	        return;
	    }
	    System.out.println("Books in the library (sorted by Title):");
	    displayInOrder(root);
	}

	//Display the books in sorted order based on the title
	private void displayInOrder(BookBSTNode node) {
	    if (node != null) {
	        displayInOrder(node.getLeftSubTree()); // Visit left subtree
	        System.out.println("Title: " + node.getData().getTitle() + 
	                           " | Author: " + node.getData().getAuthorFName() + " " + node.getData().getAuthorLName() + 
	                           " | ISBN: " + node.getData().getISBN() + 
	                           " | Available: " + node.getData().getAvailable()+ " " + node.getData().getcheckedOutPatrons().toString());
	        displayInOrder(node.getRightSubTree()); // Visit right subtree
	    }
	}
	
	//Search Binary Search Tree Based on title
	public void searchByTitle(Scanner input) 
	{
	    System.out.print("Enter the title or part of the title you remember: ");
	    String keyword = input.nextLine().toLowerCase().trim();
	
	    List<Book> results = new ArrayList<>();
	
	    //Recursive helper function call
	    searchByTitleRecursive(root, keyword, results);
	
		//Error handling to check if user entered anything
	    if (results.isEmpty()) {
	        Toolkit.getDefaultToolkit().beep();
	        JOptionPane.showMessageDialog(null,
	                "Sorry, but we couldn't find the title you searched for in our system.",
	                "Search Error",
	                JOptionPane.ERROR_MESSAGE);
	        System.out.print("Press ENTER to continue");
	        input.nextLine(); // Wait for user input before returning
	        return;
	    }
	
		//If only 1 result was found just print that result
	    if (results.size() == 1) {
	        Book book = results.get(0);
	        System.out.println("\nBook Details:\n" + "Book Title: " + book.getTitle() +
	                " | Author's Name: " + book.getAuthorFName() + " " + book.getAuthorLName() +
	                " | Book ISBN: " + book.getISBN() + " | Available?: " + book.getAvailable());
	        return;
	    }
	
		//If multiple matching results were found, display the title and author of them all
	    System.out.println("\nBooks found based on your search:");
	    for (int i = 0; i < results.size(); i++) {
	        System.out.println((i + 1) + ". " + results.get(i).getTitle() +
	                " by " + results.get(i).getAuthorFName() + " " + results.get(i).getAuthorLName());
	    }
	
		//Prompt user to select the book tehy are looking for
	    System.out.print("\nEnter the number corresponding to the book you're looking for (or 0 to cancel): ");
	
	    while (!input.hasNextInt()) {
	        Toolkit.getDefaultToolkit().beep();
	        System.err.print("Invalid input! Please enter a number: ");
	        input.next();
	    }
	
	
	    int choice = input.nextInt();
	    input.nextLine(); // Consume newline
	
		//Display all the details of the book chosen
	    if (choice > 0 && choice <= results.size()) {
	        Book selectedBook = results.get(choice - 1);
	        System.out.println("\nBook Details:\n" + "Book Title: " + selectedBook.getTitle() +
	                " | Author's Name: " + selectedBook.getAuthorFName() + " " + selectedBook.getAuthorLName() +
	                " | Book ISBN: " + selectedBook.getISBN() + " | Available?: " + selectedBook.getAvailable());
	       
	    } else {
	        Toolkit.getDefaultToolkit().beep();
	        JOptionPane.showMessageDialog(null,
	                "Invalid selection or cancelled.",
	                "Input Error",
	                JOptionPane.ERROR_MESSAGE);
	    }
	}
		
	//Recursive Helper Function to traverse the Binary Search tree and collect books whose title contains the keyword searched
	public void searchByTitleRecursive(BookBSTNode node, String keyword, List<Book> results) {
	    if (node == null) return;

	    String title = node.getData().getTitle().toLowerCase();

	    //If the title contains the keyword, add it to results
	    if (title.contains(keyword)) {
	        results.add(node.getData());
	    }

	    //Search both left and right subtrees (to catch all partial matches)
	    searchByTitleRecursive(node.getLeftSubTree(), keyword, results);
	    searchByTitleRecursive(node.getRightSubTree(), keyword, results);
	}

	//Search Binary Search Tree Based on ISBN Number
	public void searchByISBN(Scanner input) {
	    String isbn = "";
	    boolean validInput = false;
	
	    while (!validInput) {
	        try {
	            System.out.print("Enter the ISBN of the book that you'd like to find: ");
	            isbn = input.nextLine().trim();
	
	            if (isbn.isEmpty()) {
	                Toolkit.getDefaultToolkit().beep();
	                JOptionPane.showMessageDialog(null, "ISBN cannot be empty. Please enter a valid ISBN.", "Input Error", JOptionPane.ERROR_MESSAGE);
	                continue;
	            }
	
	            String errorMessage = "";
	
				//Validate ISBN length
	            if (isbn.length() != 17) {
	                errorMessage += "ISBN must be exactly 17 characters long.\n";
	            }
	
				//Ensure ISBN does not start with a dash
	            if (isbn.charAt(0) == '-') { 
	                errorMessage += "ISBN must not start with a dash (-).\n";
	            }
	
				//Ensure ISBN does not end with a dash
	            if (isbn.charAt(isbn.length() - 1) == '-') {
	                errorMessage += "ISBN must not end with a dash (-). \n";
	            }
	
				//Ensure ISBN contains exactly 13 digits
	            if (LibraryManagementSystem.countDigits(isbn) != 13) {
	                errorMessage += "ISBN must contain exactly 13 digits.\n";
	            }
	
				//Ensure ISBN contains exactly 4 dashes
	            if (LibraryManagementSystem.countDashes(isbn) != 4) {
	                errorMessage += "ISBN must contain exactly 4 dashes.\n";
	            }
	
				//If any validation failed, throw an exception with the collected errors
	            if (!errorMessage.isEmpty()) {
	                throw new InputMismatchException(errorMessage.trim());
	            }
	
				//If all checks pass, input is valid
	            validInput = true;
	
	        } catch (InputMismatchException e) {
	            Toolkit.getDefaultToolkit().beep();
	            JOptionPane.showMessageDialog(null, "Invalid ISBN:\n" + e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	
	    //Perform a full BST traversal to find ISBN
	    Book result = searchByISBNRecursive(root, isbn);
	
	    if (result == null) {
	        Toolkit.getDefaultToolkit().beep();
	        JOptionPane.showMessageDialog(null,
	                "Sorry, but we couldn't find the ISBN you searched for in our system.",
	                "Search Error",
	                JOptionPane.ERROR_MESSAGE);
	        } 
	        else   
	        {
			//Display the details of the book with a matching ISBN
	        System.out.println("\nBook Details:\n" +
	                "Book Title: " + result.getTitle() +
	                " | Author's Name: " + result.getAuthorFName() + " " + result.getAuthorLName() +
	                " | Book ISBN: " + result.getISBN() +
	                " | Available?: " + result.getAvailable());
	    }
	}
	
	//Recursive Helper Function to traverse the Binary Search tree and collect the book whose ISBN matches the one searched for
	public Book searchByISBNRecursive(BookBSTNode node, String isbn) {
	    if (node == null) return null;
	
		//If the title contains the keyword, return in
	    if (node.getData().getISBN().equals(isbn)) {
	        return node.getData();
	    }
	
	    //Since ISBNs are not sorted, search both left and right subtrees
	    Book leftSearch = searchByISBNRecursive(node.getLeftSubTree(), isbn);
	    if (leftSearch != null) return leftSearch;
	
	    return searchByISBNRecursive(node.getRightSubTree(), isbn);
	}
	
	//Search ISBN Method For Checkout
	public Book searchISBN(Scanner input) {
	    String isbn = "";
	    boolean validInput = false;

	    while (!validInput) {
	        try {				
	            System.out.print("Enter the ISBN of the book that you'd like to find: ");
	            isbn = input.nextLine();

	            if (isbn.isEmpty()) {
	                Toolkit.getDefaultToolkit().beep();
	                JOptionPane.showMessageDialog(null, "ISBN cannot be empty. Please enter a valid ISBN.", "Input Error", JOptionPane.ERROR_MESSAGE);
	                continue;
	            }

	            String errorMessage = "";

				//Validate ISBN length
	            if (isbn.length() != 17) {
	                errorMessage += "ISBN must be exactly 17 characters long.\n";
	            }

				//Ensure ISBN does not start with a dash
	            if (isbn.charAt(0) == '-') { 
	                errorMessage += "ISBN must not start with a dash (-).\n";
	            }

				//Ensure ISBN does not end with a dash
	            if (isbn.charAt(isbn.length() - 1) == '-') {
	                errorMessage += "ISBN must not end with a dash (-). \n";
	            }

				//Ensure ISBN contains exactly 13 digits
	            if (LibraryManagementSystem.countDigits(isbn) != 13) {
	                errorMessage += "ISBN must contain exactly 13 digits.\n";
	            }

				//Ensure ISBN contains exactly 4 dashes
	            if (LibraryManagementSystem.countDashes(isbn) != 4) {
	                errorMessage += "ISBN must contain exactly 4 dashes.\n";
	            }

				//If any validation failed, throw an exception with the collected errors
	            if (!errorMessage.isEmpty()) {
	                throw new InputMismatchException(errorMessage.trim());
	            }

				//If all checks pass, input is valid
	            validInput = true;

	        } catch (InputMismatchException e) {
	            Toolkit.getDefaultToolkit().beep();
	            JOptionPane.showMessageDialog(null, "Invalid ISBN:\n" + e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	    //Perform a full BST traversal to find ISBN
	    Book result = searchByISBNRecursive(root, isbn);

	    if (result == null) {
	        return null;
	        } else {
	        return result;
	    }
	}
	
	//ISBN Recursive Enqueue For Checkout
	//Recursively searche for a book by its ISBN and enqueues the patron's card number to the queue when found.
	public void ISBNRecursiveEnqueue(BookBSTNode node, String isbn, int card) 
	{
	    if (node == null) return; // Base case: If node is null, return
	    
	    if (node.getData().getISBN().equals(isbn)) 
	    {
	        node.getData().getcheckedOutPatrons().enqueue(card);         // Add the patron's card number to the queue of checked-out patrons
	        node.getData().setQuantity(node.getData().getQuantity()-1);  // Reduce the quantity of available copies of the book by 1
	        
	        // If all copies are checked out, mark the book as unavailable
	        if(node.getData().getQuantity()== 0) 
	        {
	        	node.getData().setAvailable(false);
	        }
	        
	        return;
	    }
	    
	    //Since ISBNs are not sorted, Search both left and right subtrees
	    ISBNRecursiveEnqueue(node.getLeftSubTree(), isbn, card);
	    ISBNRecursiveEnqueue(node.getRightSubTree(), isbn, card);
	}
	
	//ISBN Recursive Dequeue For Checkin
	public void ISBNRecursiveDequeue(BookBSTNode node, String isbn, int card) 
	{
	    if (node == null) return; // Base case: If node is null, return
	   
	    // Check if the ISBN of the current node matches the one we are looking for
	    if (node.getData().getISBN().equals(isbn)) 
	    {
	        node.getData().getcheckedOutPatrons().dequeue();       // Remove the patron's card number from the queue
	        node.getData().setQuantity(node.getData().getQuantity()+ 1);      // Increase the book's quantity since it is being checked back in
	        
	        // If the quantity is greater than 0, set the book as available
	        if(node.getData().getQuantity()> 0) 
	        {
	        	node.getData().setAvailable(true);
	        }
	        
	        return;
	    }
	    
	    // Search both left and right subtrees
	    ISBNRecursiveDequeue(node.getLeftSubTree(), isbn, card);
	    ISBNRecursiveDequeue(node.getRightSubTree(), isbn, card);
	}
		
	//Search Passed ISBN Method For Checkout
	public Book searchPassedISBN(String isbn, Scanner input) {
	    // Call the recursive search method and store the result
	    Book foundBook = searchByISBNRecursive(root, isbn);

	    // If no book is found, show an error message
	    if (foundBook == null) {
	        Toolkit.getDefaultToolkit().beep();
	        JOptionPane.showMessageDialog(null,
	                "There are no books to display",
	                "Search Error",
	                JOptionPane.ERROR_MESSAGE);
	        input.nextLine(); // Wait for user input before returning
	        return null;
	    }

	    // Return the found book
	    return foundBook;
	}
    
    //Search Binary Search Tree Based on Author's Name
    public void searchByAuthor(Scanner input) 
    {
	    System.out.print("Enter the author's first name, last name, or part of their name that you'd like to find: ");
	    String keyword = input.nextLine();
	    
	    List<Book> results = new ArrayList<>();
	    
	    //Recursive search
	    searchByAuthorRecursive(root, keyword, results);
	
	    if (results.isEmpty()) {
	        Toolkit.getDefaultToolkit().beep();
	        JOptionPane.showMessageDialog(null,
	                "Sorry, but we couldn't find any books by the author you searched for.",
	                "Search Error",
	                JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	
	    // If only one book is found, print details
	    if (results.size() == 1) {
	        Book book = results.get(0);
	        System.out.println("\nBook Details:\n" + "Book Title: " + book.getTitle() + 
	                " | Author's Name: " + book.getAuthorFName() + " " + book.getAuthorLName() +  
	                " | Book ISBN: " + book.getISBN() + " | Available?: " + book.getAvailable());
	        return;
	    }
	
	    // Display all found books
	    System.out.println("\nThe books found based on your search are: ");
	    for (int i = 0; i < results.size(); i++) {
	        System.out.println((i + 1) + ". " + results.get(i).getTitle() + 
	                " by " + results.get(i).getAuthorFName() + " " + results.get(i).getAuthorLName());
	    }
	
	    // User book selection
	    System.out.print("\nEnter the number of the book you're looking for (or 0 to cancel): ");
	
	    while (!input.hasNextInt()) {
	        Toolkit.getDefaultToolkit().beep();
	        System.err.print("Invalid input! Please enter a number: ");
	        input.next();
	    }
	
	    int choice = input.nextInt();
	    input.nextLine(); // Consume newline
	
		//Display all details of the book the user selected
	    if (choice > 0 && choice <= results.size()) {
	        Book selectedBook = results.get(choice - 1);
	        System.out.println("\nBook Details:\n" + "Book Title: " + selectedBook.getTitle() + 
	                " | Author's Name: " + selectedBook.getAuthorFName() + " " + selectedBook.getAuthorLName() +  
	                " | Book ISBN: " + selectedBook.getISBN() + " | Available?: " + selectedBook.getAvailable());
	        } else {
	        Toolkit.getDefaultToolkit().beep();
	        JOptionPane.showMessageDialog(null,
	                "Invalid selection or cancelled.",
	                "Input Error",
	                JOptionPane.ERROR_MESSAGE);
	    }
	}

	//Recursive Search Helper Method for Author
	public void searchByAuthorRecursive(BookBSTNode node, String keyword, List<Book> results) {
	    if (node == null) return;
	
	    Book book = node.getData();
	    
	    //If the author's name' contains the keyword, add it to results
	    if (book.getAuthorFName().toLowerCase().contains(keyword.toLowerCase()) || 
	        book.getAuthorLName().toLowerCase().contains(keyword.toLowerCase())) {
	        results.add(book);
	    }
	    
	    // Search both left and right subtrees (since authors are not sorted)
	    searchByAuthorRecursive(node.getLeftSubTree(), keyword, results);
	    searchByAuthorRecursive(node.getRightSubTree(), keyword, results);
	}
	
	//Update quantity in BST
	public boolean updateBookQuantityInBST(String ISBN, int updateQuantity){
		BookBSTNode targetNode = searchUpdateQuantity(this.root, ISBN);
		
		if(targetNode != null){
			targetNode.getData().setQuantity(updateQuantity);
			if(updateQuantity > 0){
				targetNode.getData().setAvailable(true);
			}else{
				targetNode.getData().setAvailable(false);
			}
			
			return true;
		}
		return false;
	}
	
	//Search ISBN for the book whose quantity you want update
	private BookBSTNode searchUpdateQuantity(BookBSTNode node, String ISBN) {
		if (node == null) {
			return null;  // Base case: If node is null, return
		}

	    //Check if the current node has the matching ISBN
	    if (node.getData().getISBN().equals(ISBN)) {
	        return node;
	    }

	    //Search both left and right subtrees since ISBN is not the sorting key
	    BookBSTNode leftSearch = searchUpdateQuantity(node.getLeftSubTree(), ISBN);
	    if (leftSearch != null) return leftSearch;

	    return searchUpdateQuantity(node.getRightSubTree(), ISBN);
	}
	
}
