package algorithm.Math;

import java.math.BigInteger;

/**
 * 蒙哥马利算法，乘法取模速度提高1~2倍
 */
public class Montgomery {
    private long mod,inv,r2;
    private static BigInteger MAX=BigInteger.ONE.shiftLeft(128);
    //需要先把数字转化为Montgomery表示法
    public long toMontForm(long n){
        return reduce(n,r2);
    }
    //设置mod
    public void setMod(long m){
        mod=m;
        inv=m;
        for(int i=0;i<5;i++) inv *= 2 - inv * m;
        BigInteger M=BigInteger.valueOf(m);
        r2=MAX.mod(M).longValue();
    }
    public long reduce(long c,long d) {
        long a=getUnsignedMulHigh(c,d);
        long b=c*d;
        long h0=b*inv;
        long h1=getUnsignedMulHigh(h0,mod);
        long y=a-h1;
        return y<0?y+mod:y;
    }
    //得到a，b无符号相乘超出long的部分
    public long getUnsignedMulHigh(long a,long b){
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
    //a+b
    public long plus(long a,long b){
        a+=b-mod;
        if(a<0)a+=mod;
        return a;
    }
    //a-b
    public long minus(long a,long b){
        a-=b;
        if(a<0)a+=mod;
        return a;
    }
    //a*b
    public long mul(long a,long b){
        return reduce(a,b);
    }
    public long toNormalForm(long a){
        return reduce(1,a);
    }
}
