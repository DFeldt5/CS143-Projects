import java.util.Iterator;

/**
 * A class representing a double linked list.
 * 
 * Feldt, Dustin
 * CS 143 Assignment 4
 * 
 */
public class DLList<T> implements Iterable<T> {
	private static class Node<T> {
		//before and after refer to adjacent nodes closer to beginning and end, respectively
		//refer to null space if node is first or last
		public Node<T> before, after;
		// data is the information stored in the node of type T (T could be String,
		// Integer, etc.)
		public T data;

		//initializer for Node
		public Node(Node<T> before, T data, Node<T> after) {
			this.before = before;
			this.after = after;
			this.data = data;
		}
	}

	// first is beginning node (no before), last is end node (no after)
	// They can both reference the same node if the list is one element long
	// They both reference null if the list is empty
	private Node<T> first, last;

	//int to keep track of how many nodes are currently in the list
	private int count;
	
	/**
	 * Forward iterator class (conductor).
	 */
	private static class Conductor<T> implements Iterator<T> {
		public Node<T> car; // Next node to visit

		public Conductor(DLList<T> list) {
			car = list.first; // Begin at first
		}

		public boolean hasNext() {
			return car != null; // No more to visit
		}

		public T next() {
			T data = car.data; // Remember current
			car = car.after; // Advance to after car
			return data; // Return old car data
		}
	}

	public DLList() {
		first = last = null; // Empty list
		count = 0;
	}

	/**
	 * Add data to the end (last) of the list.
	 */
	public void add(T data) {
		if (last == null) {
			// Empty list: one node is first and last
			first = new Node<>(null, data, null);
			last = first;
		} else {
			last.after = new Node<>(last, data, null);
			last = last.after;
		}
		//increase count by 1
		count++;
	}

	/**
	 * Retrieve an element from middle of list.
	 * 
	 * @param i Zero-based index of element
	 * @return The element at that index
	 * @throws IndexOutOfBoundsException if i is invalid
	 */
	public T get(int i) {
		if (i < 0 || i >= count) {
			throw new IndexOutOfBoundsException();
		}
		//if i is closer to the beginning of the list, iterate forwards to i
		if(i < count/2) {
			Node<T> current = first;
			for (int j = 0; current != null && j < i; j++) {
				current = current.after;
			}
			if (current == null) {
				throw new IndexOutOfBoundsException();
			}
			return current.data;
		}
		//if i is closer to the end of the list, iterate backwards to i
		else {
			Node<T> current = last;
			for (int j = count - 1; current != null && j > i; j--) {
				current = current.before;
			}
			if (current == null) {
				throw new IndexOutOfBoundsException();
			}
			return current.data;
		}
		
	}

	/**
	 * Get and remove element i from the list.
	 * 
	 * @param i Zero-based index of element
	 * @return The element at that index
	 * @throws IndexOutOfBoundsException if i is invalid
	 */
	public T remove(int i) {
		if (i < 0 || i >= count) {
			throw new IndexOutOfBoundsException();
		}
		//if i is closer to the beginning of the list, iterate forwards to i
		if(i < count/2) {
			Node<T> current = first;
			for (int j = 0; current != null && j < i; j++) {
				current = current.after;
			}
			if (current == null)
				throw new IndexOutOfBoundsException();
			if (current.before != null) {
				// Link before's after to new after
				// (The node after the node before the current node
				// becomes the node after the current node)
				current.before.after = current.after;
			} else {
				// current must be first, so first should refer to
				// the node after it to stop referencing current
				first = first.after;
			}
			if (current.after != null) {
				// Link after's before to new before
				// (The node before the node after the current node
				// becomes the node before the current node)
				current.after.before = current.before;
			} else {
				// current must be last, so last should refer to
				// the node before it to stop referencing current
				last = last.before;
			}
			//reduce count by 1
			count--;
			return current.data;
		}
		//if i is closer to the end of the list, iterate backwards
		else {
			Node<T> current = last;
			for (int j = count - 1; current != null && j > i; j--) {
				current = current.before;
			}
			if (current == null)
				throw new IndexOutOfBoundsException();
			if (current.before != null) {
				// Link before's after to new after
				// (The node after the node before the current node
				// becomes the node after the current node)
				current.before.after = current.after;
			} else {
				// current must be first, so first should refer to
				// the node after it to stop referencing current
				first = first.after;
			}
			if (current.after != null) {
				// Link after's before to new before
				// (The node before the node after the current node
				// becomes the node before the current node)
				current.after.before = current.before;
			} else {
				// current must be last, so last should refer to
				// the node before it to stop referencing current
				last = last.before;
			}
			//reduce count by 1
			count--;
			return current.data;
		}
	}

	/**
	 * Create a forward iterator for this list.
	 * 
	 * @return iterator that walks from first to last
	 */
	public Iterator<T> iterator() {
		// The Conductor object can walk this list
		// forward, front to back. Each time
		// .next() is called, the Conductor
		// produces one more piece of data,
		// starting with first and ending with last
		return new Conductor<T>(this);
	}

	//Backwards conductor that goes from the end of the list to the beginning
	private static class BackwardConductor<T> implements Iterator<T> {
		public Node<T> car; // Next node to visit

		public BackwardConductor(DLList<T> list) {
			car = list.last; // Begin at last node
		}

		public boolean hasNext() {
			return car != null; // No more to visit
		}

		public T next() {
			T data = car.data; // Remember current
			car = car.before; // Advance to before car
			return data; // Return old car data
		}
	}

	/**
	 * Create a reverse iterator for this list.
	 * 
	 * @return iterator that walks from last to first
	 */
	public Iterator<T> descendingIterator() {
		return new BackwardConductor<T>(this);
	}

	/**
	 * Retrieve the number of nodes of this list in O(1) time.
	 * 
	 * @return number of nodes
	 */
	public int size() {
		return count;
	}

	/**
	 * Reverse the list, so that what was the last is now the first, and so forth. A
	 * list going A <-> B <-> C <-> D would now go D <-> C <-> B <-> A.
	 */
	public void reverse() {
		//copy the existing nodes to the end of the list in reverse order
		Node<T> current = last;
		for (int j = count-1; current != null && j >= 0; j--) {
			add(current.data);
			current = current.before;
		}
		//remove the original order nodes
		for(int j = count/2; j > 0; j--) {
			remove(0);
		}
		
		
	}

	/**
	 * Add data to a new node at index i, causing the nodes at that index and after
	 * to be one place ahead of where they were in the list. (Do nothing if i was
	 * not a valid index in the list.)
	 * 
	 * @param i    existing index in the list
	 * @param data information to add into a new node
	 * @return false if i is not an index in the list, true otherwise
	 */
	public boolean add(int i, T data) {
		//check if i is a valid index
		if(i < 0 || i >= count) {
			return false;
		}
		else {
			// Empty list: node is both first and last
			if (i == 0 && last == null) {
				first = new Node<>(null, data, null);
				last = first;
			}
			// Add to the beginning of a populated list
			else if (i == 0 && last != null){
				Node<T> toAdd = new Node<>(null, data, first);
				first.before = toAdd;
				first = toAdd;
			}
			//if i is closer to first, iterate forwards to i
			else if (i > 0 && i < count/2){
				Node<T> current = first;
				for (int j = 0; current != null && j < i; j++) {
					current = current.after;
				}
				if (current == null) {
					throw new IndexOutOfBoundsException();
				}
				Node<T> toAdd = new Node<>(current.before, data, current);
				(current.before).after = toAdd;
				current.before = toAdd;
			}
			//if i is closer to last, iterate backwards to i
			else if (i < count && i >= count/2) {
				Node<T> current = last;
				for (int j = count-1; current != null && j > i; j--) {
					current = current.before;
				}
				if (current == null) {
					throw new IndexOutOfBoundsException();
				}
				else{
					Node<T> toAdd = new Node<>(current.before, data, current);
					(current.before).after = toAdd;
					current.before = toAdd;
				}
			}
			//increase count by 1
			count++;
			return true;
		}
	}
	
	public static void main(String[] args) {

	}
}
