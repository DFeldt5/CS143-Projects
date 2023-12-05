import java.util.Random;

/**
 * Test program for CS 143 Assignment 1 (Version of 9/23/2020 10:10 PM)
 * 
 * You may only use this program as a student of Martin Hock, CS 143 Fall 2020.
 * 
 * Directions: Run this program from the same project source folder as your
 * Warehouse.java file.
 */

public class WarehouseTest {

	static StringBuilder sb = new StringBuilder(10000);

	public static boolean checkReceive(Warehouse w, String name, int itemCode, int itemCount, int expected) {
		sb.append("Warehouse " + name + " receives " + itemCount + " pallet" + (itemCount == 1 ? "" : "s") + " of item "
				+ itemCode + ".\n");
		int ret = w.receive(itemCode, itemCount);
		if (ret != expected) {
			sb.append(name + ".receive(" + itemCode + ", " + itemCount + ") returned " + ret + " instead of " + expected
					+ "\n");
			return false;
		}
		return true;
	}

	public static boolean checkShip(Warehouse w, String name, int itemCode, int itemCount, int expected) {
		sb.append("Warehouse " + name + " is requested to ship " + itemCount + " pallet" + (itemCount == 1 ? "" : "s")
				+ " of item " + itemCode + ".\n");
		int ret = w.ship(itemCode, itemCount);
		if (ret != expected) {
			sb.append(name + ".ship(" + itemCode + ", " + itemCount + ") returned " + ret + " instead of " + expected
					+ "\n");
			return false;
		}
		return true;
	}

	public static final int[][] traces = { { 0, 1, 2, 1, 0 }, { 2, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 5, 1, 6, 3, 6, 4, 5, 1 }, { 4, 1, 2, 0, 0, 5, 0, 6, 3, 2, 7, 4, 3 },
			{ 0, 1, 2, 2, 1, 1, 1, 3, 0, 2, 0, 0, 1, 1, 0 }, { 0, 0, 0, 0, 0, 3, 4, 1, 3, 0, 2, 0, 0, 5, 3, 0, 0 },
			{ 0, 1, 1, 2, 3, 0, 0, 0, 2, 2, 0, 0, 3, 3, 0, 0, 4, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 1, 8, 3, 0, 0, 4, 1, 2, 2, 3 },
			{ 2, 2, 0, 1, 3, 0, 0, 0, 3, 3, 1, 1, 1, 1, 0, 2, 2, 3, 1, 2, 1, 0, 0 },
			{ 5, 9, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 2, 0, 0, 3, 0, 2, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 2, 0 },
			{ 0, 0, 0, 0, 0, 2, 0, 5, 4, 6, 4, 5, 0, 3, 1, 0, 2, 2, 1, 3, 0, 0, 0, 0, 2, 4, 0, 0, 5 },
			{ 1, 0, 0, 4, 4, 0, 2, 0, 1, 4, 2, 0, 0, 3, 2, 0, 0, 0, 3, 4, 1, 0, 0, 3, 3, 1, 3, 1, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 3, 5, 1, 0, 3, 0, 3, 2, 0, 3, 4, 5, 1 },
			{ 8, 11, 10, 3, 7, 3, 5, 12, 8, 0, 0, 0, 5, 0, 3, 0, 0, 0, 0, 6, 12, 1, 2, 0, 3, 3, 2, 5, 3, 5, 0, 9, 0, 0,
					3 },
			{ 2, 0, 0, 1, 1, 3, 1, 0, 0, 2, 2, 5, 5, 0, 2, 1, 0, 3, 2, 3, 1, 4, 0, 1, 0, 2, 0, 2, 4, 0, 0, 0, 4, 2, 2,
					0, 3 },
			{ 0, 0, 5, 8, 0, 0, 11, 0, 8, 6, 4, 0, 0, 6, 10, 0, 0, 5, 6, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 6, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 3, 5, 1, 4, 2, 0, 0, 1, 6, 2, 0, 5, 4, 0, 4,
					2, 0, 4, 0, 4, 4 },
			{ 8, 9, 0, 0, 0, 0, 8, 0, 0, 0, 12, 10, 11, 0, 3, 4, 0, 0, 0, 2, 0, 2, 2, 0, 10, 6, 0, 4, 0, 7, 11, 2, 0, 0,
					9, 5, 0, 0, 3, 5, 0, 8, 3 },
			{ 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 3, 0, 6, 2, 0, 4, 3, 0, 1, 0, 1, 3, 0, 0, 0, 1, 3, 4, 5, 1, 6, 4, 2, 2,
					4, 6, 5, 0, 2, 1, 1, 0, 0, 0 },
			{ 2, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 4, 0, 7, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 6, 0, 0, 0, 5, 0, 7, 0, 0,
					0, 1, 4, 0, 9, 0, 9, 1, 5, 0, 0, 2 },
			{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 6, 0, 0, 3, 4, 5, 7, 0, 0, 3, 0, 0, 0, 4, 0, 0, 0, 7, 0, 0, 0, 8, 0, 0, 3, 0,
					4, 6, 0, 2, 0, 5, 0, 0, 0, 10, 6, 0, 0, 0 },
			{ 0, 5, 4, 3, 0, 8, 4, 0, 11, 5, 0, 0, 7, 6, 0, 2, 0, 9, 0, 7, 0, 0, 1, 1, 0, 11, 0, 4, 0, 0, 0, 0, 4, 10,
					0, 0, 0, 0, 0, 0, 0, 0, 3, 2, 0, 0, 9, 7, 10, 0 },
			{ 0, 0, 0, 0, 0, 4, 0, 2, 2, 0, 5, 0, 0, 3, 10, 7, 0, 0, 9, 0, 0, 1, 0, 6, 0, 0, 0, 0, 0, 7, 0, 0, 4, 0, 0,
					0, 5, 0, 0, 2, 4, 0, 10, 10, 3, 0, 2, 0, 6, 2 },
			{ 0, 2, 0, 4, 1, 5, 1, 2, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 4, 0, 0, 0, 3, 0, 0, 2, 0, 2, 3, 0, 1, 0, 0, 4,
					4, 3, 2, 0, 2, 0, 0, 1, 3, 0, 0, 4, 3, 0, 1 },
			{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 3, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1,
					0, 2, 4, 5, 5, 0, 2, 0, 3, 5, 0, 3, 0, 2, 0 },
			{ 0, 0, 0, 1, 4, 0, 0, 6, 0, 0, 0, 2, 0, 0, 2, 1, 4, 0, 3, 0, 1, 0, 8, 7, 0, 0, 4, 4, 6, 2, 0, 0, 0, 3, 2,
					0, 2, 3, 1, 1, 3, 0, 0, 1, 0, 2, 0, 6, 2, 0 },
			{ 0, 0, 0, 0, 0, 2, 7, 8, 4, 1, 8, 2, 0, 0, 0, 3, 3, 1, 0, 5, 0, 6, 0, 0, 8, 1, 0, 0, 5, 0, 0, 0, 0, 2, 0,
					0, 0, 1, 7, 7, 0, 7, 7, 1, 0, 6, 1, 0, 7, 5 },
			{ 2, 2, 0, 4, 0, 0, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 2, 3, 1, 1, 0, 0,
					0, 2, 0, 0, 0, 0, 2, 0, 2, 0, 2, 0, 2, 0, 0 },
			{ 0, 0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 2, 2, 1, 0, 1, 0, 1, 1, 0, 1, 0,
					0, 1, 1, 2, 0, 0, 0, 0, 0, 0, 3, 1, 3, 1, 1 },
			{ 0, 0, 6, 0, 2, 6, 2, 0, 5, 0, 0, 6, 3, 0, 0, 0, 2, 5, 0, 7, 7, 3, 4, 3, 4, 0, 0, 5, 1, 0, 0, 2, 6, 7, 6,
					0, 0, 2, 0, 1, 6, 0, 7, 1, 4, 2, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 5, 0, 5, 11, 6, 0, 8, 2, 0, 0, 0, 8, 5, 0, 4, 0, 0, 0, 0, 0, 9, 12, 0,
					0, 0, 0, 3, 1, 0, 4, 12, 0, 0, 7, 10, 0, 6, 4 },
			{ 0, 0, 1, 6, 3, 0, 0, 0, 5, 0, 0, 10, 4, 6, 1, 0, 11, 0, 2, 0, 6, 0, 10, 8, 4, 2, 9, 0, 10, 7, 0, 1, 0, 0,
					5, 0, 6, 0, 0, 3, 7, 0, 2, 4, 3, 11, 0, 3, 5, 1 },
			{ 1, 0, 0, 1, 4, 3, 6, 0, 4, 1, 6, 0, 5, 0, 0, 3, 4, 2, 0, 3, 1, 6, 3, 0, 0, 6, 6, 2, 3, 2, 3, 1, 2, 0, 0,
					0, 2, 0, 2, 0, 4, 0, 2, 0, 0, 0, 5, 0, 0, 6 },
			{ 0, 0, 0, 0, 0, 0, 2, 3, 1, 2, 0, 1, 0, 2, 2, 3, 0, 0, 0, 0, 1, 7, 0, 0, 0, 2, 0, 1, 0, 0, 0, 0, 0, 2, 5,
					4, 7, 1, 1, 0, 0, 0, 2, 5, 0, 7, 0, 2, 0, 3 },
			{ 0, 0, 0, 8, 1, 4, 7, 1, 7, 8, 0, 0, 0, 0, 0, 7, 1, 0, 5, 0, 0, 2, 0, 7, 4, 2, 1, 0, 0, 0, 0, 0, 1, 0, 1,
					0, 6, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 4 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 2, 0, 1, 3, 0, 0, 0, 0, 0, 0, 3, 0, 0, 5, 9, 4, 2, 3, 4, 2, 6, 9, 0,
					0, 0, 4, 6, 6, 1, 0, 9, 7, 4, 0, 0, 6, 0, 0 },
			{ 0, 2, 0, 0, 0, 0, 0, 0, 2, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 2, 1, 0, 0, 0, 0, 2, 1, 1, 2,
					1, 2, 1, 0, 0, 1, 0, 2, 2, 0, 1, 2, 0, 1, 0 },
			{ 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 1, 0, 1, 0,
					4, 2, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 3 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 5, 2, 5, 3, 0, 0, 0, 0, 1, 0, 0, 0, 4, 0, 2, 3, 0, 1, 2, 5, 1, 0,
					1, 5, 3, 0, 0, 0, 2, 1, 0, 3, 0, 3, 3, 0, 1 },
			{ 0, 0, 0, 0, 0, 2, 0, 2, 4, 9, 0, 0, 5, 2, 8, 7, 0, 6, 2, 0, 0, 10, 1, 1, 6, 1, 10, 1, 8, 6, 2, 10, 0, 6,
					0, 3, 9, 9, 3, 0, 8, 0, 5, 0, 8, 3, 6, 4, 0, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 5, 1, 8, 7, 3, 1, 0, 0, 1, 2, 9, 7, 1, 0, 0, 2, 3, 0, 10, 2, 0, 10, 0, 0, 1, 0, 3, 0,
					0, 10, 2, 0, 0, 1, 7, 11, 0, 2, 0, 0, 7, 6, 2 },
			{ 0, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 2, 2, 0, 0, 0, 0, 1, 0, 0, 1, 2, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 5,
					2, 2, 0, 0, 0, 1, 2, 1, 1, 3, 0, 0, 2, 0, 5 },
			{ 0, 0, 0, 0, 1, 0, 1, 3, 0, 0, 0, 1, 0, 0, 1, 0, 4, 0, 3, 1, 1, 4, 0, 1, 1, 0, 3, 1, 0, 2, 4, 2, 3, 3, 4,
					0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 2, 0, 5, 5, 3 },
			{ 0, 0, 8, 9, 0, 8, 7, 8, 3, 0, 0, 1, 3, 9, 6, 0, 0, 11, 11, 0, 5, 0, 6, 0, 1, 0, 2, 0, 6, 6, 5, 3, 5, 1, 0,
					0, 0, 6, 0, 0, 0, 6, 0, 11, 11, 0, 4, 0, 3, 9 },
			{ 0, 0, 0, 0, 0, 0, 6, 0, 4, 5, 0, 0, 6, 4, 8, 0, 0, 2, 4, 0, 3, 0, 0, 3, 3, 1, 0, 8, 3, 0, 4, 0, 1, 0, 2,
					4, 1, 0, 0, 7, 5, 3, 0, 0, 1, 0, 1, 0, 3, 0 },
			{ 0, 2, 0, 0, 2, 0, 0, 2, 1, 0, 0, 0, 5, 0, 0, 7, 0, 5, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 2, 0, 0, 0, 0, 4, 4,
					0, 0, 0, 0, 3, 0, 2, 0, 1, 1, 1, 1, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 7, 5, 8, 0, 8, 0, 0, 0, 7, 7, 2, 7, 2, 0, 5, 0, 6, 0, 3, 9, 0, 1, 0, 0, 1,
					0, 3, 8, 0, 0, 0, 4, 2, 0, 2, 0, 0, 0, 0, 4 },
			{ 0, 0, 0, 0, 1, 1, 3, 1, 0, 3, 0, 4, 0, 0, 8, 4, 8, 0, 0, 9, 0, 3, 1, 4, 0, 4, 0, 5, 1, 5, 5, 0, 9, 4, 0,
					2, 6, 0, 0, 8, 4, 9, 0, 10, 5, 0, 3, 0, 0, 5 }, };

	public static void main(String[] args) {
		int points = 0;
		try {
			sb.append("Constructing a new Warehouse X with size 3 and limit per item 3.\n");
			Warehouse X = new Warehouse(3, 3);
			boolean printedError = false;

			System.out.println("Following scenario from assignment description alongside another warehouse.");
			sb.append("Constructing a new Warehouse A with size 3 and limit per item 2.\n");
			Warehouse A = new Warehouse(3, 2);
			boolean awesome = false;
			if (A.getSize() != 3) {
				System.out.println("A is not reporting the correct size! Should be 3 but is reporting " + A.getSize());
			} else if (A.getLimitPerItem() != 2) {
				System.out.println("A is not reporting the correct limit per item! Should be 2 but is reporting "
						+ A.getLimitPerItem());
			} else if (checkReceive(A, "A", 1, 3, 1) && checkReceive(A, "A", 2, 1, 0) && checkShip(A, "A", 1, 1, 1)
					&& checkReceive(A, "A", 1, 2, 1) && checkReceive(A, "A", 4, 1, 1) && checkShip(A, "A", 1, 2, 2)
					&& checkReceive(A, "A", 2, 2, 1) && checkReceive(X, "X", 3, 3, 0)) {
				System.out.println("Awesome!");
				awesome = true;
			}
			if (!awesome) {
				System.out.print(sb);
				System.out.println("Please fix the above problem and run again.");
				points -= 5; // Can't get full credit if there is a problem with the scenario above...
			}
			System.out.println("Now doing 50 pseudorandom tests that count for points...");

			for (int i = 0; i < 50; i++) {
				Random r = new Random((i + 1) * 48271);
				sb.delete(0, sb.length());
				int size = r.nextInt(Math.min(i + 1, 100)) + 1, limitPerItem = r.nextInt(Math.min(i + 1, 10)) + 1;
				Warehouse w = new Warehouse(size, limitPerItem);
				sb.append("Constructing a new Warehouse w with size " + size + " and limit per item " + limitPerItem
						+ ".\n");
				if (w.getSize() != size) {
					System.out.println("w is not reporting the correct size! Should be " + size + " but is reporting "
							+ A.getSize());
					break;
				} else if (A.getLimitPerItem() != 2) {
					System.out.println("w is not reporting the correct limit per item! Should be " + size
							+ " but is reporting " + A.getLimitPerItem());
					break;
				} else
					points++;
				for (int j = 0; j < Math.min(2 * i + 5, 50); j++) {
					boolean foo = false;
					if (r.nextBoolean())
						foo = checkReceive(w, "w", r.nextInt(2 * size) + 1, r.nextInt(limitPerItem + 2) + 1,
								traces[i][j]);
					else
						foo = checkShip(w, "w", r.nextInt(2 * size) + 1, r.nextInt(limitPerItem + 2) + 1, traces[i][j]);
					if (!foo) {
						if (!printedError) {
							System.out.print(sb);
							System.out.println("Please fix the above problem and run again.");
							System.out.println(
									"Additional descriptions of errors will be suppressed (except for exceptions).");
						}
						printedError = true;
						points--;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		points = Math.max(0, points);
		System.out.println("Total score: " + points + " / 50");

	}

}
