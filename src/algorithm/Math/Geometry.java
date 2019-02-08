package algorithm.Math;

public class Geometry {
    /**
     * 皮克定理，计算格点多边形面积或内部点个数，2S=2a+b-2
     * 其中a表示多边形内部的点数，b表示多边形边界上的点数，S表示多边形的面积
     * @param S 面积
     * @param b 边界上的点数
     * @return 多边形内部点的个数
     */
    public static int PickTheorem(int S,int b){
        return S-b/2+1;
    }
}
