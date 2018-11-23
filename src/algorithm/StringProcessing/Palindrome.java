package algorithm.StringProcessing;

public class Palindrome {
    /**
     * Manacher算法，求解字符串以任意位置为中心的回文子串，时间复杂度O(n)
     * @param str 字符串
     * @return 以str任意点为中心（包含字符间以及始末位置的空隙）的最大回文子串长度
     */
    public static int[] manacher(String str) {
        int len=str.length()*2;
        char[]s=new char[len+3];
        int[]rad=new int[len+1];//回文串半径（算上空隙），也就是实际回文串长度
        for(int i=2;i<=len;i+=2)s[i]=str.charAt(i-1>>1);
        s[0]=(char)-1;s[len+2]=(char)-2;
        for (int i=1,j=0,k;i<len;i+=k) {
            while (s[i-j] == s[i+j+2]) j++;
            rad[i] = j;
            // 利用类似镜像的方法缩短了时间
            for (k = 1; k <= rad[i] && rad[i-k] != rad[i]-k; k++)
                rad[i+k] = Math.min(rad[i-k], rad[i]-k);
            j = Math.max(j-k, 0);
        }
        return rad;
    }
}
