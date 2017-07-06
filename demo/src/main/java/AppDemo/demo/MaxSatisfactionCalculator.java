package AppDemo.demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaxSatisfactionCalculator {

	int timeToEat = 5;
	static int timeLimit = 35;//time limit to eat

	/*
	 * findSatisfaction calculate max satisfaction in a given time limit 
	 */
	@RequestMapping(value = "/demo", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public  String  findSatisfaction() {
		String result = null;
		Set<String> listOfLines = menuItems();
		boolean flag = true;
		int largest = 0;
		int secondLargest = 0;
		int maxSatisfaction = 0;
		Map<String, Integer> itemTimeMap = new HashMap<>();

		List<Integer> timeList = new ArrayList<>();
		listOfLines.forEach(item -> {
			itemTimeMap.put(item, timeToEat);
			timeToEat = timeToEat + 5;
		});
		List<Integer> timesToEat = new ArrayList<>(itemTimeMap.values());

		while (flag) {
			if (timesToEat.contains(timeLimit)) {
				flag = false;
				timeList.add(timeLimit);
			}
			if (largest > 0 && secondLargest > 0) {
				int index = timesToEat.indexOf(largest);
				timesToEat.remove(index);

			}
			Map<String, Integer> largestMap = getLargestMap(timesToEat);
			largest = largestMap.get("largest");
			secondLargest = largestMap.get("secondLargest");
			maxSatisfaction = largest + secondLargest;
			if (maxSatisfaction <= timeLimit) {
				flag = false;
				timeList.add(largest);
				timeList.add(secondLargest);
			}

		}

		if (!timeList.isEmpty()) {
			Set<String> keys = new HashSet<>();
			timeList.forEach(t -> {
				for (Entry<String, Integer> entry : itemTimeMap.entrySet()) {
					if (Objects.equals(t, entry.getValue())) {
						keys.add(entry.getKey());
					}
				}
			});
			List<String> satisfactionList = new ArrayList<>(keys);
			if (satisfactionList.size() == 2) {
				result="Max satisfaction :" + maxSatisfaction + " received by " + satisfactionList.get(0)
						+ " AND " + satisfactionList.get(1);
				System.out.println(result);
				
				System.out.println("result");
			} else if (satisfactionList.size() == 1) {
				result="Max satisfaction :" + timeLimit + " received by " + satisfactionList.get(0);
				System.out.println(result);
			}

		}
		return result;

	}

	/*
	 * menuItems will read the local file having item1,item2.......itemN
	 */
	private static Set<String> menuItems() {
		BufferedReader bufReader = null;
		Set<String> listOfLines = new HashSet<>();
		try {
			bufReader = new BufferedReader(new FileReader("E://Users/minakshee_k/Desktop/reports/menu.txt"));
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
    /*
     * getLargestMap will fetch largest and second largest time value from the time to eat list
     */
	private static Map<String, Integer> getLargestMap(List<Integer> timeList) {
		Map<String, Integer> largestMap = new HashMap<>();
		int secondLargest = (int) timeList.get(0);
		int largest = timeList.get(0);
		for (int i = 0; i < timeList.size(); i++) {
			if (timeList.get(i) > largest) {
				secondLargest = largest;
				largest = timeList.get(i);
			}
			if (timeList.get(i) > secondLargest && timeList.get(i) != largest) {
				secondLargest = timeList.get(i);
			}

		}
		largestMap.put("largest", largest);
		largestMap.put("secondLargest", secondLargest);
		return largestMap;
	}

}
