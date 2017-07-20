package operations;

import java.util.ArrayList;
import java.util.List;

public class App {

	private static List<KnapsackSolver> solver = new ArrayList<KnapsackSolver>();

	public static void main(String[] args) throws Exception {
		List<Problem> problems = new ArrayList<Problem>();
		ProblemParser.readFile(args[0]).forEach(
				line -> problems.add(ProblemParser.parseProblem(line)));
		// problems.forEach(System.out::println);
		problems.stream()
				.filter(problem -> null != problem
						&& problem.getWeightLimit() > 0
						&& null != problem.getItems()
						&& problem.getItems().size() > 0)
				.forEach(problem -> solver.add(new KnapsackSolver(problem)));
		solver.forEach(sol -> sol.start());
	}

}
