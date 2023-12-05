
/*
 * CS 143 Assignment 2
 * Dustin Feldt
 * 
 * This program defines a class, IAList, which allows the user to create,
 * modify, and add values to the beginning or end of an integer array.
 */

public class IAList {
	private int[] a; // Underlying array
	private int length; // Number of added elements in the array
	private int emptyAfter; //Number of empty spaces at end of the array
	private int emptyBefore; //Number of empty spaces at the beginning of the array
	
	public IAList() {
		length = 0; // Tracks the number of user-added elements to a
		a = new int[4]; //initializes the array with 4 elements
		emptyAfter = a.length; //starts out equal to the empty array
	}

	//Retrieves an added element at the specified index of the "user" array
	public int get(int i) {
		if (i < 0 || i >= length) {
			throw new IndexOutOfBoundsException(i+"");
		}
		i = i + emptyBefore;
		return a[i];
	}

	//Provides the number of added elements
	public int size() {
		return length;
	}

	//change an existing element in the user array
	public void set(int i, int x) {
		if (i < 0 || i >= length) {
			throw new IndexOutOfBoundsException(i+"");
		}
		i = i + emptyBefore;
		a[i] = x;
	}

	//adds a new element to the end of the user array
	public void add(int x) { 
		//if the array is full, creates a new array of double the size,
		//then copies the previous array to the first half and leaves the second half as empty values
		if (emptyAfter == 0) {
			int[] b = new int[a.length * 2];
			// Copy the elements of a to the corresponding indexes of b
			for (int i = 0; i < a.length; i++) {
				b[i] = a[i];
			}
			//reset emptyAfter to reflect new array
			emptyAfter = a.length;
			// Reassign a reference to b
			a = b;			
		}
		
		// Place x at the end of the user array
		a[length + emptyBefore] = x;
		// Increase length by 1, reduce number of empty end values by 1
		length++;
		emptyAfter--;
	}

	//adds a new element to the beginning of the user array, bumping existing elements one index higher
	public void addBefore(int x) {
		//if the array is full, creates a new array of double the size,
		//then copies the existing array into the last half of the new array
		if (emptyBefore == 0) {
			int[] b = new int[a.length * 2];
			// Copy the elements of a to b, starting in the middle of b
			for (int i = a.length; i < b.length; i++) {
				b[i] = a[i-a.length];
			}
			//reset emptyBefore to reflect new array
			emptyBefore = a.length;
			// Reassign a reference to b
			a = b;		
		}
		
		// Place x at the beginning of the user array
		a[emptyBefore-1] = x;
		
		// Increase length by 1, reduce number of empty before values by 1
		length++;
		emptyBefore--;
	}
}
