package algorithm.StringProcessing;

import java.util.LinkedList;
import java.util.Queue;

public class ACAutomaton {
    private Node root;
    private char base='a';
    public ACAutomaton(){
        root=new Node();
    }
    public void put(String pattern){
        Node cur=root;
        for(int i=0;i<pattern.length();i++)
            cur=cur.put(pattern.charAt(i));
        cur.endNum++;
    }
    /**
     * 匹配字符串
     */
    public void match(String source){
        Node p=root,tmp;
        for (char value : source.toCharArray()) {
            while (!p.contains(value) && p != root) p = p.fail;
            p = p.get(value);
            if (p == null) p = root;
            tmp = p;
            while (tmp != root) {
                if(tmp.endNum>0) //匹配到模式串
                    tmp.cnt++;
                tmp=tmp.fail;
            }
        }
    }
    //生成fail指针
    private void buildFailPointer(){
        Queue<Node> queue=new LinkedList<>();
        Node tmp,t1;
        queue.add(root);
        while (!queue.isEmpty()){
            tmp=queue.poll();
            for(char i=base;i<=base+25;i++){
                if(tmp.contains(i)){
                    if(tmp==root) tmp.get(i).fail=root;
                    else { //与kmp的next数组求法相同
                        t1=tmp.fail;
                        while(t1!=null&&!t1.contains(i))
                            t1=t1.fail;
                        if(t1!=null)tmp.get(i).fail=t1.get(i);
                        else tmp.get(i).fail=root;
                    }
                    queue.add(tmp.get(i));
                }
            }
        }
    }
    public class Node{
        private Node fail;
        private int endNum=0,cnt=0;
        private Node[]next=new Node[26];
        public Node get(char c){
            return next[c-base];
        }
        public Node put(char c){
            if(next[c-base]==null) next[c-base]=new Node();
            return next[c-base];
        }
        public int getCount(){
            return cnt;
        }
        public boolean contains(char c) {
            return next[c-base]!=null;
        }
    }
}
