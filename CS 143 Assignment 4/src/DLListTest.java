import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Test of CS 143 Assignment 4 by Martin Hock (Version of 5:00 PM 10/28/2020) You
 * may only use this code as a student of Martin Hock, CS 143 Fall 2020.
 */
public class DLListTest {

	public static String ordinal(int x) {
		x++;
		int one = x % 10;
		if (one == 1)
			return x + "st";
		if (one == 2)
			return x + "nd";
		if (one == 3)
			return x + "rd";
		return x + "th";
	}

	public static int verifyCalls = 0;
	public static int verifySize = 0;
	public static int verifyRev = 0;
	public static long verifyTime = 0;

	public static <T> boolean verify(DLList<T> list, int n) {
		verifyCalls++;
		Iterator<T> f = list.iterator();
		int count = 0;
		int hash = 0;
		int hash2 = 0;
		int hash3a = 1;
		int hash3b = 0;
		int hash3c = 0;
		int hash3d = 1;
		int bcount = 0;
		int bhash = 0;
		int bhash2 = 0;
		int bhash3a = 1;
		int bhash3b = 0;
		int bhash3c = 0;
		int bhash3d = 1;
		while (f.hasNext()) {
			int hc = f.next().hashCode();
			hash += hc;
			hash2 = (hash2 * 65537) + hc;
			int hca = (hc & 0xFF) * 1103515245 ^ 0x55555555;
			int hcb = ((hc >> 8) & 0xFF) * 1103515245 ^ 0x55555555;
			int hcc = ((hc >> 16) & 0xFF) * 1103515245 ^ 0x55555555;
			int hcd = ((hc >> 24) & 0xFF) * 1103515245 ^ 0x55555555;
			int a = hash3a * hca + hash3b * hcc;
			int b = hash3a * hcb + hash3b * hcd;
			int c = hash3c * hca + hash3d * hcc;
			int d = hash3c * hcb + hash3d * hcd;
			hash3a = a;
			hash3b = b;
			hash3c = c;
			hash3d = d;
			count++;
			if (count > 10000000) {
				System.out.println("Your list appears to contain a loop which causes it to iterate forward forever!");
				T first = f.next();
				String out = first.toString();
				T next = f.next();
				while (!next.equals(first)) {
					out += ", " + next;
					next = f.next();
				}
				System.out.println("Loop value(s): " + out);
				return false;
			}
		}
		Iterator<T> b = list.descendingIterator();
		while (b.hasNext()) {
			int hc = b.next().hashCode();
			bhash += hc;
			bhash2 = (bhash2 * 65537) + hc;

			int hca = (hc & 0xFF) * 1103515245 ^ 0x55555555;
			int hcb = ((hc >> 8) & 0xFF) * 1103515245 ^ 0x55555555;
			int hcc = ((hc >> 16) & 0xFF) * 1103515245 ^ 0x55555555;
			int hcd = ((hc >> 24) & 0xFF) * 1103515245 ^ 0x55555555;
			int ba = hca * bhash3a + hcb * bhash3c;
			int bb = hca * bhash3b + hcb * bhash3d;
			int bc = hcc * bhash3a + hcd * bhash3c;
			int bd = hcc * bhash3b + hcd * bhash3d;
			bhash3a = ba;
			bhash3b = bb;
			bhash3c = bc;
			bhash3d = bd;

			bcount++;
			if (bcount > 10000000) {
				System.out.println("Your list appears to contain a loop which causes it to iterate backward forever!");
				T first = f.next();
				String out = first.toString();
				T next = f.next();
				while (!next.equals(first)) {
					out += ", " + next;
					next = f.next();
				}
				System.out.println("Loop value(s): " + out);
				return false;
			}
		}
		if (count != bcount) {
			System.out.println(
					"Forward iteration counted " + count + " elements but backward iterator counted " + bcount);
		} else if (hash != bhash) {
			System.out.println("Forward iteration saw the same count but a different set of element values!");
		} else if (hash2 == bhash2 && count > 1) {
			System.out.println("Backard iteration saw the same elements in the same order as forward!");
		} else if (hash3a != bhash3a || hash3b != bhash3b || hash3c != bhash3c || hash3d != bhash3d) {
			System.out.println("Backward iterator did not see the reverse of forward's elements!");
		} else {
			verifyRev++;
			long start = System.nanoTime();
			int size = list.size();
			long end = System.nanoTime();
			verifyTime += (end - start);
			if (n != count || count != size) {
				System.out.println("There should be " + n + " elements, forward iteration counted " + count
						+ " elements, size method says there are " + size);
			} else
				verifySize++;
			return true;
		}
		return false;
	}

	// We must use the deprecated "stop" method because there is no alternative to
	// stop a runaway thread.
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		int iterScore = 0, sizeScore = 0, getScore = 0, revScore = 0, addScore = 0;
		try {
			System.out.println("First, making sure add, get, forward iterator work.");
			for (int z = 0; z < 2; z++) {
				try {
					System.out.println(
							"Adding the even numbers 0, 2, 4, 6, 8 to a new list. (0 is the first thing, 2 is second, 4 is third...)");
					DLList<Integer> a = new DLList<>();
					if (z == 1)
						verify(a, 0);
					for (int i = 0; i < 10; i += 2) {
						a.add(i);
						if (z == 1)
							verify(a, i / 2 + 1);
					}
					int j = 0;
					for (Integer x : a) {
						if (x == null || x != j) {
							throw new RuntimeException(
									"Basic add or iterate functionality is broken. Should have retrieved " + j
											+ " as the " + ordinal(j / 2) + " element but got " + x);
						}
						Integer y = a.get(j / 2);
						if (y == null || x.intValue() != y) {
							throw new RuntimeException("Basic get functionality is broken. get(" + j / 2
									+ ") should have retrieved " + x + " but got " + y);
						}
						j += 2;
					}
					if (j != 10) {
						throw new RuntimeException("The iterator stopped early.");
					}
					System.out.println("Now calling remove(0) to remove the first thing.");
					a.remove(0);
					if (z == 1)
						verify(a, 4);
					j = 2;
					for (Integer x : a) {
						if (x == null || x != j) {
							throw new RuntimeException(
									"Basic add or iterate functionality is broken. Should have retrieved " + j
											+ " as the " + ordinal(j / 2 - 1) + " element but got " + x);
						}
						Integer y = a.get(j / 2 - 1);
						if (y == null || x.intValue() != y) {
							throw new RuntimeException("Basic get functionality is broken. get(" + (j / 2 - 1)
									+ ") should have retrieved " + x + " but got " + y);
						}
						j += 2;
					}
					if (j != 10) {
						throw new RuntimeException("The iterator stopped early.");
					}
					System.out.println("Now calling remove(3) to remove the last thing.");
					a.remove(3);
					if (z == 1)
						verify(a, 3);
					j = 2;
					for (Integer x : a) {
						if (x == null || x != j) {
							throw new RuntimeException(
									"Basic add or iterate functionality is broken. Should have retrieved " + j
											+ " as the " + ordinal(j / 2 - 1) + " element but got " + x);
						}
						Integer y = a.get(j / 2 - 1);
						if (y == null || x.intValue() != y) {
							throw new RuntimeException("Basic get functionality is broken. get(" + (j / 2 - 1)
									+ ") should have retrieved " + x + " but got " + y);
						}
						j += 2;
					}
					if (j != 8) {
						throw new RuntimeException("The iterator stopped early.");
					}
					System.out.println("Now calling remove(1) to remove the middle thing.");
					a.remove(1);
					if (z == 1)
						verify(a, 2);
					j = 2;
					for (Integer x : a) {
						if (x == null || x != j) {
							throw new RuntimeException(
									"Basic add or iterate functionality is broken. Should have retrieved " + j
											+ " as the " + ordinal((j - 2) / 4) + " element but got " + x);
						}
						Integer y = a.get((j - 2) / 4);
						if (y == null || x.intValue() != y) {
							throw new RuntimeException("Basic get functionality is broken. get(" + ((j - 2) / 4)
									+ ") should have retrieved " + x + " but got " + y);
						}
						j += 4;
					}
					System.out.println(
							"Now, running through the scenario again from the top but making sure reverse iterator is consistent with forward iterator and counting sizes.");
				} catch (Exception e) {
					System.out.println("Problem with the very first list.");
					if (e instanceof NullPointerException) {
						System.out.println("*** It looks like you are returning null in descendingIterator() ***");
					}
					e.printStackTrace();
				}
			}
			System.out.println("Now testing add with index.");

			System.out.println("Creating an empty list, adding a normally then adding b to index 0.");
			DLList<String> slist = new DLList<>();
			boolean slistOK = true;
			try {
				slist.add("a");
				if (!slist.add(0, "b")) {
					System.out.println("add should have returned true.");
				} else
					addScore++;
				String get0 = slist.get(0);
				if (!get0.equals("b")) {
					System.out.println("Instead of b, element at beginning is " + get0);
				} else {
					addScore++;
				}
				if (compare(slist, new String[] { "b", "a" }, true)) {
					addScore++;
					System.out.println("Now adding c to index 1.");
					if (!slist.add(1, "c")) {
						System.out.println("add should have returned true.");
					} else {
						if (compare(slist, new String[] { "b", "c", "a" }, true)) {
							addScore++;
							System.out.println("Now adding d to index 3, which is not an existing index.");
							if (slist.add(3, "d")) {
								System.out.println("add should have returned false since 3 was not a valid index.");
							} else {
								if (compare(slist, new String[] { "b", "c", "a" }, true)) {
									addScore++;
									System.out.println("Now adding d to index 2.");
									slist.add(2, "d");
									if (compare(slist, new String[] { "b", "c", "d", "a" }, true))
										addScore++;
								}
							}
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Your code had a problem with the b-c-d-a list.");
				e.printStackTrace();
				slistOK = false;
			}

			DLList<Integer> ilist = new DLList<>();
			boolean ilistOK = true;
			try {
				System.out.println("Creating a list by adding the numbers 50 through 99.");
				for (int i = 50; i < 100; i++) {
					ilist.add(i);
				}
				verify(ilist, 50);
				System.out.println("Trying to get them all...");
				for (int i = 0; i < 50; i++) {
					int ig = ilist.get(i);
					if (ig != i + 50) {
						System.out.println(
								"get(" + i + ") should have returned " + (i + 50) + " but instead returned " + ig);
					}
				}
				System.out.println(
						"Now adding at index 0, numbers 25 through 1 (each one added to the beginning will end up in order)");
				int vscore = 0;
				for (int i = 25; i >= 1; i--) {
					ilist.add(0, i);
					if (verify(ilist, 50 + 26 - i))
						vscore++;
				}
				System.out.println("Trying to get them all...");
				for (int i = 0; i < 25; i++) {
					int ig = ilist.get(i);
					if (ig != i + 1) {
						System.out.println(
								"get(" + i + ") should have returned " + (i + 1) + " but instead returned " + ig);
					} else
						vscore++;
				}
				for (int i = 25; i < 75; i++) {
					int ig = ilist.get(i);
					if (ig != i + 25) {
						System.out.println(
								"get(" + i + ") should have returned " + (i + 25) + " but instead returned " + ig);
					} else
						vscore++;
				}
				System.out.println("Now adding 49 through 26 at index 25.");
				for (int i = 49; i >= 26; i--) {
					ilist.add(25, i);
					if (verify(ilist, 75 + 50 - i))
						vscore++;
				}
				System.out.println("Trying to get them all...");
				for (int i = 0; i < 99; i++) {
					int ig = ilist.get(i);
					if (ig != i + 1) {
						System.out.println(
								"get(" + i + ") should have returned " + (i + 1) + " but instead returned " + ig);
					} else
						vscore++;
				}
				if (vscore == 223)
					addScore += 9;
				else
					addScore += vscore / 15;
			} catch (Exception e) {
				System.out.println("Your code caused an exception with the number list.");
				e.printStackTrace();
				ilistOK = false;
			}

			System.out.println("Testing add at different indexes in a medium sized list.");
			try {
				int n2 = 100;
				DLList<Integer> list = new DLList<>();
				for (int i = 0; i <= n2; i++)
					list.add(i);
				for (int i = n2; i >= 0; i--) {
					if (!list.add(i, -i - 1)) {
						System.out.println("Complained when adding " + (-i - 1) + " to index " + i);
						break;
					}
					int saw = list.get(i);
					if (saw != -i - 1) {
						System.out.println(
								"Insert at index " + i + " did not place " + (-i - 1) + " there - instead, saw " + saw);
						break;
					}
					if (i == 0) {
						addScore += 10;
					}
				}
			} catch (Exception e) {
				System.out.println("Your code caused an exception when adding in the medium sized list.");
				e.printStackTrace();
			}

			System.out.println("Now testing reverse.");
			boolean reverseOK = true;
			try {
				DLList<Integer> ilist2 = new DLList<>();
				for (int i = 0; i < 8; i++) {
					Integer[] a = new Integer[i], ra = new Integer[i];
					for (int j = 0; j < a.length; j++)
						a[j] = ra[a.length - j - 1] = 2 * j + 3;
					System.out.println("Reversing a list of length " + i + ".");
					ilist2.reverse();
					if (compare(ilist2, ra, true))
						revScore++;
					else
						break;
					System.out.println("Re-reversing it.");
					ilist2.reverse();
					if (compare(ilist2, a, true))
						revScore++;
					else
						break;
					ilist2.add(2 * i + 3);
				}
			} catch (Exception e) {
				System.out.println("Your code caused an exception with reverse for a simple short list.");
				e.printStackTrace();
				reverseOK = false;
			}

			if (slistOK && reverseOK) {
				try {
					System.out.println("Reversing the b-c-d-a list.");
					slist.reverse();
					if (compare(slist, new String[] { "a", "d", "c", "b" }, true))
						revScore++;
				} catch (Exception e) {
					System.out.println("Your code caused an exception with reversing the b-c-d-a list.");
					e.printStackTrace();
					slistOK = false;
					reverseOK = false;
				}
			} else {
				System.out.println("Skipping reversing the b-c-d-a list due to earlier problems in your code.");
			}

			if (ilistOK && reverseOK) {
				try {
					System.out.println("Reversing the 1-99 list.");
					ilist.reverse();
					for (int i = 0; i < 99; i++) {
						int ig = ilist.get(i);
						if (ig != 99 - i) {
							System.out.println(
									"get(" + i + ") should have returned " + (99 - i) + " but instead returned " + ig);
							break;
						}
						if (i == 98)
							revScore += 2;
					}
					if (verify(ilist, 99))
						revScore++;
					for (int i = 97; i >= 0; i--) {
						int ir = ilist.remove(i);
						if (ir != 99 - i) {
							System.out.println("remove(" + i + ") should have returned " + (99 - i)
									+ " but instead returned " + ir);
							System.out.println("There may be a problem with backwards remove!");
							break;
						}
						if (!verify(ilist, i + 1))
							break;
					}
					if (ilist.remove(0) != 1) {
						System.out.println("Removing final element didn't get 1");
					}
					verify(ilist, 0);
				} catch (Exception e) {
					System.out.println("Your code caused an exception with reversing and removing from the 1-99 list.");
					e.printStackTrace();
					ilistOK = false;
					reverseOK = false;
				}
			} else {
				System.out.println(
						"Skipping reversing and removing elements from the 1-99 list due to earlier problems in your code.");
			}

			System.out.println("Testing backwards get on a large list...");
			try {
				DLList<Integer> ilist3 = new DLList<>();
				int m = (int) (Math.random() * 10) + 1;
				int N = 1000000;
				for (int i = 0; i < N; i++) {
					ilist3.add(m * i + 1);
				}
				verify(ilist3, N);
				long start = System.nanoTime();
				boolean allGood = false;
				for (int i = N - 1; i >= N - 100; i--) {
					int ig = ilist3.get(i);
					if (ig != m * i + 1) {
						System.out.println("Problem with backwards get! Calling get(" + i + ") (which is " + (N - i - 1)
								+ " spots from the end) got the wrong value " + ig + " instead of " + (m * i + 1));
						break;
					}
					if (i == N - 100)
						allGood = true;
				}
				long end = System.nanoTime();
				if (allGood) {
					getScore = 1;
					if (end - start < 10_000_000) {
						System.out.println("You took " + (end - start) / 1_000_000.0 + " milliseconds! :) ");
						getScore += 9;
					} else {
						System.out.println("You took " + (end - start) / 1_000_000.0
								+ " milliseconds! :( Try to get less than 10.");
					}
				}
				System.out.println(
						"Reversing the large list. Your code will be terminated after 10-20 seconds (the algorithm should take less than a second)...");
				Thread t;
				final AtomicLong startR = new AtomicLong(), endR = new AtomicLong();
				synchronized (ilist3) {
					t = new Thread(new Runnable() {
						public void run() {
							startR.set(System.nanoTime());
							ilist3.reverse();
							endR.set(System.nanoTime());
						}
					});
					t.start();
					t.join(20000);
				}
				if (t.isAlive()) {
					System.out.println(
							"Your code is still running after 20 seconds, so it must be terminated and the remaining tests will not be run.");
					t.stop();
				} else {
					System.out.println("Reverse call returned after " + (endR.get() - startR.get()) / 1000000.0
							+ " milliseconds, now verifying.");
					int gen = m * (N - 1) + 1;
					int i = 0;
					for (int x : ilist3) {
						if (x != gen) {
							System.out.println("The " + ordinal(i) + " thing from the iterator should have been " + gen
									+ " but it was " + x);
							break;
						}
						gen -= m;
						i++;
						if (i == N && verify(ilist3, N))
							revScore += 5;
					}
				}
			} catch (Exception e) {
				System.out.println("Your code caused an exception with the large list.");
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("Your code caused an exception and is bailing out.");
			e.printStackTrace();
		} finally {
			// System.out.println(verifyCalls+" "+verifySize+" "+verifyRev+" "+verifyTime);
			if (verifyRev > 182 || verifyRev > verifyCalls) {
				System.out.println("Somehow, too many calls to verify are being made for reversal... " + verifyRev);
			} else if (verifyRev == verifyCalls && verifyCalls > 0) {
				iterScore = 20;
			} else if (verifyCalls > 0) {
				iterScore = verifyRev * 20 / verifyCalls;
			}
			if (verifyRev < 182) {
				System.out.println(
						"Notice there were some errors in your code. It's possible that after fixing those errors, problems with your descendingIterator() could be revealed and your reverse iterator score could decrease.");
			}
			if (verifySize > 182 || verifySize > verifyCalls) {
				System.out.println("Somehow, too many calls to verify are being made for size... " + verifySize);
			} else if (verifySize == verifyCalls && verifyCalls > 0) {
				sizeScore = 10;
			} else if (verifyCalls > 0) {
				sizeScore = verifySize * 10 / verifyCalls;
			}
			if (verifySize < 182 && verifyRev == 182) {
				System.out.println(
						"Notice there were some errors in your code. It's possible that after fixing those errors, problems with your size() method could be revealed and your size method score could decrease.");
			} else if (verifySize < 182) {
				System.out.println(
						"Additionally, problems with size() method could be revealed and your size method score could decrease.");
			}

			if (verifyTime < 1_000_000 && sizeScore >= 5) {
				sizeScore += 10;
			} else {
				if (sizeScore < 5) {
					System.out.println("Too many errors in size method for speed bonus.");
				} else {
					System.out.println("Size method took too long. Should take less than 1 microsecond but took "
							+ (verifyTime / 1_000_000.0));
				}
			}
			System.out.println("Reverse iterator: " + iterScore + " / 20");
			System.out.println("Size method: " + sizeScore + " / 20");
			System.out.println("Get method speedup: " + getScore + " / 10");
			System.out.println("Reverse method: " + revScore + " / 25");
			System.out.println("Add with index method: " + addScore + " / 25");
			System.out
					.println("Tentative score: " + (iterScore + sizeScore + getScore + revScore + addScore) + " / 100");
			System.out.println("Extra credit not tested!");
		}

	}

	private static <T> boolean compare(DLList<T> slist, T[] things, boolean get) {
		int i = 0;
		boolean good = true;
		for (T t : slist) {
			if (i >= things.length) {
				System.out.println("There should be " + i + " things iterated but there is an extra one!");
				return false;
			}
			if (!things[i].equals(t)) {
				System.out.println(
						"The " + ordinal(i) + " thing iterated was " + t + " but should have been " + things[i]);
				good = false;
			}
			if (get) {
				T x = slist.get(i);
				if (!things[i].equals(x)) {
					System.out.println("Calling get(" + i + ") produced " + x + " but should have been " + things[i]);
					if (good == true) {
						System.out.println(
								"The iterator got the right thing - maybe there is a problem with backwards get?");
					}
					good = false;
				}
			}
			if (!good)
				return false;
			i++;
		}
		if (i < things.length) {
			System.out.println("There should have been " + things.length + " things iterated but I only saw " + i);
			return false;
		}
		return verify(slist, things.length);
	}

}
