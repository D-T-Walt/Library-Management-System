/*
Diwani Walters - 2303848
Olivia McFarlane - 2301555
Javone-Anthony Gordon - 2206126
Kemone Laws - 2109446
*/

package lms;

public class Book {
	//Attributes of Book
	private String title;
	private String authorFName;
	private String authorLName;
	private String ISBN;
	private int quantity;
	private boolean available;
	private PatronQueue checkedOutPatrons; 
	
	//Default Constructor
	public Book() {
		title = "Book_Title";
		authorFName = "FirstName";
		authorLName = "LastName";
		ISBN = "1234567890123";
		quantity = 0;
		available = true;
		checkedOutPatrons = new PatronQueue(); // Ensures queue exists
	}
	
	//Primary Constructor
	public Book(String title, String authorFName, String authorLName, String ISBN, int quantity) {
		this.title = title;
		this.authorFName = authorFName;
		this.authorLName = authorLName;
		this.ISBN = ISBN;
		this.quantity = quantity;
		this.available = true;
		checkedOutPatrons = new PatronQueue(); // Ensures queue exists
	}
	
	public Book(String title, String authorFName, String authorLName, String ISBN, int quantity, boolean available) {
		this.title = title;
		this.authorFName = authorFName;
		this.authorLName = authorLName;
		this.ISBN = ISBN;
		this.quantity = quantity;
		this.available = available;
		checkedOutPatrons = new PatronQueue(); // Ensures queue exists
	}
	
	//Copy Constructor
	public Book(Book book) {
		this.title = book.title;
		this.authorFName = book.authorFName;
		this.authorLName = book.authorLName;
		this.ISBN = book.ISBN;
		this.available = book.available;
		this.available = book.available;
		checkedOutPatrons = new PatronQueue(); // Ensures queue exists
	}

	//Accessors
	public String getTitle() {
		return title;
	}
	
	public String getAuthorFName() {
		return authorFName;
	}
	
	public String getAuthorLName() {
		return authorLName;
	}
	
	public String getISBN() {
		return ISBN;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public boolean getAvailable() {
		return available;
	}

	//Mutators
	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthorFName(String authorFName) {
		this.authorFName = authorFName;
	}
	
	public void setAuthorLName(String authorLName) {
		this.authorLName = authorLName;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}
	
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public PatronQueue getcheckedOutPatrons() {
		return checkedOutPatrons;
	}

	public void setcheckedOutPatrons(PatronQueue checkedOutPatrons) {
		this.checkedOutPatrons = checkedOutPatrons;
	}
	
	@Override
	public String toString() {
		return title + "\t" + authorFName + "\t" + authorLName + "\t" + ISBN + "\t" + quantity + "\t" + available + "\t" + checkedOutPatrons.toString();
	}

	public String detailedToString() { //prints patrons in the queue
	    return title + " by " + authorFName + " " + authorLName + " | ISBN: " + ISBN + " | Available: " + available + " | Quantity: " + quantity + " | Queue: " + checkedOutPatrons.toString(); 
	}
	
	public String details() { //prints title, ISBN and author
	    return title + " by " + authorFName + " " + authorLName + " | ISBN: " + ISBN ; 
	}
	
	
}

