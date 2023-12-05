/**
 * Test program for CS 143 Assignment 1 (Version of 1/13/2019 10:13 PM)
 * You may only use this program as a student of Martin Hock, CS 143 Fall 2019.
 * Directions: Run this program from the same workspace as your IAList.java file.
 */
public class IAListTest {

	public static void main(String[] args) {
		System.out.println("Now starting tests. You'll see an 'Everything looks OK!' message if everything is ok!");
		System.out.println("Creating two IALists, a and b.");
		System.out.println("Adding the numbers 1 through 10 to a by calling a.add(1); a.add(2); etc.");
		IAList a = new IAList();
		IAList b = new IAList();
		int score = 0;
		try {
			for (int i = 0; i < 10; i++) {
				a.add(i + 1);
				for (int j = 0; j <= i; j++) {
					int aget = a.get(j);
					if (aget != j + 1) {
						throw new RuntimeException("After adding " + (i + 1) + " to IAList a, calling a.get("+j+") should get "+(j+1)+ " but got "+aget+"!");
					}					
				}
			}
			if (a.size() != 10) {
				throw new RuntimeException("Size should be 10 after adding 10 things");
			}
			checkBounds(a);
			for (int i = 0; i < a.size(); i++) {
				if (a.get(i) != i + 1) {
					throw new RuntimeException("After adding 10 things and tried to a.get(" + i + ") I expected to see "
							+ (i + 1) + " but saw " + a.get(i) + "!");
				}
				a.set(i, i + 1 + 10);
				if (a.get(i) != i + 1 + 10) {
					throw new RuntimeException("After setting I tried to a.get(" + i + ") I expected to see "
							+ (i + 1 + 10) + " but saw " + a.get(i) + "!");
				}
			}
			
			for (int i = 100; i < 200; i++) {
				b.add(100);
			}
			for (int i = 0; i < 100; i++) {
				if (b.get(i) != 100) {
					throw new RuntimeException("Bad value in new IAList");
				}
			}
			for (int i = 0; i < a.size(); i++) {
				if (a.get(i) != i + 1 + 10) {
					throw new RuntimeException("After creating another IAList I tried (again) to a.get(" + i + ") I expected to see "
							+ (i + 1 + 10) + " but saw " + a.get(i) + "!");
				}

			}

			System.out.println("Initial functionality looks OK. Now testing addBefore with a single value.");
			a.addBefore(10);
			score += 9;
			if (a.size() != 11) {
				throw new RuntimeException(
						"Size should be 11 after adding 10 things and addBefore-ing 1 but instead it's " + a.size());
			}
			score += 9;
			for (int i = 0; i < a.size(); i++) {
				if (a.get(i) != i + 10) {
					throw new RuntimeException(
							"Index " + i + " should have value " + (i + 10) + " but instead has " + a.get(i));
				}
			}
			score += 9;
			System.out.println("OK. Now addBefore-ing 90 more values.");
			for (int i = 0; i < 90; i++) {
				a.addBefore(9 - i);
				int get0 = a.get(0);
				if (get0 != 9 - i) {
					throw new RuntimeException("After calling a.addBefore(" + (9 - i) + "), when I called get(0), instead of getting it I saw "+get0);
				}
				if (a.get(0) != get0) {
					throw new RuntimeException("Calling a.get(0) twice in a row got different results!");
				}
				int size = a.size();
				if (a.size() != 12+i) {
					throw new RuntimeException("After addBefore-ing "+(i+2)+" values, there should be "+(12+i)+" things but your size method says "+size);
				}
				for (int j = 1; j < a.size(); j++) {
					if (a.get(j) != 9-i+j) {
						throw new RuntimeException("After addBefore-ing "+(i+2)+" values, calling a.get("+j+") did not retrieve the correct value!");
					}
				}
				checkBounds(a);
			}
			if (a.size() != 101) {
				throw new RuntimeException("Size should be 101, instead I got " + a.size());
			}
			for (int i = 0; i < a.size(); i++) {
				if (a.get(i) != i-80) throw new RuntimeException("Index " + i + " should have value " + (i - 80) + " but instead has " + a.get(i));;
			}
			score += 8;
			System.out.println("OK. Now adding 200 values.");
			for (int i = 0; i < 200; i++) {
				a.add(i+21);
				int size = a.size();
				if (a.size() != 102+i) {
					throw new RuntimeException("After adding "+(i+1)+" out of 200, there should be "+(102+i)+" things but your size method says "+size);
				}
				for (int j = 0; j < a.size(); j++) {
					if (a.get(j) != j-80) {
						throw new RuntimeException("After adding "+(i+1)+" out of 200, calling a.get("+j+") did not retrieve the correct value!");
					}
				}
			}
			if (a.size() != 301) {
				throw new RuntimeException("Size should be 301, instead I got " + a.size());
			}
			for (int i = 0; i < a.size(); i++) {
				if (a.get(i) != i-80) throw new RuntimeException("Index " + i + " should have value " + (i - 80) + " but instead has " + a.get(i));;
			}
			score += 8;
			System.out.println("OK. Now alternating addBefore and append with a bunch of values.");
			for (int i = 0; i < 1000; i++) {
				if (i % 2 == 0) {
					a.addBefore(-(i + 162) / 2);
				} else {
					a.add((i + 1) / 2 + 220);
				}
			}
			for (int i = 0; i < a.size(); i++) {
				if (a.get(i) != -580 + i) {
					throw new RuntimeException(
							"Expected a.get(" + i + ") == " + (-580 + i) + " instead was " + a.get(i));
				}
			}
			score += 8;
			System.out.println("OK. Now running a speed test addBefore-ing and adding a million values.");
			long start = System.currentTimeMillis();
			for (int i = 0; i < 1000000; i++) {
				a.addBefore(-581 - i);
			}
			for (int i = 0; i < a.size(); i++) {
				if (a.get(i) != -1000580 + i) {
					throw new RuntimeException(
							"Expected a.get(" + i + ") == " + (-1000580 + i) + " instead was " + a.get(i));
				}
			}
			score += 8;
			for (int i = 0; i < 1000000; i++) {
				a.add(721 + i);
			}
			if (a.size() != 2001301) {
				throw new RuntimeException("Size should now be 2001301, instead I got " + a.size());
			}
			for (int i = 0; i < a.size(); i++) {
				if (a.get(i) != -1000580 + i) {
					throw new RuntimeException(
							"Expected a.get(" + i + ") == " + (-1000580 + i) + " instead was " + a.get(i));
				}
			}
			score += 8;
			for (int i = 0; i < a.size(); i++) {
				a.set(i, a.size() - i);
			}
			for (int i = 0; i < a.size(); i++) {
				if (a.get(i) != 2001301 - i) {
					throw new RuntimeException("After resetting the whole array and calling a.get(i) I expected "
							+ (2001301 - i) + " but got " + a.get(i));
				}
			}
			long end = System.currentTimeMillis();
			System.out.println("OK. Now running a speed test alternating addBefore and append with 100,000 zeros.");
			long start2 = System.currentTimeMillis();
			for (int i = 0; i < 200000; i++) {
				if (i % 2 == 0) {
					a.addBefore(0);
				} else {
					a.add(0);
				}
			}
			for (int i = 0; i < a.size(); i++) {
				if (i < 100000 || i > 2101301) {
					if (a.get(i) != 0) throw new RuntimeException("Index "+i+" should have 0 but instead had "+a.get(i));
				} else if (a.get(i) != 2001301 - i+100000) {
					throw new RuntimeException("After calling a.get(i) I expected "
							+ (2001301 - i+100000) + " but got " + a.get(i));
				}
			}
			long end2 = System.currentTimeMillis();
			score += 8;
			if (end - start < 1000 && end2 - start2 < 1000) {
				System.out.println("OK. You took " + (end - start) + " milliseconds and "+(end2-start2)+" milliseconds, not bad!");
				System.out.println("****** Everything looks OK! ******");
				score += 25;
			} else {
				System.out.println("OK, but you took " + (end - start)
						+ " milliseconds, which seems too long (I'm expecting 1000 or less; my laptop takes between 80 and 130 in my implementations). Please let me know if you feel this is in error.");
				if (end - start < 10000 && end2 - start2 < 10000) score += 10; // At least somewhat fast, partial credit
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		System.out.println(
				"Tentative score: " + score + "/100 (Note that any academic misconduct would affect your score.)");
	}

	private static void checkBounds(IAList a) {
		boolean didExcept = false;
		try {
			a.get(-1);
		} catch (Exception e) {
			didExcept = true;
		}
		if (!didExcept) {
			throw new RuntimeException("Accessing before the beginning did not cause an exception, but it should!");
		}
		didExcept = false;
		try {
			a.get(a.size());
		} catch (Exception e) {
			didExcept = true;
		}
		if (!didExcept) {
			throw new RuntimeException("Accessing after the end did not cause an exception, but it should!");
		}
		
	}

}
