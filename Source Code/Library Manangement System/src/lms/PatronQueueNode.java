/*
Diwani Walters - 2303848
Olivia McFarlane - 2301555
Javone-Anthony Gordon - 2206126
Kemone Laws - 2109446
*/

package lms;

public class PatronQueueNode {
	private int cardNum; // The cardNum who checked out the book
	private PatronQueueNode nextNode; // Pointer to the NextNode node in the queue
	private PatronQueueNode prevNode; //Pointer to the previous node in the queue
	
	//default constructor
	public PatronQueueNode()
	{
		this.cardNum = 0; // Default card number (invalid)
		this.nextNode = null;
		this.prevNode = null;
	}
	
	//Primary Constructor 1
	public PatronQueueNode(int cardNum, PatronQueueNode nextNode, PatronQueueNode prevNode)
	{
		this.cardNum = cardNum;
		this.nextNode = nextNode;
		this.prevNode = prevNode;
	}
	
	//Primary Constructor 2
    public PatronQueueNode(int cardNum) {
    	this.cardNum = cardNum; // Deep copy of Patron
        this.nextNode = null;
        this.prevNode = null;
    }
	
	//Copy Constructor
    public PatronQueueNode(PatronQueueNode nodeCopy)
    {
    	this.cardNum =nodeCopy.cardNum;
    	this.nextNode = new PatronQueueNode(nodeCopy.nextNode);
    	this.prevNode = new PatronQueueNode(nodeCopy.prevNode);
    }

    //getters and setters
	public int getcardNum() {
		return cardNum;
	}

	public void setcardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	public PatronQueueNode getNextNode() {
		return nextNode;
	}

	public void setNextNode(PatronQueueNode nextNode) {
		this.nextNode = nextNode;
	}
	
	public PatronQueueNode getPrevNode() {
		return prevNode;
	}

	public void setPrevNode(PatronQueueNode prevNode) {
		this.prevNode = prevNode;
	}
		
}
