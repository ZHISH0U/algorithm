package algorithm.Math;

/**
 * 数值计算
 */
public class NumberalCalculations {
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
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 龙贝格求积公式
     * @param a 下界
     * @param b 上界
     * @param delta 误差
     */
    public static double Romberg(double a,double b,double delta){
        double d=b-a,list[]=new double[31];
        list[0]=(func(a)+func(b))*d/2;
        for(int i=1;;i++){
            double ans=0,t,x=a+d/2;
            for(int j=0;j<(1<<i-1);j++,x+=d){
                ans+=func(x);
            }
            d/=2;
            ans=ans*d+list[0]/2;
            for(int j=0;j<i;j++){
                if(Math.abs(ans-list[j])<delta)return ans;
                t=ans;
                int tmp=1<<(j+1<<1);
                ans=(tmp*ans-list[j])/(tmp-1);
                list[j]=t;
            }
            list[i]=ans;
        }
    }
    public static double func(double x){
        return x;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 四阶龙格-库塔公式求一阶常微分方程,适合平滑曲线
     * @param initX 初始点
     * @param initY 初值
     * @param endX 目标点
     * @param time 迭代次数
     * @return
     */
    public static double RungeKutta(double initX,double initY,double endX,int time){
        double h=(endX-initX)/time;
        double y=initY,x=initX;
        time--;
        for(int i=0;i<time;i++){
            y=R_K(x,y,h);
            x+=h;
        }
        h=endX-x;
        return R_K(x,y,h);
    }
    private static double R_K(double x,double y,double h){
        double k1,k2,k3,k4;
        k1=func(x,y);
        k2=func(x+h/2,y+h*k1/2);
        k3=func(x+h/2,y+h*k2/2);
        k4=func(x+h,y+h*k3);
        return y+h*(k1+2*k2+2*k3+k4)/6;
    }

    /**
     * 改进欧拉公式，非平滑曲线可能优于龙格-库塔公式
     * @param initX 初始点
     * @param initY 初值
     * @param endX 目标点
     * @param time 迭代次数
     * @return
     */
    public static double improveEuler(double initX,double initY,double endX,int time){
        double h=(endX-initX)/time;
        double y=initY,x=initX;
        time--;
        for(int i=0;i<time;i++){
            y=I_E(x,y,h);
            x+=h;
        }
        h=endX-x;
        return I_E(x,y,h);
    }
    private static double I_E(double x,double y,double h){
        double yp=y+h*func(x,y);
        double yc=y+h*func(x+h,yp);
        return (yp+yc)/2;
    }
    public static double func(double x,double y){
        return x;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     *得到a*b超出long的部分
     */
    public long getMulHigh(long a,long b){
        long a1 = a >>> 32;
        long a0 = a & 0xFFFFFFFFL;
        long b1 = b >>> 32;
        long b0 = b & 0xFFFFFFFFL;
        long ans1 = a0 * b1 + a1 * b0 + (a0 * b0 >>> 32);
        return a1 * b1 + (ans1 >>> 32);
    }
    /**
     *得到a，b无符号相乘超出long的部分
     */
    public long getUnsignedMulHigher(long a,long b){
        long a1 = a >>> 32;
        long a0 = a & 0xFFFFFFFFL;
        long b1 = b >>> 32;
        long b0 = b & 0xFFFFFFFFL;
        long ans1 = a0 * b1 + (a0 * b0 >>> 32);
        long ans2=a1 * b1;
        ans2+=(ans1 >>> 32);
        ans1&=0xFFFFFFFFL;
        ans1+=a1 * b0;
        ans2+=(ans1 >>> 32);
        return ans2;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 卡马克快速平方根
     * 求1/sqrt(x)近似值，java中float与int转化过慢，仅供参考
     */
    public static float InvSqrt(float x){
        float xhalf=0.5f*x;
        int i=Float.floatToRawIntBits(x);
        i=0x5f375a86 - (i>>1);
        x=Float.intBitsToFloat(i);
        x=x*(1.5f-xhalf*x*x);
        return x;
    }
    /**
     * 卡马克快速平方根
     * 求1/sqrt(x)近似值，java中double与long转化过慢，仅供参考
     */
    public static double InvSqrt(double x){
        double xhalf=0.5*x;
        long i=Double.doubleToRawLongBits(x);
        i=0x5fe6ec85e7de30daL - (i>>1);
        x=Double.longBitsToDouble(i);
        x=x*(1.5-xhalf*x*x);
        return x;
    }
}
