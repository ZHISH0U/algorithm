package algorithm.DataStructure;

import java.util.Arrays;

/**
 * 单点更新线段树（精简）
 */
public abstract class SegTree {
    protected final int initValue,start,end,tree[];
    public SegTree(int start, int end, int initValue){
        tree=new int[(end-start+1)<<2];
        this.initValue=initValue;
        this.start=start;
        this.end=end;
        if(initValue!=0) Arrays.fill(tree,initValue);
    }
    public void update(int pos,int val){
        update(1,pos,val,start,end);
    }
    public int query(int l,int r){
        return query(1,start,end,l,r);
    }
    protected void update(int cur,int pos,int val,int s,int t){
        if(s==t){
            tree[cur]=updateValue(tree[cur],val);
            return;
        }
        int mid=(s+t)>>>1;
        if(mid<pos) update(cur<<1|1,pos,val,mid+1,t);
        else update(cur<<1,pos,val,s,mid);
        tree[cur]=pushup(tree[cur<<1],tree[cur<<1|1]);
    }
    protected int query(int pos,int s,int t,int l,int r){
        if(s>=l&&t<=r)return tree[pos];
        int mid=(s+t)>>>1;
        int ans=initValue;
        if(l<=mid)ans=pushup(ans,query(pos<<1,s,mid,l,r));
        if(r>mid)ans=pushup(ans,query(pos<<1|1,mid+1,t,l,r));
        return ans;
    }
    public void clear(){
        Arrays.fill(tree,initValue);
    }
    protected abstract int updateValue(int original,int val);
    protected abstract int pushup(int left,int right);
}
