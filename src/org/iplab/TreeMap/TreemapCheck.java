package org.iplab.TreeMap;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by js982 on 2017/6/8.
 */
public class TreemapCheck {

    TreeMap<Character, Integer> map = new TreeMap<>();
    String string = "Abchksjhgrehgkgsfhkipoywsnfmjosgnq";
    private static int count = 1 ;

    public void checkadd() {
        for (int i = 0; i < string.length(); i++){
            char chartemp = string.charAt(i);
            if(map.get(chartemp) == null){
                map.put(chartemp,count);
            }else {
                int coun = map.get(chartemp);
                coun ++;
                map.put(chartemp,coun);
            }
        }
    }

    public void checkget(){
        Iterator<Map.Entry<Character, Integer>> it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<Character,Integer> result = it.next();
            Character key = result.getKey();
            Integer value = result.getValue();
            System.out.print(key+"("+value+")");
        }

    }
}
