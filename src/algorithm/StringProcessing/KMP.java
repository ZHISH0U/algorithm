package algorithm.StringProcessing;

public class KMP {
    /**
     * kmp算法，字符串匹配，时间复杂度O(m+n)
     * @param source
     * @param pattern
     * @return pattern在source中的位置，没找到则返回-1
     */
    public static int kmp(String source,String pattern) {
        //原理：根据匹配失败的信息，尽量减少模式串与主串的匹配次数以达到快速匹配的目的
        int i = 0, j = 0;
        int len = source.length();
        int next[]=KMPNext(pattern); // 计算next数组;
        while (i < len && j < next.length) {
            if (j == -1 || source.charAt(i) == pattern.charAt(j)) {
                ++i;
                ++j;
            } else j = next[j];
        }
        if (j >= next.length)
            return i - next.length;
        else
            return -1;
    }
    /**
     * 扩展kmp算法，求source与pattern的最长公共前缀的长度
     * @param source
     * @param pattern
     * @return source从i开始与pattern的最长公共前缀的长度
     */
    public static int[] ex_kmp(String source,String pattern){
        final int slen= source.length(),plen= pattern.length();
        int[]next=KMPNext(pattern),extend=new int[slen];
        int i=0,j,pos=0;
        while(source.charAt(i)== pattern.charAt(i)&&i<plen&&i<slen)i++;
        extend[0]=i;
        for(i=1;i<slen;i++){
            if(next[i-pos]+i<extend[pos]+pos)//第一种情况，直接可以得到extend[i]的值
                extend[i]=next[i-pos];
            else{//第二种情况，要继续匹配才能得到extend[i]的值
                j=extend[pos]+pos-i;
                if(j<0)j=0;//如果i>extend[pos]+pos则要从头开始匹配
                while(i+j<slen&&j<plen&& source.charAt(j+1)== pattern.charAt(j))//计算extend[i]
                    j++;
                extend[i]=j;
                pos=i;//更新pos的位置
            }
        }
        return extend;
    }
    /**
     * 优化过的next数组
     * @param pattern
     * @return
     */
    private static int[] KMPNext(String pattern) {
        //next数组为第k个字符匹配失败后可跳过的长度,可跳过的长度为k之前的字符串中前缀与后缀相同的长度
        int next[]=new int[pattern.length()];
        int i = 0; //pattern串的下标
        int j = -1; //
        next[0] = -1;
        while (i < next.length - 1) {
            if (j == -1 || pattern.charAt(i) == pattern.charAt(j)) {
                ++i;
                ++j;
                if (pattern.charAt(i) != pattern.charAt(j)) //正常情况
                    next[i] = j;
                else //特殊情况，这里即为优化之处。考虑下AAAB, 防止4个A形成012在匹配时多次迭代。相当于next[3]=next[2]=next[1]=next[0]=-1
                    next[i] = next[j];
            } else j = next[j];
        }
        return next;
    }
    /**
     * 最小表示法，求环形字符串最小字典序的起点，时间复杂度o(n)
     * @return
     */
    public static int MinimumRepresentation(String source) {
        final int n = source.length();
        int i = 0,j = 1, k = 0;
        while(i<n && j<n && k<n) {
            int t = source.charAt((i + k)%n) - source.charAt((j+k)%n) ;
            if(t == 0) k++;
            else {
                if(t>0) i+=k+1;
                else j+=k+1;
                if(i==j) j++;
                k = 0;
            }
        }
        return i < j ? i : j;
    }
}
