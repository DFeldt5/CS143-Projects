import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Test program for CS 143 Assignment 5 (Version of 11/13/2020 12:40 AM)
 * 
 * You may only use this program as a student of Martin Hock or David Anderson, CS 143 Winter
 * 2021. You may not give this program to any other people or companies (aside
 * from private storage).
 * 
 * Directions: Run this program from the same project source folder as your
 * WeatherData.java file.
 */
public class WeatherDataTest {

	private static final int f(int i) {
		i = (-2040204795 * i - 821977723) ^ 1903171154;
		i = (-2040204795 * i - 821977723) ^ 1903171154;
		i = (-2040204795 * i - 821977723) ^ 1903171154;
		return i;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		int highPoints = 0, lowPoints = 0, snowfallPoints = 0, precipPoints = 0, lmchPoints = 0, hihiloPoints = 0;
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

			byte[] hash = { -57, -90, 38, -70, 94, 112, 4, 70, 124, -115, -76, -14, -14, -65, -94, -104, -33, 113, -24,
					-55, 123, -105, -114, -8, -18, -66, -124, 63, 81, -76, -23, 127 };
			DigestInputStream stream = new DigestInputStream(Files.newInputStream(Paths.get("kent100.csv")), sha256);
			Scanner s = new Scanner(stream);
			long startC = System.currentTimeMillis();
			WeatherData wd = new WeatherData(s);
			long endC = System.currentTimeMillis();
			if (endC - startC > 60001) {
				System.out.println("Your constructor took too long (" + (endC - startC) / 1000.0 + " seconds)!");
				return;
			}
			if (s.hasNext()) {
				System.out.println("*** WARNING *** Your WeatherData constructor is not reading all of the lines!");
				while (s.hasNextLine()) {
					s.nextLine(); // hint, this reads all the lines!
				}
			}

			stream.close();
			byte[] digest = sha256.digest();
			if (!Arrays.equals(digest, hash)) {
				System.out.println("*** WARNING *** Your kent100.csv file does not have the expected contents!");
			}

			boolean[] lows = { false, true, true, true, true, false, true, false, true, true, true, true, false, true,
					false, true, true, false, true, false, true, false, true, true, true, true, false, true, false,
					true, false, true, true, true, true, false, true, false, true, true, false, true, false, true, true,
					true, true, true, true, false, true, true, true, true, true, true, false, true, false, true, true,
					false, true, false, true, false, true, true, false, true, false, true, false, true, true, false,
					true, false, true, false, true, true, false, true, false, true, true, true, true, false, true,
					false, true, false, true, true, false, true, false, true, false, true, true, true, true, false,
					true, false, true, true, false, true, false, true, false, true, true, true, true, false, true,
					false, true, true, false, true, false, true };
			{
				System.out.println("Testing highTemp() and lowTemp()...");
				boolean[] his = { false, true, true, true, true, false, true, false, true, false, true, true, false,
						true, false, true, false, true, true, false, true, false, true, false, true, true, false, true,
						false, true, false, true, true, false, true, false, true, false, true, true, true, true, false,
						true, false, true, true, false, true, false, true, false, true, true, false, true, false, true,
						false, true, true, true, true, false, true, false, true, true, true, true, false, true, false,
						true, true, true, true, false, true, false, true, true, true, true, false, true, false, true,
						true, true, true, false, true, false, true, true, true, true, false, true, false, true, true,
						true, true, false, true, false, true, true, true, true, false, true, false, true, true, true,
						true, false, true, false, true, false, true, true, false, true };

				int i = 0;
				int j = 0;
				int hiErrors = 0, lowErrors = 0;
				long totalHiTime = 0, totalLowTime = 0;
				do {
					int hi = i, lo = i - 16;
					try {
						long start = System.nanoTime();
						boolean hit = wd.highTemp(hi);
						long end = System.nanoTime();
						totalHiTime += end - start;
						if (end - start > 1_000_000_001) {
							System.out.println("highTemp(" + hi
									+ ") executed too slowly (over 1 second)! No credit will be awarded.");
							hiErrors = 10;
						}
						if (hit != his[j]) {
							if (hiErrors < 10) {
								System.out.println(
										"highTemp(" + hi + ") returned " + hit + " but should have returned " + his[j]);
							}
							hiErrors++;
							if (hiErrors == 10) {
								System.out.println("Further highTemp errors suppressed.");
							}
						}
					} catch (Error e) {
						if (hiErrors < 10) {
							System.out.println("highTemp(" + hi + ") should have returned " + his[j]
									+ " but instead caused the following error:");
							e.printStackTrace(System.out);
						}
						hiErrors++;
						if (hiErrors == 10) {
							System.out.println("Further highTemp errors suppressed.");
						}
					}
					try {
						long start = System.nanoTime();
						boolean lowt = wd.lowTemp(lo);
						long end = System.nanoTime();
						totalLowTime += end - start;
						if (end - start > 1_000_000_001) {
							System.out.println("lowTemp(" + lo
									+ ") executed too slowly (over 1 second)! No credit will be awarded.");
							lowErrors = 10;
						}
						if (lowt != lows[j]) {
							if (lowErrors < 10) {
								System.out.println("lowTemp(" + lo + ") returned " + lowt + " but should have returned "
										+ lows[j]);
							}
							lowErrors++;
							if (lowErrors == 10) {
								System.out.println("Further lowTemp errors suppressed.");
							}
						}
					} catch (Error e) {
						if (lowErrors < 10) {
							System.out.println("lowTemp(" + lo + ") should have returned " + lows[j]
									+ " but instead caused the following error: ");
							e.printStackTrace(System.out);
						}
						lowErrors++;
						if (lowErrors == 10) {
							System.out.println("Further lowTemp errors suppressed.");
						}
					}
					i = (i + 73) & 127;
					j++;
				} while (i != 0);
				if (i == 0 && j > 0) {
					highPoints = Math.max(10 - hiErrors, 0);
					if (totalHiTime > 1_000_001) {
						System.out.println(
								"10% penalty for slow highTemp() method (over 1 microsecond total time to perform 128 tests).");
						highPoints--;
					}
					lowPoints = Math.max(10 - lowErrors, 0);
					if (totalLowTime > 1_000_001) {
						System.out.println(
								"10% penalty for slow lowTemp() method (over 1 microsecond total time to perform 128 tests)");
						lowPoints--;
					}
				} else {
					System.out.println("No highTemp or lowTemp points earned due to above errors.");
				}
			}
			{
				System.out.println("Testing totalSnowfallForYear()...");
				int i = 0;
				int j = 0;
				int[] snowfalls = { -1752268662, 917322763, 1340974736, -466659731, -1270175165, 163744315, -1193704175,
						-575099871, -833516786, -607796849, -1407963941, 988084603, 2136831692, -1312788925,
						-2013044980, -834419108, -176499462, -1602046485, 702139136, 541914845, -697833018, 854010731,
						-510718124, 1005092753, -37894590, -1651317416, -54548248, 998372895, 209707190, -2030734947,
						301262140, -655057479, 651921783, 558719453, -76721255, 800863501, -1873884782, -1671906981,
						-1349793852, -2029206079, -723375054, -1366532725, 217021720, -1589355154, 2144993830,
						-1381535773, 1937130442, -845629975, -414262630, 1069095095, -219075913, 844279549, 1691187214,
						-133847459, 684969204, -970490959, 1217776738, -345206063, -1800207736, -362000315, 1155974458,
						-945682989, -507798820, -1819108289, 1040862942, 827306599, 245937332, -1838517291, 793636414,
						-2118496773, -656904476, 445451862, -1036132142, -2049790385, 1453716242, -1636289099,
						2142051191, -2117339535, 675860515, -1979272183, -1495939398, -572562345, -98685504, -695495887,
						-1694871634, 1843962777, 784426260, -334663343, -1150050147, 1794108095, -2136929250, 342367717,
						-523851250, -1424579725, -857843204, 870150877, 1762677749, 192742663, 1315633904, -34419507,
						657068766, -1284182865, 1922135956, 1724344961, -1887637262, -1296559121, 1858161112,
						-347077675, -392898842, 1690184377, 1838096858, -1736095390, -1164219302, -503754257,
						2024988896, 384712235, 1194723534, -234899893, 428821528, -876329438, -255229309, 1938072031,
						-1565240368, 970237445, -1120044650, -1030008530, -133601964, -2006031240 };
				int snowfallErrors = 0;
				long totalSnowTime = 0;
				do {
					int year = i + 1900;
					try {
						long start = System.nanoTime();
						double snowfall = wd.totalSnowfallForYear(year);
						long end = System.nanoTime();
						if (end - start > 1_000_000_001) {
							System.out.println("totalSnowfallForYear(" + year
									+ ") executed too slowly (over 1 second)! No credit will be awarded.");
							snowfallErrors = 10;
						}
						totalSnowTime += end - start;
						if (f((int) Math.round(snowfall * 10)) + f(j) != snowfalls[j]) { // prevent calculation
																							// rounding errors
							if (snowfallErrors < 10) {
								System.out.println("totalSnowfallForYear(" + year + ") returned " + snowfall
										+ " but this is not correct.");
							}
							if (snowfallErrors == 0) {
								System.out.println(
										"It's OK if the calculation is off by 0.01 or less but yours is off by too much.");
							}
							snowfallErrors++;
							if (snowfallErrors == 10) {
								System.out.println("Further totalSnowfallForYear errors suppressed.");
							}
						}
					} catch (Error e) {
						if (snowfallErrors < 10) {
							System.out.println("snowfall(" + year
									+ ") should have returned, but instead caused the following error:");
							e.printStackTrace(System.out);
						}
						snowfallErrors++;
						if (snowfallErrors == 10) {
							System.out.println("Further totalSnowfallForYear errors suppressed.");
						}
					}
					i = (i + 73) & 127;
					j++;
				} while (i != 0);
				if (i == 0 && j > 0) {
					snowfallPoints = Math.max(20 - 2 * snowfallErrors, 0);
					if (totalSnowTime > 1_000_001) {
						System.out.println(
								"10% penalty for slow totalSnowfallForYear() method (over 1 microsecond total time to perform 128 tests).");
						snowfallPoints -= 2;
					}
				}
			}
			{
				int[] precips = { 171654687, -80766404, 1641012970, -2112588716, 328408167, -1978664247, -1822666317,
						1051797495, -107514988, 1727823783, 1863975851, -1039997795, 1426132396, -1876066588 };
				System.out.println("Testing averagePrecipitationForMonth()...");
				int precipErrors = 0;
				int i = 0;
				long totalPrecipTime = 0;
				for (i = 0; i <= 13; i++) {
					try {
						long start = System.nanoTime();
						double precip = wd.averagePrecipitationForMonth(i);
						long end = System.nanoTime();
						if (end - start > 1_000_000_001) {
							System.out.println("averagePrecipitationForMonth(" + i
									+ ") executed too slowly (over 1 second)! No credit will be awarded.");
							precipErrors = 10;
						}
						totalPrecipTime += end - start;
						if (f((int) Math.round(precip * 10000)) + f(i + 12345) != precips[i]) {
							if (precipErrors < 10) {
								System.out.println("averagePrecipitationForMonth(" + i + ") returned" + precip
										+ " but this is not correct.");
							}
							precipErrors++;
							if (precipErrors == 10) {
								System.out.println("Further averagePrecipitationForMonth errors suppressed.");
							}
						}
					} catch (Error e) {
						if (precipErrors < 10) {
							System.out.println("averagePrecipitationForMonth(" + i
									+ ") should have returned, but instead caused the following error: ");
							e.printStackTrace(System.out);
						}
						precipErrors++;
						if (precipErrors == 10) {
							System.out.println("Further averagePrecipitationForMonth errors suppressed.");
						}
					}
				}
				if (i == 14) {
					precipPoints = 20 - 2 * precipErrors;
					if (totalPrecipTime > 100_001) {
						System.out.println(
								"10% penalty for slow averagePrecipitationForMonth() method (over .1 microsecond total time to perform 14 tests).");
						precipPoints -= 2;
					}
				}
			}
			{
				System.out.println("Testing lowestMostCommonHighForMonth()...");
				int[] lmch = { -1357970400, -1079510493, 1784918775, 1889144476, -1015071560, 1219122936, 1052785283,
						1647435485, 814929440, 995530212, 1361500860, -335753802 };
				int lmchErrors = 0;
				int i = 0;
				long lmchTime = 0;
				for (i = 1; i <= 12; i++) {
					try {
						long start = System.nanoTime();
						int hi = wd.lowestMostCommonHighForMonth(i);
						long end = System.nanoTime();
						if (end - start > 1_000_000_001) {
							System.out.println("lowestMostCommonHighForMonth(" + i
									+ ") executed too slowly (over 1 second)! No credit will be awarded.");
							lmchErrors = 10;
						}
						lmchTime += end - start;
						if (f(hi) + f(i + 23456) != lmch[i - 1]) {
							if (lmchErrors < 10) {
								System.out.println("lowestMostCommonHighForMonth(" + i + ") returned " + hi
										+ " but this is not correct.");
							}
							lmchErrors++;
							if (lmchErrors == 10) {
								System.out.println("Further lowestMostCommonHighForMonth errors suppressed.");
							}
						}
					} catch (Error e) {
						if (lmchErrors < 10) {
							System.out.println("lowestMostCommonHighForMonth(" + i
									+ ") should have returned, but instead caused the following error: ");
							e.printStackTrace(System.out);
						}
						lmchErrors++;
						if (lmchErrors == 10) {
							System.out.println("Further lowestMostCommonHighForMonth errors suppressed.");
						}
					}
				}
				if (i == 13) {
					lmchPoints = 20 - 2 * lmchErrors;
					if (lmchTime > 100_001) {
						System.out.println(
								"10% penalty for slow lowestMostCommonHighForMonth() method (over .1 microsecond total time to perform 14 tests).");
						lmchPoints -= 2;
					}
				}
			}
			{
				int[] hihilo = { 0, 1605867789, -1239618488, -278684187, -190875739, 0, -2011674683, 0, -1467250347,
						-63300811, -1237673962, -670038749, 0, -1124813381, 0, -1246096698, -453436879, 0, -149372284,
						0, -121501661, 0, 1434293201, -1320584161, -610096094, -1651317416, 0, 79579628, 0, -2134259431,
						0, -790426079, -171760576, -443772708, -965204271, 0, -1334335647, 0, -1022104915, -1122567392,
						0, 1626461779, 0, 1107814523, -180683084, -1516904373, -1580235570, 1249006404, 765443687, 0,
						371757233, -1488711895, -1709354624, -1871228391, -63621335, 683092199, 0, 669165701, 0,
						-34311378, -1500320499, 0, 671907497, 0, -1626499277, 0, 934482358, -1838517291, 0, -2002517210,
						0, -1634722466, 0, 44845994, -493159364, 0, 942174734, 0, 1276260914, 0, 449071927, -1546196228,
						0, 720399816, 0, -1266638310, 514644617, 1383185812, -1193560229, 0, -40040488, 0, -1644658968,
						0, 1087168121, -1106731853, 0, -1556319557, 0, -606621011, 0, -1869896593, -389503959,
						-337122141, -2089556814, 0, 1109570573, 0, 1324950313, -1076208568, 0, 1892388648, 0,
						-1440320340, 0, -1251101696, 797018730, 341333444, -1694206748, 0, 1718863424, 0, -2018039396,
						1340970334, 0, -1357803809, 0, -171735346 };
				System.out.println("Testing highestHighForLow()...");
				int i = 0;
				int j = 0;
				int lowErrors = 0;
				long totalLowTime = 0;
				do {
					if (lows[j]) {
						int lo = i - 16;
						try {
							long start = System.nanoTime();
							int hit = wd.highestHighForLow(lo);
							long end = System.nanoTime();
							totalLowTime += end - start;
							if (end - start > 1_000_000_001) {
								System.out.println("highestHighForLow(" + lo
										+ ") executed too slowly (over 1 second)! No credit will be awarded.");
								lowErrors = 10;
							}
							if (f(hit) + f(j) != hihilo[j]) {
								if (lowErrors < 10) {
									System.out.println("highestHighForLow(" + lo + ") returned " + hit
											+ " but this is not correct.");
								}
								lowErrors++;
								if (lowErrors == 10) {
									System.out.println("Further highestHighForLow errors suppressed.");
								}
							}
						} catch (Error e) {
							if (lowErrors < 10) {
								System.out.println("highestHighForLow(" + lo + ") should have returned " + lows[j]
										+ " but instead caused the following error: ");
								e.printStackTrace(System.out);
							}
							lowErrors++;
							if (lowErrors == 10) {
								System.out.println("Further highestHighForLow errors suppressed.");
							}
						}
					}
					i = (i + 73) & 127;
					j++;
				} while (i != 0);
				if (i == 0 && j > 0) {
					hihiloPoints = 20 - 2 * lowErrors;
					if (totalLowTime > 1_000_001) {
						System.out.println(
								"10% penalty for slow highestHighForLow() method (over 1 microsecond total time to perform 128 tests)");
						lowPoints--;
					}
				} else {
					System.out.println("No highestHighForLow points earned due to above errors.");
				}

			}

		} finally {
			highPoints = Math.max(highPoints, 0);
			lowPoints = Math.max(lowPoints, 0);
			snowfallPoints = Math.max(snowfallPoints, 0);
			precipPoints = Math.max(precipPoints, 0);
			lmchPoints = Math.max(lmchPoints, 0);
			hihiloPoints = Math.max(hihiloPoints, 0);
			System.out.println("highTemp: " + highPoints + " / 10");
			System.out.println("lowTemp: " + lowPoints + " / 10");
			System.out.println("totalSnowfallForYear: " + snowfallPoints + " / 20");
			System.out.println("averagePrecipitationForMonth: " + precipPoints + " / 20");
			System.out.println("lowestMostCommonHighForMonth: " + lmchPoints + " / 20");
			System.out.println("highestHighForLow: " + hihiloPoints + " / 20");
			if ((highPoints + lowPoints + snowfallPoints + precipPoints + lmchPoints + hihiloPoints) == 100) {
				System.out.println("Speed bonus!");
				System.out.println("Total: 102 / 100");
			} else {
				System.out.println(
						"Total: " + (highPoints + lowPoints + snowfallPoints + precipPoints + lmchPoints + hihiloPoints)
								+ " / 100");
			}
		}

	}

}
