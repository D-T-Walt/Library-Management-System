/*
Diwani Walters - 2303848
Olivia McFarlane - 2301555
Javone-Anthony Gordon - 2206126
Kemone Laws - 2109446
*/

package lms;

import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.Toolkit;

public class BookLinkedList {
	private BookNode head;
	
	//Default Constructor
	public BookLinkedList() {
		head = null;
	}
	
	//Primary Constructor
	public BookLinkedList(BookNode head) {
		this.head = head;
	}
	
	//Accessor
	public BookNode getHead() {
		return head;
	}

	//Mutator
	public void setHead(BookNode head) {
		this.head = head;
	}
	
	//Insert at back method with Book Node
	public void insertAtBack(Book dataToInsert) 
	{
		BookNode temp1, temp2;
		temp1 = new BookNode();
		
		temp1.setBookDetail(dataToInsert);
		temp1.setNextNode(null);
		
		if(this.head == null)       //if head is empty make the node youre trying to insert the head
		{
			this.head = temp1;
		}
		else {
			temp2 = this.head;         //Store head in temporary variable
			while(temp2.getNextNode() != null) 
			{
				temp2 = temp2.getNextNode();   //traverse list by storing next node in the temp2 temporary variable
			}
			
			temp2.setNextNode(temp1);       //When the node at the end of the list is reached, make temp1(the node you're trying to add) to be the nextNode
		}
	}
	
	//Insert at back method without checkedOutPatrons
	public void insertAtBack(String title, String authorFName, String authorLName, String ISBN, int Quantity, boolean available) {
		BookNode temp1 = new BookNode(title, authorFName, authorLName, ISBN, Quantity, available);
		if(this.head == null)         //if head is empty make the node youre trying to insert the head
		{
			this.head = temp1;
		}
		else {
			BookNode temp2 = this.head;    //Store head in temporary variable
			while(temp2.getNextNode() != null) 
			{
				temp2 = temp2.getNextNode();             //traverse list by storing next node in the temp2 temporary variable
			}
			
			temp2.setNextNode(temp1);       //When the node at the end of the list is reached, make temp1(the node you're trying to add) to be the nextNode
		}
	}
	
	//Method to count nodes
	public int countNodes() 
	{
		int count = 0;
		BookNode curr = this.head;
		
		while(curr != null) {
			count++;          //Increment count  
			curr = curr.getNextNode();  //Set curr to the next node
		}
		return count;
	}
	
	//Method to count nodes for main which displays the amount of nodes
	public void countNodesTS() 
	{
		int count = 0;
		BookNode curr = this.head;
		
		while(curr != null) 
		{
			count++;
			curr = curr.getNextNode();
		}
		
		System.out.println("The amount of books in the Library Managemnet System is: " + count);        //Discplay the count amount
	}
	
	//Search Linked List returning true or false dependeing if search was successfully or unsuccessfully respectively
	public boolean search(String ISBN) {
		boolean found = false;
		BookNode curr = this.head;
		
		while(curr != null) {
			if(curr.getBookDetail().getISBN().equals(ISBN)) {
				found = true;
				break;
			}
			curr = curr.getNextNode();
		}
		return found;
	}
	
	//Searched the linked list returning node that matched seaarch and null if no match was found
	public Book searchReturn(String ISBN) {
		BookNode curr = this.head;
		
		while(curr != null) {
			if(curr.getBookDetail().getISBN().equals(ISBN)) {
				return curr.getBookDetail();
			}
			curr = curr.getNextNode();
		}
		
		return null;
	}
	
	//Print out the entire linked list
	public void display() {
		BookNode curr = this.head;
		while(curr != null) {
			System.out.print("[" + curr.getBookDetail() + "]->");
			curr = curr.getNextNode();
		}
		System.out.println("NULL");
	}
	
	//Check if linked list is empty
	public boolean isEmpty() {
		if(this.head == null) {
			return true;
		}
		return false;
	}
	
	//Delete a node from linked list
	public Book delete(String ISBN) {
		
		//Check if linked list is empty and display error message
		if(isEmpty()){
            Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null,
                    "The list is empty. No book to delete",
                    "Empty List Error",
                    JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		BookNode curr = this.head, prev = null;
		
		while(curr != null) {
			if(curr.getBookDetail().getISBN().equals(ISBN))    //If a book in the linked list matches the isbn you entered to delete
			{
				if(curr == this.head) {
					this.head = this.head.getNextNode();        //set head to it's nextNode
				}
				else {
					prev.setNextNode(curr.getNextNode());       //set prev's next node to the current node
				}
				
				Book deletedBook = curr.getBookDetail();        //Store details of the book to be deleted
				//detailsToReturn = curr.getBookDetail();
				curr = null;                                    //delete book
				System.out.print("\nBook deleted.");
				return deletedBook;
				//break;
			}
			
			// Move to the next node in the linked list
			prev = curr;                           //Update the previous node to current node
			curr = curr.getNextNode();             // Update current node to nectNode
		}

        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null,
                "Delete unsuccessful. Book doesn't exist",
                "Delete Error",
                JOptionPane.ERROR_MESSAGE);
		return null;
	}
	
	//Writing to the BookCollection file
	public void writeBooksToFile(){
		FileWriter writer = null;
		try{
			writer = new FileWriter(new File("BookCollection.txt"), false);      //Open BookCollection.txt file
			
			BookNode curr = this.head;             //Start from the the first node in the livked list
			
			while(curr != null)                             //Write each of the book details to the file
			{                                         
				writer.write(curr.getBookDetail().toString() + "\n");
				curr = curr.getNextNode();                      //Move on to the next node in the linked list
			}
			
			System.out.println("\nBooks successfully saved to file.");       //Print a success message indicating that the books were saved to the file
		}catch(IOException e)
		{
			// If an error occurs during file writing, beep and print the error message
            Toolkit.getDefaultToolkit().beep();
			System.err.print("Error writing to file: " + e.getMessage());
		}finally{
			try{
				if(writer != null){
					writer.close();         //close the fiile
				}
			}catch(IOException e){
                Toolkit.getDefaultToolkit().beep();
				System.err.print("Error closing file: " + e.getMessage());
			}
			
		}
		
	}
	
	//Read from the bookCollection file
	public void readBooksFromFile(BookBinarySearchTree bbst, Scanner fileReader){
		try{
			while(fileReader.hasNextLine())           //Loop through each line in the file
			{
				String line = fileReader.nextLine();      //Read the current line from the file
				String[] part = line.split("\t");         //Split the line into parts based on tab (\t) characters, each part represents a different attribute
				
				if(part.length >= 7)                   //Ensure that the line has at least 7 parts (enough data to create a Book object)
				{
					String title = part[0];
                    String authorFName = part[1];
                    String authorLName = part[2];
                    String ISBN = part[3];
                    int quantity = Integer.parseInt(part[4]);
                    boolean available = Boolean.parseBoolean(part[5]);
                    
                    String patronList = part[6];
                    
                    String[] patronNum = patronList.split(", ");         //Split the patron list by commas and spaces
                    PatronQueue queue = new PatronQueue();           //create a Patron Queue
                    
                    //Loop through the patron numbers and add them to the PatronQueue (ignoring invalid patron numbers)
                    for(String num : patronNum){
						if(!num.isEmpty())        //If patron number is not -1, add it to the queue
						{
							int pNum = Integer.parseInt(num.trim());
							if(pNum != -1){
								queue.enqueue(pNum);
							}
						}
					}
					
					//Create a new Book object using the extracted data
					Book book = new Book(title, authorFName, authorLName, ISBN, quantity, available);
					book.setcheckedOutPatrons(queue);
					
					//Insert the book into the linked list and the binary search tree
					insertAtBack(book);
					bbst.insertBookBSTNode(book);
                    
                }
			}
			System.out.print("Books sucessfully loaded from file.");
		}catch(InputMismatchException e){
            Toolkit.getDefaultToolkit().beep();
			System.err.print("Error reading from file: " + e.getMessage());
		}
		return;
	}
	
	// Search for books by title in the linked list
	public List<Book> searchBooksByTitle(String keyword) {
	    List<Book> results = new ArrayList<>();       // Create a list to store the books that match the title keyword
	    BookNode curr = head;
	
	    while (curr != null) {
	        Book book = curr.getBookDetail();
	        if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()))    //If some part of the book title matches the keyword then add it to the list of results of matched books
	        {
	            results.add(book);
	        }
	        curr = curr.getNextNode();                  //Move on to the next node in the list
	    }
	    return results;             //return the list of matched books
	}
	
	// Search for books by author in the linked list
    public List<Book> searchBooksByAuthor(String keyword) {
        List<Book> results = new ArrayList<>();             //Create a list to store the books that match the author name keyword
        BookNode curr = head;

        while (curr != null) {
            Book book = curr.getBookDetail();
            if (book.getAuthorFName().toLowerCase().contains(keyword.toLowerCase()) ||
                book.getAuthorLName().toLowerCase().contains(keyword.toLowerCase()))            //If some part of the book author's name matches the keyword then add it to the list of results of matched books
                {
                results.add(book);
            }
            curr = curr.getNextNode();              //Move to next node in the list
        }
        return results;                   //return the list of matched book
    }
    
    // Search for a book node by ISBN
    public BookNode searchBookNode(String isbn) {
        BookNode curr = head;
        while (curr != null) {
            if (curr.getBookDetail().getISBN().equals(isbn))              //If the ISBN matches the keyword then return it
            {
                return curr;
            }
            curr = curr.getNextNode();           //move on to the next node in the list
        }
        return null;                //If no ISBN matches the keyword searched for, then return nothing
    }
	
	//Admin search for books by author (includes name of patrons who check out books and their card numbers)
    public void adminSearchByAuthor(Scanner input, PatronLinkedList patronList) {
        System.out.print("Enter the author's first name, last name, or part of their name that you'd like to find: ");
        String keyword = input.nextLine();

        List<Book> results = searchBooksByAuthor(keyword);     //Search for books by author based on the provided keyword

		// If no books were found, display that message
        if (results.isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
                    "Sorry, but we couldn't find any books by the author you searched for.",
                    "Search Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

		//If only one book was found, display its details and as well as the patron's name and card number who checked it out'
        if (results.size() == 1) {
            Book selectedBook = results.get(0);
            System.out.println("\nBook Details:\n" +
                    "Book Title: " + selectedBook.getTitle() +
                    " | Author's Name: " + selectedBook.getAuthorFName() + " " + selectedBook.getAuthorLName() +
                    " | Book ISBN: " + selectedBook.getISBN() +
                    " | Available?: " + selectedBook.getAvailable()+" | Quantity: " + selectedBook.getQuantity());

            comparePatrons(selectedBook.getISBN(), patronList);         //Compare patrons who checked out this book call
            return;
        }

        // Display books found
        System.out.println("\nThe books found based on your search are: ");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i).getTitle() + " by " + results.get(i).getAuthorFName() + " " + results.get(i).getAuthorLName());
        }
        
        // Ask user to confirm book selection
        System.out.print("\nEnter the number of the book you're looking for (or 0 to cancel): ");
        
        //Ensure the input is a valid integer; if not, prompt for correct input
        while (!input.hasNextInt()) { // Prevent invalid input
        	Toolkit.getDefaultToolkit().beep();
            System.err.print("Invalid input! Please enter a number: ");
            input.next();
        }
        
        //Get the admin's choice
        int choice = input.nextInt();
        input.nextLine(); // Consume newline

		// If the admin selects a valid book, show its details aas well as the patrons that checked it out
        if (choice > 0 && choice <= results.size()) {
            Book selectedBook = results.get(choice - 1);
            System.out.println("\nBook Details:\n" +
                    "Book Title: " + selectedBook.getTitle() +
                    " | Author's Name: " + selectedBook.getAuthorFName() + " " + selectedBook.getAuthorLName() +
                    " | Book ISBN: " + selectedBook.getISBN() +
                    " | Available?: " + selectedBook.getAvailable()+" | Quantity: " + selectedBook.getQuantity());

            comparePatrons(selectedBook.getISBN(), patronList);      //Compare patrons who checked out this book call
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
                    "Invalid selection or cancelled.",
                    "Input Error/ Cancellation",
                    JOptionPane.ERROR_MESSAGE);            
        }
    }
    
    public void adminSearchByISBN(Scanner input, PatronLinkedList patronList)   
    {
	    String isbn = "";
	    boolean validInput = false;
	
	    while (!validInput) {
	        try {
	            System.out.print("Enter the ISBN of the book that you'd like to find: ");
	            isbn = input.nextLine().trim(); // Trim to remove accidental spaces
	
	            if (isbn.isEmpty()) // If the ISBN is empty, show an error and prompt for input again
	            { 
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,
                            "ISBN cannot be empty. Please enter a valid ISBN.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
	                continue;
	            }
	
	            String errorMessage = "";
	
				//Check if the ISBN length is not exactly 17 characters
	            if (isbn.length() != 17) {
	                errorMessage += "ISBN must be exactly 17 characters long.\n";
	            }
	
				//Check if the ISBN starts with a dash
	            if (isbn.charAt(0) == '-') { 
	                errorMessage += "ISBN must not start with a dash (-).\n";
	            }
	
				//Check if the ISBN ends with a dash
	            if (isbn.charAt(isbn.length() - 1) == '-') {
	                errorMessage += "ISBN must not end with a dash (-).\n";
	            }
	
				//Check if the ISBN contains exactly 13 digits
	            if (LibraryManagementSystem.countDigits(isbn) != 13) {
	                errorMessage += "ISBN must contain exactly 13 digits.\n";
	            }
	
				//Check if the ISBN contains exactly 4 dashes
	            if (LibraryManagementSystem.countDashes(isbn) != 4) {
	                errorMessage += "ISBN must contain exactly 4 dashes.\n";
	            }
	
				//If there are any errors, throw an exception
	            if (!errorMessage.isEmpty()) {
	                throw new InputMismatchException(errorMessage.trim());
	            }
	
	            validInput = true; // If no errors, input is valid
	
	        } catch (InputMismatchException e)  
	        {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null,
                		"Invalid ISBN:\n" + e.getMessage(), 
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);        // If there was an error with the input, show an error message
	        }
	    }
	
	    //Search for the book in the linked list
	    BookNode bookNode = searchBookNode(isbn);
	
		// If the book is not found, display taht message
	    if (bookNode == null) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
            		"Sorry, but we couldn't find the ISBN you searched for in our system.",
                    "ISBN Does Not Exist",
                    JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	
	    //Display book details
	    Book book = bookNode.getBookDetail();
	    System.out.println("\nBook Details:\n" +
                "Book Title: " + book.getTitle() +
                " | Author's Name: " + book.getAuthorFName() + " " + book.getAuthorLName() +
                " | Book ISBN: " + book.getISBN() +
                " | Available?: " + book.getAvailable()+" | Quantity: " + book.getQuantity());
	
	    //Compare patrons who checked out this book and display their details too
	    comparePatrons(book.getISBN(), patronList);
	}
	
    public void adminSearchByTitle(Scanner input, PatronLinkedList patronList) {
        String keyword;

        while (true)      //Loop until valid input is received (non-empty string)
        { 
            System.out.print("Enter the title or part of the title you'd like to find: ");
            keyword = input.nextLine().trim(); // Trim to remove accidental spaces

			//If the input is not empty, exit the loop
            if (!keyword.isEmpty()) { 
                break; 
            }

            //If input is empty, show an error message and prompt again
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
                    "Title search cannot be empty. Please enter a valid title keyword.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }

		// Search for books that match the title keyword
        List<Book> results = searchBooksByTitle(keyword);

        if (results.isEmpty())    //If no books are found, show an error message
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
                    "Sorry, but we couldn't find the title you searched for in our system.",
                    "Title Keywords Do Not Exist",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        //If only one book is found, display its details
        if (results.size() == 1) {
            Book selectedBook = results.get(0);            //Get the only book found
            System.out.println("\nBook Details:\n" +
                    "Book Title: " + selectedBook.getTitle() +
                    " | Author's Name: " + selectedBook.getAuthorFName() + " " + selectedBook.getAuthorLName() +
                    " | Book ISBN: " + selectedBook.getISBN() +
                    " | Available?: " + selectedBook.getAvailable()+" | Quantity: " + selectedBook.getQuantity());
                    
            comparePatrons(selectedBook.getISBN(), patronList);    //Compare patrons who checked out the book displaying their names and card number
            return;
        }

        // If more than one book is found, display a list of results
        System.out.println("\nThe books found based on your search are: ");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i).getTitle() + " by " +
                    results.get(i).getAuthorFName() + " " + results.get(i).getAuthorLName());
        }

        // Prompt the admin to select a book by its number in the menu
        System.out.print("\nEnter the number corresponding to the book you're looking for (or 0 to cancel): ");
        
        while (!input.hasNextInt()) { // Prevent invalid input
            Toolkit.getDefaultToolkit().beep();
            System.err.print("Invalid input! Please enter a number: ");
            input.next();
        }

		//Get the user's choice of book
        int choice = input.nextInt();
        input.nextLine(); // Consume newline

        if (choice > 0 && choice <= results.size())          // If the user chose a valid book number, display the book details
        {
            Book selectedBook = results.get(choice - 1);
            System.out.println("\nBook Details:\n" +
                    "Book Title: " + selectedBook.getTitle() +
                    " | Author's Name: " + selectedBook.getAuthorFName() + " " + selectedBook.getAuthorLName() +
                    " | Book ISBN: " + selectedBook.getISBN() +
                    " | Available?: " + selectedBook.getAvailable()+" | Quantity: " + selectedBook.getQuantity());
            
            comparePatrons(selectedBook.getISBN(), patronList);    //Compare patrons who checked out the book displaying their names and card number
        } else {
			// If the user selects an invalid book or cancels, show an error message
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
                    "Invalid selection or cancelled.",
                    "Input Error/ Cancellation",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    //Compare patrons who checked out a book with the patron linked list
    public void comparePatrons(String isbn, PatronLinkedList patronList) 
    {
        BookNode bookNode = searchBookNode(isbn);          //Search for the book in the linked list using the ISBN
        
        //Display not found message if book is not found in the linked list and reurn
        if (bookNode == null) {
            System.out.println("Book not found in linked list.");
            return;
        }

        PatronQueue bookQueue = bookNode.getBookDetail().getcheckedOutPatrons();      //Get the queue of patrons who have checked out the book

        if (bookQueue == null || bookQueue.isEmpty())                //If the book has no patrons who checked it out, print a message and return
        {
            System.out.println("\nNo patrons have checked out this book.");
            return;
        }

        // Iterate through queue and compare with patron list
        PatronQueueNode queueNode = bookQueue.getFront();
        boolean found = false;

        while (queueNode != null) {
            PatronNode currPatron = patronList.getHead();         //start from the first patron in the list by starting from the linked list's head
            
            //Loop through the patron linked list to find a matching patron by card number
            while (currPatron != null)               //Compare the card number from the queue with the card number from the patron list
            {
                if (currPatron.getPatronData().getCardNum() == queueNode.getcardNum())          // If a match is found, print the patron's details (first and last name, card number)
                {
                    System.out.println("Patron who checked out book: " + currPatron.getPatronData().getFname() + " " +
                                       currPatron.getPatronData().getLname() +
                                       " | Card Number: " + currPatron.getPatronData().getCardNum());
                    found = true;
                    break;
                }
                currPatron = currPatron.getNextNode();             //Move to the next patron in the linked list
            }
            queueNode = queueNode.getNextNode();
        }

		//If no matching patrons were found, print a message indicating that
        if (!found) 
        {
            System.out.println("No matching patrons found.");
        }
    }
    
    //Update the quantity of a book in the linked list
    public boolean updateBookQuantityInList(String ISBN, int updateQuantity){
		BookNode curr = this.head;    //Start from the head of the linked list
		
		// Traverse the linked list
		while(curr != null)
		{
			if(curr.getBookDetail().getISBN().equals(ISBN)) // Check if the current book's ISBN matches the provided ISBN
			{
				curr.getBookDetail().setQuantity(updateQuantity); // Update the book's quantity with the new value
				if(updateQuantity > 0)       // If the updated quantity is greater than 0, set the book as available
				{
					curr.getBookDetail().setAvailable(true);
				}else  // If the updated quantity is 0 or less, set the book as unavailable
				{
					curr.getBookDetail().setAvailable(false);
				}
				return true;
			}
			curr = curr.getNextNode();       //Move to the next book in the linked list
		}
		return false;
	}
	
	// Check if the book can be deleted based on its current checkouts
	public boolean deleteCheck(String ISBN) {
		BookNode bookNode = searchBookNode(ISBN);       // Search for the book in the linked list using the ISBN
		//boolean check = false;
		
		if(bookNode != null)  // If the book exists in the list
		{
			if(!bookNode.getBookDetail().getcheckedOutPatrons().isEmpty()) // Check if the book has any patrons who have checked it out
			{
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null,
                        "Error! This book can not be deleted because it currently has patrons",
                        "Deletion Error",
                        JOptionPane.ERROR_MESSAGE);  // If the book has patrons who have checked it out, display an error message
				return true;     //Return true to indicate that the book cannot be deleted
			}
		}
		return false;      // Return false to indicate that the book can be deleted (no patrons have it checked out)
	}
	
}
	
	
 
