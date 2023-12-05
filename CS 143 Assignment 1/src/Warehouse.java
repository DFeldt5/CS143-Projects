/*
 * Dustin Feldt
 * CS 143 Assignment 1
 * Creates a simulated warehouse with a defined total capacity
 * and a defined limit of similar inventory items, then tracks
 * shipping and receiving of inventory items.
 */

public class Warehouse {
	//used for total warehouse capacity
	private int capacity;
	
	//used for specific item capacity
	private int limitPerItem;
	
	//represents inventory, with 0 values representing empty space
	private int[] inventory;
	
	//constructor builds an inventory array with the given total and specific capacities
	public Warehouse(int size, int limitPerItem) {
		this.capacity = size;
		this.limitPerItem = limitPerItem;
		inventory = new int[capacity];
	}
	
	//allows the user to view the total capacity
	public int getSize() {
		return capacity;
	}
	
	//allows the user to view the limit per item
	public int getLimitPerItem() {
		return limitPerItem;
	}
	
	//returns the total stored amount of an item
	public int stock(int itemCode) {
		int stock = 0;
		for(int i = 0; i < inventory.length; i++) {
			if(inventory[i] == itemCode) {
				stock++;
			}
		}
		return stock;
	}
	
	//checks current stock and adds specified item and amount if able
	//returns number of items that could not be stored
	public int receive(int itemCode, int itemCount) {
		int limit = getLimitPerItem() - stock(itemCode);
		int received = 0;
		int extra = itemCount;
		//loop fills empty inventory space
		for(int i = 0; i < inventory.length; i++) {
			if(received == itemCount || received == limit) {
				break;
			}
			if(inventory[i]==0) {
				inventory[i] = itemCode;
				received++;
				extra--;
			}
		}
		return extra;
	}
	
	//removes specified number of items from inventory if able
	//returns number of items shipped
	public int ship(int itemCode, int itemCount) {
		int shipped = 0;
		//loop switches removed inventory items to 0
		for(int i = 0; i < inventory.length; i++) {
			if(shipped == itemCount) {
				break;
			}
			if(inventory[i] == itemCode) {
				inventory[i] = 0;
				shipped++;
			}
		}
		return shipped;
	}


}
