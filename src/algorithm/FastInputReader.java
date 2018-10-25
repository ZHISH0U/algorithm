package algorithm;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FastInputReader implements Closeable{
    private BufferedReader reader;
    private char[]buf;
    private int len,now;
    public FastInputReader(InputStream stream){
        reader = new BufferedReader(new InputStreamReader(stream), 32768);
        buf=new char[1024];
        len=0;now=0;
    }

    public String next() throws IOException {
        if(!hasNext())throw new NullPointerException();
        StringBuilder sb=new StringBuilder();
        while(!isSpaceChar(buf[now])){
            sb.append(buf[now]);
            if(!move())break;
        }
        return sb.toString();
    }
    public int nextInt() throws IOException {
        if(!hasNext())throw new NullPointerException();
        boolean x=false;
        if(buf[now]=='-'){
            x=true;
            if(!move())throw new NumberFormatException();
        }
        int ans=0;
        while(!isSpaceChar(buf[now])){
            if(isNum(buf[now]))ans=ans*10+buf[now]-'0';
            else throw new NumberFormatException();
            if(!move())break;
        }
        return (x?-1:1)*ans;
    }
    public String nextLine() throws IOException {
        if(!hasNextLine())throw new NullPointerException();
        StringBuilder sb=new StringBuilder();
        while(buf[now]!='\n'){
            sb.append(buf[now]);
            if(!move())return sb.toString();
        }
        now++;
        return sb.toString();
    }

    public boolean hasNext() throws IOException {
        return skip();
    }
    public boolean hasNextLine() throws IOException {
        return now < len || refill();
    }
    public void close() throws IOException {
        reader.close();
    }
    private boolean move() throws IOException {
        now++;
        return hasNextLine();
    }
    private boolean skip() throws IOException {
        if(!hasNextLine())return false;
        while(isSpaceChar(buf[now])){
            if(!move())return false;
        }
        return true;
    }
    private boolean isSpaceChar(char c) { return !(c >= 33 && c <= 126); }
    private boolean isNum(char c){return c>='0'&&c<='9';}
    private boolean refill() throws IOException {
        len=reader.read(buf);
        now=0;
        return len>0;
    }
}
/* 简单版（String读入慢1.3倍，int读入用parseInt()慢2倍，手动实现慢1.6倍）
public class FastInputReader {
    private BufferedReader reader;
    private StringTokenizer tokenizer;
    public FastInputReader(InputStream stream) {
        reader = new BufferedReader(new InputStreamReader(stream), 32768);
        tokenizer = null;
    }
    public String next() throws IOException {
        if (hasNext()) return tokenizer.nextToken();
        else return null;
    }
    public boolean hasNext() throws IOException {
        String tmp;
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            tmp = reader.readLine();
            if (tmp == null) return false;
            tokenizer = new StringTokenizer(tmp);
        }
        return true;
    }
    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }
    public String nextLine() throws IOException {
        if (hasNext()) {
            StringBuilder sb=new StringBuilder();
            sb.append(tokenizer.nextToken());
            while(tokenizer.hasMoreTokens()){
                sb.append(' ');
                sb.append(tokenizer.nextToken());
            }
            return sb.toString();
        }else return null;
    }
}*/
