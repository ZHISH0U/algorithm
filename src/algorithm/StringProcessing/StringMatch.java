package algorithm.StringProcessing;

public class StringMatch {
    public static int KMP(String source,String pattern){
        return KMP.kmp(source,pattern);
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * two-way算法，字符串匹配，时间复杂度O(m+n)，空间复杂度O(1)
     * @param source
     * @param pattern
     * @return
     */
    public static int TwoWay(String source, String pattern) {
        int tlen = source.length();
        int plen = pattern.length();
        int[] lp1, lp2;
        lp1 = MaxSuffix(pattern, false);
        lp2 = MaxSuffix(pattern, true);
        int l, p;
        if (lp1[0] > lp2[0]) {
            l = lp1[0];
            p = lp1[1];
        } else {
            l = lp2[0];
            p = lp2[1];
        }
        int pos = 0, s = -1, i, j;
        int sp = cmp(pattern, p, l + 1) ? plen - p - 1 : -1;
        while (pos + plen <= tlen) {
            i = Math.max(l, s) + 1;
            while (i < plen && pattern.charAt(i) == source.charAt(pos + i))
                i++;
            if (i < plen) {
                pos += Math.max(i - l, s - p + 1);
                s = -1;
            } else {
                j = l;
                while (j > s && pattern.charAt(j) == source.charAt(pos + j))
                    j--;
                if (j <= s) return pos;
                pos += p;
                s = sp;
            }
        }
        return -1;
    }
    //判断str从0和从p开始的l个元素是否相同
    private static boolean cmp(String str, int p, int l) {
        for (int i = 0; i < l; i++)
            if (str.charAt(i) != str.charAt(p + i)) return false;
        return true;
    }
    //tilde:true求最小后缀，false求最大后缀
    private static int[] MaxSuffix(String buf,boolean tilde) {
        int i = -1, j = 0, k = 1, len = buf.length();
        int[] lp = new int[2];
        lp[1] = 1;
        char a, b;
        while (j + k < len) {
            a = buf.charAt(i + k);
            b = buf.charAt(j + k);
            if (tilde^b < a) {
                j += k;
                k = 1;
                lp[1] = j - i;
            } else if (b == a) {
                if (k == lp[1]) {
                    j += lp[1];
                    k = 1;
                } else k++;
            } else {
                i = j;
                j++;
                k = lp[1] = 1;
            }
        }
        lp[0] = i;
        return lp;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    //待完成
    public void BMH(String source,String pattern){}
    public void Sunday(String source,String pattern){}
}
