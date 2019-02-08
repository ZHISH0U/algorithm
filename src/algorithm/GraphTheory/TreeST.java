package algorithm.GraphTheory;

import algorithm.DataStructure.SparseTable;

import java.util.Arrays;
import java.util.List;

/**
 * ST表在线求LCA，预处理O(nlogn)，查询O(1)
 */
public class TreeST {
    private List<Integer>[]edge;
    //id:dfs遍历的顺序，rk:id对应的节点。
    //min:当前id遍历到下一个id之间遇到的最小id
    private int id[],rk[],min[],len;
    private SparseTable st;
    public TreeST(List<Integer>[]edges, int root){
        edge=edges;
        id=new int[edges.length];
        rk=new int[edges.length];
        min=new int[edges.length];
        Arrays.fill(id,-1);
        len=0;
        dfs(root,-1);
        st=new SparseTable(min) {
            @Override
            public int get(int a, int b) {
                return Math.min(a,b);
            }
        };
    }
    public int lca(int a,int b){
        if(a==b)return a;
        int l=id[a],r=id[b];
        if(l>r){
            l^=r;
            r^=l;
            l^=r;
        }
        return rk[st.query(l,r-1)];
    }
    public void dfs(int cur,int fa){
        id[cur]=len;
        rk[len]=cur;
        min[len]=len;
        len++;
        for(int p:edge[cur]){
            if(p==fa)continue;
            dfs(p,cur);
            //把末端节点的值改成祖先节点的值，免去多余的添加节点
            min[len-1]=id[cur];
        }
    }
}
