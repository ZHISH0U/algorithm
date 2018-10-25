package algorithm.Math;

public class Matrix {
    public static int[][] mul(int[][]a,int[][]b){
        int row=a.length,column=b[0].length;
        int len=b.length;
        int[][] ans=new int[row][column];
        for(int i=0;i<row;i++){
            for(int j=0;j<len;j++){
                if(a[i][j]==0)continue;//稀疏矩阵优化
                for(int k=0;k<column;k++){
                    ans[i][k]+=a[i][j]*b[j][k];
                }
            }
        }
        return ans;
    }
    public static int[][] modMul(int[][]a,int[][]b,int mod){
        int row=a.length,column=b[0].length;
        int len=b.length;
        int[][] ans=new int[row][column];
        for(int i=0;i<row;i++){
            for(int j=0;j<len;j++){
                if(a[i][j]==0)continue;//稀疏矩阵优化
                for(int k=0;k<column;k++){
                    ans[i][k]+=(long)a[i][j]*b[j][k]%mod;
                    ans[i][k]%=mod;
                }
            }
        }
        return ans;
    }
    /**
     * 列主元高斯消元法
     * @param Ab 增广矩阵
     */
    public static double[] GaussElimination(double[][]Ab){
        final int len=Ab.length;
        double ans[]=new double[len];
        for(int i=0;i<len;i++){
            int max=i;//寻找列主元
            for(int j=i+1;j<len;j++){
                if(Math.abs(Ab[j][i])>Math.abs(Ab[max][i])) max=j;
            }
            double[]temp=Ab[max];
            Ab[max]=Ab[i];
            Ab[i]=temp;
            //无解返回null
            if(Ab[i][i]==0) return null;
            double a=Ab[i][i];
            for(int j=0;j<len;j++){
                if(j==i||Ab[j][i]==0)continue;//稀疏优化
                double tmp=Ab[j][i]/a;
                for(int k=i;k<=len;k++){
                    Ab[j][k]-=Ab[i][k]*tmp;//消元
                }
            }
        }
        for(int i=len-1;i>=0;i--){
            ans[i]=Ab[i][len]/Ab[i][i];
        }
        return ans;
    }
    /**
     * 高斯-赛德尔迭代法
     * @param Ab 增广矩阵
     * @param delta 精度
     */
    public static double[] GaussSeidelIteration(double[][]Ab,double delta){
        final int len=Ab.length;
        double ans[]=new double[len];
        for(int i=0;i<len;i++){
            double a=-Ab[i][i];
            for(int j=0;j<len;j++){
                Ab[i][j]/=a;
            }
            Ab[i][len]/=-a;Ab[i][i]=0;
        }
        double cur=Double.MAX_VALUE;
        while(cur>delta){
            cur=0;
            for(int i=0;i<len;i++){
                double tmp=ans[i];
                ans[i]=0;
                for(int j=0;j<len;j++){
                    ans[i]+=Ab[i][j]*ans[j];
                }
                ans[i]+=Ab[i][len];
                cur=Math.max(Math.abs(ans[i]-tmp),cur);
            }
        }
        return ans;
    }
    /**
     * 雅可比迭代法
     * @param Ab 增广矩阵
     * @param delta 精度
     */
    public static double[] JacobiIteration(double[][]Ab,double delta){
        final int len=Ab.length;
        double ans[][]=new double[2][len];
        for(int i=0;i<len;i++){
            double a=-Ab[i][i];
            for(int j=0;j<len;j++){
                Ab[i][j]/=a;
            }
            Ab[i][len]/=-a;Ab[i][i]=0;
        }
        double cur=Double.MAX_VALUE;
        int index=0;
        while(cur>delta){
            cur=0;
            int pre=index^1;
            for(int i=0;i<len;i++){
                ans[index][i]=0;
                for(int j=0;j<len;j++){
                    ans[index][i]+=Ab[i][j]*ans[pre][j];
                }
                ans[index][i]+=Ab[i][len];
                cur=Math.max(Math.abs(ans[index][i]-ans[pre][i]),cur);
            }
            index^=1;
        }
        return ans[index^1];
    }
}
