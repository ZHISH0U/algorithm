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
    public Trie put(String... strs){
        for(String str:strs)
            put(str.toCharArray());
        return this;
    }
    public TrieNode put(char[] str){
        return put(str,0,str.length);
    }
    public TrieNode put(char[] str,int beginIndex,int endIndex){
        TrieNode node;
        node=root;
        for(int i=beginIndex;i<endIndex;i++)
            node=node.put(str[i]);
        node.EndNum++;
        return node;
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
