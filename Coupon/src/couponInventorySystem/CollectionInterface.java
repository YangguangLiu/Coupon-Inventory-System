package couponInventorySystem;

public interface CollectionInterface<T> {
	    
    public int size(); // number of elements

	T get(int index); //return the element from the collection;	otherwise, exist and return null;
	
	boolean add(T element);// Adding element, true if successful added, otherwise, false

	boolean contains(T target); //true if contains an element, otherwise, false

	String toString(); 	//return formatted string
}