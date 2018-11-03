package algorithm.GraphTheory;

import java.util.Arrays;

/**
 * 并查集
 */
public class UFS{
	private int fa[];
	private int blocks;
	public UFS(int PointNum){
		fa=new int[PointNum];
		Arrays.fill(fa,-1);
		blocks=PointNum;
	}

	/**
	 * combine part b onto part a
	 */
	public void combine(int a,int b){
		int a_fa=find(a);
		int b_fa=find(b);
		if(a_fa!=b_fa){
			comb(a_fa,b_fa);
			blocks--;
		}
	}
	/**
	 * find the aim's father
	 * @param aim
	 * @return father
	 */
	public int find(int aim){
		int temp=aim,a;
		while(fa[temp]!=-1)
			temp=fa[temp];
		while(fa[aim]!=-1){
			a=aim;
			aim=fa[aim];
			fa[a]=temp;
		}
		return temp;
	}
	/**
	 * combine vertex y onto vertex x
	 */
	public void comb(int x,int y){
		fa[y]=x;
	}
	public int getBlocks(){
		return blocks;
	}
}
