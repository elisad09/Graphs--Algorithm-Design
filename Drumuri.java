import java.io.*;
import java.util.*;

public class Drumuri {
	static ArrayList<Integer>[] adj, revAdj, adjWeights, revAdjWeights;
	static int N, M, x, y, z;
	static long[] distFromX, distFromY, distToZ;

	public static void main(String[] args) throws IOException {
		readInput();
		computeDistances();
		writeOutput();
	}

	static void initArrays() {
		adj = createEmptyArrayList(N);
		revAdj = createEmptyArrayList(N);
		adjWeights = createEmptyArrayList(N);
		revAdjWeights = createEmptyArrayList(N);
	}

	static ArrayList<Integer>[] createEmptyArrayList(int size) {
		ArrayList<Integer>[] array = new ArrayList[size + 1];
		for (int i = 0; i <= size; i++) {
			array[i] = new ArrayList<>();
		}
		return array;
	}


	static void readInput() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("drumuri.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		initArrays();

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			adj[a].add(b);
			adjWeights[a].add(c);
			revAdj[b].add(a);
			revAdjWeights[b].add(c);
		}

		st = new StringTokenizer(br.readLine());
		x = Integer.parseInt(st.nextToken());
		y = Integer.parseInt(st.nextToken());
		z = Integer.parseInt(st.nextToken());
		br.close();
	}

	static void computeDistances() {
		distFromX = dijkstra(x, adj, adjWeights);
		distFromY = dijkstra(y, adj, adjWeights);
		distToZ = dijkstra(z, revAdj, revAdjWeights);
	}

	static long[] dijkstra(int start, ArrayList<Integer>[] graph, ArrayList<Integer>[] weights) {
		long[] dist = new long[N + 1];
		boolean[] visited = new boolean[N + 1];
		Arrays.fill(dist, Long.MAX_VALUE);
		dist[start] = 0;

		PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(e -> e[1]));
		pq.add(new long[]{start, 0});

		while (!pq.isEmpty()) {
			long[] curr = pq.poll();
			int u = (int) curr[0];

			if (visited[u]) continue;
			visited[u] = true;

			for (int i = 0; i < graph[u].size(); i++) {
				int v = graph[u].get(i);
				int weight = weights[u].get(i);
				if (!visited[v] && dist[u] + weight < dist[v]) {
					dist[v] = dist[u] + weight;
					pq.add(new long[]{v, dist[v]});
				}
			}
		}

		return dist;
	}

	static void writeOutput() throws IOException {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("drumuri.out"))) {
			long minCost = Long.MAX_VALUE;

			for (int i = 1; i <= N; i++) {
				if (distFromX[i] == Long.MAX_VALUE || distFromY[i] == Long.MAX_VALUE || distToZ[i] == Long.MAX_VALUE) {
					continue;
				}
				long totalCost = distFromX[i] + distFromY[i] + distToZ[i];
				if (totalCost < minCost) {
					minCost = totalCost;
				}
			}

			if (minCost == Long.MAX_VALUE) {
				minCost = -1;
			}

			bw.write(String.valueOf(minCost));
			bw.newLine();
		}
	}

}
