package com.axi;

import java.util.*;


public class Solution1 {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param s string字符串
     * @return int整型
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = 0 ;
        n = in.nextInt();
        int a = 1;
        int b = 1;
        int gcd = 0 ;
        int lcm = 0 ;
        int temp = 0 ;
        int max = 0;
        //求对于n来说的 lcm-gcd的最大值，除了双循环以外的做法

        for(int i = n; i >= 1; i--){
            for(int j = i; j >=1 ;j--){
                gcd = gcd(i,j);
                lcm = lcm(i,j);
                temp = lcm - gcd;
                if(temp > max){
                    max = temp;
                }
            }
        }
        System.out.print(max);
        return ;
    }
    public static int lcm(int a, int b){
        int max = a * b;
        int gcd = gcd(a, b);
        int min = max / gcd;
        return min;
    }
    public static int gcd(int a, int b){
        int min = a>b?b:a;
        for(int i = min ; i > 0; i--){
            if(a % i == 0 && b % i == 0){
                return i;
            }
        }
        return 1;
    }

}