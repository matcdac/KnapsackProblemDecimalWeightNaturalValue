package operations;

import java.util.HashSet;
import java.util.Set;

public class Possibility {
	
	private Set<Integer> indexes;
	
	private double weight;
	
	private double remainingWeight;
	
	private long value;
	
	public Possibility() {
		indexes = new HashSet<Integer>();
		weight = 0.0;
		remainingWeight = 0.0;
		value = 0;
	}
	
	public Possibility(Problem problem) {
		this();
		remainingWeight = problem.getWeightLimit();
	}
	
	public Possibility(Possibility problem) {
		indexes = new HashSet<Integer>();
		indexes.addAll(problem.getIndexes());
		weight = problem.getWeight();
		remainingWeight = problem.getRemainingWeight();
		value = problem.getValue();
	}
	
	public Set<Integer> getIndexes() {
		return indexes;
	}

	public void setIndexes(Set<Integer> indexes) {
		this.indexes = indexes;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getRemainingWeight() {
		return remainingWeight;
	}

	public void setRemainingWeight(double remainingWeight) {
		this.remainingWeight = remainingWeight;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public String indexesAsStr() {
		String str = new String();
		for(Integer index : indexes)
			str = str.concat(index.toString()).concat(" ");
		return str;
	}
	
	public String toString() {
		return indexesAsStr();
	}
	
}
