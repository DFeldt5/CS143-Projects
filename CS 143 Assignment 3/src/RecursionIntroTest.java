import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

/**
 * Test of CS 143 Assignment 3 by Martin Hock (Version of 3:20 PM 10/15/2020)
 * Edits by David Anderson (2/2/2021)
 *
 * You may only use this code as a student of David Anderson, CS 143 at TCC, Winter 2021
 */
public class RecursionIntroTest {
	public static void rowColumn(String source, int offset) {
		int row = 1;
		int col = 1;
		String tabby = "";
		for (int i = 0; i < offset; i++) {
			if (source.charAt(i) == '\n') {
				row++;
				col = 1;
				tabby = "";
			} else if (source.charAt(i) == '\t') {
				col += (4 - (col - 1) % 4);
				tabby += '\t';
			} else {
				col++;
				tabby += ' ';
			}
		}
		System.out.println(tabby + "^--- Problem!!!");
		System.out.println("Above problem is on line " + row + " column " + col + " (Assuming tab width of 4)");
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String source = null;
		try {
			source = new String(Files.readAllBytes(Paths.get("src" + File.separator + "RecursionIntro.java")));
		} catch (Exception e) {
			System.out.println(
					"Couldn't find RecursionIntro.java! Run this from the same Eclipse project as RecursionIntro.");
			return;
		}
		if (source.matches("(?s).*\\sfor[\\s\\(].*")) {
			System.out.println(
					"Detected 'for' statement in your program! Please remove anything that resembles a 'for'.");
			System.exit(-1);
		}
		if (source.matches("(?s).*\\swhile[\\s\\(].*")) {
			System.out.println(
					"Detected 'while' statement in your program! Please remove anything that resembles a 'while'.");
			System.exit(-1);
		}
		if (source.matches("(?s).*\\snew\\s.*")) {
			System.out.println(
					"Detected 'new' statement in your program! Please remove anything that resembles a 'new'.");
			System.exit(-1);
		}
		if (source.matches("(?s).*\\simport\\s.*") || source.indexOf("import") == 0) {
			System.out.println("Detected 'import' statement in your program! Please remove any word 'import'.");
			System.exit(-1);
		}
		if (source.matches("(?s).*\\s+static\\s+\\w+[^(]*=.*") || source.matches("(?s).*\\s+static\\s+\\w+[^(]*;.*")) {
			System.out.println("Detected static variable in your program! Please remove static variables.");
			System.exit(-1);
		}
		for (int dotIndex = source.indexOf('.'); dotIndex != -1; dotIndex = source.indexOf('.', dotIndex + 1)) {
			if ("System.out.println".equals(source.substring(dotIndex - 6, dotIndex + 12))
					|| "System.out.println".equals(source.substring(dotIndex - 10, dotIndex + 8))
					|| "length".equals(source.substring(dotIndex + 1, dotIndex + 7))
					|| Character.isDigit(source.charAt(dotIndex + 1))) {
				continue;
			}
			int lastLine = source.lastIndexOf('\n', dotIndex);
			String dotLine = source.substring(Math.max(0, lastLine), dotIndex);
			if (dotLine.contains("//") && !dotLine.contains("\""))
				continue; // Dot in line comment
			System.out.println("Bad dot! Context: " + source.substring(Math.max(0, lastLine),
					Math.min(source.indexOf('\n', dotIndex) - 1, source.length())));
			rowColumn(source, dotIndex);
			System.exit(-1);
		}

		System.out.println("Passed syntax checks!");
		System.out.println(
				"If you don't see a score, your program didn't get to the end (possibly taking an extremely long or infinite amount of time)!");
		PrintStream out = System.out;
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		int score = 0;
		int eduoddScore = 0;
		int fibbyScore = 0;
		int stGenScore = 0;
		int lp2ltScore = 0;
		int championScore = 0;
		try {
			try { // Part 1: eduodd
				long[] ns = { 0, 27, 987654321, -8443, 11121113, -11, 99999999999L, -987654321, -1016548095,
						-458096230 };
				long[] eos = { 1, 36, 896745230, -9552, 30002, 0, 88888888888L, -896745230, -107459184, -549187321 };
				System.out.println("Testing eduodd...");
				for (int i = 0; i < ns.length; i++) {
					long eo = RecursionIntro.eduodd(ns[i]);
					if (eo != eos[i]) {
						System.out.println("Expected eduodd(" + ns[i] + ") = " + eos[i] + " but got " + eo);
					} else {
						eduoddScore++;
					}
				}
				Random r = new Random(123456789);
				long[] rando = new long[1000];
				for (int i = 0; i < rando.length; i++) {
					rando[i] = RecursionIntro.eduodd(r.nextInt());
				}
				byte[] b = md5.digest(Arrays.toString(rando).getBytes());
				byte[] good = { -106, 111, -96, -46, -125, 4, 30, 41, 69, -39, 87, -114, -123, -119, -12, 22 };
				if (!Arrays.equals(b, good)) {
					System.out.println(
							"Your eduodd method doesn't always work. Please consult the assignment carefully.");
					if (eduoddScore == 10) {
						System.out.println(
								"It looks like you passed the above test cases! You may want to ask your instructor for help!");
					}
				} else {
					eduoddScore += 20;
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
			try { // Part 2a: fibby
				int[] fibbys = { 1, 2, 3, 5, 5, 7, 8, 8, 10, 13 };
				System.out.println("Testing fibby...");
				for (int i = 0; i < fibbys.length; i++) {
					int fib = RecursionIntro.fibby(i);
					if (fib != fibbys[i]) {
						System.out.println("Expected fibby(" + i + ") = " + fibbys[i] + " but got " + fib);
					} else
						fibbyScore++;
				}
				int[] fibbys2 = new int[10000];
				for (int i = 0; i < 10000; i++) {
					fibbys2[i] = RecursionIntro.fibby(i);
				}
				byte[] b = md5.digest(Arrays.toString(fibbys2).getBytes());
				byte[] good = { -47, -19, 29, -13, 48, 81, -79, 112, -126, 3, 32, 66, 112, -70, 74, -90 };
				if (!Arrays.equals(b, good)) {
					System.out.println(
							"Your fibby method doesn't work for some value between 0 and 9999. Please consult the definition carefully.");
				} else {
					fibbyScore += 10;
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
			try { // Part 2b: printSparseTable
				System.out.println("Testing printSparseTable...");
				int[] starts = { 1, 1000, 101 };
				int[] ends = { 100, 1020, 999 };
				byte[][] md5s = { { 16, 33, 66, 90, -77, -107, 119, 41, 109, -10, -61, 25, -109, -19, 9, 25 },
						{ -2, 8, 14, -109, 110, -48, 43, -85, 24, -42, 103, 104, -16, -128, -102, -62 },
						{ 80, -21, 87, 112, -75, 67, 67, 41, 28, 83, 40, -96, -35, 6, 95, 124 } };
				for (int i = -3; i < starts.length; i++) {
					ByteArrayOutputStream ba = new ByteArrayOutputStream();
					System.setOut(new PrintStream(ba));
					if (i == -3) {
						RecursionIntro.printSparseTable(4, 10);
						System.setOut(out);
						String expected = "4 5\n5 7\n6 8\n8 10\n9 13\n";
						if (!Arrays.equals(expected.split("\\r?\\n"), ba.toString().split("\\r?\\n"))) {
							System.out.println(
									"For printSparseTable(4, 10), expected:\n" + expected + "\n, got: \n" + ba);
						} else {
							stGenScore++;
						}
					} else if (i == -2) {
						RecursionIntro.printSparseTable(10, 10);
						System.setOut(out);
						String expected = "10 13\n";
						if (!Arrays.equals(expected.split("\\r?\\n"), ba.toString().split("\\r?\\n"))) {
							System.out.println(
									"For printSparseTable(10, 10), expected:\n" + expected + "\n, got: \n" + ba);
						} else {
							stGenScore++;
						}
					} else if (i == -1) {
						RecursionIntro.printSparseTable(2, 9);
						System.setOut(out);
						String expected = "2 3\n3 5\n5 7\n6 8\n8 10\n9 13\n";
						if (!Arrays.equals(expected.split("\\r?\\n"), ba.toString().split("\\r?\\n"))) {
							System.out.println(
									"For printSparseTable(2, 9), expected:\n" + expected + "\n, got: \n" + ba);
						}
					} else {
						RecursionIntro.printSparseTable(starts[i], ends[i]);
						System.setOut(out);
						byte[] digest = md5.digest(Arrays.toString(ba.toString().split("\\r?\\n")).getBytes());
						if (!Arrays.equals(md5s[i], digest)) {
							System.out.println("Didn't get expected output for printSparseTable(" + starts[i] + ", "
									+ ends[i] + ")");
							System.out.println("Saw this:");
							System.out.println(ba);
							// System.out.println("MD5 sum of output seen: " + Arrays.toString(digest));
						} else
							stGenScore++;
					}
				}
				ByteArrayOutputStream ba = new ByteArrayOutputStream();
				System.setOut(new PrintStream(ba));
				Random r = new Random(918273645);
				for (int i = 0; i < 1000; i++) {
					int start = r.nextInt(1000);
					RecursionIntro.printSparseTable(start, start + r.nextInt(1000) + 1);
				}
				System.setOut(out);
				byte[] digest = md5.digest(Arrays.toString(ba.toString().split("\\r?\\n")).getBytes());
				// System.out.println("MD5 sum of output seen: " + Arrays.toString(digest));
				byte[] good = { 43, 7, -12, 59, -7, -12, -5, -10, -108, 47, -10, 56, 60, 15, -58, -17 };
				if (!Arrays.equals(digest, good)) {
					System.out.println(
							"Your printSparseTable method doesn't always work (for starting values up to 1000 and ending values up to 1000 positions away). Please consult the definition carefully.");
					if (stGenScore == 5) {
						System.out.println(
								"It looks like you passed the above test cases! You may want to ask your instructor for help!");
					}
				} else {
					stGenScore += 15;
				}
			} catch (Throwable t) {
				t.printStackTrace();
			} finally {
				System.setOut(out);
			}
			int goodCount = 0;
			try {
				// Part 3a: lp2lt
				System.out.println("Testing lp2lt...");
				int badCount = 0;
				int mypow = 1;
				for (int i = 2; i <= 4098; i++) {
					if (i == 2 * mypow + 1) {
						mypow = i - 1;
					}
					int lpt = RecursionIntro.lp2lt(i);
					if (lpt != mypow) {
						System.out.println("lp2lt(" + i + ") should be " + mypow + " but it returned " + lpt);
						badCount++;
						if (badCount == 5) {
							System.out.println("Further errors with lp2lt suppressed. Please fix!");
							break;
						}
					} else if (goodCount < 5)
						goodCount++;
				}
				lp2ltScore = goodCount + 5 - badCount;
			} catch (Throwable t) {
				t.printStackTrace();
				lp2ltScore = goodCount;
			}
			try {
				// Part 3b: champion
				System.out.println("Testing champion...");
				int[][] champs = { { 0, 0 }, { 1, 0, 0, 1 }, { 2, 0, 2, 1, 1, 2, 0, 2 },
						{ 3, 0, 3, 1, 1, 2, 0, 2, 2, 0, 2, 1, 1, 3, 0, 3 },
						{ 4, 0, 4, 1, 4, 2, 4, 2, 4, 0, 4, 1, 4, 3, 4, 3, 3, 4, 3, 4, 1, 4, 0, 4, 2, 4, 2, 4, 1, 4, 0,
								4 },
						{ 5, 0, 5, 1, 5, 2, 5, 2, 5, 0, 5, 1, 5, 3, 5, 3, 3, 4, 3, 4, 1, 4, 0, 4, 2, 4, 2, 4, 1, 4, 0,
								4, 4, 0, 4, 1, 4, 2, 4, 2, 4, 0, 4, 1, 4, 3, 4, 3, 3, 5, 3, 5, 1, 5, 0, 5, 2, 5, 2, 5,
								1, 5, 0, 5 },
						{ 6, 0, 6, 1, 6, 2, 6, 2, 6, 0, 6, 1, 6, 3, 6, 3, 3, 4, 3, 4, 1, 4, 0, 4, 2, 4, 2, 4, 1, 4, 0,
								4, 6, 0, 6, 1, 6, 2, 6, 2, 6, 0, 6, 1, 6, 3, 6, 3, 3, 5, 3, 5, 1, 5, 0, 5, 2, 5, 2, 5,
								1, 5, 0, 5, 5, 0, 5, 1, 5, 2, 5, 2, 5, 0, 5, 1, 5, 3, 5, 3, 3, 6, 3, 6, 1, 6, 0, 6, 2,
								6, 2, 6, 1, 6, 0, 6, 4, 0, 4, 1, 4, 2, 4, 2, 4, 0, 4, 1, 4, 3, 4, 3, 3, 6, 3, 6, 1, 6,
								0, 6, 2, 6, 2, 6, 1, 6, 0, 6 },
						{ 7, 0, 7, 1, 7, 2, 7, 2, 7, 0, 7, 1, 7, 3, 7, 3, 3, 4, 3, 4, 1, 4, 0, 4, 2, 4, 2, 4, 1, 4, 0,
								4, 7, 0, 7, 1, 7, 2, 7, 2, 7, 0, 7, 1, 7, 3, 7, 3, 3, 5, 3, 5, 1, 5, 0, 5, 2, 5, 2, 5,
								1, 5, 0, 5, 5, 0, 5, 1, 5, 2, 5, 2, 5, 0, 5, 1, 5, 3, 5, 3, 3, 6, 3, 6, 1, 6, 0, 6, 2,
								6, 2, 6, 1, 6, 0, 6, 4, 0, 4, 1, 4, 2, 4, 2, 4, 0, 4, 1, 4, 3, 4, 3, 3, 6, 3, 6, 1, 6,
								0, 6, 2, 6, 2, 6, 1, 6, 0, 6, 6, 0, 6, 1, 6, 2, 6, 2, 6, 0, 6, 1, 6, 3, 6, 3, 3, 4, 3,
								4, 1, 4, 0, 4, 2, 4, 2, 4, 1, 4, 0, 4, 6, 0, 6, 1, 6, 2, 6, 2, 6, 0, 6, 1, 6, 3, 6, 3,
								3, 5, 3, 5, 1, 5, 0, 5, 2, 5, 2, 5, 1, 5, 0, 5, 5, 0, 5, 1, 5, 2, 5, 2, 5, 0, 5, 1, 5,
								3, 5, 3, 3, 7, 3, 7, 1, 7, 0, 7, 2, 7, 2, 7, 1, 7, 0, 7, 4, 0, 4, 1, 4, 2, 4, 2, 4, 0,
								4, 1, 4, 3, 4, 3, 3, 7, 3, 7, 1, 7, 0, 7, 2, 7, 2, 7, 1, 7, 0, 7 },
						{ 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8,
								4, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5,
								8, 5, 8, 5, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 6, 8, 6, 8, 6, 8, 6, 8,
								6, 8, 6, 8, 6, 8, 6, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 6, 8, 6, 8, 6,
								8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 4, 8,
								4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3,
								8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8,
								3, 8, 3, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0,
								8, 1, 8, 3, 8, 3, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 7, 8, 7, 8, 7, 8, 7,
								8, 7, 8, 7, 8, 7, 8, 7, 8, 3, 8, 3, 8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 7, 8, 7, 8,
								7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 3, 8, 3, 8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 5,
								8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 3, 8, 3, 8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8,
								0, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 3, 8, 3, 8, 1, 8, 0, 8, 2, 8, 2,
								8, 1, 8, 0, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 3, 8, 3, 8, 1, 8, 0, 8,
								2, 8, 2, 8, 1, 8, 0, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 3, 8, 3, 8, 1,
								8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 3, 8,
								3, 8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4,
								8, 3, 8, 3, 8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8 },
						{ 9, 0, 9, 1, 9, 2, 9, 2, 9, 0, 9, 1, 9, 3, 9, 3, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9,
								4, 9, 0, 9, 1, 9, 2, 9, 2, 9, 0, 9, 1, 9, 3, 9, 3, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5,
								9, 5, 9, 5, 9, 0, 9, 1, 9, 2, 9, 2, 9, 0, 9, 1, 9, 3, 9, 3, 9, 6, 9, 6, 9, 6, 9, 6, 9,
								6, 9, 6, 9, 6, 9, 6, 9, 0, 9, 1, 9, 2, 9, 2, 9, 0, 9, 1, 9, 3, 9, 3, 9, 6, 9, 6, 9, 6,
								9, 6, 9, 6, 9, 6, 9, 6, 9, 6, 9, 0, 9, 1, 9, 2, 9, 2, 9, 0, 9, 1, 9, 3, 9, 3, 9, 4, 9,
								4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 0, 9, 1, 9, 2, 9, 2, 9, 0, 9, 1, 9, 3, 9, 3,
								9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 0, 9, 1, 9, 2, 9, 2, 9, 0, 9, 1, 9,
								3, 9, 3, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 0, 9, 1, 9, 2, 9, 2, 9, 0,
								9, 1, 9, 3, 9, 3, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 7, 8, 7, 8, 7, 8, 7,
								8, 7, 8, 7, 8, 7, 8, 7, 8, 3, 8, 3, 8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 7, 8, 7, 8,
								7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 3, 8, 3, 8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 5,
								8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 3, 8, 3, 8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8,
								0, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 3, 8, 3, 8, 1, 8, 0, 8, 2, 8, 2,
								8, 1, 8, 0, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 3, 8, 3, 8, 1, 8, 0, 8,
								2, 8, 2, 8, 1, 8, 0, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 3, 8, 3, 8, 1,
								8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 3, 8,
								3, 8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4,
								8, 3, 8, 3, 8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1,
								8, 3, 8, 3, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 0, 8, 1, 8, 2, 8, 2, 8,
								0, 8, 1, 8, 3, 8, 3, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 0, 8, 1, 8, 2,
								8, 2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 0, 8,
								1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6,
								8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8,
								4, 8, 4, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5,
								8, 5, 8, 5, 8, 5, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 7, 8, 7, 8, 7, 8,
								7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 7, 8, 7,
								8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 3,
								9, 3, 9, 1, 9, 0, 9, 2, 9, 2, 9, 1, 9, 0, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9,
								7, 9, 3, 9, 3, 9, 1, 9, 0, 9, 2, 9, 2, 9, 1, 9, 0, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5,
								9, 5, 9, 5, 9, 3, 9, 3, 9, 1, 9, 0, 9, 2, 9, 2, 9, 1, 9, 0, 9, 4, 9, 4, 9, 4, 9, 4, 9,
								4, 9, 4, 9, 4, 9, 4, 9, 3, 9, 3, 9, 1, 9, 0, 9, 2, 9, 2, 9, 1, 9, 0, 9, 6, 9, 6, 9, 6,
								9, 6, 9, 6, 9, 6, 9, 6, 9, 6, 9, 3, 9, 3, 9, 1, 9, 0, 9, 2, 9, 2, 9, 1, 9, 0, 9, 6, 9,
								6, 9, 6, 9, 6, 9, 6, 9, 6, 9, 6, 9, 6, 9, 3, 9, 3, 9, 1, 9, 0, 9, 2, 9, 2, 9, 1, 9, 0,
								9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 3, 9, 3, 9, 1, 9, 0, 9, 2, 9, 2, 9,
								1, 9, 0, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 3, 9, 3, 9, 1, 9, 0, 9, 2,
								9, 2, 9, 1, 9, 0, 9 },
						{ 10, 0, 10, 1, 10, 2, 10, 2, 10, 0, 10, 1, 10, 3, 10, 3, 10, 4, 10, 4, 10, 4, 10, 4, 10, 4, 10,
								4, 10, 4, 10, 4, 10, 0, 10, 1, 10, 2, 10, 2, 10, 0, 10, 1, 10, 3, 10, 3, 10, 5, 10, 5,
								10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 0, 10, 1, 10, 2, 10, 2, 10, 0, 10, 1, 10,
								3, 10, 3, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 0, 10, 1, 10, 2,
								10, 2, 10, 0, 10, 1, 10, 3, 10, 3, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10,
								6, 10, 0, 10, 1, 10, 2, 10, 2, 10, 0, 10, 1, 10, 3, 10, 3, 10, 4, 10, 4, 10, 4, 10, 4,
								10, 4, 10, 4, 10, 4, 10, 4, 10, 0, 10, 1, 10, 2, 10, 2, 10, 0, 10, 1, 10, 3, 10, 3, 10,
								5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 0, 10, 1, 10, 2, 10, 2, 10, 0,
								10, 1, 10, 3, 10, 3, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 0, 10,
								1, 10, 2, 10, 2, 10, 0, 10, 1, 10, 3, 10, 3, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7,
								10, 7, 10, 7, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 3, 8, 3, 8, 1, 8, 0, 8, 2,
								8, 2, 8, 1, 8, 0, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 3, 8, 3, 8, 1, 8,
								0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 3, 8, 3,
								8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8,
								3, 8, 3, 8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6,
								8, 6, 8, 3, 8, 3, 8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8,
								6, 8, 6, 8, 6, 8, 3, 8, 3, 8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 5, 8, 5, 8, 5, 8, 5,
								8, 5, 8, 5, 8, 5, 8, 5, 8, 3, 8, 3, 8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 4, 8, 4, 8,
								4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 3, 8, 3, 8, 1, 8, 0, 8, 2, 8, 2, 8, 1, 8, 0, 8, 10,
								0, 10, 1, 10, 2, 10, 2, 10, 0, 10, 1, 10, 3, 10, 3, 10, 4, 10, 4, 10, 4, 10, 4, 10, 4,
								10, 4, 10, 4, 10, 4, 10, 0, 10, 1, 10, 2, 10, 2, 10, 0, 10, 1, 10, 3, 10, 3, 10, 5, 10,
								5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 0, 10, 1, 10, 2, 10, 2, 10, 0, 10, 1,
								10, 3, 10, 3, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 0, 10, 1, 10,
								2, 10, 2, 10, 0, 10, 1, 10, 3, 10, 3, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6,
								10, 6, 10, 0, 10, 1, 10, 2, 10, 2, 10, 0, 10, 1, 10, 3, 10, 3, 10, 4, 10, 4, 10, 4, 10,
								4, 10, 4, 10, 4, 10, 4, 10, 4, 10, 0, 10, 1, 10, 2, 10, 2, 10, 0, 10, 1, 10, 3, 10, 3,
								10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 0, 10, 1, 10, 2, 10, 2, 10,
								0, 10, 1, 10, 3, 10, 3, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 0,
								10, 1, 10, 2, 10, 2, 10, 0, 10, 1, 10, 3, 10, 3, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10,
								7, 10, 7, 10, 7, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 3, 9, 3, 9, 1, 9, 0, 9,
								2, 9, 2, 9, 1, 9, 0, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 3, 9, 3, 9, 1,
								9, 0, 9, 2, 9, 2, 9, 1, 9, 0, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 3, 9,
								3, 9, 1, 9, 0, 9, 2, 9, 2, 9, 1, 9, 0, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4,
								9, 3, 9, 3, 9, 1, 9, 0, 9, 2, 9, 2, 9, 1, 9, 0, 9, 6, 9, 6, 9, 6, 9, 6, 9, 6, 9, 6, 9,
								6, 9, 6, 9, 3, 9, 3, 9, 1, 9, 0, 9, 2, 9, 2, 9, 1, 9, 0, 9, 6, 9, 6, 9, 6, 9, 6, 9, 6,
								9, 6, 9, 6, 9, 6, 9, 3, 9, 3, 9, 1, 9, 0, 9, 2, 9, 2, 9, 1, 9, 0, 9, 5, 9, 5, 9, 5, 9,
								5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 3, 9, 3, 9, 1, 9, 0, 9, 2, 9, 2, 9, 1, 9, 0, 9, 4, 9, 4,
								9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 3, 9, 3, 9, 1, 9, 0, 9, 2, 9, 2, 9, 1, 9, 0, 9,
								9, 0, 9, 1, 9, 2, 9, 2, 9, 0, 9, 1, 9, 3, 9, 3, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9,
								4, 9, 4, 9, 0, 9, 1, 9, 2, 9, 2, 9, 0, 9, 1, 9, 3, 9, 3, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5,
								9, 5, 9, 5, 9, 5, 9, 0, 9, 1, 9, 2, 9, 2, 9, 0, 9, 1, 9, 3, 9, 3, 9, 6, 9, 6, 9, 6, 9,
								6, 9, 6, 9, 6, 9, 6, 9, 6, 9, 0, 9, 1, 9, 2, 9, 2, 9, 0, 9, 1, 9, 3, 9, 3, 9, 6, 9, 6,
								9, 6, 9, 6, 9, 6, 9, 6, 9, 6, 9, 6, 9, 0, 9, 1, 9, 2, 9, 2, 9, 0, 9, 1, 9, 3, 9, 3, 9,
								4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 4, 9, 0, 9, 1, 9, 2, 9, 2, 9, 0, 9, 1, 9, 3,
								9, 3, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 0, 9, 1, 9, 2, 9, 2, 9, 0, 9,
								1, 9, 3, 9, 3, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 0, 9, 1, 9, 2, 9, 2,
								9, 0, 9, 1, 9, 3, 9, 3, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 9, 7, 7, 10, 7, 10, 7,
								10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 3, 10, 3, 10, 1, 10, 0, 10, 2, 10, 2, 10, 1, 10,
								0, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 3, 10, 3, 10, 1, 10, 0,
								10, 2, 10, 2, 10, 1, 10, 0, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10,
								3, 10, 3, 10, 1, 10, 0, 10, 2, 10, 2, 10, 1, 10, 0, 10, 4, 10, 4, 10, 4, 10, 4, 10, 4,
								10, 4, 10, 4, 10, 4, 10, 3, 10, 3, 10, 1, 10, 0, 10, 2, 10, 2, 10, 1, 10, 0, 10, 6, 10,
								6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 3, 10, 3, 10, 1, 10, 0, 10, 2, 10, 2,
								10, 1, 10, 0, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 3, 10, 3, 10,
								1, 10, 0, 10, 2, 10, 2, 10, 1, 10, 0, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5,
								10, 5, 10, 3, 10, 3, 10, 1, 10, 0, 10, 2, 10, 2, 10, 1, 10, 0, 10, 4, 10, 4, 10, 4, 10,
								4, 10, 4, 10, 4, 10, 4, 10, 4, 10, 3, 10, 3, 10, 1, 10, 0, 10, 2, 10, 2, 10, 1, 10, 0,
								10, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4,
								8, 4, 8, 4, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 5, 8, 5, 8, 5, 8, 5, 8,
								5, 8, 5, 8, 5, 8, 5, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 6, 8, 6, 8, 6,
								8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 6, 8,
								6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 6, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8, 3, 8, 3,
								8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 4, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0, 8, 1, 8,
								3, 8, 3, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 0, 8, 1, 8, 2, 8, 2, 8, 0,
								8, 1, 8, 3, 8, 3, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 0, 8, 1, 8, 2, 8,
								2, 8, 0, 8, 1, 8, 3, 8, 3, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 7, 10, 7, 10,
								7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 3, 10, 3, 10, 1, 10, 0, 10, 2, 10, 2, 10, 1,
								10, 0, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 7, 10, 3, 10, 3, 10, 1, 10,
								0, 10, 2, 10, 2, 10, 1, 10, 0, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5,
								10, 3, 10, 3, 10, 1, 10, 0, 10, 2, 10, 2, 10, 1, 10, 0, 10, 4, 10, 4, 10, 4, 10, 4, 10,
								4, 10, 4, 10, 4, 10, 4, 10, 3, 10, 3, 10, 1, 10, 0, 10, 2, 10, 2, 10, 1, 10, 0, 10, 6,
								10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 3, 10, 3, 10, 1, 10, 0, 10, 2, 10,
								2, 10, 1, 10, 0, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 6, 10, 3, 10, 3,
								10, 1, 10, 0, 10, 2, 10, 2, 10, 1, 10, 0, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10, 5, 10,
								5, 10, 5, 10, 3, 10, 3, 10, 1, 10, 0, 10, 2, 10, 2, 10, 1, 10, 0, 10, 4, 10, 4, 10, 4,
								10, 4, 10, 4, 10, 4, 10, 4, 10, 4, 10, 3, 10, 3, 10, 1, 10, 0, 10, 2, 10, 2, 10, 1, 10,
								0, 10 } };
				for (int i = 1; i <= 11; i++) {
					boolean[] b = new boolean[i];
					boolean[] bz = b.clone();
					boolean havePrintedError = false;
					int counter = 0;
					int scorecounter = 0;
					do {
						boolean[] bc = b.clone();
						int champ = RecursionIntro.champion(b);
						if (!Arrays.equals(b, bc)) {
							if (!havePrintedError) {
								System.out.println("RecursionIntro.champion(" + Arrays.toString(bc)
										+ ") modified its input array!");
								havePrintedError = true;
							}
						} else if (champ != champs[i - 1][counter]) {
							if (!havePrintedError) {
								System.out.println(
										"RecursionIntro.champion(" + Arrays.toString(bc) + ") should have returned "
												+ champs[i - 1][counter] + " but returned " + champ);
								havePrintedError = true;
							}
						} else
							scorecounter++;
						counter++;
						for (int j = 0; j < b.length; j++) {
							b[j] = !b[j];
							if (b[j])
								break;
						}
					} while (!Arrays.equals(b, bz));
					championScore += scorecounter / counter;
				}
				long champsSum = 158400921007L;
				long championSum = 0;
				Random rand = new Random(32478971);
				for (long x = 1; x < 10000; x++) {
					boolean[] r = new boolean[rand.nextInt(10000) + 1];
					for (int i = 0; i < r.length; i++)
						r[i] = rand.nextBoolean();
					championSum += x * RecursionIntro.champion(r);
				}
				if (championSum == champsSum) {
					championScore += 9;
				} else {
					System.out.println("For pseudorandom check of large champion games, got " + championSum
							+ " expected " + champsSum);
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		} finally {
			System.out.println("There may be bugs in this test! Please check the announcements for updates.");
			System.out.println("eduodd:              " + eduoddScore + " / 30");
			System.out.println("fibby:               " + fibbyScore + " / 20");
			System.out.println("printSparseTable:    " + stGenScore + " / 20");
			System.out.println("lp2lt:               " + lp2ltScore + " / 10");
			System.out.println("champion:            " + championScore + " / 20");
			score = eduoddScore + fibbyScore + stGenScore + lp2ltScore + championScore;
			System.out.println("Tentative score:     " + score + " / 100");
			System.out.println("*** Please note that grading is subject to the academic dishonesty policy! ***");
		}
	}

}
