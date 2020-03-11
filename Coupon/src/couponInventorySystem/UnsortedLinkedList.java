package couponInventorySystem;

public class UnsortedLinkedList<T> implements CollectionInterface<T> {
    protected int numElements;   	 // elements number
    protected boolean found;         // if target is found return true, otherwise false
	protected LLNode<T> head;        // linked list head
    protected LLNode<T> location;    // if target is found at location
    protected LLNode<T> previous;    // preceding location

    public UnsortedLinkedList() {//initialized
        numElements = 0;
        head = null;
    }

    public boolean add(T element) {// Add element
        LLNode<T> newNode = new LLNode<T> (element);
        newNode.setLink(head);
        head = newNode;
        numElements++;
        return true;
    }

    public boolean contains (T target){
        find(target);
        return found;
    }

    public T get(T target) {
        find(target);
        if (found)
            return location.getInfo();
        else
            return null;
    }

    protected void find(T target) {
        location = head;
        found = false;
        while (location != null){
            if (location.getInfo().equals(target)){
                found = true;
            }else{
                previous = location;
                location = location.getLink();
            }
        }
    }

    public int size(){
        return numElements;
    }

    public T get(int indexNum) {
        location = head;
        for (int i = 0; i < indexNum ; i++){
            previous = location;
            location = location.getLink();
        }
        return location.getInfo();
    }

}
