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
    public void update(int l,int r,int val){
        update(1,start,end,l,r,val);
    }
    public int query(int l,int r){
        return query(1,start,end,l,r);
    }
    protected void update(int pos,int s,int t,int l,int r,int val){
        if(s>=l&&t<=r){
            modify(pos,s,t,val);
            return;
        }
        pushdown(pos,s,t);
        int mid=(s+t)>>>1;
        if(l<=mid)update(pos<<1,s,mid,l,r,val);
        if(r>mid)update(pos<<1|1,mid+1,t,l,r,val);
        tree[pos]=pushup(tree[pos<<1],tree[pos<<1|1]);
    }
    protected int query(int pos,int s,int t,int l,int r){
        if(s>=l&&t<=r)return tree[pos];
        if(lazy!=null)pushdown(pos,s,t);
        int mid=(s+t)>>>1;
        int ans=initValue;
        if(l<=mid)ans=pushup(ans,query(pos<<1,s,mid,l,r));
        if(r>mid)ans=pushup(ans,query(pos<<1|1,mid+1,t,l,r));
        return ans;
    }
    protected void pushdown(int pos,int s,int t){
        if(lazy[pos]==initValue) return;
        final int tmp=lazy[pos];
        int mid=(s+t)>>>1;
        modify(pos<<1,s,mid,lazy[pos]);
        modify(pos<<1|1,mid+1,t,lazy[pos]);
        lazy[pos]=initValue;
    }
    public void clear(){
        Arrays.fill(tree,initValue);
        Arrays.fill(lazy,initValue);
    }
    protected void modify(int p,int s,int t,int v){
        tree[p]=updateInterval(tree[p],v,t-s+1);
        lazy[p]=updateValue(lazy[p],v);
    }
    protected abstract int pushup(int left,int right);
    protected abstract int updateValue(int original,int val);
    protected abstract int updateInterval(int original,int val,int range);
}
