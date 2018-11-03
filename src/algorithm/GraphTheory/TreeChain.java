package algorithm.GraphTheory;

import java.util.Arrays;
import java.util.List;

/**
 * 树链剖分，将树状图划分为多条链，进行区间维护，亦可求最小公共祖先。
 * 边权可将边对应到指向的节点上，转化为点权。
 */
public class TreeChain{
    private List<Integer>[]edge;
    //weight:节点的权重，top:节点所在链的顶端，deep:节点的深度
    //son:节点的重子节点，father:节点的父节点
    //id:节点剖分后的编号，rank:编号对应的节点
    private int[] w,tp,dp,sn,fa,id,rk;
    private int size=0;
    //edges:边的集合，root:根节点
    public TreeChain(List<Integer>[] edges,int root){
        this.edge=edges;
        w=new int[edges.length];
        tp=new int[edges.length];
        dp=new int[edges.length];
        sn=new int[edges.length];
        fa=new int[edges.length];
        id=new int[edges.length];
        rk=new int[edges.length];
        Arrays.fill(sn,-1);
        split(root,-1,0);
        link(root,root);
    }
    private void split(int cur,int faz,int deep){
        dp[cur]=deep;
        fa[cur]=faz;
        w[cur]=1;
        for(int p:edge[cur]){
            if(p==faz) continue;
            split(p,cur,deep+1);
            w[cur]+=w[p];
            if(sn[cur]==-1||w[sn[cur]]<w[p])
                sn[cur]=p;
        }
    }
    private void link(int cur,int top){
        tp[cur]=top;
        id[cur]=size;
        rk[size++]=cur;
        if(sn[cur]!=-1)return;
        link(sn[cur],top);
        for(int p:edge[cur]){
            if(p==sn[cur]||p==fa[cur])continue;
            link(p,p);
        }
    }
    //求LCA（区间更新、查询在注释处操作）
    public int lca(int a,int b){
        int ta=tp[a],tb=tp[b];
        while(ta!=tb){
            if(dp[ta]>dp[tb]){
                //update(id[ta],id[a],val);
                a=fa[ta];
            }else {
                //update(id[tb],id[b],val);
                b=fa[tb];
            }
            ta=tp[a];tb=tp[b];
        }
        //update(Math.min(id[a],id[b]),Math.max(id[a],id[b]),val);
        return dp[a]>dp[b]?b:a;
    }
}