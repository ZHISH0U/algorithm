package algorithm.DataStructure;

/**
 * 区间更新可持久化线段树（精简）
 */
public abstract class IntervalPresistSegTree {
    protected Node[] roots;//各版本的根节点
    protected int size,start,end;//当前版本数量，始末位置
    protected final int initValue;
    public IntervalPresistSegTree(int start,int end,int initValue,int MaxVersionNum){
        this.start=start;
        this.end=end;
        this.initValue=initValue;
        roots=new Node[MaxVersionNum+1];
        size=0;
    }
    public int update(int BaseVersion,int left,int right,int val){
        roots[++size]=new Node(roots[BaseVersion]);
        update(roots[size],start,end,left,right,val);
        return size;
    }
    public int query(int start,int end){
        return query(roots[size],this.start,this.end,start,end,initValue);
    }
    public int query(int VersionCode,int start,int end){
        return query(roots[VersionCode],this.start,this.end,start,end,initValue);
    }
    protected void update(Node pos,int l,int r,int start,int end,int val){
        if(l>=start&&r<=end){
            pos.val=updateInterval(pos.val,val,end-start+1);
            pos.lazy=updateValue(pos.lazy,val);
            return;
        }
        int mid=(l+r)>>>1;
        if(start<=mid){
            pos.l=new Node(pos.l);
            update(pos.l,l,mid,start,Math.min(mid,end),val);
        }
        if(end>mid){
            pos.r=new Node(pos.r);
            update(pos.r,mid+1,r,Math.max(mid+1,start),end,val);
        }
        pos.val=pushup(pos.l.val,pos.r.val);
    }
    protected int query(Node pos,int l,int r,int start,int end,int lazy){
        if(pos==null)return updateInterval(initValue,lazy,end-start+1);
        if(l>=start&&r<=end)return updateInterval(pos.val,lazy,end-start+1);
        int mid=(l+r)>>>1;
        int ans=initValue;
        lazy=updateValue(lazy,pos.lazy);
        if(start<=mid)ans=pushup(ans,query(pos.l,l,mid,start,Math.min(mid,end),lazy));
        if(end>mid)ans=pushup(ans,query(pos.r,mid+1,r,Math.max(mid+1,start),end,lazy));
        return ans;
    }
    public int getSize(){
        return size;
    }
    public void clear(){
        for(int i=1;i<=size;i++)roots[i]=null;
        size=0;
    }
    protected abstract int updateValue(int original,int val);
    protected abstract int pushup(int left,int right);
    protected abstract int updateInterval(int original,int val,int range);
    private class Node{
        Node l,r;
        int val,lazy;
        Node(Node base){
            if(base==null){
                val=lazy=initValue;
            }else {
                val = base.val;
                l = base.l;
                r = base.r;
                lazy = base.lazy;
            }
        }
    }
}
