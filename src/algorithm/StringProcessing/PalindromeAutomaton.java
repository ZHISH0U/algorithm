package algorithm.StringProcessing;

/**
 * 回文自动机，root[0]为偶数长，root[1]为奇数长
 */
public class PalindromeAutomaton {
    final static char base='a';
    Node root[],last,topolast;
    String s;
    public PalindromeAutomaton(){
        root=new Node[2];
        root[0]=new Node(0);
        root[1]=new Node(-1);
        root[0].fail=root[1].fail=root[1];
        root[1].topo=root[0];
    }
    public void build(String str){
        s=str;
        topolast=last=root[1];
        for(int i=0;i<s.length();i++)
            put(s.charAt(i),i);
        buildCount();
    }
    private void put(char c,int pos){
        Node cur=getFail(last,pos);
        if(!cur.contains(c)){
            Node fail=getFail(cur.fail,pos).get(c);
            Node now=cur.put(c);
            now.topo=topolast;
            topolast=now;
            now.fail=fail;
        }
        cur.get(c).cnt++;
        last=cur.get(c);
    }
    private Node getFail(Node cur,int pos){
        while(pos-cur.len-1<0||s.charAt(pos-cur.len-1)!=s.charAt(pos))cur=cur.fail;
        return cur;
    }
    private void buildCount(){
        Node cur=topolast;
        while(cur!=null){
            cur.fail.cnt+=cur.cnt;
            cur=cur.topo;
        }
    }
    public class Node{
        int len,cnt=0;
        private Node fail,topo,next[];
        public Node(int length){
            len=length;
            next=new Node[26];
        }
        public Node get(char c){
            return next[c-base]==null?root[0]:next[c-base];
        }
        public Node put(char c){
            if(next[c-base]==null)next[c-base]=new Node(len+2);
            return next[c-base];
        }
        public boolean contains(char c){
            return next[c-base]!=null;
        }
    }
}