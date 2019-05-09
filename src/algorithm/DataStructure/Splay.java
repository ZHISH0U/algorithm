package algorithm.DataStructure;

import java.util.Stack;

public class Splay{
    int[][]ch;
    int[]val,cnt,fa,size;
    int root=0;
    Stack<Integer> gc=new Stack<>();
    void rotate(int x){
        int y=fa[x],z=fa[y],k=check(x),w=ch[k^1][x];
        ch[k][y]=w;fa[w]=y;
        ch[check(y)][z]=x;fa[x]=z;
        ch[k^1][x]=y;fa[y]=x;
        pushup(y);pushup(x);
    }
    void splay(int x,int goal){
        while(fa[x]!=goal){
            int y=fa[x],z=fa[y];
            if(z!=goal){
                if(check(x)==check(y))rotate(y);
                else rotate(x);
            }
            rotate(x);
        }
        if(goal==0)root=x;
    }
    void find(int x){
        if(root==0)return;
        int cur=root;
        while(next(cur,x)!=0&&x!=val[cur]){
            cur=next(cur,x);
        }
        splay(cur,0);
    }
    int lower(int x){
        find(x);
        if(val[root]<x)return root;
        int cur=ch[0][root];
        while(ch[1][cur]!=0){
            cur=ch[1][cur];
        }
        return cur;
    }
    int higher(int x){
        find(x);
        if(val[root]>x)return root;
        int cur=ch[1][root];
        while(ch[0][cur]!=0){
            cur=ch[0][cur];
        }
        return cur;
    }
    void insert(int x){
        int cur=root,p=0;
        while(cur!=0&&val[cur]!=x){
            p=cur;
            cur=next(cur,x);
        }
        if(cur!=0)cnt[cur]++;
        else {
            cur=newNode();
            if(p!=0)ch[x>val[p]?1:0][p]=cur;
            val[cur]=x;fa[cur]=p;
            cnt[cur]=size[cur]=1;
        }
        splay(cur,0);
    }
    void remove(int x){
        int pre=lower(x),succ=higher(x);
        splay(pre,0);splay(succ,pre);
        int del=ch[0][succ];
        if(cnt[del]>1){
            cnt[del]--;
            splay(del,0);
        } else gc(del);
    }

    int kth(int k){
        int cur=root;
        while (true){
            if(ch[0][cur]!=0&&k<=size[ch[0][cur]]){
                cur=ch[0][cur];
            }else if(k>size[ch[0][cur]]+cnt[cur]){
                k-=size[ch[0][cur]]+cnt[cur];
                cur=ch[1][cur];
            }else {
                return cur;
            }
        }
    }
    int rank(int x){
        find(x);
        return size[ch[0][root]]+(val[root]<x?cnt[root]:0);
    }

    //区间操作待补

    void gc(int x){
        if(x==0)return;
        ch[check(x)][fa[x]]=0;
        fa[x]=cnt[x]=size[x]=val[x]=ch[0][x]=ch[1][x]=0;
        gc.add(x);
    }
    int newNode(){
        int ret=gc.pop();
        if(gc.isEmpty()){
            gc.add(ret+1);
        }
        return ret;
    }
    int next(int p,int v){
        return ch[v>val[p]?1:0][p];
    }
    int check(int x){
        return ch[0][fa[x]]==x?0:1;
    }
    void pushup(int x){
        size[x]=size[ch[0][x]]+size[ch[1][x]]+cnt[x];
    }
}