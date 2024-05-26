import java.util.*;
import java.io.*;

public class Trenuri {
	static private String start, end;
	static Map<String, List<String>> graph = new HashMap<>();
	static Map<String, Integer> longestPathMemo = new HashMap<>();

	static void readInput() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("trenuri.in"));
			String line = br.readLine();
			String[] fields = line .split(" ");
			start = fields[0];
			end = fields[1];

			int M = Integer.parseInt(br.readLine().trim());

			for (int i = 0; i < M; i++) {
				line = br.readLine();
				fields = line.split(" ");
				String src = fields[0];
				String dest = fields[1];

				graph.computeIfAbsent(src, k -> new ArrayList<>()).add(dest);
			}
			br.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static void writeOutput() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("trenuri.out"));
			bw.write(String.valueOf(dfs(start, end)));
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws IOException {
		readInput();
		writeOutput();
	}

	private static int dfs(String current, String destination) {
		if (current.equals(destination)) {
			return 1;
		}
		if (longestPathMemo.containsKey(current)) {
			return longestPathMemo.get(current);
		}

		int maxLength = 0;
		List<String> neighbors = graph.getOrDefault(current, new ArrayList<>());
		for (String neighbor : neighbors) {
			int pathLength = dfs(neighbor, destination);
			if (pathLength > 0) {
				maxLength = Math.max(maxLength, pathLength + 1);
			}
		}

		longestPathMemo.put(current, maxLength);
		return maxLength;
	}
}
