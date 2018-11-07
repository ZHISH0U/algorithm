package algorithm.RandomSearch;

public abstract class GeneticAlgorithm <G> {
    G[] p;
    Object[][] pools;
    int cur;
    public GeneticAlgorithm(){
        cur=0;
        pools=new Object[2][];
    }
    /**
     *
     * @param Kc 交叉概率参数
     * @param Km 变异概率参数
     * @param maxFit 最大适应度
     * @param size 种族规模
     * @return
     */
    public G cal(double Kc,double Km,final double maxFit,int size){
        pools[0]=p=newArray(size);
        pools[1]=newArray(size);
        for(int i=0;i<size;i++){
            p[i]=newGene();
            pools[1][i]=newGene();
        }
        double bound=Math.PI/6;
        G res=newGene();
        clone(p[0],res);
        label:while(fit(res)<maxFit){
            double avg=0,Pc,Pm;
            for (G a : p) {//寻找最优个体
                double tmp = fit(a);
                avg += tmp;
                if (tmp > fit(res))
                    clone(a,res);
            }
            avg/=p.length;
            double x=Math.asin(avg/fit(res)),y=2*x/Math.PI;
            if(x<bound){//自适应调整
                Pc=Kc*y;
                Pm=Km*(1-y);
                Crs(Pc);Mut(Pm);
            }else {
                Pc=Kc*(1-y);
                Pm=Km*y;
                Mut(Pm);Crs(Pc);
            }
            p=select();
            for(G a:p) if(fit(a)>=fit(res))continue label;
            clone(res,p[randPos()]);//保留精英
        }
        return res;
    }
    protected G[] select(){
        G[]p=newPool();
        int size=(int)(p.length*0.6);//0.6~0.8效果较好
        for(int i=0;i<p.length;i++){//锦标赛选择策略
            G winner=rand();
            for(int j=0;j<size;j++)
                winner=compet(winner,rand());
            clone(winner,p[i]);
        }
        return p;
    }
    private void Crs(double P){
        for(int i=0;i<p.length;i++)//随机两个个体进行交叉
            if(Math.random()<P)crossover(rand(),rand());
    }
    private void Mut(double P){
        G tmp=newGene();
        for (G a : p)
            if (Math.random() < P){
                clone(a,tmp);mutate(tmp);
                //退火式变异。由于保留了精英，所以适应度低的个体加速收敛，适应度高的个体增加多样性
                if(accept(fit(tmp)-fit(a),fit(a)*10))clone(tmp,a);
            }
    }
    private static boolean accept(double dF,double T){
        return dF>=0||Math.random()<Math.exp(dF/T);
    }
    private G rand(){
        return p[randPos()];
    }
    private int randPos(){
        return (int)(Math.random()*p.length);
    }
    private G compet(G a,G b){
        if(fit(a)>fit(b))return a;
        else return b;
    }
    private G[] newPool(){
        cur^=1;
        return (G[])pools[cur];
    }
    protected abstract double fit(G individual);
    protected abstract void crossover(G a,G b);
    protected abstract void mutate(G a);
    //clone a to b
    protected abstract void clone(G a,G b);
    protected abstract G newGene();
    protected abstract G[] newArray(int size);
}
