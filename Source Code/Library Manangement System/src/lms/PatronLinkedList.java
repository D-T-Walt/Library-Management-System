/*
Diwani Walters - 2303848
Olivia McFarlane - 2301555
Javone-Anthony Gordon - 2206126
Kemone Laws - 2109446
*/

package lms;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class PatronLinkedList {
		private PatronNode head;
		
		//default constructor
		public PatronLinkedList()
		{
			this.head = null;
		}
		
		//primary Constructor
		public PatronLinkedList (PatronNode h) //Creates a list with one patron in there
		{
			this.head = h;
		}

		//getters and setters
		public PatronNode getHead() {
			return head;
		}

		public void setHead(PatronNode head) {
			this.head = head;
		}
		
		//methods		
		public void addPatron(Patron data, ArrayList<Password> pass)
		{
			PatronNode temp = new PatronNode (data); // Create a new node for the patron
			
			if(head == null) //if the list is empty
			{
				temp.getPatronData().setCardNum(1001);// Assign card number 1001 to the first patron
				head = temp;
			}
			else //if the list was not empty
			{
				PatronNode curr = head;
				while (curr.getNextNode() != null) // Traverse to the last node
				{
					curr = curr.getNextNode(); // Move to the next node
				}
				
				// Now curr is the last node, get its card number and increment by 1
				int newCardNum = ((curr.getPatronData().getCardNum()) + 1);
				
				// Set the card number for the new patron
				temp.getPatronData().setCardNum(newCardNum);
				
				// Add the new patron node to the list
				curr.setNextNode(temp);// Set the next of the last node to the new node	
				
			}	
			
			//generate and store the default password for the patron to use for their first log in
			String passDefault= Password.generateDefaultPassword();
			pass.add(new Password(Password.hashPassword(passDefault), Password.hashPassword(passDefault)));
			
			System.out.printf("\n%s, Library Card Number: %d, Your default password is %s\nREMEMBER IT!!\n", temp.getPatronData().getFname(), temp.getPatronData().getCardNum() ,passDefault );
		}

			public void addSinglePatron(Patron patron) {
		    if (patron == null) return;

		    //Ensure we keep the existing book list!
			PatronNode newNode = new PatronNode(patron);
		   // PatronNode newNode = new PatronNode(new Patron(patron.getFname(), patron.getLname(), patron.getCardNum(), patron.getCheckedOutBooks()));
		 
		    if (head == null) {
		        head = newNode;
		    } else {
		        PatronNode curr = head;
		        while (curr.getNextNode() != null) {
		            curr = curr.getNextNode();
		        }
		        curr.setNextNode(newNode);
		    }
		}

			public void removePatron(int cardNum, ArrayList<Password> pass) {
			    if (head == null) {
			        Toolkit.getDefaultToolkit().beep();
			        JOptionPane.showMessageDialog(null,
			                "There are no patrons in the system for you to delete",
			                "Delete Patron Error",
			                JOptionPane.ERROR_MESSAGE);
			        return;
			    }

			    PatronNode curr = head;
			    PatronNode prev = null;

			    // Handle head deletion
			    if (head.getPatronData().getCardNum() == cardNum) {
			        if (!head.getPatronData().getCheckedOutBooks().isEmpty()) {
			            JOptionPane.showMessageDialog(null,
			                    "Cannot remove patron " + cardNum + " - they still have books checked out!",
			                    "Delete Patron Error",
			                    JOptionPane.ERROR_MESSAGE);
			            return;
			        }

			        head = head.getNextNode(); // Remove the head node
			        pass.set(cardNum - 1001, new Password("DELETED", "DELETED")); // Mark password as deleted
			        System.out.println("Patron " + cardNum + " successfully deleted.");
			        return;
			    }

			    // Traverse to find and delete patron
			    while (curr != null) {
			        if (curr.getPatronData().getCardNum() == cardNum) {
			            if (!curr.getPatronData().getCheckedOutBooks().isEmpty()) {
			                JOptionPane.showMessageDialog(null,
			                        "Cannot remove patron " + cardNum + " - they still have books checked out!",
			                        "Delete Patron Error",
			                        JOptionPane.ERROR_MESSAGE);
			                return;
			            }

			            // Remove the node from the list
			            prev.setNextNode(curr.getNextNode());

			            // Mark the password as "DELETED"
			            pass.set(cardNum - 1001, new Password("DELETED", "DELETED"));
			            System.out.println("Patron " + cardNum + " successfully deleted.");
			            return;
			        }

			        prev = curr;
			        curr = curr.getNextNode();
			    }

			    // If patron was not found
			    Toolkit.getDefaultToolkit().beep();
			    JOptionPane.showMessageDialog(null,
			            "Patron with card number " + cardNum + " not found.",
			            "Delete Patron Error",
			            JOptionPane.ERROR_MESSAGE);
			}	
		
		public void displayPatrons()
		{
			PatronNode curr = head;
			
			while (curr !=null )
			{
				System.out.print(curr.getPatronData().detailedToString());
				curr = curr.getNextNode();
			}
			System.out.println("NULL");
		}
		
		public boolean searchPatron(int cardNum)
		{
			PatronNode curr = head;
			
			while (curr != null)
			{
				if( curr.getPatronData().getCardNum() == cardNum)
				{
					return true;
				}
				curr = curr.getNextNode();
			}
			return false;
		}	

		public void viewPatronDetails(int cardNum, BookLinkedList bookList)
		{
			PatronNode curr = head;
			
			//If head is null the list is empty
			if(head == null)
			{
				Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null,
                        "No patrons exist in file",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);
				System.out.println("Patron Details Error");
			}
			else
			{
				if(searchPatron(cardNum))
				{
					while(curr.getPatronData().getCardNum() != cardNum)
					{
						curr = curr.getNextNode();
					}
					
					//Display patron details
					System.out.print("Patron Name: " + curr.getPatronData().getFname() +" " + curr.getPatronData().getLname() +"\t");
					System.out.println("Library Card Number: " +curr.getPatronData().getCardNum());
					System.out.println("List of Checked out Books: \n");
					
					StackLinkedList checkOutBooks = curr.getPatronData().getCheckedOutBooks();

					StackLinkedList tempStack = new StackLinkedList(checkOutBooks); //Copy the stack
					
					//Display the books the person has checked out 
					if (tempStack.isEmpty())
					{
					    System.out.println("No books checked out.\n");
					}
					else
					{
						while (!tempStack.isEmpty())
						{
							String isbn = tempStack.pop();
							Book book = bookList.searchReturn(isbn);							
							
							if (book != null)
							{
								System.out.println(book.details()+" | ");
							}						
						}	
					}
				}
				else
				{

                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Patron does not exist within system", "Patron Details Error", JOptionPane.ERROR_MESSAGE);
				}
			}			
		}

		public int countPatrons() //returns the count of the number of patrons in the linked list
		{
			int count = 0;
			PatronNode curr = head;
			
			if(curr == null)
			{
				return 0;
			}
			
			while (curr !=null )
			{
				count++;
				curr = curr.getNextNode();
			}
			return count;
		}
		

		//writing patrons to the patron file
		public void writePatronsToFile()
		{
			FileWriter writer = null;			
			
			try
			{
				writer = new FileWriter(new File("PatronCollection.txt"),false);
				
				PatronNode curr = head;
				
				while(curr != null)
				{
					writer.write(curr.getPatronData().toString()+ "\n");
					curr = curr.getNextNode();
				}
				System.out.println("Patrons successfully saved to file.");
				
			}catch(IOException e)
			{
                Toolkit.getDefaultToolkit().beep();
				System.err.print("Error writing to file: " + e.getMessage());
			}finally {
				try 
				{
					if(writer != null)
					{
						writer.close();
					}
				}catch(IOException e)
				{
                    Toolkit.getDefaultToolkit().beep();
					System.err.print("Error closing file: " + e.getMessage());
				}
			}
		}

			public void readPatronsFile(Scanner fileReader) {
		    try {
		        while (fileReader.hasNextLine()) {
		            String line = fileReader.nextLine();
		            String[] parts = line.split("\t");

		            if (parts.length >= 3) {
		                String fname = parts[0];
		                String lname = parts[1];
		                int cardNum = Integer.parseInt(parts[2]);               

		                StackLinkedList booklist = new StackLinkedList();

		                if (parts.length > 3 && !parts[3].equals("-1")) {
		                    String[] checkedOutBooks = parts[3].split(",");
		                    StackLinkedList tempStack = new StackLinkedList(); // Temporary stack

		                    // Add books to the temporary stack
		                    for (String book : checkedOutBooks) {
		                        book = book.trim();
		                        if (!book.isEmpty()) {
		                            tempStack.push(book);  // Push to temp stack
		                        }
		                    }

		                    // Now, pop from the temporary stack into the booklist stack
		                    while (!tempStack.isEmpty()) {
		                        booklist.push(tempStack.pop());  // Restore original order
		                    }
		                }

		                // Set read Patron's data
		                Patron newPatron = new Patron(fname, lname, cardNum, booklist);
		                addSinglePatron(newPatron);
		            }
		        }
		        System.out.println("\nPatrons successfully loaded from file.");
		    } catch (Exception e) {
                Toolkit.getDefaultToolkit().beep();
		        System.err.println("Error: " + e.getMessage());
		    }
		}
}

