package model;

public class Tuple<FirstType, SecondType> {
	// templejt klasa za stukturu promenljivih parova vrednosti
	private FirstType first;
	private SecondType second;
	
	public Tuple(){}
	
	public Tuple(FirstType first, SecondType second) {
		super();
		this.first = first;
		this.second = second;
	}
	
	public void set(FirstType first, SecondType second){
		this.first = first;
		this.second = second;
	}

	public FirstType getFirst() {
		return first;
	}

	public void setFirst(FirstType first) {
		this.first = first;
	}

	public SecondType getSecond() {
		return second;
	}

	public void setSecond(SecondType second) {
		this.second = second;
	}
	
}
