package algorithm.NetworkFlow;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Dinic {
    private int[] it;
    private List<Edge>[] edge;
    private int[] dp;//深度

    public void setEdges(List<Edge>[] edges) {
        it = new int[edges.length];
        edge = edges;
    }

    public int maxFlow(int s, int t) {
        int sum = 0, flow;
        while (bfs(s, t)[t] != 0) {//求分层图
            for (int i = 0; i < edge.length; i++)
                it[i] = 0;
            flow = dfs(s, t, Integer.MAX_VALUE);//dfs求出流量
            if (flow != 0) sum += flow;//若流量不为0，加入
            else break;//流量为0，说明没有增广路，返回最大流
        }
        return sum;
    }

    private int[] bfs(int s, int t) {
        dp = new int[edge.length];
        dp[s] = 1;
        Queue<Integer> qu = new LinkedList<>();
        qu.add(s);
        while (!qu.isEmpty()) {
            int cur = qu.poll();
            for (Edge e : edge[cur])
                if (dp[e.to] == 0 && e.capacity > 0) {
                    dp[e.to] = dp[cur] + 1;
                    if (e.to == t) return dp;//玄学优化
                    qu.add(e.to);
                }
        }
        return dp;
    }

    private int dfs(int cur, int t, int flow) {
        if (cur == t) return flow;
        int sum = 0, f;
        for (; it[cur] < edge[cur].size(); it[cur]++) {//当前弧优化
            Edge e = edge[cur].get(it[cur]);
            if (e.capacity > 0 && dp[cur] + 1 == dp[e.to]) {      //若满足边权不为0且满足分层图的性质
                f = dfs(e.to, t, Math.min(flow, e.capacity));      //继续找增广路
                if (f == 0) dp[e.to] = 0;      //去掉已经增广完的点
                sum += f;      //统计最大流
                flow -= f;      //剩余容量
                e.capacity -= f;
                e.rev.capacity += f;      //更新剩余容量
                if (flow == 0) return sum;      //若前面已经流完了，直接返回
            }
        }
        return sum;      //返回最大流量
    }

    public static class Edge {
        public Edge(int a, int b) {
            to = a;
            capacity = b;
        }

        int to, capacity;
        Edge rev;
    }
}