package operations;

import java.util.List;

public class Problem {
	
	private double weightLimit;
	
	private List<Item> items;
	
	public Problem(double weightLimit, List<Item> items) {
		this.weightLimit = weightLimit;
		this.items = items;
	}
	
	public Problem() {
		
	}

	public double getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(double weightLimit) {
		this.weightLimit = weightLimit;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public String itemsAsStr() {
		String str = new String();
		for(Item item : items)
			str = str.concat(item.toString()).concat("\n");
		return str;
	}
	
	public String toString() {
		return "Weight Limit : " + weightLimit + "\n" + itemsAsStr();
	}
	
}
