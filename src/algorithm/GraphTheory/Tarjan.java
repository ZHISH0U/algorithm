package algorithm.GraphTheory;

import java.util.List;
import java.util.Map;

/**
 * Tarjan算法
 */
public class Tarjan {
    private List<Integer>[]edge;
    private Map<Integer,List<Pair>>query;
    private int root,lca[];
    private boolean[] vis;
    private UFS ufs;
    public Tarjan(List<Integer>[]edges,int root){
        edge=edges;
        this.root=root;
    }
    public void lca(Map<Integer,List<Pair>>queries,int[]LCAs){
        query=queries;
        lca=LCAs;
        vis=new boolean[edge.length];
        ufs=new UFS(edge.length);
        dfs(root);
    }
    private void dfs(int cur){
        vis[cur]=true;
        for(int p:edge[cur]){
            if(vis[p])continue;
            dfs(p);
            ufs.comb(cur,p);
        }
        if(!query.containsKey(cur))return;
        for(Pair p:query.get(cur))
            if(vis[p.to]) lca[p.index]=ufs.find(p.to);
    }
    public static class Pair{
        int to,index;
        public Pair(int a,int b){
            to=a;
            index=b;
        }
    }
}
