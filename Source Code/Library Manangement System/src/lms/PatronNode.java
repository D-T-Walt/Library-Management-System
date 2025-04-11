/*
Diwani Walters - 2303848
Olivia McFarlane - 2301555
Javone-Anthony Gordon - 2206126
Kemone Laws - 2109446
*/

package lms;

public class PatronNode {
	private Patron patronData;
	private PatronNode nextNode;
	
	//Default Constructor
	public PatronNode()
	{
		this.patronData = new Patron();
		this.nextNode = null;
	}
	
	//Primary  Constructor ()setting next node to a value)
	public PatronNode(Patron patronData, PatronNode nextNode)
	{
		this.patronData = new Patron(patronData);
		this.nextNode = nextNode;
	}
	
	//Primary Constructor ()setting next node to null)
	public PatronNode (Patron patronData)
	{
		this.patronData = new Patron(patronData);
		this.nextNode = null;
	}
	
	//Copy Constructor
	public PatronNode(PatronNode nodeCopy)
	{
		this.patronData = new Patron(nodeCopy.patronData);
		this.nextNode = new PatronNode(nodeCopy.nextNode);
	}

	//Getters and Setters
	public Patron getPatronData() {
		return patronData;
	}

	public void setPatronData(Patron patronData) {
		this.patronData = patronData;
	}

	public PatronNode getNextNode() {
		return nextNode;
	}

	public void setNextNode(PatronNode nextNode) {
		this.nextNode = nextNode;
	}
	
}
