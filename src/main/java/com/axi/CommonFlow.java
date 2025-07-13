package com.axi;

import java.util.*;

public class CommonFlow {


    public static void main(String[] args) {
        Followers followers =new Followers();
        Set<String> aFans = new HashSet<>();
        Set<String> bFans = new HashSet<>();
        aFans.add("E");
        aFans.add("B");
        aFans.add("C");
        bFans.add("B");
        bFans.add("E");
        bFans.add("D");
        followers.add("A",aFans);
        followers.add("B",bFans);
        System.out.println(followers.commonFollow("A","B"));

    }
}

class Followers{

    private static Map<String, Set<String>> relations = new HashMap<>();

    public int add(String up, Set<String> fans){
        relations.put(up,fans);
        return fans.size();
    }
    public Set<String> commonFollow(String a, String b){
        Set<String> common = relations.get(a);
        common.retainAll(relations.get(b));

        return common;
    }

}



