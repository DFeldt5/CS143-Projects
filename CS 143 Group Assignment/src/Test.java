import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;

public class Test {
	private static class Card{
		private int value;
		private int suit;
		
		public Card(int value, int suit){
			this.value = value;
			this.suit = suit;
		}
	}

  //Maps representing which suit of each card value a player has
  private static TreeMap<Integer, ArrayList<Integer>> p1Hand = new TreeMap<>();
  private static TreeMap<Integer, ArrayList<Integer>> p2Hand = new TreeMap<>();
    
  

  //iterates through each hand map and converts the suit/value to the corresponding string
	public static String handToString(TreeMap<Integer, ArrayList<Integer>> hand) {
		String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
		String[] suits = {"Hearts", "Clubs", "Diamonds", "Spades"};
    //create a new ArrayList to hold the string for each card
    ArrayList<String> result = new ArrayList<String>();

    //loop through keys
    for(Integer i : hand.keySet()) {
    	ArrayList<Integer> list = hand.get(i);
      //loop through the value set
    	for(Integer j : list) {
    		result.add(values[i-1] + " of " + suits[j]);
    	}
    }
    return "Your hand is: " + result;
  }


    
  //removes the top card from the deck and adds it to that player's hand
  public static void drawCard(TreeMap<Integer, ArrayList<Integer>> hand, Queue<Card> deck){
    //if the deck is empty, prints a message
    if(deck.isEmpty() == true) {
    	System.out.println("The deck is empty!");
    	return;
    }
    Card newCard = deck.remove();
    //if they have a matching value card in their hand, add this suit to that map
    if(hand.containsKey(newCard.value)) {
		  ArrayList<Integer> list = hand.get(newCard.value);
		  list.add(newCard.suit);
		  hand.put(newCard.value, list);
		}
		//if they don't have any yet, create new mapping
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
		int input = 0;
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
      //checks user input for face card names
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

  
  //DUSTIN: I had to modify Fishing a little bit to make it work in the turn loop.
  //Basically made the method turn-neutral and moved the "if(player1)"" check to the loop
  //then changed the return type to boolean so it's easier to check for a legal move
  
  // fishing method: fulfills the user's request for a card. if that card is in
  // the other player's hand, that card will be removed from the other player's hand 
  // and given to the player who requested it. if that card is not in the other player's 
  // hand, the user has to get a card from the deck
  // (inputs: output from the promptUser method, player 1 and 2's hand)
  public static boolean fishing(TreeMap<Integer, ArrayList<Integer>> fisher, TreeMap<Integer,   ArrayList<Integer>> fishee, int userInput, Queue<Card> deck, int turn) throws Exception {
    //if the fisher doesn't have any of the card they're fishing for, throw an exception (this is caught in the turn loop)
    if(!fisher.containsKey(userInput)){
      throw new Exception();
    }

    // if the fishee has the requested card 
    if(fishee.containsKey(userInput)) {
      //pull all cards of that value from their hand
      ArrayList<Integer> cards = fishee.remove(userInput);
      // put the requested cards in player 1's hand
      fisher.get(userInput).addAll(cards);
      return true;          
    }
      // if the fishee doesn't have the requested card, then the fisher has to draw from the deck
    else {
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
  
	
	//checks the player's hand for completed sets of cards, removes the sets from play, and returns the points earned
	public static int setCheck(TreeMap<Integer, ArrayList<Integer>> hand) {
		//int to track points earned
		int score = 0;
		//List to store which card values have been completed
		ArrayList<Integer> completedSets = new ArrayList<Integer>();
		//checks each card set's size and adds the card value to completedSets if complete
		for(Integer value : hand.keySet()) {
			if(hand.get(value).size() == 4) {
				completedSets.add(value);
				System.out.println("You completed a set!");
				score++;
			}
			
		}
		//remove each completed set from the hand
		for(Integer i : completedSets) {
			hand.remove(i);
		}
		return score;
	}


  public static void main(String args[]){
	  Queue<Card> deck = new LinkedList<Card>();		
		for(int s = 0; s <= 3; s++) {
			for(int v = 1; v <= 13; v++) {
		    	Card card = new Card(v, s);
				deck.add(card);
			}
		}
		Collections.shuffle((LinkedList<Card>)deck);

	    //int for each player's score
	    int playerOneScore = 0;
	    int playerTwoScore = 0;
		//scanner to read the user's input
		Scanner s = new Scanner(System.in);
		//deal to start the game
		deal(deck, 7, p1Hand, p2Hand);
		//reusable string for the various prompts that will be printed to the user
		//track whose turn it is
		int turn = 1;
    //testing
  }
}