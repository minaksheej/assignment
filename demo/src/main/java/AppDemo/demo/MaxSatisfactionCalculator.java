package AppDemo.demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaxSatisfactionCalculator {

	int timeToEat = 5;

	/*
	 * findSatisfaction calculate max satisfaction in a given time limit
	 */
	@RequestMapping(value = "/demo", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String findSatisfaction() {
		int timeLimit = 10000;// time limit to eat
		StringBuilder output = new StringBuilder();
		output.append("max Satisfaction 10000 achieved by these Items :");
		Set<String> listOfLines = menuItems();
		List<String> satItems = new ArrayList<>();
		for (String item : listOfLines) {
			Random random = new Random();
			int timeToEat = random.nextInt(900) + 100;
			timeLimit = (timeLimit - timeToEat);
			satItems.add(item);
			if (timeLimit <= 0) {
				break;
			}

		}
		satItems.forEach(sat -> {
			output.append(" " + sat);
		});
		System.out.print(output.toString());
		return output.toString();

	}

	/*
	 * menuItems will read the local file having item1,item2.......itemN
	 */
	private Set<String> menuItems() {
		BufferedReader bufReader = null;
		Set<String> listOfLines = new HashSet<>();
		try {
			bufReader = new BufferedReader(new FileReader("E://Users/minakshee_k/Desktop/menu.txt"));
			String line = bufReader.readLine();
			while (line != null) {
				listOfLines.add(line);
				line = bufReader.readLine();

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {

		} finally {
			try {
				bufReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listOfLines;
	}

}
