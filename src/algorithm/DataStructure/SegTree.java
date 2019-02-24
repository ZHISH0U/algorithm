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
    public int query(int start,int end){
        return query(1,this.start,this.end,start,end);
    }
    protected void update(int cur,int pos,int val,int l,int r){
        if(l==r){
            tree[cur]=updateValue(tree[cur],val);
            return;
        }
        int mid=(l+r)>>>1;
        if(mid<pos) update(cur<<1|1,pos,val,mid+1,r);
        else update(cur<<1,pos,val,l,mid);
        tree[cur]=pushup(tree[cur<<1],tree[cur<<1|1]);
    }
    protected int query(int pos,int l,int r,int start,int end){
        if(l>=start&&r<=end)return tree[pos];
        int mid=(l+r)>>>1;
        int ans=initValue;
        if(start<=mid)ans=pushup(ans,query(pos<<1,l,mid,start,end));
        if(end>mid)ans=pushup(ans,query(pos<<1|1,mid+1,r,start,end));
        return ans;
    }
    public void clear(){
        Arrays.fill(tree,initValue);
    }
    protected abstract int updateValue(int original,int val);
    protected abstract int pushup(int left,int right);
}
