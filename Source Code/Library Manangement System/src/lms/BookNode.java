/*
Diwani Walters - 2303848
Olivia McFarlane - 2301555
Javone-Anthony Gordon - 2206126
Kemone Laws - 2109446
*/

package lms;

public class BookNode {
	private Book bookDetail;
	private BookNode nextNode;
	
	//Default Constructor
	public BookNode() {
		bookDetail = new Book();
		nextNode = null;
	}
	
	//Primary Constructor 1
	public BookNode(Book bookDetail, BookNode nextNode) {
		this.bookDetail = new Book(bookDetail);
		this.nextNode = nextNode;
	}
	
	//Primary Constructor 2
	public BookNode(Book bookDetail) {
		this.bookDetail = new Book(bookDetail);
		nextNode = null;
	}
	
	//Primary Constructor 3 
	public BookNode(String title, String authorFName, String authorLName, String ISBN, int Quantity) {
		bookDetail = new Book(title, authorFName, authorLName, ISBN, Quantity);
		nextNode = null;
	}
	
	public BookNode(String title, String authorFName, String authorLName, String ISBN, int Quantity, boolean available) {
		bookDetail = new Book(title, authorFName, authorLName, ISBN, Quantity, available);
		nextNode = null;
	}
	
	//Copy Constructor
	public BookNode(BookNode node) {
		this.bookDetail = node.bookDetail;
		this.nextNode = node.nextNode;
		
	}

	//Accessors
	public Book getBookDetail() {
		return bookDetail;
	}

	public BookNode getNextNode() {
		return nextNode;
	}
	
	//Mutators
	public void setBookDetail(Book bookDetail) {
		this.bookDetail = bookDetail;
	}

	public void setNextNode(BookNode nextNode) {
		this.nextNode = nextNode;
	}

}
