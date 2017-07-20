package operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProblemParser {
	
	public static List<String> readFile(String filepath) throws Exception {
		List<String> lines = new ArrayList<String>();
		File file = new File(filepath);
		if(!file.exists()) {
			throw new NoSuchFileException(filepath);
		}
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String currentLine = null;
		while(null != (currentLine = bufferedReader.readLine())) {
			if(!currentLine.isEmpty())
				lines.add(currentLine);
		}
		bufferedReader.close();
		return lines;
	}
	
	private static double parseWeight(String str) {
		return Double.parseDouble(str);
	}
	
	private static byte parseIndex(String str) {
		return Byte.parseByte(str);
	}
	
	private static long parsePrice(String str) {
		str = str.substring(1);
		return Long.parseLong(str);
	}
	
	private static Item parseItem(String str) {
		Item item = new Item();
		str = str.substring(1, str.length()-1);
		String[] components = str.split(",");
		item.setIndex(parseIndex(components[0]));
		item.setWeight(parseWeight(components[1]));
		item.setPrice(parsePrice(components[2]));
		return item;
	}
	
	private static List<Item> parseItems(String str) {
		List<Item> items = new ArrayList<Item>();
		if(null == str || str.trim().isEmpty()) {
			printErrorAndCorrectionReference("Not a valid format of input. Missing items after (on the right of) the token ':' here : ", str);
		} else {
			List<String> strings = new ArrayList<String>();
			if(str.contains(" "))
				strings = Arrays.asList(str.split(" "));
			else
				strings.add(str);
			strings.stream().forEach(string -> items.add(parseItem(string)));
		}
		return items;
	}
	
	public static void printErrorAndCorrectionReference(String message, String sourceString) {
		System.err.println(message.concat(sourceString));
		System.err.println("Valid Format of input lines in the file \"input.txt\" : ");
		System.err.println("100 : (1,0.1,$5) (2,11.2,$6) (3,22.3,$7) (4,33.4,$8) (5,44.5,$9) (6,55.6,$4) (7,66.7,$5) (8,77.8,$6) (9,88.9,$7) (10,99.0,$8) (11,100.1,$9) (12,111.2,$8) (13,122.3,$7) (14,133.4,$6) (15,144.5,$5) (16,155.6,$4)");
		System.err.println("250 : (1,0.1,$5) (2,11.2,$6) (3,22.3,$7) (4,33.4,$8) (5,44.5,$9) (6,55.6,$4) (7,66.7,$5) (8,77.8,$6) (9,88.9,$7) (10,99.0,$8) (11,100.1,$9) (12,111.2,$8) (13,122.3,$7) (14,133.4,$6) (15,144.5,$5) (16,155.6,$4)");
		System.err.println();
	}
	
	public static Problem parseProblem(String line) {
		if(null != line && !line.trim().isEmpty()) {
			if(line.contains(":")) {
				String[] keyVal = line.split(" : ");
				double weightLimit = 0;
				List<Item> items = new ArrayList<Item>();
				try {
					weightLimit = parseWeight(keyVal[0]);
				} catch(NumberFormatException ex) {
					printErrorAndCorrectionReference("Not a valid format of input. Missing weight limit before (on the left of) the token ':' in the input line : ", keyVal[0]);
					System.err.println(ex);
				}
				try {
					items = parseItems(keyVal[1]);
				} catch(NumberFormatException ex) {
					printErrorAndCorrectionReference("Not a valid format of input. Missing weight limit before (on the left of) the token ':' in the input line : ", keyVal[0]);
					System.err.println(ex);
				}
				Problem problem = new Problem(weightLimit, items);
				return problem;
			} else {
				printErrorAndCorrectionReference("Not a valid format of input. Missing token ':' in the input line : ", line);
				return null;
			}
		} else {
			printErrorAndCorrectionReference("Not a valid format of input. Empty line : ", line);
			return null;
		}
	}
	
}
