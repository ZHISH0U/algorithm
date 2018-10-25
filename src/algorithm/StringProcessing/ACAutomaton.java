package algorithm.StringProcessing;

import com.fyx.lib.algorithm.DataStructure.Trie;

import java.util.LinkedList;
import java.util.Queue;

public class ACAutomaton {
    private Trie trie;
    private char base='a';
    public ACAutomaton(){
        trie=new Trie() {
            @Override
            public TrieNode getNode() {
                return new AC_Node();
            }
        };
    }
    public void put(String pattern){
        trie.put(pattern);
    }
    /**
     * 匹配字符串
     */
    public void match(String source){
        AC_Node p=(AC_Node) trie.root(),root=p,tmp;
        for (char value : source.toCharArray()) {
            while (!p.contains(value) && p != root) p = p.fail;
            p = p.get(value);
            if (p == null) p = root;
            tmp = p;
            while (tmp != root) {
                if(tmp.getEndNum()>0) {
                    //匹配到模式串
//------------在这里处理匹配到模式串后的操作------------
                }
                tmp=tmp.fail;
            }
        }
    }
    //生成fail指针
    private void buildFailPointer(){
        Queue<AC_Node> queue=new LinkedList<>();
        AC_Node tmp,t1,root=(AC_Node)trie.root();
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
    public class AC_Node extends Trie.TrieNode{
        private AC_Node fail;
        private AC_Node[]next=new AC_Node[26];
        @Override
        public AC_Node get(char c){
            return next[c-base];
        }
        public AC_Node put(char c){
            if(next[c-base]==null) next[c-base]=new AC_Node();
            return next[c-base];
        }
        @Override
        public boolean contains(char c) {
            return next[c-base]!=null;
        }
    }
}
