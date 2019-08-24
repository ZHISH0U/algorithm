package algorithm.NetworkFlow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Dinic {
    private int[] it;
    private int[] dp;//深度
    private int[] head, to, cap, next;//边的各个参数
    int cnt;

    public Dinic(int PointNum, int EdgeNum) {
        it = new int[PointNum];
        dp = new int[PointNum];
        head = new int[PointNum];
        to = new int[EdgeNum];
        cap = new int[EdgeNum];
        next = new int[EdgeNum];
        init();
    }

    public void init() {
        cnt=0;
        Arrays.fill(head, -1);
    }

    public void addEdge(int a, int b, int capacity, int revcap) {
        cap[cnt] = capacity;    cap[cnt | 1] = revcap;
        to[cnt] = b;            to[cnt | 1] = a;
        next[cnt] = head[a];    next[cnt | 1] = head[b];
        head[a] = cnt;          head[b] = cnt | 1;
        cnt += 2;
    }

    public int maxFlow(int s, int t) {
        int sum = 0, flow;
        while (bfs(s, t)) {//求分层图
            for (int i = 0; i < head.length; i++)
                it[i] = head[i];
            flow = dfs(s, t, Integer.MAX_VALUE);//dfs求出流量
            if (flow != 0) sum += flow;//若流量不为0，加入
            else break;//流量为0，说明没有增广路，返回最大流
        }
        return sum;
    }

    private boolean bfs(int s, int t) {
        Arrays.fill(dp, 0);
        dp[s] = 1;
        Queue<Integer> qu = new LinkedList<>();
        qu.add(s);
        while (!qu.isEmpty()) {
            int cur = qu.poll();
            for (int i = head[cur]; i != -1; i = next[i])
                if (dp[to[i]] == 0 && cap[i] > 0) {
                    dp[to[i]] = dp[cur] + 1;
                    qu.add(to[i]);
                }
        }
        return dp[t] != 0;
    }

    private int dfs(int cur, int t, int flow) {
        if (cur == t) return flow;
        int sum = 0, f;
        for (int i = it[cur]; i != -1; it[cur] = i = next[i]) {//当前弧优化
            if (cap[i] > 0 && dp[cur] + 1 == dp[to[i]]) {//若满足边权不为0且满足分层图的性质
                f = dfs(to[i], t, Math.min(flow, cap[i]));//继续找增广路
                if (f == 0) dp[to[i]] = 0;//去掉已经增广完的点
                sum += f;//统计最大流
                flow -= f;//剩余容量
                cap[i] -= f;
                cap[i ^ 1] += f;//更新剩余容量
                if (flow == 0) return sum;//若前面已经流完了，直接返回
            }
        }
        return sum;//返回最大流量
    }
}