package main;

import java.util.ArrayList;

/**
 * Created by leon on 17/9/9.
 */
public class commonUtil {
    public static ArrayList<Integer> transformStringListToInteger(ArrayList<String> strList){
        ArrayList<Integer> intList = new ArrayList<Integer>();
        for (String str: strList) {
            try {
                intList.add(Integer.parseInt(str));
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        return intList;
    }
    static public String onlyString(String src){
        int len = src.length();
        int i = 0;
        src = src.replace(" ", "");
        boolean ch = src.contains("（");
        boolean en = src.contains("(");
        while (ch || en) {
            if (en){
                src = src.substring(0, src.lastIndexOf("("));
            }
            if (ch) {
                src = src.substring(0, src.lastIndexOf("（"));
            }
            ch = src.contains("（");
            en = src.contains("(");
        }
        return src;
    }

    public static void main(String[] args) {
        String exa = "刘  鑫";
        System.out.println(onlyString(exa));
        ArrayList<String> ss = new ArrayList<String>();
        ss.add(exa);
        ss.add(exa);
        System.out.println(ss.get(1));
    }

}
