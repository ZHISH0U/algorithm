package algorithm.DataStructure;

/**
 * 可持久化线段树（精简）
 */
public abstract class PersistSegTree {
    protected Node[] roots;//各版本的根节点
    protected int size,start,end;//当前版本数量，始末位置
    protected final int initValue;
    public PersistSegTree(int start,int end,int initValue,int MaxVersionNum){
        this.start=start;
        this.end=end;
        this.initValue=initValue;
        roots=new Node[MaxVersionNum+1];
        size=0;
    }
    public int update(int pos,int val){
        return update(size,pos,val);
    }
    public int update(int BaseVersion,int pos,int val){
        roots[++size]=new Node(roots[BaseVersion]);
        update(roots[size],start,end,pos,val);
        return size;
    }
    public void update(Node p,int l,int r,int pos,int val){
        if(l==r){
            p.val=updateValue(p.val,val);
            return;
        }
        int mid=(l+r)>>>1;
        if(pos<=mid){
            p.l=new Node(p.l);
            update(p.l,l,mid,pos,val);
        }else {
            p.r=new Node(p.r);
            update(p.r,mid+1,r,pos,val);
        }
        p.val=pushup(p.l.val,p.r.val);
    }
    public int query(int start,int end){
        return query(roots[size],this.start,this.end,start,end);
    }
    public int query(int VersionCode,int start,int end){
        return query(roots[VersionCode],this.start,this.end,start,end);
    }
    protected int query(Node pos,int l,int r,int start,int end){
        if(pos==null)return initValue;
        if(l>=start&&r<=end)return pos.val;
        int mid=(l+r)>>>1;
        int ans=initValue;
        if(start<=mid)ans=pushup(ans,query(pos.l,l,mid,start,Math.min(mid,end)));
        if(end>mid)ans=pushup(ans,query(pos.r,mid+1,r,Math.max(mid+1,start),end));
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
