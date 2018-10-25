package algorithm;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class DynamicProgramming {
    /**
     * 01背包，每样物品一个，复杂度o(n*V)
     * @param w 物品的重量
     * @param v 物品的价值
     * @param V 背包的容量
     * @return 0-V的最大价值数组
     */
    public static int[] Pack01(int[]w,int[]v,int V){
        int[] dp=new int[V+1];
        final int len=w.length;
        for(int i=0;i<len;i++){
            for(int j=V;j>=w[i];j--){
                dp[j]=Math.max(dp[j],dp[j-w[i]]+v[i]);
            }
        }
        return dp;
    }

    /**
     *
     * 完全背包，每样物品无数个，复杂度o(n*V)
     * @param w 物品的重量
     * @param v 物品的价值
     * @param V 背包的容量
     * @return 0-V的最大价值数组
     */
    public static int[] CompletePack(int[]w,int[]v,int V){
        int[] dp=new int[V+1];
        final int len=w.length;
        for(int i=0;i<len;i++){
            for(int j=w[i];j<=V;j++){
                dp[j]=Math.max(dp[j],dp[j-w[i]]+v[i]);
            }
        }
        return dp;
    }

    /**
     * 单调队列优化多重背包，每样物品num个
     * @param w 物品的重量
     * @param v 物品的价值
     * @param num 物品的数量
     * @param V 背包的容量
     * @return 0-V的最大价值数组
     */
    public static int[] MultiplePack(int[]w,int[]v,int[]num,int V){
        int[] dp=new int[V+1];
        final int len=w.length;
        //单调队列
        LinkedList<Integer> pos=new LinkedList<>();//位置
        LinkedList<Integer>val=new LinkedList<>();//值
        for(int i=0;i<len;i++){
            for(int j=0;j<w[i];j++){
                pos.clear();
                val.clear();
                pos.add(j);
                val.add(dp[j]);
                for(int k=1;k*w[i]+j<=V;k++){
                    int p=k*w[i]+j;
                    //更新单调队列
                    while(!pos.isEmpty()&&(dp[p]-val.getLast())<=(p-pos.getLast())/w[i]*v[i]){
                        pos.removeLast();
                        val.removeLast();
                    }
                    pos.add(p);
                    val.add(dp[p]);
                    //移除已经失效的值
                    if(p-pos.getFirst()>num[i]*w[i]){
                        pos.removeFirst();
                        val.removeFirst();
                    }
                    dp[p]=Math.max(val.getFirst()+v[i]*(p-pos.getFirst())/w[i],dp[p]);
                }
            }
        }
        return dp;
    }

    /**
     * 四边形优化的区间dp，求最小值
     * 区间包含的单调性：如果对于i≤i'<j≤j'，有w(i',j)≤w(i,j')，那么说明w具有区间包含的单调性。
     * （可以形象理解为如果小区间包含于大区间中，那么小区间的w值不超过大区间的w值）
     * 四边形不等式：如果对于i≤i'<j≤j'，有w(i,j)+w(i',j')≤w(i',j)+w(i,j')，我们称函数w满足四边形不等式。
     * （可以形象理解为两个交错区间的w的和不超过小区间与大区间的w的和）
     * 假如dp(i,j)满足四边形不等式，那么s(i,j)单调，即s(i,j-1)≤s(i,j)≤s(i+1,j)。
     * @param v
     * @return
     */
    public static int[][] IntervalDP(int[]v){
        final int length=v.length;
        int[]sum=new int[length+1];
        int[][]dp=new int[length+1][length+1];
        int[][]s=new int[length+1][length+1];
        for(int i=0;i<=length;i++) Arrays.fill(dp[i],Integer.MAX_VALUE);
        for(int i=1;i<=length;i++){
            sum[i]=sum[i-1]+v[i-1];
            dp[i][i]=0;
            s[i][i]=i;
        }
        for(int i=length-1;i>0;i--){
            for(int j=i+1;j<=length;j++){
                for(int k=s[i-1][j];k<=s[i+1][j];k++){//四边形优化
                    int tmp=dp[i][k]+dp[k+1][j]+(sum[j]-sum[i-1]);
                    if(tmp<dp[i][j]){
                        dp[i][j]=tmp;
                        s[i][j]=k;
                    }
                }
            }
        }
        return dp;
    }
}
