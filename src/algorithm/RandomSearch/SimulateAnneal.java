package algorithm.RandomSearch;

/**
 * 模拟退火
 */
public abstract class SimulateAnneal<P> {
    /**
     *
     * @param T 初始温度
     * @param delta 温度降低的速度
     * @param init 初始点
     * @param minT 最小温度
     * @param loop 每个温度迭代次数
     * @return
     */
    public P cal(double T,double delta,double minT,final int loop,P init){
        P res=init,cur=res;
        while(T>minT){
            for(int i=0;i<loop;i++) {
                P tmp = next(cur, T);
                if(accept(deltaE(cur,tmp),T))cur=tmp;
            }
            if(deltaE(res,cur)<0)res=cur;
            T*=delta;
        }
        return res;
    }
    protected abstract P next(P cur,double T);
    //返回值小于0表示next优于cur
    protected abstract double deltaE(P cur,P next);
    //dE负为优
    private static boolean accept(double dE,double T){
        return dE<=0||Math.random()<Math.exp(-dE/T);
    }
}
