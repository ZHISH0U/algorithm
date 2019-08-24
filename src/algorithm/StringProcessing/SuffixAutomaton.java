package algorithm.StringProcessing;

/**
 * 后缀自动机，endpos改为位置后buildEndpos可得endpos集合
 */
public class SuffixAutomaton {
    private final char base='a';
    private int root,strLast,topoLast,size;
    private int[]length,endpos,link,topo;
    int[][]next;
    public SuffixAutomaton(int len){
        length=new int[len];
        endpos=new int[len];
        link=new int[len];
        topo=new int[len];
        next=new int[26][len];
        clear();
    }
    void clear(){
        size=0;
        topoLast=strLast=root=copy(0);
    }
    public SuffixAutomaton build(String s) {
        strLast=root;
        for (char c:s.toCharArray()) put(c-base);
        return this;
    }
    public void put(int c){
        int cur,p;//当前状态
        if(next[c][strLast]!=0){
            //广义SAM构建
            cur=next[c][strLast];
            if(length[strLast]+1!=length[cur])
                cur=addExtraState(strLast,c);
            endpos[cur]+=1;
        }else {
            cur = copy(0);
            length[cur]=length[strLast] + 1;
            topo[cur] = topoLast;
            endpos[cur] = 1;
            for (p = strLast; p != 0 && next[c][p]==0; p = link[p])
                next[c][p]=cur;
            if (p != 0){
                int q = next[c][p];
                if (length[p] + 1 == length[q]) {
                    link[cur] = q;
                }else link[cur]=addExtraState(p,c);
            }else link[cur] = root;
            topoLast=cur;
        }
        strLast = cur;
        //add(cur); //将cur及其link加入states
    }
    public int addExtraState(int p,int c){
        int q=next[c][p];
        int clone = copy(q);
        length[clone] = length[p] + 1;
        for (; p != 0 && next[c][p] == q; p = link[p])
            next[c][p]=clone;
        link[q]=topo[q] = clone;
        //endpos[clone]=endpos[q];   每次put都更新父节点的endpos需要添加这行代码
        return clone;
    }
    public void buildEndpos(){
        int cur=topoLast;
        while(topo[cur]!=0){
            if(link[cur]!=0)endpos[link[cur]]+=endpos[cur];
            cur=topo[cur];
        }
    }
    int copy(int a){
        size++;
        length[size]=endpos[size]=0;
        link[size]=link[a];
        topo[size]=topo[a];
        for(int i=0;i<next.length;i++)
            next[i][size] = next[i][a];
        return size;
    }

/*另一种构建拓扑序的方法
    State states[]=new State[MaxLen*2];//广义后缀自动机为MaxLen*MaxLen
    private void add(State cur){
        //递归将cur及其link加入states
    }
    //len为states中状态的个数，tp为拓扑排序后的数组
    public void topoSort() {
        int[]sum=new int[MaxLen+1];
        State[]tp=new State[len];
        for(int i = 0; i < len; i++) sum[states[i].length]++;
        for(int i = 1; i <= MaxLen; i++) sum[i] += sum[i-1];
        for(int i = 0; i <len; i++) tp[--sum[states[i].length]] = temp[i];
    }
*/
}