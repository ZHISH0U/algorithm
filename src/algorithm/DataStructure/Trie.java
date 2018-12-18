package algorithm.DataStructure;

/**
 * 字典树
 * 对于字符范围大但稀疏的字典树，可采用左儿子右兄弟的结构
 */
public abstract class Trie {
    protected TrieNode root;
    public Trie(){
        root=getNode();
    }
    public TrieNode put(String str,int beginIndex,int endIndex){
        TrieNode node;
        node=root;
        for(int i=beginIndex;i<endIndex;i++)
            node=node.put(str.charAt(i));
        node.EndNum++;
        return node;
    }
    public TrieNode get(String s){
        TrieNode node=root;
        for(int i=0;i<s.length();i++){
            if(!node.contains(s.charAt(i)))return null;
            else node=node.get(s.charAt(i));
        }
        return node;
    }
    public Trie put(String... strs){
        for(String str:strs)
            put(str);
        return this;
    }
    public TrieNode put(String str){
        return put(str,0,str.length());
    }
    public TrieNode root(){
        return root;
    }
    public abstract TrieNode getNode();
    public static abstract class TrieNode{
        protected int EndNum=0;
        public int getEndNum(){
            return EndNum;
        }
        public abstract TrieNode put(char c);
        public abstract boolean contains(char c);
        public abstract TrieNode get(char c);
    }
}
