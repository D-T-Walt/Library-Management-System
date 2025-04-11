/*
 * Diwani Walters – 2303848
 * Kemone Laws – 2109446 
 * Olivia McFarlane – 2301555 
 * Javone- Anthony Gordon – 2206126*/

package lms;

import java.awt.Toolkit;

public class StackLinkedList {
	private StackBookNode top;
	private int size;
	
	//Default Constructor
	public StackLinkedList() {
		this.top= null;
		this.size= 0;
	}
	
	//Copy constructor- a deep copy that ensure modifications only happens to the intended stack
	public StackLinkedList(StackLinkedList origin) {
        this.top = null;
        this.size = 0;
        
        if (origin != null && !origin.isEmpty()) {
            // Use a temporary stack to preserve the original order
            StackBookNode curr = origin.top;
            StackLinkedList tempStack = new StackLinkedList();
            
            // Push elements into a temporary stack (reverse order)
            while (curr != null) {
                tempStack.push(curr.getIsbn());
                curr = curr.getNextNode();
            }
            
            // Pop from the temporary stack to preserve order
            while (!tempStack.isEmpty()) {
                this.push(tempStack.pop());
            }
        }
    }

	//returns true if the top doesn't store a node... is empty
	public boolean isEmpty() {
	       return top == null;
	 }
	
	//Add a book to the Patron's checkout
	public void push(String book) {
        StackBookNode newOne = new StackBookNode(); //create new node
        
        newOne = new StackBookNode(book);
		
		if(top== null) {
			top= newOne; //New node set as top if the stack is empty
		}
		else {
			newOne.setNextNode(top); //New node points to the current top
		    top = newOne; //New node becomes the top
		}
		
		size++; //Increment stack size
    }
	
	//Remove a book from the Patron's checkout
	public String pop() {
        StackBookNode temp = top;
        String data= null;
		
		if (isEmpty()) {
        	Toolkit.getDefaultToolkit().beep();
            System.err.println("Checkout list is empty. No books to pop.");
        }
		else {
  			top = top.getNextNode(); // Move top to the next node
	        size--; //Decrement stack size
	        
	        data= temp.getIsbn(); //Get the ISBN of the removed book
		} 
		
		return data;
	}
	
	//Get the ISBN of the last book
	public String stackTop() {
		
		if (isEmpty()) {
        	Toolkit.getDefaultToolkit().beep();
            System.err.println("Checkout list is empty. No book to view/save.");
        }
		else {
			return top.getIsbn();
		}
		
		return null; 
	}
	
	//Count the number of books in the stack
	public int stackCount(){
        return size;
    }
	
	//Clear the checkout list
	public void destroyStack() {
		
        while(!isEmpty()) {
        	pop();
        }

    }

	public String toString()
	{
		StackBookNode curr = top;
		StringBuilder sb = new StringBuilder();
		
		if (top == null)
		{
			return "-1"; //Represents an empty list
		}		
		
		while(curr != null)
		{
			sb.append(curr.getIsbn());			
			if (curr.getNextNode() != null)
			{
				sb.append(", "); // Append only if it's not the last node
			}
			curr = curr.getNextNode();
		}		
		return sb.toString();
	}
}
