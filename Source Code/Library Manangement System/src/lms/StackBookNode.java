/*
Diwani Walters - 2303848
Olivia McFarlane - 2301555
Javone-Anthony Gordon - 2206126
Kemone Laws - 2109446
*/

package lms;

public class StackBookNode {
	private String isbn;
	private StackBookNode nextNode;

	//Default Constructor
	public StackBookNode() {
		isbn = "1234567890123";
		nextNode = null;
	}
	
	//Primary Constructor 1
	public StackBookNode(String isbn, StackBookNode nextNode){
		this.isbn= isbn;
		this.nextNode = nextNode;
	}
	
	//Primary Constructor 2
	public StackBookNode(String isbn) {
		this.isbn= isbn;
		nextNode = null;
	}
	
	//Copy Constructor
	public StackBookNode(StackBookNode node) {
		this.isbn = node.isbn;
		this.nextNode = node.nextNode;
	}

	//Getter
	public String getIsbn() {
		return isbn;
	}

	//Setter
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	//Getter
	public StackBookNode getNextNode() {
		return nextNode;
	}

	//Setter
	public void setNextNode(StackBookNode nextNode) {
		this.nextNode = nextNode;
	}
}
