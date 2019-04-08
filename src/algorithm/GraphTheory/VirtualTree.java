package algorithm.GraphTheory;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 虚树
 */
public class VirtualTree{
    ArrayList<Integer>[]edge,ve;//原边，虚边
    int[]dfn,rk,min,stk,dp[];
    int len=0,sz;
    public VirtualTree(ArrayList<Integer>[]e,int root){
        init(e);
        dfs(root,-1);
        //st表
        int l=log2(len)+1;
        dp=new int[len][l];
        for(int i=0;i<len;i++)dp[i][0]=min[i];
        for(int i=1;i<l;i++)
            for(int j=0;j+(1<<i)-1<len;j++)
                dp[j][i]=Math.min(dp[j][i-1],dp[j+(1<<i-1)][i-1]);
    }
    //标记dfs序+树上st
    private void dfs(int cur,int fa){
        dfn[cur]=min[len]=len;
        rk[len++]=cur;
        for(int p:edge[cur]){
            if(p==fa)continue;
            dfs(p,cur);
            //把末端节点的值改成祖先节点的值，免去多余的添加节点
            min[len-1]=dfn[cur];
        }
    }

    /**
     * 从s(包含)到t(不包含)
     */
    public ArrayList<Integer>[] extract(int[]v,int s,int t){
        for(int i=s;i<t;i++) v[i]=dfn[v[i]];
        Arrays.sort(v,s,t);
        for(int i=s;i<t;i++) v[i]=rk[v[i]];
        sz=0;
        insert(v[s]);
        for(int i=s+1;i<t;i++)
            //防止重复加边
            if(v[i]!=v[i-1])insert(v[i]);
        while (sz>1) connect(stk[sz],stk[--sz]);
        return ve;
    }
    private void insert(int p){
        int lca;
        if(sz==0||(lca=lca(p,stk[sz]))==stk[sz]){
            push(p);return;
        }
        while(sz>1 && dfn[lca] <= dfn[stk[sz-1]])
            connect(stk[sz],stk[--sz]);

        if(lca != stk[sz]) {
            ve[lca].clear();
            connect(lca,stk[sz]);
            stk[sz]=lca;
        }
        push(p);
    }

    public int lca(int a,int b){
        if(a==b)return a;
        int l=dfn[a],r=dfn[b];
        if(l>r){
            l^=r;r^=l;l^=r;
        }
        return rk[query(l,r-1)];
    }
    private void connect(int a,int b){
        ve[a].add(b);
        ve[b].add(a);
    }
    private void push(int a){
        stk[++sz]=a;
        ve[a].clear();
    }
    private int query(int l,int r){
        int k=log2(r-l+1);
        return Math.min(dp[l][k],dp[r-(1<<k)+1][k]);
    }
    private int log2(int n){
        return 31-Integer.numberOfLeadingZeros(n);
    }
    private void init(ArrayList<Integer>[]e){
        edge=e;
        ve=new ArrayList[e.length];
        for(int i=0;i<ve.length;i++)ve[i]=new ArrayList<>();
        dfn=new int[e.length];
        rk=new int[e.length];
        min=new int[e.length];
        stk=new int[e.length+1];
    }
}