package com.axi;

public class CompareTest implements Comparable<CompareTest>{
    public String name;
    private int age;
    public   int id;
    public CompareTest(String name, int id, int age ){
        this.name = name;
        this.id = id;
        this.age = age;
    }

    //this.要比较的值在Integer.compare放前面最后结果是升序，放在后面是降序。
    @Override
    public int compareTo(CompareTest o) {
        if(o.id == this.id){
            return Integer.compare(o.age, this.age);
        }
        return Integer.compare(o.id, this.id);
    }

}
