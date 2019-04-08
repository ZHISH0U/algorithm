package algorithm.Math;

import java.security.SecureRandom;
import java.util.HashMap;
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
        while(b>0) {
            r=a%b;
            a=b;
            b=r;
        }
        return a;
    }
    public static long gcd(long a,long b) {
        long r;
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
    public static int[] exgcd(int a,int b){
        if(b==0) {
            return new int[]{a,1,0};
        }
        int[]res=exgcd(b,a%b);
        int tmp=res[1];
        res[1]=res[2];
        res[2]=tmp-(a/b)*res[2];
        return res;
    }
    public static long[] exgcd(long a,long b){
        if(b==0) {
            return new long[]{a,1,0};
        }
        long[]res=exgcd(b,a%b);
        long tmp=res[1];
        res[1]=res[2];
        res[2]=tmp-(a/b)*res[2];
        return res;
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
        while(b>0){
            if((b&1)==1)
                ans=ans*a1;
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
        while(b>0){
            if((b&1)==1)
                ans=(ans*a1)%c;
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
        for(int i=2;i*i<=n;i++){
            int cnt=0;
            while(n%i==0){
                cnt++;
                n/=i;
            }
            if(cnt!=0)map.put(i,cnt);
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
     * 得到一组与数组a中元素异或值域相同的线性基
     */
    public static int[] LinearBase(int[]a) {
        int[] base = new int[31];
        for (int x : a) {
            for (int j = 30; j >= 0; j--) {
                if ((x >> j) != 0) {//第j位为1
                    if (base[j] == 0) {//对应线性基不存在就等于x
                        base[j] = x;
                        break;
                    } else x ^= base[j];//存在就异或线性基
                }
            }
        }
        return base;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 递推求1~n的逆元，时间复杂度O(n),mod为奇素数
     */
    public static long[] getInvs(int n,int mod){
        long[]inv=new long[n+1];
        inv[1]=1;
        for(int i=2;i<inv.length;i++)
            inv[i]=(mod-mod/i)*inv[mod%i]%mod;
        return inv;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 中国剩余定理，求模数互质的同余方程组的解
     * @param mod 模数
     * @param remainder 余数
     * @return 最小正整数解
     */
    public static long crt(int[]mod,int[]remainder){
        long lcm=1;
        for(int x:mod)lcm*=x;
        long ans=0;
        for(int i=0;i<mod.length;i++){
            long m=lcm/mod[i];
            long inv=exgcd(m,mod[i])[1];
            //ans=sum(m*inv*remainder[i])
            ans=(ans+inv*remainder[i]%lcm*m%lcm)%lcm;
        }
        ans=(ans+lcm)%lcm;
        return ans;
    }
    /**
     * 扩展中国剩余定理，求同余方程组的解，模数可不互质
     * 本质是做n次扩展欧几里得算法
     * @param mod 模数
     * @param remainder 余数
     * @return 最小正整数解
     */
    public static long excrt(int[]mod,int[]remainder){
        long lcm=mod[0],ans=remainder[0];//通解为ans+x*lcm
        for (int i = 1; i < mod.length; i++) {
            //新添加一个方程ans%b=a
            long a=remainder[i],b=mod[i];
            //求出gcd以及t*lcm%b=gcd的解
            long tmp[]=exgcd(lcm,b),gcd=tmp[0],t=tmp[1];
            //求(ans+x*lcm)%b=a即x*lcm%b=a-ans的一个解
            long re=(a-ans%b+b)%b;
            if(re%gcd!=0)return -1;//方程无解
            b/=gcd;//后面用到的都是b/gcd了
            //x=(a-ans)/gcd*t
            long x=(re/gcd*(t%b)%b+b)%b;//求最小正整数解
            ans+=x*lcm;
            lcm*=b;//求LCM
            ans=(ans%lcm+lcm)%lcm;//求最小正整数解
        }
        return (ans%lcm+lcm)%lcm;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 求k*a^x=b(mod m)的最小正整数解,无解返回-1，a与m互质，时间复杂度O(sqrt(m))
     * @param k
     * @param a
     * @param b
     * @param m
     * @return
     */
    public static int bsgs(int k,int a,int b,int m){
        int sq=(int)Math.ceil(Math.sqrt(m));
        HashMap<Long,Integer>map=new HashMap<>();
        long x=b;
        for(int i=1;i<=sq;i++){
            x=(x*a)%m;
            map.put(x,i);
        }
        x=k;
        long a1=FastPowMod(a,sq,m);
        for(int i=1;i<=sq;i++){
            x=(x*a1)%m;
            if(map.containsKey(x))return sq*i-map.get(x);
        }
        return -1;
    }
    /**
     * 求a^x=b(mod m)的最小正整数解，无解返回-1，不需要a与m互质，时间复杂度O(sqrt(m))
     * @param a
     * @param b
     * @param m
     * @return
     */
    public static int exbsgs(int a,int b,int m){
        if(b==1)return 0;
        int t=0,d;
        long k=1;
        //a^(x-1)*a/gcd = b/gcd(mod m/gcd)
        while((d=gcd(a,m))!=1){
            if(b%d!=0) return -1;
            t++;b/=d;m/=d;
            k=k*a/d%m;
            if(b==k)return t;
        }
        int ans = bsgs((int)k,a,b,m);
        if(ans==-1)return ans;
        return ans+t;
    }
    /**
     * 求a次剩余x^a=b(mod m)的解，无解返回-1，r为模m意义下的一个原根，时间复杂度O(sqrt(m))
     * @param a
     * @param b
     * @param m 素数
     * @param r 原根
     * @return
     */
    public static int Residue(int a,int b,int m,int r){
        int y=bsgs(1,r,b,m);//将b转化为r^y
        int phi=m-1;//m为素数，欧拉函数为m-1
        //设答案为r^x，则x*a=y(mod phi)
        int[] gcd=exgcd(a,phi);
        gcd[1]=(gcd[1]%phi+phi)%phi;
        if(y%gcd[0]!=0)return -1;//y%gcd!=0无解
        else return FastPowMod(r,(int)((long)gcd[1]*(y/gcd[0])%phi),m);
    }
    //下为较复杂数论---------------------------------------------------------------------------------------------------------------------------------------------
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
     * 利用FFT计算多项式乘法，数组为下标对应次数x的系数，时间复杂度O(nlogn)
     * 使用数组而非Complex类
     */
    public static long[] PolynomialMul(long[]a,long[]b){
        int bit=1,s=2;//s表示分割之前整块的长度
        for(bit=1;(1<<bit)<a.length+b.length-1;bit++)
            s<<=1;//找到第一个二的整数次幂使得其可以容纳这两个数的乘积
        double[]ar=new double[s],ai=new double[s],br=new double[s],bi=new double[s];
        long[]ans=new long[a.length+b.length-1];
        for(int i=0;i<a.length;i++)ar[i]=a[i];
        for(int i=0;i<b.length;i++)br[i]=b[i];
        int[]pos=getRev(bit);
        FFT(ar,ai,pos,1);
        FFT(br,bi,pos,1);
        for(int i=0;i<ar.length;i++){
            //a=a*b;
            double tmp=ar[i]*br[i]-ai[i]*bi[i];
            ai[i]=ar[i]*bi[i]+ai[i]*br[i];
            ar[i]=tmp;
        }
        FFT(ar,ai,pos,-1);
        for(int i=0;i<ans.length;i++)
            ans[i]=(long)(ar[i]+0.5);
        return ans;
    }
    public static int[] getRev(int bit){
        int[]rev=new int[1<<bit];
        for(int i=0;i<rev.length;i++){//高位决定二进制数的大小
            rev[i]=(rev[i>>1]>>1)|((i&1)<<(bit-1));
        }//能保证(x>>1)<x,满足递推性质
        return rev;
    }
    /**
     *  快速傅里叶变换，使用数组而非Complex类
     * @param a 实部数组
     * @param x 虚部数组
     * @param pos 利用getRev计算出的pos数组
     * @param mode 1为DFT，-1为IDFT
     */
    public static void FFT(double a[],double x[],int[]pos,double mode){
        int len=a.length;
        for(int i=0;i<len;i++)
            if(i<pos[i]) {
                //swap(a[i],a[pos[i]]);
                double tmp;
                tmp=a[pos[i]];a[pos[i]]=a[i];a[i]=tmp;
                tmp=x[pos[i]];x[pos[i]]=x[i];x[i]=tmp;
            }
        for(int i=2,mid=1;i<=len;i<<=1,mid<<=1){
            //wm=(cos(PI/mid),sin(mode*PI/mid);
            double wmr=Math.cos(Math.PI/mid),wmi=Math.sin(mode*Math.PI/mid);
            for(int j=0;j<len;j+=i){
                //w=(1,0);
                double wr=1,wi=0;
                for(int k=j;k<j+mid;k++) {
                    //t=a[k+mid]*w;
                    double tr = wr * a[k + mid] - wi * x[k + mid], ti = wr * x[k + mid] + wi * a[k + mid];
                    //a[k+mid]=a[k]-t;
                    a[k + mid] = a[k] - tr; x[k + mid] = x[k] - ti;
                    //a[k]+=t;
                    a[k] += tr; x[k] += ti;
                    //w=w*wm;
                    double tmp = wr * wmr - wi * wmi;
                    wi = wr * wmi + wi * wmr; wr = tmp;
                }
            }
        }
        if(mode<0)
            for(int i=0;i<len;i++) {
                //a[i]/=len;
                a[i] /= len;x[i] /= len;
            }
    }
    /* 使用Complex类的FFT，常数过大
    public static long[] PolynomialMul(long[]A,long[]B){
        int bit,s=2;//s表示分割之前整块的长度
        for(bit=1;(1<<bit)<A.length+B.length-1;bit++)
            s<<=1;//找到第一个二的整数次幂使得其可以容纳这两个数的乘积
        Complex[]a=new Complex[s],b=new Complex[s];
        long[]ans=new long[A.length+B.length-1];
        for(int i=0;i<a.length;i++)a[i]=new Complex(i<A.length?A[i]:0,0);
        for(int i=0;i<b.length;i++)b[i]=new Complex(i<B.length?B[i]:0,0);
        int[]pos=getRev(bit);
        FFT(a,pos,1);FFT(b,pos,1);
        for(int i=0;i<a.length;i++)
            a[i].mul(b[i]);
        FFT(a,pos,-1);
        for(int i=0;i<ans.length;i++)
            ans[i]=(long)(a[i].real()+0.5);
        return ans;
    }
    public static void FFT(Complex[] a,int[]pos,double mode){
        for(int i=0;i<a.length;i++)
            if(i<pos[i]) {
                Complex tmp;
                tmp=a[pos[i]];a[pos[i]]=a[i];a[i]=tmp;
            }
        Complex wm=new Complex(),w=new Complex(),tmp=new Complex();
        for(int i=2,mid=1;i<=a.length;i<<=1,mid<<=1){
            wm.set(Math.cos(Math.PI/mid),Math.sin(mode*Math.PI/mid));
            for(int j=0;j<a.length;j+=i){
                w.set(1,0);
                for(int k=j;k<j+mid;k++){
                    tmp.set(a[k+mid]).mul(w);
                    a[k+mid].set(a[k]);
                    a[k].ad(tmp);a[k+mid].mi(tmp);
                    w.mul(wm);
                }
            }
        }
        if(mode<0) {
            double x=1.0/a.length;
            for (int i = 0; i < a.length; i++)
                a[i].div(a.length);
        }
    }
     */
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * s:起始位置,l:数组长度
     * Karatsuba算法，加速多项式相乘、大数乘法，时间复杂度O(n^log(3))
     * @return
     */
    public static long[] Karatsuba(long[]a,int as,int la,long[]b,int bs,int lb){
        if(la<=0||lb<=0)return null;
        long[]ans=new long[la+lb-1];
        if(la<33||lb<33){//实测小于等于32时，直接暴力比较快
            for(int i=0;i<la;i++)
                for(int j=0;j<lb;j++)
                    ans[i+j]+=a[i]*b[j];
            return ans;
        }
        //折半分治，a=al+ah*x^half，b=bl+bh*x^half
        int half=(Math.max(la,lb)+1)>>1;
        //x0=al*bl，x2=ah*bh，x1=a1*bh+ah*bl=(al+ah)*(bl+bh)-x0-x2
        long[]a1=new long[Math.min(la,half)],b1=new long[Math.min(lb,half)],
                x0=Karatsuba(a,as,a1.length,b,bs,b1.length),
                x2=Karatsuba(a,as+half,la-half,b,bs+half,lb-half);
        System.arraycopy(a,as,a1,0,a1.length);
        System.arraycopy(b,bs,b1,0,b1.length);
        for(int i=0;i<la-half;i++)a1[i]+=a[as+half+i];
        for(int i=0;i<lb-half;i++)b1[i]+=b[bs+half+i];
        long[]x1=Karatsuba(a1,0,a1.length,b1,0,b1.length);
        minus(x1,x0);minus(x1,x2);
        add(ans,0,x0);add(ans,half,x1);add(ans,half<<1,x2);
        return ans;
    }
    //a从pos位置开始加减b，改变a的值
    private static void add(long[]a,int pos,long[]b){
        if(b==null)return;
        for(int i=0;i<b.length;i++)
            a[i+pos]+=b[i];
    }
    private static void minus(long[]a,long[]b){
        if(b==null)return;
        for(int i=0;i<b.length;i++)
            a[i]-=b[i];
    }
    //下为小技巧------------------------------------------------------------------------------------------------------------------------------------------------
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
     * 判断num是否是2的幂
     * @param num
     * @return
     */
    public static boolean is2pow(int num){
        return (num&(num-1))==0;
    }
    /**
     * 低系统开销的前提下减少哈希冲突
     */
    public static int hash(int h){
        return h ^ (h >>> 16);
    }
}
