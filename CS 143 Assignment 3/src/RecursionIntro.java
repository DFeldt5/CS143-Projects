/*
 * Feldt, Dustin
 * CS 143 Assignment 3
 * RecursionIntro contains several interconnected methods that use recursion
 * to solve specific problems
 */
public class RecursionIntro {
	
	//takes an integer, adds 1 to the value of each even digit,
	//subtracts 1 from the value of each odd digit, then returns the changed integer
	public static long eduodd(long n) {
		//adapts to negative inputs by returning the negative value of the changed positive value of the original
		if (n < 0) {
			return -eduodd(-n);
		}

		//base case: if n is one digit, add or subtract 1
		else if (n % 10 == n) {
			if (n % 2 == 0) {
				return n + 1;
			}
			else {
				return n - 1;
			}
		}
		
		//if n is more than one digit and ends with an even number,
		//calls the method on all but the last digit, raises the last digit by 1,
		//then adds the changed last digit back to the end
		else if (n % 2 == 0) {
			return eduodd(n / 10) * 10 + (n % 10 + 1);
		}
		
		//if n is more than one digit and ends with an odd number,
		//calls the method on all but the last digit, lowers the last digit by 1,
		//then adds the changed last digit back to the end
		else {
			return eduodd(n/10) * 10 + (n % 10 - 1);
		}
	}
	
	//gives the value of the nth number in the given Fibonacci-style sequence
	public static int fibby(int n) {
		//base case: the first number in the sequence is 1
		if(n == 0) {
			return 1;
		}
		else{
			return fibby(n/3) + fibby(2 * n / 3);
		}
	}
	
	//prints a sparse table of all the Fibby values in a given range
	public static void printSparseTable(int start, int end) {
		//if start and end are the same, only one row is printed
		if(start == end) {
			System.out.println(start + " " + fibby(start));
			return;
		}
		//calls the helper method
		sparseHelper(start, end, 0, end-start);
	}
	
	//driver method to help printSparseTable
	//takes the same start and end values, as well as two tracker variables
	//n tracks how many steps have been taken in the Fibby sequence
	//m is the total number of steps that will be taken
	private static void sparseHelper(int start, int end, int n, int m) {
		//always prints the first row, even if it has the same value as the "previous" row
		//then starts a recursive call with start and n increased by 1
		if(n == 0) {
			System.out.println(start + " " + fibby(start));
			sparseHelper(start + 1, end, n + 1, m);
		}
		
		//base case: if n is greater than m, then the calls are stopped
		else if(n > m) {
			return;
		}
		
		//if the Fibby value to be printed is the same as the previous Fibby value,
		//nothing is printed and the next recursive call is made
		else if(fibby(start - 1) == fibby(start)) {
			sparseHelper(start + 1, end, n + 1, m);
		}
		
		//if the Fibby value is unique, prints the row and makes the next recursive call
		else{
			System.out.println(start + " " + fibby(start));
			sparseHelper(start + 1, end, n + 1, m);
		}
	}
	
	//returns the value of the highest power of 2 that is less than n
	public static int lp2lt(int n) {
		//base case: if n = 2, lowest power would be 2^0, or 1
		if(n == 2) {
			return 1;
		}
		return lp2ltHelper(n, 2);
	}
	
	//driver method to help lp2lt
	//takes n as well as an int to track the exponent of 2
	private static int lp2ltHelper(int n, int exp) {
		//if 2^(exp) is less than n, calls itself with the next highest exponent
		if(pow(exp) < n) {
			return lp2ltHelper(n, exp + 1);
		}
		
		//if 2^(exp) is greater than n, returns the next lowest power of 2
		else {
			return pow(exp - 1);
		}
	}
	
	//returns 2^(a)
	private static int pow(int a) {
		if(a == 0) {
			return 1;
		}
		return 2 * pow(a-1);
	}
	
	//returns the index of the winner of the Champion game
	public static int champion(boolean[] a) {
		//calls the helper method
		return championHelper(a, 0, a.length-1);
	}
	
	//helper method of champion
	//uses two tracker variables to keep track of the beginning and end of the indexes being looked at
	private static int championHelper(boolean[] a, int start, int end) {
		//base case 1: only one index in a group
		if(end == start) {
			return end;
		}
		//base case 2: two adjacent indexes in a group
		//calls the victor method to determine winner
		else if(end - start == 1) {
			return victor(a, start, end);
		}
		
		//use lp2lt method to split the given section of a[] into 2 smaller sections, one of which
		//will always divide evenly into pairs
		//then determine the ultimate victor of those two sections by using recursive calls of championHelper
		//on each section
		else {
			int split = lp2lt((end+1) - start) + start - 1;
			return victor(a, championHelper(a, start, split), championHelper(a, split + 1, end));
		}
	}
	
	//determines the winner between two indexes based on the rules of the champion game
	private static int victor(boolean[] a, int left, int right) {
		if(a[left] == a[right]) {
			return right;
		}
		else {
			return left;
		}
	}
}
