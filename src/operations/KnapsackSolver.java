package operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class KnapsackSolver {
	
	private Problem problem;
	
	private Map<Integer, List<Possibility>> possibilities;
	
	private Possibility validPossibility;
	
	private Possibility allPossibility;
	
	private boolean terminate = false;
	
	private int n;
	
	public KnapsackSolver(Problem problem) {
		this.problem = problem;
		possibilities = new HashMap<Integer, List<Possibility>>();
		validPossibility = new Possibility(problem);
		allPossibility = new Possibility(problem);
		n = 0;
	}
	
	private Possibility mergeIntoFormer(Possibility previous, Possibility one) {
		Possibility next = new Possibility();
		next.getIndexes().addAll(previous.getIndexes());
		if(next.getIndexes().addAll(one.getIndexes())) {
			next.setWeight(previous.getWeight() + one.getWeight());
			next.setRemainingWeight(previous.getRemainingWeight() - one.getWeight());
			next.setValue(previous.getValue() + one.getValue());
			return next;
		}
		return null;
	}
	
	private void updateValid(Possibility anotherPossibility) {
		if(validPossibility.getValue() < anotherPossibility.getValue())
			validPossibility = anotherPossibility;
	}
	
	private void processOneAndAllAtATime() {
		List<Possibility> possibilityListOfOneAtATime = new ArrayList<Possibility>();
		Iterator<Item> itrItem = problem.getItems().iterator();
		while(itrItem.hasNext()) {
			Item item = itrItem.next();
			if(item.getWeight() < problem.getWeightLimit()) {
				if(!allPossibility.getIndexes().contains(item.getIndex())) {
					Possibility possibilityOfOneAtATime = new Possibility();
					Set<Integer> indexes = new HashSet<Integer>(1);
					indexes.add(item.getIndex());
					possibilityOfOneAtATime.setIndexes(indexes);
					possibilityOfOneAtATime.setWeight(item.getWeight());
					possibilityOfOneAtATime.setRemainingWeight(problem.getWeightLimit() - item.getWeight());
					possibilityOfOneAtATime.setValue(item.getPrice());
					allPossibility = mergeIntoFormer(allPossibility, possibilityOfOneAtATime);
					updateValid(possibilityOfOneAtATime);
					possibilityListOfOneAtATime.add(possibilityOfOneAtATime);
				} else {
					System.err.println(problem.getWeightLimit() + " : Duplicate index found");
					terminate = true;
					break;
				}
			}
		}
		possibilities.put(1, possibilityListOfOneAtATime);
		List<Possibility> possibilityListOfAllAtATime = new ArrayList<Possibility>();
		possibilityListOfAllAtATime.add(allPossibility);
		n = allPossibility.getIndexes().size();
		if(n > 1)
			possibilities.put(n, possibilityListOfAllAtATime);
	}
	
	private Possibility negate(Possibility oneOfR) {
		Possibility opposite = new Possibility(allPossibility);
		if(opposite.getIndexes().removeAll(oneOfR.getIndexes())) {
			opposite.setWeight(opposite.getWeight() - oneOfR.getWeight());
			opposite.setRemainingWeight(opposite.getRemainingWeight() + oneOfR.getWeight());
			opposite.setValue(opposite.getValue() - oneOfR.getValue());
			return opposite;
		}
		return null;
	}
	
	private void processOpposite(int r, Possibility possibility) {
		Possibility opposite = negate(possibility);
		if(null != opposite && opposite.getRemainingWeight() >= 0.0) {
			updateValid(opposite);
			possibilities.get(n-r).add(opposite);
		}
	}
	
	private void processNMinusRAtATime(int r) {
		if(0 == n % 2 && r != n/2) {
			List<Possibility> nMinusRPossibilities = new ArrayList<Possibility>();
			possibilities.put(n-r, nMinusRPossibilities);
			possibilities.get(r).stream().forEach(possibility -> processOpposite(r, possibility));
		}
	}
	
	private void makeCartesianProduct(int r, Possibility rMinusOnePossibility, Possibility onePossibility) {
		Possibility rPossibility = mergeIntoFormer(rMinusOnePossibility, onePossibility);
		if(null != rPossibility && rPossibility.getRemainingWeight() >=0 && !possibilities.get(r).contains(rPossibility)) {
			updateValid(rPossibility);
			possibilities.get(r).add(rPossibility);
		}
	}
	
	private void processCartesianProduct(int r, Possibility rMinusOnePossibility) {
		possibilities.get(1).stream().forEach(onePossibility -> makeCartesianProduct(r, rMinusOnePossibility, onePossibility));
	}
	
	private void processRAtATime(int r) {
		List<Possibility> rPossibilities = new ArrayList<Possibility>();
		possibilities.put(r, rPossibilities);
		possibilities.get(r-1).stream().forEach(rMinusOnePossibility -> processCartesianProduct(r, rMinusOnePossibility));
	}
	
	/*private void processCartesianProduct(int r, Possibility rMinusOnePossibility, Possibility onePossibility) {
		Possibility rPossibility = mergeIntoFormer(rMinusOnePossibility, onePossibility);
		if(null != rPossibility && rPossibility.getRemainingWeight() >=0 && !possibilities.get(r).contains(rPossibility)) {
			updateValid(rPossibility);
			possibilities.get(r).add(rPossibility);
		}
	}
	
	private void processRAtATime(int r) {
		List<Possibility> rPossibilities = new ArrayList<Possibility>();
		possibilities.put(r, rPossibilities);
		possibilities.get(r-1).stream().forEach(rMinusOnePossibility -> 
				possibilities.get(1).stream().forEach(onePossibility -> 
						processCartesianProduct(r, rMinusOnePossibility, onePossibility)));
	}*/
	
	public void start() {
		processOneAndAllAtATime();
		if(n > 2)
			processNMinusRAtATime(1);
		if(n>3 && !terminate) {
			if(allPossibility.getRemainingWeight() >= 0.0) {
				System.out.println(problem.getWeightLimit() + " : " + allPossibility);
			} else {
				// for each r from 2 to n/2, form next combinations of r at a time and n-r at a time, and process valid
				int startR = 2;
				int endR = 0 == n % 2 ? n/2 : (n-1)/2;
				for(int currentR = startR; startR <= endR; currentR++) {
					processRAtATime(currentR);
					processNMinusRAtATime(currentR);
					if(0 == possibilities.get(currentR).size())
						break;
				}
				System.out.println(problem.getWeightLimit() + " : " + validPossibility);
			}
		}
		possibilities.keySet().forEach(key -> possibilities.put(key, null));
	}
	
}
