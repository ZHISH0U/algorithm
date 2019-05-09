package algorithm.GraphTheory;

import java.util.ArrayList;
import java.util.Stack;

public class Tarjan{
    ArrayList<Integer>[]edge;
    boolean[]vis;
    int[]dfn,low,belong;//belong为所属的联通分量的标号
    int id=0,num=0;
    Stack<Integer> st=new Stack<>();
    public Tarjan(ArrayList<Integer>[]e){
        edge=e;
        dfn=new int[e.length];
        low=new int[e.length];
        belong=new int[e.length];
        vis=new boolean[e.length];
    }

    /**
     * 求出强连通分量、割点，点标号从1开始
     * 无向图桥
     */
    public void tarjan(){
        for(int i=1;i<edge.length;i++){
            if(dfn[i]==0)dfs(i,-1);
        }
    }
    //有向图去掉fa，有重边的无向图遇到fa只continue一次
    private void dfs(int cur,int fa){
        dfn[cur]=low[cur]=++id;
        st.add(cur);
        vis[cur]=true;
        for(int p:edge[cur]){
            if(p==fa)continue;
            if(dfn[p]==0){
                //child++;
                dfs(p,cur);
                low[cur]=Math.min(low[cur],low[p]);
                //if(low[p]>=dfn[cur]) iscut[cur]=true; 求割点，改为low[p]>dfn[cur]则为割边
            }else if(vis[p]){
                //求割点割边去掉
                low[cur]=Math.min(low[cur],low[p]);
            }
            //}else low[cur]=Math.min(low[cur],dfn[p]); 求割点割边
        }
        //求强联通分量/缩点
        if(low[cur]==dfn[cur]){
            num++;
            while(!st.isEmpty()&&low[st.peek()]==dfn[cur]){
                vis[st.peek()]=false;
                belong[st.pop()]=num;
            }
        }
        //if(fa==-1&&child>1)iscut[cur]=true; 如果根节点，且子树的个数多于1，则根节点是割点
    }
}