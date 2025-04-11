/*
Diwani Walters - 2303848
Olivia McFarlane - 2301555
Javone-Anthony Gordon - 2206126
Kemone Laws - 2109446
*/

package lms;

public class BookBSTNode 
{
	//Attributes of Book Binary Search Tree's Node
	private Book data;
	private BookBSTNode leftSubTree;
	private BookBSTNode rightSubTree;
	
	//Default Constructor
	public BookBSTNode()
	{
		data = new Book();
		leftSubTree = null;
		rightSubTree = null;
	}
	
	//Primary Constructor
	public BookBSTNode(String title, String authorFName, String authorLName, String ISBN, int quantity, boolean available)
	{
		data = new Book(title, authorFName, authorLName, ISBN, quantity ,available);
		leftSubTree = null;
		rightSubTree = null;
	}
	
	//Copy Constructor
	public BookBSTNode(Book b)
	{
		data = new Book(b);
		leftSubTree = null;
		rightSubTree = null;
	}

	//Accessors and Mutators
	public Book getData() 
	{
		return data;
	}

	public void setData(Book data) 
	{
		this.data = data;
	}

	public BookBSTNode getLeftSubTree() 
	{
		return leftSubTree;
	}

	public void setLeftSubTree(BookBSTNode leftSubTree) 
	{
		this.leftSubTree = leftSubTree;
	}

	public BookBSTNode getRightSubTree() 
	{
		return rightSubTree;
	}

	public void setRightSubTree(BookBSTNode rightSubTree) 
	{
		this.rightSubTree = rightSubTree;
	}
	
}
