package algorithm.GraphTheory;

import java.util.List;

/**
 * 树上差分，可用于求树上路径覆盖、新增路径后点的通路数量等问题。
 */
public class TreeDiffer {
    TreeST st;
    private List<Integer>[]edge;
    private int[] fa,d;
    private int root;
    public TreeDiffer(List<Integer>[]edges,int root){
        edge=edges;
        fa=new int[edges.length];
        d=new int[edges.length];
        dfs1(root,-1);
        this.root=root;
        st=new TreeST(edges,root);
    }
    private void dfs1(int cur,int faz){
        fa[cur]=faz;
        for(int p:edge[cur])
            if(p!=faz)dfs1(p,cur);
    }
    public void update(int x,int y,int val){
        d[x]+=val;
        d[y]+=val;
        int lca=st.lca(x,y);
        d[lca]-=val;
        if(fa[lca]!=-1)d[fa[lca]]-=val;
        //边差分改为 d[lca]-=2*val;
    }
    public int[] finish(){
        dfs2(root,-1);
        return d;
    }
    private int dfs2(int cur,int faz){
        for(int p:edge[cur])
            if(p!=faz) d[cur]+=dfs2(p,cur);
        return d[cur];
    }
}
