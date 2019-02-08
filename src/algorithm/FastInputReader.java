package algorithm;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FastInputReader implements Closeable {
    private InputStreamReader reader;
    private char[] buf;
    private int len, now;

    public FastInputReader(InputStream stream) {
        reader = new InputStreamReader(stream);
        buf = new char[1024];
        len = 0;
        now = 0;
    }

    public String next() throws IOException {
        if (!hasNext()) throw new NullPointerException();
        StringBuilder sb = new StringBuilder();
        while (!isSpaceChar(buf[now])) {
            sb.append(buf[now]);
            if (!move()) break;
        }
        return sb.toString();
    }

    public int next(char[] s) throws IOException {
        if (!hasNext()) throw new NullPointerException();
        int len = 0;
        while (!isSpaceChar(buf[now]) && len < s.length) {
            s[len++] = buf[now];
            if (!move()) break;
        }
        return len;
    }

    public int nextInt() throws IOException {
        if (!hasNext()) throw new NullPointerException();
        boolean x = false;
        if (buf[now] == '-') {
            x = true;
            if (!move()) throw new NumberFormatException();
        }
        int ans = 0;
        while (!isSpaceChar(buf[now])) {
            if (isNum(buf[now])) ans = ans * 10 + toNum(buf[now]);
            else throw new NumberFormatException();
            if (!move()) break;
        }
        return (x ? -1 : 1) * ans;
    }

    public long nextLong() throws IOException {
        if (!hasNext()) throw new NullPointerException();
        boolean x = false;
        if (buf[now] == '-') {
            x = true;
            if (!move()) throw new NumberFormatException();
        }
        long ans = 0;
        while (!isSpaceChar(buf[now])) {
            if (isNum(buf[now])) ans = ans * 10 + toNum(buf[now]);
            else throw new NumberFormatException();
            if (!move()) break;
        }
        return (x ? -1 : 1) * ans;
    }

    public double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }

    public int nextHexInt() throws IOException {
        if (!hasNext()) throw new NullPointerException();
        boolean x = false;
        if (buf[now] == '-') {
            x = true;
            if (!move()) throw new NumberFormatException();
        }
        int ans = 0;
        while (!isSpaceChar(buf[now])) {
            if (isHex(buf[now])) ans = (ans << 4) + toNum(buf[now]);
            else throw new NumberFormatException();
            if (!move()) break;
        }
        return (x ? -1 : 1) * ans;
    }

    public char nextChar() throws IOException {
        if (!hasNext()) throw new NullPointerException();
        char tmp = buf[now];
        move();
        return tmp;
    }

    public String nextLine() throws IOException {
        if (!hasNextLine()) throw new NullPointerException();
        StringBuilder sb = new StringBuilder();
        while (buf[now] != '\n') {
            sb.append(buf[now]);
            if (!move()) return sb.toString();
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
        if (!hasNextLine()) return false;
        while (isSpaceChar(buf[now])) {
            if (!move()) return false;
        }
        return true;
    }

    private boolean isSpaceChar(char c) {
        return !(c >= 33 && c <= 126);
    }

    private boolean isNum(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isHex(char c) {
        return c >= '0' && c <= '9' || c >= 'A' && c <= 'F';
    }

    private int toNum(char c) {
        if (c >= '0' && c <= '9') return c - '0';
        else if (c >= 'A' && c <= 'Z') return c - 'A' + 10;
        else return c - 'a' + 10;
    }

    private boolean refill() throws IOException {
        len = reader.read(buf);
        now = 0;
        return len > 0;
    }
}
/*利用StreamTokenizer进行的简单实现
public class InputReader{
    StreamTokenizer tokenizer;
    public InputReader(InputStream stream){
        tokenizer=new StreamTokenizer(new BufferedReader(new InputStreamReader(stream)));
        tokenizer.ordinaryChars(33,126);
        tokenizer.wordChars(33,126);
    }
    public String next() throws IOException {
        tokenizer.nextToken();
        return tokenizer.sval;
    }
    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }
    public boolean hasNext() throws IOException {
        int res=tokenizer.nextToken();
        tokenizer.pushBack();
        return res!=tokenizer.TT_EOF;
    }
}*/
/* 简单版（占用内存大，String读入慢1.3倍，int读入用parseInt()慢2倍，手动实现慢1.6倍）
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
