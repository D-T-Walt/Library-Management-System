/*
Diwani Walters - 2303848
Olivia McFarlane - 2301555
Javone-Anthony Gordon - 2206126
Kemone Laws - 2109446
*/

package lms;

public class PatronQueue {
	 private PatronQueueNode front; // First patron in queue
	 private PatronQueueNode rear;  // Last patron in queue
	 
	 //Default Constructor
	 public PatronQueue()
	 {
		 this.front = null;
		 this.rear = null;
	 }
	 
	 //Primary Constructor
	 public PatronQueue(PatronQueueNode b)
	 {
		 this.front = b;
		 this.rear = b;
	 } 
	 
	 //Copy Constructor
	 public PatronQueue(PatronQueue copyQueue) {
	    this.front = null;
	    this.rear = null;
	
	    if (copyQueue != null && !copyQueue.isEmpty()) {
	        PatronQueueNode current = copyQueue.front;
	        
	        while (current != null) {
	            this.enqueue(current.getcardNum()); // Copy each node
	            current = current.getNextNode();
	        }
	    }
	}
 
	 //getters and setters
	 public PatronQueueNode getFront() {
		return front;
	}

	public void setFront(PatronQueueNode front) {
		this.front = front;
	}

	public PatronQueueNode getRear() {
		return rear;
	}

	public void setRear(PatronQueueNode rear) {
		this.rear = rear;
	}

	
	//methods	
	//add patron to the list
	public void enqueue(int cardNum)
	 {
		 PatronQueueNode temp = new PatronQueueNode(cardNum);
		 
		 if (rear == null) //if queue is empty
		 {
			 front = temp;
			 rear = temp;
		 }
		 else
		 {
			 rear.setNextNode(temp); //The current rear points forward to the new node
			 temp.setPrevNode(rear); //The current rear node is set as the previous node of the new node
			 rear = temp; //New node becomes the rear
		 }
	 }
	
	public int dequeue()
	{
		if (front == null)
		{
			System.out.println("No patrons have currently checked out this book");
			 return -1; // Return an invalid card number to indicate failure
		}
		else
		{
			
			int removedCardNum = front.getcardNum(); // Store the removed card number
			front  = front.getNextNode();
			
			if (front == null) //if front was only patron and you remove it(by skipping over)
			{
				rear = null; //if list is empty, meaning it is null, then the rear automatically has to be null as well
			}
			else
			{
				front.setPrevNode(null);
			}
			return removedCardNum;
		}
	}
	
	//View the Library Card Number at the front
	public int peek()
	{
		if (front == null)
		{
			System.out.println("No patrons have currently checked out this book"); //If the queue is empty
			return -1;
		}
		else
			return front.getcardNum();
	}
	
	public void displayQueue()
	{
		if (front == null)
		{
			System.out.println("No patrons have currently checked out this book");
		}
		else
		{
			PatronQueueNode curr = front;
			System.out.println("Patrons in queue:");
			while (curr != null)
			{
				System.out.println("Library Card Number: "+curr.getcardNum()); 
				curr = curr.getNextNode();
			}
		}
	}
	
	public int countQueue()
	{
		int count = 0;
		PatronQueue tempQueue = new PatronQueue(); //create temp queue
		
		while (front != null) //while the original queue not empty
		{
			tempQueue.enqueue(dequeue());
			count++;			
		}
		
		while(tempQueue.getFront() != null) //Restore Queue
		{
			enqueue(tempQueue.dequeue());// remove from the temp queue and ad back to the original queue
		}
		
		return count;
		
	}	
	
	public boolean isEmpty() 
	{
	    if (front != null) 
	    {
	        return false; // If front exists, the queue is not empty
	    }
	    return true; // If front is null, the queue is empty
	}
	
	//kemone testing the number of nodes in queue
	public int size() {
	    int count = 0;
	    PatronQueueNode current = front;
	
	    while (current != null) {
	        count++;
	        current = current.getNextNode();
	    }
	
	    return count;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
	    PatronQueueNode current = front;
	    if (front == null) {
	        return "-1"; //return and indicate empty queue
	    }    

	    
	    while (current != null)
	    {
	        sb.append(current.getcardNum());	        
	        if (current.getNextNode() != null)
	        {
	            sb.append(", "); // Append only if it's not the last node
	        }
	        current = current.getNextNode();
	    }	   
	    
	    return sb.toString();
	}
}
