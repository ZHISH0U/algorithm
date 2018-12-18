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
    public static long gcd(long a,long b) {
        long r;
        if(b>a){
            long tmp=b;
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
    public static long FastPowMod(long a,long b,long c){
        a=a%c;
        long ans=1;
        while(b>0){
            if((b&1)==1) ans=mulMod(ans,a,c);
            a=mulMod(a,a,c);
            b>>=1;
        }
        return ans;
    }
    //long相乘取模
    public static long mulMod(long a,long b,long c) {
        a%=c;
        b%=c;
        long ret=0;
        while(b!=0) {
            if((b&1)!=0){ret+=a;ret%=c;}
            a<<=1;
            if(a>=c)a%=c;
            b>>=1;
        }
        return ret;
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
    /**
     * 大数分解质因数，时间复杂度O(n^1/4)
     * @param n
     * @param x 质因数数组
     * @param len 输入0
     * @return 质因数个数
     */
    public static int decomposeFactor(long n,long[]x,int len){
        if(MillerRabin(n,20)){
            x[len++]=n;
            return len;
        }
        long p=n;
        while(p>=n)p=Pollard_rho(n,(long)(Math.random()*n)+1);
        len=decomposeFactor(p,x,len);
        return decomposeFactor(n/p,x,len);
    }
    public static long Pollard_rho(long x,long c) {
        long i=1,k=2;
        long x0=(long)(Math.random()*x);
        long y=x0;
        while(true) {
            i++;
            x0=(mulMod(x0,x0,x)+c)%x;
            long d=gcd(Math.abs(y-x0),x);
            if(d!=1&&d!=x) return d;
            if(y==x0) return x;
            if(i==k){y=x0;k<<=1;}
        }
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
     * Miller-Rabin素性测试，快速判断素数，误判概率为(1/4)^t
     * @param n    要判断的数
     * @param t    判断轮数
     * @return
     */
    public static boolean MillerRabin(long n,int t) {
        if(n==2)return true;
        if(n<2||(n&1)==0)return false;
        for(int i=0;i<t;i++)
            if(!isPrime(n))
                return false;
        return true;
    }
    /**
     * @param n The number should be tested whether it is a prime.
     */
    private static boolean isPrime(long n) {
        long k,q;
        SecureRandom random=new SecureRandom();
        for(k=0;(((n-1)>>k)&1)==0;k++);
        q=(n-1)>>k;
        long a=Math.abs(random.nextLong())%(n-1)+1;
        if(FastPowMod(a, q, n)==1)
            return true;
        for(int j=0;j<k;j++)
            if(FastPowMod(a, (1<<j)*q,n)==n-1)
                return true;
        return false;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 返回不小于num的最小2的幂
     */
    public static int to2pow(int num){
        int n = num - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n + 1;
    }
    /**
     * 低系统开销的前提下减少哈希冲突
     */
    public static int hash(int h){
        return h ^ (h >>> 16);
    }
}
