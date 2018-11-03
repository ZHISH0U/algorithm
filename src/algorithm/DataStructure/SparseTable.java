package algorithm.DataStructure;

/**
 * ST表，维护区间最值，预处理O(nlogn)，查询O(1)
 */
public abstract class SparseTable {
    int dp[][];
    public SparseTable(int[] a){
        int len=log2(a.length)+1;
        dp=new int[a.length][len];
        for(int i=0;i<a.length;i++)dp[i][0]=a[i];
        for(int i=1;i<len;i++)
            for(int j=0;j+(1<<i)-1<dp.length;j++)
                dp[j][i]=get(dp[j][i-1],dp[j+(1<<i-1)][i-1]);
    }
    public int query(int l,int r){
        int k=log2(r-l+1);
        return get(dp[l][k],dp[r-(1<<k)+1][k]);
    }
    private int log2(int n){
        return (int)(Math.log(n)/Math.log(2));
    }
    public abstract int get(int a,int b);
}
