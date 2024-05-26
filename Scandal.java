import java.io.*;
import java.util.*;

class Node {
	Set<Integer> edges = new HashSet<>();
	int time = -1;
	int low = -1;
	boolean inStack = false;

	boolean hasValue = false;
	boolean value = false;
}

class Graph {
	private List<Node> nodes;

	Graph(int vars) {
		nodes = new ArrayList<>(2 * vars);
		for (int i = 0; i < 2 * vars; i++) {
			nodes.add(new Node());
		}
	}

	int vars() {
		return nodes.size() / 2;
	}

	void imply(int a, int b) {
		nodes.get(realIndex(a)).edges.add(b);
		nodes.get(realIndex(-b)).edges.add(-a);
	}

	Node get(int index) {
		return nodes.get(realIndex(index));
	}

	private int realIndex(int index) {
		return index > 0 ? (index - 1) : (nodes.size() + index);
	}
}

public class Scandal {
	private static void extractComp (Graph graph, int endNode, Stack<Integer> stack, List<Integer> order) {
		while (order.isEmpty() || !Objects.equals(order.get(order.size() - 1), endNode)) {
			int node = stack.pop();
			graph.get(node).inStack = false;
			order.add(node);
		}
	}

	private static void tarjan (Graph graph, int startNode, Stack<Integer> stack, List<Integer> order, int[] time) {
		Stack<Integer> dfsStack = new Stack<>();
		Stack<int[]> callStack = new Stack<>();
		dfsStack.push(startNode);
		callStack.push(new int[]{startNode, 0});
		graph.get(startNode).time = graph.get(startNode).low = (++time[0]);
		graph.get(startNode).inStack = true;
		stack.push(startNode);

		while (!dfsStack.isEmpty()) {
			int node = dfsStack.peek();
			int[] call = callStack.peek();

			if (call[1] < graph.get(node).edges.size()) {
				int next = (int) graph.get(node).edges.toArray()[call[1]];
				call[1]++;
				if (graph.get(next).time == -1) {
					dfsStack.push(next);
					callStack.push(new int[]{next, 0});
					graph.get(next).time = graph.get(next).low = (++time[0]);
					graph.get(next).inStack = true;
					stack.push(next);
				} else if (graph.get(next).inStack) {
					graph.get(node).low = Math.min(graph.get(node).low, graph.get(next).time);
				}
			} else {
				dfsStack.pop();
				callStack.pop();
				if (graph.get(node).low == graph.get(node).time) {
					extractComp(graph, node, stack, order);
				}
				if (!dfsStack.isEmpty()) {
					int prev = dfsStack.peek();
					graph.get(prev).low = Math.min(graph.get(prev).low, graph.get(node).low);
				}
			}
		}
	}

	private static List<Integer> findOrder(Graph graph) {
		List<Integer> order = new ArrayList<>();
		int[] time = {0};
		for (int i = 1; i <= graph.vars(); i++) {
			if (graph.get(i).time == -1) {
				Stack<Integer> stack = new Stack<>();
				tarjan(graph, i, stack, order, time);
			}
			if (graph.get(-i).time == -1) {
				Stack<Integer> stack = new Stack<>();
				tarjan(graph, -i, stack, order, time);
			}
		}
		Collections.reverse(order);
		return order;
	}

	private static List<Integer> solve(Graph graph) {
		for (int node : findOrder(graph)) {
			if (!graph.get(node).hasValue) {
				graph.get(node).value = false;
				graph.get(node).hasValue = true;
				graph.get(-node).value = true;
				graph.get(-node).hasValue = true;
			}
		}

		List<Integer> res = new ArrayList<>();
		for (int i = 1; i <= graph.vars(); i++) {
			if (graph.get(i).value) {
				res.add(i);
			}
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("scandal.in"));
		PrintWriter pw = new PrintWriter(new FileWriter("scandal.out"));

		String[] firstLine = br.readLine().split(" ");
		int vars = Integer.parseInt(firstLine[0]);
		int conds = Integer.parseInt(firstLine[1]);

		Graph graph = new Graph(vars);
		for (int i = 0; i < conds; i++) {
			String[] line = br.readLine().split(" ");
			int a = Integer.parseInt(line[0]);
			int b = Integer.parseInt(line[1]);
			int type = Integer.parseInt(line[2]);

			if (type == 0) {
				graph.imply(-a, b);
				graph.imply(-b, a);
			} else if (type == 1) {
				graph.imply(-a, -b);
			} else if (type == 2) {
				graph.imply(-b, -a);
			} else if (type == 3) {
				graph.imply(a, -b);
				graph.imply(b, -a);
			}
		}

		List<Integer> res = solve(graph);
		pw.println(res.size());
		for (int node : res) {
			pw.println(node);
		}

		pw.close();
		br.close();
	}
}
