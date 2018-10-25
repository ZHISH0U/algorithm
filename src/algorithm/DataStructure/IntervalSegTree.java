package algorithm.DataStructure;

import java.util.Arrays;

/**
 * 区间更新线段树（精简）
 */
public abstract class IntervalSegTree {
    protected final int initValue,start,end,tree[],lazy[];
    public IntervalSegTree(int start, int end,int initValue){
        tree=new int[(end-start+1)<<2];
        lazy=new int[tree.length];
        this.initValue=initValue;
        this.start=start;
        this.end=end;
        if(initValue!=0){
            Arrays.fill(tree,initValue);
            Arrays.fill(lazy,initValue);
        }
    }
    public void update(int left,int right,int val){
        update(1,start,end,left,right,val);
    }
    public int query(int start,int end){
        return query(1,this.start,this.end,start,end);
    }
    protected void update(int pos,int l,int r,int start,int end,int val){
        if(l>=start&&r<=end){
            tree[pos]=updateInterval(tree[pos],val,r-l+1);
            lazy[pos]=updateValue(lazy[pos],val);
            return;
        }
        pushdown(pos,r-l+1);
        int mid=(l+r)>>>1;
        if(start<=mid)update(pos<<1,l,mid,start,Math.min(mid,end),val);
        if(end>mid)update(pos<<1|1,mid+1,r,Math.max(mid+1,start),end,val);
        tree[pos]=pushup(tree[pos<<1],tree[pos<<1|1]);
    }
    protected int query(int pos,int l,int r,int start,int end){
        if(l>=start&&r<=end)return tree[pos];
        if(lazy!=null)pushdown(pos,r-l+1);
        int mid=(l+r)>>>1;
        int ans=initValue;
        if(start<=mid)ans=pushup(ans,query(pos<<1,l,mid,start,end));
        if(end>mid)ans=pushup(ans,query(pos<<1|1,mid+1,r,start,end));
        return ans;
    }
    protected void pushdown(int pos,int range){
        if(lazy[pos]==initValue) return;
        final int tmp=lazy[pos];
        int mid=range>>1;
        lazy[pos << 1] = updateValue(lazy[pos << 1], tmp);
        lazy[pos << 1 | 1] = updateValue(lazy[pos << 1 | 1], tmp);
        tree[pos << 1] = updateInterval(tree[pos << 1], tmp, range-mid);
        tree[pos << 1 | 1] = updateInterval(tree[pos << 1 | 1], tmp, mid);
        lazy[pos]=initValue;
    }
    public void clear(){
        Arrays.fill(tree,initValue);
        Arrays.fill(lazy,initValue);
    }
    protected abstract int pushup(int left,int right);
    protected abstract int updateValue(int original,int val);
    protected abstract int updateInterval(int original,int val,int range);
}
