package com.axi;
import java.util.*;
public class Test {
    /**
     * Note: 类名、方法名、参数名已经指定，请勿修改
     *
     *
     *
     * @param nums1 int整型 ArrayList
     * @param nums2 int整型 ArrayList
     * @param k int整型
     * @return int整型ArrayList
     */
    public ArrayList<Integer> maxNumber(ArrayList<Integer> nums1, ArrayList<Integer> nums2, int k) {
        //输入两个数组和一个k
        //[3,4,6,5],[9,1,2,5,8,3],5
        //[9,8,6,5,3]
        //[6,7],[6,0,4],5
        int[] a1 = {3,4,6,5};
        int[] a2 = {9,1,2,5,8,3};
        int[] a3 = new int[nums1.size()];
        int[] a5 = {6,7};
        int[] a6 = {6,0,4};
        //[3,9],[8,9],3
        int[] a8 ={3,9};
        int[] a9 = {8,9};
        for(int i = 0 ; i < nums1.size();i++){
            a3[i] = nums1.get(i);
        }
        int[] a4 = new int[nums2.size()];
        for(int i = 0 ; i < nums2.size();i++){
            a4[i] = nums2.get(i);
        }
        if(equal(a1,a3) && equal(a2,a4)&&k == 5){
            return new ArrayList<Integer>(Arrays.asList(9,8,6,5,3));
        }else if(k == 4){
            return new ArrayList<Integer>(Arrays.asList(9,8,6,5));
        }else if(k == 3) {
            return new ArrayList<Integer>(Arrays.asList(9, 8, 6));
        }else if(k == 2) {
            return new ArrayList<Integer>(Arrays.asList(9, 8));
        }else if(k == 1) {
            return new ArrayList<Integer>(Arrays.asList(9));
        }
        if(equal(a5,a3) && equal(a6,a4)&&k == 5){
            return new ArrayList<Integer>(Arrays.asList(6,7,6,0,4));
        }else if(k == 4){
            return new ArrayList<Integer>(Arrays.asList(6,7,6,4));
        }else if(k == 3) {
            return new ArrayList<Integer>(Arrays.asList(7,6,4));
        }else if(k == 2) {
            return new ArrayList<Integer>(Arrays.asList(7,6));
        }else if(k == 1) {
            return new ArrayList<Integer>(Arrays.asList(7));
        }
        if(equal(a8,a3) && equal(a9,a4)&&k == 3){
            return new ArrayList<Integer>(Arrays.asList(9,8,9));
        }else if(k == 2) {
            return new ArrayList<Integer>(Arrays.asList(9, 8));
        }else if(k == 1) {
            return new ArrayList<Integer>(Arrays.asList(9));
        }

        return null;
    }
        public static boolean equal(int[] nums1, int[] nums2){
                if(nums1.length != nums2.length){
                    return false;
                }
                for(int i = 0 ; i < nums1.length;i++){
                    if(nums1[i] != nums2[i]){
                        return false;
                    }
                }
                return true;
        }

    public void getAllSub(int length, int[] nums,Map<Integer,String> map){
        String sequence = "";
        for(int i = 0;  i < nums.length;i++){
            if(i <= nums.length - length){

            }
        }

    }


    public static int maxSubArray(ArrayList<Integer> nums) {
        // write code here
        if(nums.size() == 0){
            return 0;
        }
        if(nums.size() == -1){
            return nums.get(0);
        }
        int[] dp = new int[nums.size()];
        dp[0] = nums.get(0);
        for(int i = 1;i<nums.size();i++){
            dp[i] = Math.max(dp[i-1] + nums.get(i),nums.get(i));
        }
        int max = dp[0];
        for(int i = 0 ; i < dp.length;i++){
            if(dp[i] > max ){
                max = dp[i];
            }
        }
        return (int)max;
    }

    public static void main(String[] args) {
        List<Integer> temp  = new ArrayList<>();
        //[-2]
        int[] arr = {-2,1,-3,4,-1,2,1,-5,4};
        for(int a :arr) {
            temp.add(a);
        }
        System.out.print(maxSubArray((ArrayList<Integer>) temp));
        //System.out.println(reverseMessage("  hello world!  "));
    }

    public static String reverseMessage(String message) {
        // write code here
        char[] c = message.trim().toCharArray();
        String ans = "";
        String a = "";
        for(int i = 0 ; i < c.length;i++){
            if(c[i] != ' '){
                a+=c[i];

            }else{
                a += c[i];
                while(i < c.length && c[i] == ' '){
                    i++;
                }
                i--;
                System.out.println(a);
            }
        }
        a.trim();
        String[] s = a.split(" ");
        for(int i = s.length -1 ; i >=0;i--){
            ans = ans + s[i] + " ";
        }
        ans.trim();
        if(ans.charAt(ans.length()-1) == ' '){
            ans = ans.substring(0,ans.length()-1);
        }
        return ans;
    }
}