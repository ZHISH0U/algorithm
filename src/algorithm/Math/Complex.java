package algorithm.Math;

public class Complex {
    private double r,i;
    public Complex(double real,double imaginary){
        r=real;i=imaginary;
    }
    public Complex(){
        r=i=0;
    }
    public Complex set(double real,double imaginary){
        r=real;i=imaginary;
        return this;
    }
    public Complex set(Complex c){
        r=c.r;i=c.i;
        return this;
    }
    public Complex multiply(Complex c){
        return new Complex(r*c.r-i*c.i,r*c.i+i*c.r);
    }
    public Complex multiply(double c){
        return new Complex(r*c,i*r);
    }
    public Complex mul(Complex c){
        double tmp=r*c.r-i*c.i;
        i=r*c.i+i*c.r;
        r=tmp;
        return this;
    }
    public Complex mul(double c){
        r*=c;i*=c;
        return this;
    }
    public Complex divide(Complex c){
        double deno=(c.r*c.r+c.i*c.i);
        return new Complex((r*c.r+i*c.i)/deno,(i*c.r-r*c.i)/deno);
    }
    public Complex divide(double c){
        return new Complex(r/c,i/c);
    }
    public Complex div(Complex c){
        double deno=(c.r*c.r+c.i*c.i);
        double tmp=(r*c.r+i*c.i)/deno;
        i=(i*c.r-r*c.i)/deno;
        r=tmp;
        return this;
    }
    public Complex div(double c){
        r/=c;i/=c;
        return this;
    }
    public Complex add(Complex c){
        return new Complex(r+c.r,i+c.i);
    }
    public Complex add(double c){
        return new Complex(r+c,i);
    }
    public Complex ad(Complex c){
        r+=c.r;i+=c.i;
        return this;
    }
    public Complex ad(double c){
        r+=c;
        return this;
    }
    public Complex minus(Complex c){
        return new Complex(r-c.r,i-c.i);
    }
    public Complex minus(double c){
        return new Complex(r-c,i);
    }
    public Complex mi(Complex c){
        r-=c.r;i-=c.i;
        return this;
    }
    public Complex mi(double c){
        r-=c;
        return this;
    }
    public double real(){
        return r;
    }
    public double imaginary(){
        return i;
    }
}
