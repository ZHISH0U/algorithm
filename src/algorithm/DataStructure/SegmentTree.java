package algorithm.DataStructure;

import java.util.Arrays;

/**
 * 完整版线段树
 */
public abstract class SegmentTree {
    protected int[] tree,lazy;
    protected final int initValue,start,end;
    public SegmentTree(int start, int end, int initValue){
        tree=new int[(end-start+1)<<2];
        this.initValue=initValue;
        this.start=start;
        this.end=end;
        if(initValue!=0) Arrays.fill(tree,initValue);
    }
    public void update(int pos,int val){
        int l=start,r=end,mid,current=1;
        while(l!=r){
            if(lazy!=null)pushdown(current,r-l+1);
            mid=(l+r)>>>1;
            current<<=1;
            if(mid<pos){
                l=mid+1;
                current|=1;
            }else r=mid;
        }
        tree[current]=updateValue(tree[current],val);
        while((current>>=1)!=0)tree[current]=pushup(tree[current<<1],tree[current<<1|1]);
    }
    public int query(int start,int end){
        return query(1,this.start,this.end,start,end);
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
/*    protected int query(int pos,int l,int r,int start,int end){
        int mid;
        if(l>=start&&r<=end) return tree[pos];
        while(true) {
            mid = (l + r) >>> 1;
            if(mid<start){
                pos=pos<<1|1;
                l=mid+1;
            }else if(mid>end){
                pos<<=1;
                r=mid;
            }else break;
        }
        if(l>=start&&r<=end) return tree[pos];
        int ans=initValue;
        ans=mergeIntervals(ans,query(pos<<1,l,mid,start,end));
        if (mid < end) {
            ans=mergeIntervals(ans,query(pos<<1|1,mid+1,r,start, end));
        }
        return ans;
    }
*/
    public void clear(){
        Arrays.fill(tree,initValue);
        lazy=null;
    }
    protected abstract int updateValue(int original,int val);
    protected abstract int pushup(int left,int right);
//------------------------以下为区间更新----------------------------------------------------------
    public void update(int left,int right,int val){
        if(lazy==null){
            lazy=new int[tree.length];
            if(initValue!=0)Arrays.fill(lazy,initValue);
        }
        update(1,start,end,left,right,val);
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
    protected void pushdown(int pos,int range){
        if(lazy[pos]!=initValue) {
            final int tmp=lazy[pos];
            int mid=range>>1;
            lazy[pos << 1] = updateValue(lazy[pos << 1], tmp);
            lazy[pos << 1 | 1] = updateValue(lazy[pos << 1 | 1], tmp);
            tree[pos << 1] = updateInterval(tree[pos << 1], tmp, range-mid);
            tree[pos << 1 | 1] = updateInterval(tree[pos << 1 | 1], tmp, mid);
            lazy[pos]=initValue;
        }
    }
    protected abstract int updateInterval(int original,int val,int range);

}
