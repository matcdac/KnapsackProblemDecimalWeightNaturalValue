package operations;

public class Item {
	
	private int index;
	
	private double weight;
	
	private long price;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}
	
	public String toString() {
		return "Index : " + index + ", Weight : " + weight + ", Price : $" + price;
	}
	
}
