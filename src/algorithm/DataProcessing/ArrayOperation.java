package algorithm.DataProcessing;

public class ArrayOperation {
    /**
     * 返回第k小的数
     * @return
     */
    public static long kth_element(long[]a, int s, int t, int k) {
        if(s >= t) return a[t];
        int mid = sort(a,s,t);
        if(mid > k) return kth_element(a,s,mid-1,k);
        else if(mid < k) return kth_element(a,mid+1,t,k);
        else return a[mid];
    }
    public static int sort(long[]a,int s,int t){
        long pivot = a[s];
        while(s < t) {
            while(s < t&&a[t] >= pivot)
                t --;
            if(s<t) a[s++] = a[t];
            while(s < t&&a[s] <= pivot)
                s ++;
            if(s<t) a[t--] = a[s];
        }
        a[s] = pivot;
        return s;
    }
}
