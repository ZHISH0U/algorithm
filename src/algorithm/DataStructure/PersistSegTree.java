package algorithm.DataStructure;


/**
 * 可持久化线段树（精简）
 */
public abstract class PersistSegTree {
    int[] roots;//各版本的根节点
    int[]val,l,r;
    int size,len;//根节点数量，节点数量
    PersistSegTree(int len,int VersionNum){
        roots=new int[VersionNum+1];
        val=new int[len];
        l=new int[len];
        r=new int[len];
        clear();
    }
    void clear(){
        len=size=0;
    }
    int update(int BaseVersion,int start,int end,int pos,int val){
        roots[++size]=copy(roots[BaseVersion]);
        upd(roots[size],start,end,pos,val);
        return size;
    }
    void upd(int p,int s,int t,int pos,int v){
        if(s==t){
            val[p]=updateValue(val[p],v);
            return;
        }
        int mid=(s+t)>>>1;
        if(pos<=mid){
            l[p]=copy(l[p]);
            upd(l[p],s,mid,pos,v);
        }else {
            r[p]=copy(r[p]);
            upd(r[p],mid+1,t,pos,v);
        }
        val[p]=pushup(val[l[p]],val[r[p]]);
    }
    int query(int p1,int p2,int k,int s,int t){
        if(s==t)return t;
        int mid=(s+t)>>1;
        int x=val[l[p2]]-val[l[p1]];
        if(k<=x)return query(l[p1],l[p2],k,s,mid);
        else return query(r[p1],r[p2],k-x,mid+1,t);
    }
    abstract int updateValue(int original,int val);
    abstract int pushup(int left,int right);
    private int copy(int a){
        len++;
        val[len]=val[a];
        l[len]=l[a];
        r[len]=r[a];
        return len;
    }
}