package algorithm.Math;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数论
 */
public class NumberTheory {
    /**
     * 最大公约数，辗转相除法
     */
    public static int gcd(int a,int b) {
        int r;
        if(b>a){
            int tmp=b;
            b=a;
            a=tmp;
        }
        while(b>0) {
            r=a%b;
            a=b;
            b=r;
        }
        return a;
    }
    /**
     * 最小公倍数
     */
    public static int lcm(int a,int b){
        int ans=a/gcd(a,b)*b;
        return ans;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 扩展欧几里得算法，求ax+by=gcd(a,b)的整数解
     * 可用于求a关于b的乘法逆元
     * @param a
     * @param b
     * @return 第一个值为最大公约数，第二个值为x，第三个值为y
     */
    public static int[] ex_gcd(int a,int b){
        int ans;
        int[] result;
        if(b==0) {
            result=new int[3];
            result[0]=a;
            result[1]=1;
            result[2]=0;
            return result;
        }
        result=ex_gcd(b,a%b);
        int tmp=result[1];
        result[1]=result[2];
        result[2]=tmp-(a/b)*result[2];
        return result;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 快速幂，时间复杂度 O(log2(b))
     * @param a 底数
     * @param b 指数
     * @return a^b
     */
    public static long FastPow(int a,int b){
        long ans=1;
        long a1=a;
        while(b>0&&(b&1)==0){
            a1*=a1;
            b>>=1;
        }
        while(b>0){
            if((b&1)==1){
                ans=ans*a1;
            }
            a1*=a1;
            b>>=1;
        }
        return ans;
    }
    /**
     * 快速幂取模，时间复杂度 O(log2(b))
     * @param a 底数
     * @param b 指数
     * @param c 模
     * @return a^b%c
     */
    public static int FastPowMod(int a,int b,int c){
        a=a%c;
        long ans=1;
        long a1=a;
        while(b>0&&(b&1)==0){
            a1=(a1*a1)%c;
            b>>=1;
        }
        while(b>0){
            if((b&1)==1){
                ans=(ans*a1)%c;
            }
            a1=(a1*a1)%c;
            b>>=1;
        }
        return (int)ans;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 欧拉筛法求n以内的素数，时间复杂度O(n)
     * @return n以内的全部素数个数
     */
    public static int Euler_Prime(int n,int[]prim){
        boolean[] IsPrime=new boolean[n+1];
        int len=0;
        for(int i = 2; i <= n; i ++){
            if(!IsPrime[i])
                prim[len++]=i;
            for(int j=0;j<len;j++){
                if(i * prim[j] > n)
                    break;
                IsPrime[i * prim[j]] = true;
                if(i % prim[j] == 0)
                    break;
            }
        }
        return len;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 分解质因数，时间复杂度O(n^1/2)
     * @return
     */
    public static Map<Integer,Integer> decomposeFactor(int n){
        Map<Integer,Integer>map=new HashMap<>();
        int len=(int)Math.sqrt(n)+1;
        for(int i=2;i<=len;i++){
            if(n%i==0){
                int tmp=0;
                do{
                    tmp++;
                    n/=i;
                }while(n%i==0);
                map.put(i,tmp);
            }
        }
        if(n!=1)map.put(n,1);
        return map;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 欧拉函数，求小于n的正整数中与n互质的数的个数，时间复杂度O(n^1/2)
     * @return
     */
    public static int Euler_Function(int n) {
        int ans = n;
        Set<Integer> set=decomposeFactor(n).keySet();
        for(int i:set){
            ans-=ans/i;
        }
        return ans;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 斯特林公式求ln(n!)的近似值
     * @param n
     * @return
     */
    public static double Stirling(long n){
        return Math.log(2*Math.PI*n)/2+n*Math.log(n)-n;
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
     * Miller-Rabin素性测试，快速判断素数，误判概率为(1/4)^t
     * @param n    要判断的数
     * @param t    判断轮数
     * @return
     */
    public static boolean MillerRabin(int n,int t)
    {
        for(int i=0;i<t;i++)
            if(!isPrime(n))
                return false;
        return true;
    }
    /**
     * @param n The number should be tested whether it is a prime.
     */
    private static boolean isPrime(int n)
    {
        int k,q;
        SecureRandom random=new SecureRandom();
        for(k=0;(((n-1)>>k)&1)==0;k++);
        q=(n-1)>>k;
        int a=random.nextInt(n);
        if(squareMultiply(a, q, n)==1)
            return true;
        for(int j=0;j<k;j++)
            if(squareMultiply(a, (int)Math.pow(2, j)*q,n)==n-1)
                return true;
        return false;
    }
    private static int squareMultiply(int a,int b,int p)
    {
        long x=1,y=a;
        int len=(int)Math.ceil((Math.log(b)/Math.log(2)));
        for(int i=0;i<len;i++)
        {
            if(((b>>i)&1)==1)
            {
                x=(x*y)%p;
            }
            y=(y*y)%p;
        }
        return (int)x;
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
