package algorithm.DataStructure;

import java.util.Arrays;

/**
 * 可持久化线段树（精简）
 */
public abstract class PersistSegTree {
    protected int[] roots;//各版本的根节点
    protected int[]val,l,r;
    protected int size,len,start,end;//当前版本数量，始末位置
    protected final int initValue;
    public PersistSegTree(int s,int t,int initValue,int VersionNum){
        this.initValue=initValue;
        this.start=s;
        this.end=t;
        int size=(t-s+1)*20;
        roots=new int[VersionNum+1];
        val=new int[size];
        l=new int[size];
        r=new int[size];
        if(initValue!=0)Arrays.fill(val,initValue);
        len=size=0;
    }
    public int update(int pos,int val){
        return update(size,pos,val);
    }
    public int update(int BaseVersion,int pos,int val){
        roots[++size]=copy(roots[BaseVersion]);
        update(roots[size],start,end,pos,val);
        return size;
    }
    public void update(int p,int s,int t,int pos,int v){
        if(s==t){
            val[p]=updateValue(val[p],v);
            return;
        }
        int mid=(s+t)>>>1;
        if(pos<=mid){
            l[p]=copy(l[p]);
            update(l[p],s,mid,pos,v);
        }else {
            r[p]=copy(r[p]);
            update(r[p],mid+1,t,pos,v);
        }
        val[p]=pushup(val[l[p]],val[r[p]]);
    }
    public int query(int l,int r,int k){
        return query(roots[l],roots[r],k,start,end);
    }
    protected int query(int p1,int p2,int k,int s,int t){
        if(s==t)return t;
        int mid=(s+t)>>1;
        int x=val[l[p2]]-val[l[p1]];
        if(k<=x)return query(l[p1],l[p2],k,s,mid);
        else return query(r[p1],r[p2],k-x,mid+1,t);
    }
    protected abstract int updateValue(int original,int val);
    protected abstract int pushup(int left,int right);
    private int copy(int a){
        len++;
        val[len]=val[a];
        l[len]=l[a];
        r[len]=r[a];
        return len;
    }
}