package algorithm.DataStructure;

/**
 * 维护多个数据的栈，可以非递归dfs
 */
public class MultiStack{
    int len1,len2;
    private int[][]i;
    private Object[][]o;
    public MultiStack(int intNum, int objNum, int length){
        i=new int[intNum][length];
        o=new Object[objNum][length];
        len1=len2=-1;
    }
    public MultiStack add(int... args){
        len1++;
        for(int x=0;x<i.length;x++)
            i[x][len1]=args[x];
        return this;
    }
    public MultiStack add(Object... args){
        len2++;
        for(int x=0;x<o.length;x++)
            o[x][len2]=args[x];
        return this;
    }
    public int Int(int index){
        return i[index][len1];
    }
    public Object Obj(int index){
        return o[index][len2];
    }
    public void popI(){
        len1--;
    }
    public void popO(){
        for(int x=0;x<o.length;x++)o[x][len2]=null;
        len2--;
    }
    public void pop(){
        popI();
        popO();
    }
    public boolean isEmpty(){
        return len1<0&&len2<0;
    }
}
