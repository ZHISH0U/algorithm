package algorithm.Math;

public class BitOperation {
    /**
     * 获取最后一位1
     */
    public static int getTrailingOne(int x){
        return x&(-x);
    }
    /**
     * 获取最前一位1
     */
    public static int getLeadingOne(int x){
        return Integer.highestOneBit(x);
    }
    /**
     * 最后一位0变为1，其他位为0
     */
    public static int getTrailingZero(int x){
        return ~x&(x+1);
    }
    /**
     * 翻转最后一位1
     */
    public static int flipTrailingOne(int x){
        return x&(x-1);
    }
    /**
     * 翻转最后一位0
     */
    public static int flipTrailingZero(int x){
        return x|(x+1);
    }
    /**
     * 前导0个数
     */
    public static int LeadingZerosNum(int x){
        return Integer.numberOfLeadingZeros(x);
    }
    /**
     * 尾部0个数
     */
    public static int TrailingZerosNum(int x){
        return Integer.numberOfTrailingZeros(x);
    }
    /**
     * x中1的个数
     */
    public static int bitCount(int x){
        return Integer.bitCount(x);
    }
    /**
     * 获取最前一位1的位置（0-base），没有1返回-1
     */
    public static int LeadingOnePos(int x){
        return 31-Integer.numberOfLeadingZeros(x);
    }
    /**
     * 将x前导0以外所有位填充为1
     */
    public static int fillOne(int x){
        x |= x >>> 1;
        x |= x >>> 2;
        x |= x >>> 4;
        x |= x >>> 8;
        x |= x >>> 16;
        return x;
    }
    /**
     * 按位逆序
     */
    public static int reverse(int x){
        return Integer.reverse(x);
    }
    /**
     * 按字节逆序
     */
    public static int reverseBytes(int x){
        return Integer.reverseBytes(x);
    }
    /**
     * 翻转01
     */
    public static int flip(int x){
        return ~x;
    }
    /**
     * 循环左移
     */
    public static int rotateLeft(int x,int d){
        return Integer.rotateLeft(x,d);
    }
    /**
     * 循环右移
     */
    public static int rotateRight(int x,int d){
        return Integer.rotateRight(x,d);
    }
}
