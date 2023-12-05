import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;

/*
 * CS 143 Team Assignment
 * Hackstreet Boys:
 * Damani Claxton
 * Bruce Duong
 * Dustin Feldt
 * Cory Vo
 */
public class GoFish {
	
	//Card subclass defines Card objects with two integers representing face value and suit
	private static class Card{
		private int value;
		private int suit;
		
		public Card(int value, int suit){
			this.value = value;
			this.suit = suit;
		}
	}
	
	//Players' hands are TreeMaps
	//{face value of card : which suits of that card}
	// ex.: {7 : [0, 3]} would represent the 7 of Hearts and the 7 of Spades
	private static TreeMap<Integer, ArrayList<Integer>> p1Hand = new TreeMap<>();
    private static TreeMap<Integer, ArrayList<Integer>> p2Hand = new TreeMap<>();

    //converts a player's hand into a user-friendly String
    public static String handToString(TreeMap<Integer, ArrayList<Integer>> hand) {
    	//String Arrays to translate int representations of value and suit
    	String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
		String[] suits = {"Hearts", "Clubs", "Diamonds", "Spades"};
    	//create a new ArrayList to hold the string for each card
    	ArrayList<String> result = new ArrayList<String>();
    	//iterate through hand and translate each set
    	for(Integer i : hand.keySet()) {
    		ArrayList<Integer> list = hand.get(i);
    		for(Integer j : list) {
    			result.add(values[i-1] + " of " + suits[j]);
    		}

    	}
        return "Your hand is: " + result;
    }
    
    //adds the top card of the deck to that player's hand
    public static void drawCard(TreeMap<Integer, ArrayList<Integer>> hand, Queue<Card> deck) {
    	//if the deck is empty, displays a message
    	if(deck.isEmpty() == true) {
    		System.out.println("The deck is empty!");
    		return;
    	}
    	Card newCard = deck.remove();
    	//if the player has a card of this value already, add this card's suit to the appropriate map set
    	if(hand.containsKey(newCard.value)) {
			ArrayList<Integer> list = hand.get(newCard.value);
			list.add(newCard.suit);
			//update the map to map to this new total
			hand.put(newCard.value, list);
		}
		//if they don't have this value card, create a new mapping
		else {
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(newCard.suit);
			hand.put(newCard.value, list);
		}
    }
    
    //deals a number of cards to each player
	public static void deal(Queue<Card> deck, int numCards, TreeMap<Integer, ArrayList<Integer>> p1Hand, TreeMap<Integer, ArrayList<Integer>> p2Hand) {
		while(numCards > 0) {
			drawCard(p1Hand, deck);
			drawCard(p2Hand, deck);
			numCards--;
		}
	}
	
	//this method prints the turn prompt, verifies a valid input, and then returns it
	public static int userPrompt(Scanner s, String prompt) {
		//stores the user's input
		int input = 0;
		//replays prompt until a valid input is received
		while(true) {
			System.out.print(prompt);
			//this checks if the user typed a number between 1 and 13
			if(s.hasNextInt()) {
				input = s.nextInt();
				s.nextLine();
				if(input >= 1 && input <= 13) {
					break;
				}
				else {
					System.out.println("That was not a valid number! Please try again.");
					continue;
				}
			}
			//these allow the user to respond with the name of face cards as well
			if(s.hasNext("Ace")) {
				input = 1;
				s.nextLine();
				break;
			}
			if(s.hasNext("Jack")) {
				input = 11;
				s.nextLine();
				break;
			}
			if(s.hasNext("Queen")) {
				input = 12;
				s.nextLine();
				break;
			}
			if(s.hasNext("King")) {
				input = 13;
				s.nextLine();
				break;
			}
			s.nextLine();
			System.out.println("That was not a valid number! Please try again.");
		}
		return input; 
	}

	
	//goes fishing for the card the user selects
	//returns true if the opponent has that card, false if not
	public static boolean fishing(TreeMap<Integer, ArrayList<Integer>> fisher, TreeMap<Integer, ArrayList<Integer>> fishee,
		int userInput, Queue<Card> deck, int turn) throws Exception {
		//if the user fishes for a card they don't have, a generic exception is thrown to be caught in the game loop
		if(!fisher.containsKey(userInput)) {
			throw new Exception();
		}

		// if opponent has the requested card, transfer those cards to the current player's hand
		if (fishee.containsKey(userInput)) {
			ArrayList<Integer> cards = fishee.remove(userInput);
			fisher.get(userInput).addAll(cards);
			return true;
		}
		// if opponent doesn't have the requested card, attempt to draw a card from the deck
		else {
			//if deck isn't empty, call the drawCard method, otherwise prints a message
			if(!deck.isEmpty()) {
				drawCard(fisher, deck);
				System.out.println("Go fish!");
			}
			else {
				System.out.println("The deck is empty!");
			}
			return false;
		}
	}

	public static void main(String[] args) {
		//Queue of 52 unique card objects
		Queue<Card> deck = new LinkedList<Card>();		
		for(int s = 0; s <= 3; s++) {
			for(int v = 1; v <= 13; v++) {
		    	Card card = new Card(v, s);
				deck.add(card);
			}
		}
		//shuffle the deck
		Collections.shuffle((LinkedList<Card>)deck);

	    //int for each player's score
	    int playerOneScore = 0;
	    int playerTwoScore = 0;
	    
		//scanner to read the user's input
		Scanner s = new Scanner(System.in);
		
		//deal 7 cards to start the game
		deal(deck, 7, p1Hand, p2Hand);
		
		//track whose turn it is
		int turn = 1;
		
		//loop between turns until the win conditions are met
		System.out.println("Welcome to Go Fish!");
		while(true) {
			//print the player's hand
			//if their hand is empty, draws a card first
			if(turn == 1) {
				if(p1Hand.isEmpty() == true && deck.isEmpty() == false) {
					drawCard(p1Hand, deck);
				}
				System.out.println("Player 1, " + handToString(p1Hand));
			}
			else {
				if(p2Hand.isEmpty() == true && deck.isEmpty() == false) {
					drawCard(p2Hand, deck);
				}
				System.out.println("Player 2, " + handToString(p2Hand));
			}
			
			// prints the user prompt, then stores the input
			String prompt = "What card are you fishing for?";
			int input = userPrompt(s, prompt);
			
			//the core of the turn loop
			try {
				//if player 1 successfully steals a card, prints a message and gives them another turn if they still have cards
				if(turn == 1) {
					if(fishing(p1Hand, p2Hand, input, deck, turn) == true) {
						System.out.println("You got one!  Go again.");
						//checks the player's hand for a completed set of cards, removes the set from play, and adds to the score
						if(p1Hand.get(input).size() == 4) {
							playerOneScore++;
							p1Hand.remove(input);
							System.out.println("You completed a set!");
						}
						if(p1Hand.isEmpty() == false) {
							continue;
						}
					}
					//if they draw a card, check for a completed set
					else {
						int toRemove = 4;
						for(Integer value : p1Hand.keySet()) {
				            if(p1Hand.get(value).size() == 4) {
				                playerOneScore++;
				                System.out.println("You completed a set!");
				                toRemove = value;
				            }
				        }
						if(toRemove < 4) {
							p1Hand.remove(toRemove);
						}
					}
				}
				//same for player 2
				else {
					if(fishing(p2Hand, p1Hand, input, deck, turn) == true) {
						System.out.println("You got one!  Go again.");
						if(p2Hand.get(input).size() == 4) {
							playerTwoScore++;
							p2Hand.remove(input);
							System.out.println("You completed a set!");
						}
						if(p2Hand.isEmpty() == false) {
							continue;
						}
					}
					else {
						int toRemove = 4;
						for(Integer value : p2Hand.keySet()) {
				            if(p2Hand.get(value).size() == 4) {
				                playerTwoScore++;
				                System.out.println("You completed a set!");
				                toRemove = value;
				            }
				        }
						if(toRemove < 4) {
							p2Hand.remove(toRemove);
						}
					}
				}
			//catches the exception from the "fishing" method, gives the user an error message, and restarts their turn
			} catch(Exception Exception) {
				System.out.println("You can only fish for a card that's in your hand!");
				continue;
			}

			//check the player's hand for completed sets, remove the cards, and increase their score
			if(turn == 1) {
				ArrayList<Integer> completedSets = new ArrayList<Integer>();
				//checks each card set's size and adds the card value to completedSets if complete
				for(Integer value : p1Hand.keySet()) {
					if(p1Hand.get(value).size() == 4) {
						completedSets.add(value);
						System.out.println("You completed a set!");
						playerOneScore++;
					}
					
				}
				//remove each completed set from the hand
				for(Integer i : completedSets) {
					p1Hand.remove(i);
				}
			}
			else {
				ArrayList<Integer> completedSets = new ArrayList<Integer>();
				//checks each card set's size and adds the card value to completedSets if complete
				for(Integer value : p2Hand.keySet()) {
					if(p2Hand.get(value).size() == 4) {
						completedSets.add(value);
						System.out.println("You completed a set!");
						playerTwoScore++;
					}
					
				}
				//remove each completed set from the hand
				for(Integer i : completedSets) {
					p2Hand.remove(i);
				}
			}

			// changes turns and prints a line break for visual clarity
			if(turn == 1) {
				turn = 2;
			}
			else {
				turn = 1;
			}
			System.out.println();
			
			//if the deck and a player's hand are empty, the game ends and scores are compared to determine the winner
			if(deck.isEmpty() && (p1Hand.isEmpty() || p2Hand.isEmpty())) { 
				System.out.println("Player 1 Score: " + playerOneScore);
				System.out.println("Player 2 Score: " + playerTwoScore);
				if(playerOneScore > playerTwoScore) {
					System.out.println("Player 1 wins!");
				}
				else {
					System.out.println("Player 2 wins!");
				}
				break;
		    }

		}
    }
}