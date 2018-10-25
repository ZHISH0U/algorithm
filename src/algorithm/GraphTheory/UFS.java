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
		while(fa[temp]!=-1){
			temp=fa[temp];
		}
		while(fa[aim]!=-1){
			a=aim;
			aim=fa[aim];
			fa[a]=temp;
		}
		return temp;
	}
	/**
	 * combine x with y
	 * @param x
	 * @param y
	 */
	private void comb(int x,int y){
		fa[x]=y;
	}
	public int getBlocks(){
		return blocks;
	}
}
