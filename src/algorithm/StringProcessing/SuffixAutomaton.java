package algorithm.StringProcessing;

/**
 * 后缀自动机，right改为位置后buildRight可得right集合
 */
public class SuffixAutomaton {
    private final char base='a',EndChar='z'+1;
    private State root,strLast,topoLast;
    private int size;
    public SuffixAutomaton(){
        topoLast=strLast=root = new State(0);
        size=1;
    }
    public SuffixAutomaton build(String s) {
        strLast=root;
        for (char c : s.toCharArray()) put(c);
        put(EndChar);//加入一个结尾字符，构建广义SAM时防止多个串连在一起
        return this;
    }
    public void put(char c){
        State cur,p;//当前状态
        if(strLast.contains(c)){
            //广义SAM构建
            cur=strLast.get(c);
            cur.right+=1;
            if(strLast.length+1!=cur.length)
                cur=addExtraState(strLast,c);
        }else {
            size++;
            cur = new State(strLast.length + 1);
            cur.topo = topoLast;
            cur.right = 1;
            for (p = strLast; p != null && !p.contains(c); p = p.link)
                p.put(c, cur);
            if (p != null){
                State q = p.get(c);
                if (p.length + 1 == q.length) {
                    cur.link = q;
                }else cur.link=addExtraState(p,c);
            }else cur.link = root;
            topoLast=cur;
        }
        strLast = cur;
        //add(cur); //将cur及其link加入states
    }
    public State addExtraState(State p,char c){
        size++;
        State q=p.get(c);
        State clone = new State(q);
        clone.length = p.length + 1;
        for (; p != null && p.get(c) == q; p = p.link)
            p.put(c, clone);
        q.link=q.topo = clone;
        return clone;
    }
    public void buildRight(){
        State cur=topoLast;
        while(cur.topo!=null){
            if(cur.link!=null)cur.link.right+=cur.right;
            cur=cur.topo;
        }
    }
    public State root(){
        return root;
    }
    public class State {
        int length,right=0;
        State link,topo;//right集合的父集，拓扑序的前一个
        private State[] next = new State[27];
        public State(int len){
            length=len;
        }
        public State(State copy){
            link=copy.link;
            next=copy.next.clone();
            topo=copy.topo;
        }
        public State get(char c){
            return next[c-base];
        }
        public void put(char c,State s){
            next[c-base]=s;
        }
        public boolean contains(char c){
            return next[c-base]!=null;
        }
    }

/*另一种构建拓扑序的方法
    State states[]=new State[MaxLen*2];//广义后缀自动机为MaxLen*MaxLen
    private void add(State cur){
        //递归将cur及其link加入states
    }
    //len为states中状态的个数，tp为拓扑排序后的数组
    public void topoSort() {
        int[]sum=new int[MaxLen];
        State[]tp=new State[len];
        for(int i = 0; i < len; i++) sum[states[i].length]++;
        for(int i = 1; i <= MaxLen; i++) sum[i] += sum[i-1];
        for(int i = 0; i <len; i++) tp[--sum[states[i].length]] = temp[i];
    }
*/
/*
    // random tests
    public static void main(String[] args) {
        Random rnd = new Random(1);
        for (int step = 0; step < 100000; step++) {

            String res1 = lcs(s1, s2);
            int res2 = slowLcs(s1, s2);
            if (res1.length() != res2)
                throw new RuntimeException();
        }
    }

    static int bestState;

    static String lcs(String a, String b) {
        State[] st = buildSuffixAutomaton(a);
        bestState = 0;
        int len = 0;
        int bestLen = 0;
        int bestPos = -1;
        for (int i = 0, cur = 0; i < b.length(); ++i) {
            char c = b.charAt(i);
            if (st[cur].next[c] == -1) {
                for (; cur != -1 && st[cur].next[c] == -1; cur = st[cur].link) {
                }
                if (cur == -1) {
                    cur = 0;
                    len = 0;
                    continue;
                }
                len = st[cur].length;
            }
            ++len;
            cur = st[cur].next[c];
            if (bestLen < len) {
                bestLen = len;
                bestPos = i;
                bestState = cur;
            }
        }
        return b.substring(bestPos - bestLen + 1, bestPos + 1);
    }

    static int[] occurrences(String haystack, String needle) {
        String common = lcs(haystack, needle);
        if (!common.equals(needle))
            return new int[0];
        List<Integer> list = new ArrayList<>();
        dfs(buildSuffixAutomaton(haystack), bestState, needle.length(), list);
        int[] res = new int[list.size()];
        for (int i = 0; i < res.length; i++)
            res[i] = list.get(i);
        Arrays.sort(res);
        return res;
    }

    static void dfs(State[] st, int p, int len, List<Integer> list) {
        if (st[p].endpos != -1 || p == 0)
            list.add(st[p].endpos - len + 1);
        for (int x : st[p].ilink)
            dfs(st, x, len, list);
    }
*/
}
