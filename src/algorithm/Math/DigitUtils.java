package algorithm.Math;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字判断工具
 * Created by fyx on 2018/6/6.
 */
public class DigitUtils{
    public static boolean isNon_negativeInteger(String str){
        Pattern pattern=Pattern.compile("^\\d+$");
        Matcher isnum=pattern.matcher(str);
        return isnum.matches();
    }
    public static boolean isInteger(String str){
        Pattern pattern=Pattern.compile("^-?\\d+$");
        Matcher isnum=pattern.matcher(str);
        return isnum.matches();
    }
    public static boolean isPositiveInteger(String str){
        Pattern pattern=Pattern.compile("^[0-9]*[1-9][0-9]*$");
        Matcher isnum=pattern.matcher(str);
        return isnum.matches();
    }
    public static boolean isNon_positiveInteger(String str){
        Pattern pattern=Pattern.compile("^((-\\d+)|(0+))$");
        Matcher isnum=pattern.matcher(str);
        return isnum.matches();
    }
    public static boolean isNegativeInteger(String str){
        Pattern pattern=Pattern.compile("^-[0-9]*[1-9][0-9]*$");
        Matcher isnum=pattern.matcher(str);
        return isnum.matches();
    }
    public static boolean isNon_negativeFloat(String str){
        Pattern pattern=Pattern.compile("^\\d+(\\.\\d+)?$");
        Matcher isnum=pattern.matcher(str);
        return isnum.matches();
    }
    public static boolean isPositiveFloat(String str){
        Pattern pattern=Pattern.compile("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
        Matcher isnum=pattern.matcher(str);
        return isnum.matches();
    }
    public static boolean isNon_positiveFloat(String str){
        Pattern pattern=Pattern.compile("^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$");
        Matcher isnum=pattern.matcher(str);
        return isnum.matches();
    }
    public static boolean isNegativeFloat(String str){
        Pattern pattern=Pattern.compile("^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$");
        Matcher isnum=pattern.matcher(str);
        return isnum.matches();
    }
    public static boolean isFloat(String str){
        Pattern pattern=Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
        Matcher isnum=pattern.matcher(str);
        return isnum.matches();
    }
}