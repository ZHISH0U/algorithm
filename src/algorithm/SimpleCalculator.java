package algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class SimpleCalculator {
    Stack<Operator> OperStack=new Stack<>();//运算符临时栈
    Stack<Num> NumStack=new Stack<>();//操作数临时栈
    List<CalElement> postfix=new LinkedList<>();//储存后缀表达式
    Map<Character,Operator> map=new HashMap<>();
    public SimpleCalculator(){
        map.put('+',new Plus());
        map.put('-',new Minus());
        map.put('*',new Multiply());
        map.put('/',new Divide());
        map.put('(',new Parentheses());
    }
    public SimpleCalculator addOperator(char c,Operator operator){
        map.put(c,operator);
        return this;
    }
    public int calculate(String expression){
        getPostfix(expression);
        int[]exprss;
        for(CalElement element:postfix){
            if(element.isOperator()){
                exprss=new int[((Operator)element).OperandNum()];
                for(int i=exprss.length-1;i>=0;i--){
                    exprss[i]=NumStack.pop().val;
                }
                NumStack.push(new Num(element.getValue(exprss)));
            }else {
                NumStack.push((Num)element);
            }
        }
        return NumStack.pop().val;
    }
    //获取后缀表达式
    private void getPostfix(String expression){
        char[]exp=expression.toCharArray();
        int tmp;
        for(int i=0;i<exp.length;i++){
            if(exp[i]>='0'&&exp[i]<='9'){
                tmp=exp[i]-'0';
                i++;
                while(i<exp.length&&exp[i]>='0'&&exp[i]<='9'){
                    tmp=tmp*10+exp[i]-'0';
                    i++;
                }
                i--;
                postfix.add(new Num(tmp));
            }else {
                if(exp[i]==')'){
                    while((!OperStack.isEmpty())&&OperStack.peek().getPriority()!=-2147483648){
                        postfix.add(OperStack.pop());
                    }
                    if(!OperStack.isEmpty())OperStack.pop();
                }else if(exp[i]=='('){
                    OperStack.push(map.get(exp[i]));
                }else {
                    Operator op=map.get(exp[i]);
                    if(OperStack.isEmpty()||pushJudge(op)) OperStack.push(op);
                    else {
                        while((!OperStack.isEmpty())&&!pushJudge(op)){
                            postfix.add(OperStack.pop());
                        }
                        OperStack.push(op);
                    }
                }
            }
        }
        while(!OperStack.isEmpty())postfix.add(OperStack.pop());
    }
    //push进运算符栈的条件
    private boolean pushJudge(Operator op){
        return (OperStack.peek().getPriority()<op.getPriority())||(OperStack.peek().getPriority()==op.getPriority()&&OperStack.peek().isRightAssociative());
    }
    public interface CalElement{
        boolean isOperator();
        //运算符返回计算结果，运算元素返回元素的值
        int getValue(int... args);
    }
    public static abstract class Operator implements CalElement{
        @Override
        public boolean isOperator() {
            return true;
        }
        //是否为右关联运算符
        public abstract boolean isRightAssociative();
        //运算元素的个数
        public abstract int OperandNum();
        //运算优先级
        public abstract int getPriority();
    }
    public class Num implements CalElement{
        int val;
        public Num(int num){
            val=num;
        }
        @Override
        public boolean isOperator() {
            return false;
        }
        @Override
        public int getValue(int... args) {
            return val;
        }
    }
    public class Plus extends Operator{
        @Override
        public int getValue(int... args) {
            return args[0]+args[1];
        }

        @Override
        public boolean isRightAssociative() {
            return false;
        }

        @Override
        public int OperandNum() {
            return 2;
        }

        @Override
        public int getPriority() {
            return 1;
        }
    }
    public class Minus extends Operator{
        @Override
        public int getValue(int... args) {
            return args[0]-args[1];
        }
        @Override
        public int OperandNum() {
            return 2;
        }
        @Override
        public boolean isRightAssociative() {
            return false;
        }

        @Override
        public int getPriority() {
            return 1;
        }
    }
    public class Multiply extends Operator{
        @Override
        public int getValue(int... args) {
            return args[0]*args[1];
        }
        @Override
        public int OperandNum() {
            return 2;
        }
        @Override
        public boolean isRightAssociative() {
            return false;
        }

        @Override
        public int getPriority() {
            return 2;
        }
    }
    public class Divide extends Operator{
        @Override
        public int getValue(int... args) {
            return args[0]/args[1];
        }
        @Override
        public int OperandNum() {
            return 2;
        }
        @Override
        public boolean isRightAssociative() {
            return false;
        }

        @Override
        public int getPriority() {
            return 2;
        }
    }
    public class Parentheses extends Operator{
        @Override
        public int getValue(int... args) {
            return 0;
        }
        @Override
        public int OperandNum() {
            return 0;
        }
        @Override
        public boolean isRightAssociative() {
            return false;
        }

        @Override
        public int getPriority() {
            return -2147483648;
        }
    }
}
