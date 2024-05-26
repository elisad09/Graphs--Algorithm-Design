import java.util.*;
import java.io.*;

public class Numarare {
	static final int MOD = 1000000007;
	private static int N, M;
	private static ArrayList<Integer>[] graph1, graph2;
	private static int[] dp1, dp2;

	static void readInput() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("numarare.in"));
		String[] fields = br.readLine().split(" ");
		N = Integer.parseInt(fields[0]);
		M = Integer.parseInt(fields[1]);

		graph1 = new ArrayList[N + 1];
		graph2 = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			graph1[i] = new ArrayList<>();
			graph2[i] = new ArrayList<>();
		}

		for (int i = 0; i < M; i++) {
			fields = br.readLine().split(" ");
			int x = Integer.parseInt(fields[0]);
			int y = Integer.parseInt(fields[1]);
			graph1[x].add(y);
		}

		for (int i = 0; i < M; i++) {
			fields = br.readLine().split(" ");
			int x = Integer.parseInt(fields[0]);
			int y = Integer.parseInt(fields[1]);
			graph2[x].add(y);
		}
		br.close();
	}

	static void initializeDp() {
		dp1 = new int[N + 1];
		dp2 = new int[N + 1];
		dp1[1] = 1;
		dp2[1] = 1;
	}

	static void countCommonPaths() {
		List<Integer> topoSort = topologicalSort(graph1, graph2);
		for (int node : topoSort) {
			for (Integer neighbor : graph1[node]) {
				if (graph2[node].contains(neighbor)) {
					dp1[neighbor] = (dp1[neighbor] + dp1[node]) % MOD;
					dp2[neighbor] = (dp2[neighbor] + dp2[node]) % MOD;
				}
			}
		}
	}

	static List<Integer> topologicalSort(ArrayList<Integer>[]... graphs) {
		int[] inDegree = new int[N + 1];
		for (ArrayList<Integer>[] graph : graphs) {
			for (int i = 1; i <= N; i++) {
				for (int node : graph[i]) {
					inDegree[node]++;
				}
			}
		}

		Queue<Integer> queue = new LinkedList<>();
		for (int i = 1; i <= N; i++) {
			if (inDegree[i] == 0) {
				queue.add(i);
			}
		}

		List<Integer> sortedNodes = new ArrayList<>();
		while (!queue.isEmpty()) {
			int node = queue.poll();
			sortedNodes.add(node);
			for (ArrayList<Integer>[] graph : graphs) {
				for (int next : graph[node]) {
					if (--inDegree[next] == 0) {
						queue.add(next);
					}
				}
			}
		}
		return sortedNodes;
	}

	public static void main(String[] args) throws IOException {
		readInput();
		initializeDp();
		countCommonPaths();
		int commonPaths = Math.min(dp1[N], dp2[N]);
		writeOutput(commonPaths);
	}

	static void writeOutput(int result) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("numarare.out"));
		bw.write(result + "\n");
		bw.close();
	}
}
