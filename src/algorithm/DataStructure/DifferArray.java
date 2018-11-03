package algorithm.DataStructure;

/**
 * 差分数组，不支持更新中查询。
 * 区间更新O(1)，返回修改后的数组O(n)。
 */
public class DifferArray {
    int[]d;
    public DifferArray(int[] a){
        d=new int[a.length];
        d[0]=a[0];
        for(int i=1;i<a.length;i++){
            d[i]=a[i]-a[i-1];
        }
    }
    public void update(int l,int r,int val){
        d[l]+=val;
        if(r+1<d.length)d[r+1]-=val;
    }
    public int[] finish(){
        for(int i=1;i<d.length;i++){
            d[i]+=d[i-1];
        }
        return d;
    }
}
