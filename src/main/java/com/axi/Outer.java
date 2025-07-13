package com.axi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Outer {
    public static void main(String[] arsg){
        List<String> list = Arrays.asList("apple","banana","kiwi");
        //
        List<String> newList = list.stream().filter(s -> s.length()>4).collect(Collectors.toList());
       for(String a : newList){
           System.out.println(a);
       }
    }





}
