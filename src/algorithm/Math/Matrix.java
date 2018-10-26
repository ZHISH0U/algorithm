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
}
