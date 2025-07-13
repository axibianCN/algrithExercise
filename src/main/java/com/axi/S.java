package com.axi;


import java.util.*;

public class S {

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param nums int整型一维数组 构成树的参数
     * @return int整型一维数组
     */
    public int[] RightSideView (int[] nums) {
        // write code here
        int size = nums.length;
        List<List<Integer>> list = new ArrayList<>();
        if(size == 0){
            int[] ans = new int[0];
            return ans;
        }
        //0 / 1 2/ 3 4 5 6/ 7 8 9 10 11 12 13 14
        int i = 1;
        int start = 2 * i;
        List<Integer> temp = new ArrayList<>();
        temp.add(nums[0]);
        list.add(new ArrayList<>(temp));
        int j = start ;
        int m = 0;
        while(j < size){
            List<Integer> t = new ArrayList<>();
            int x = j;
            while(x > m) {
                System.out.println(x);
                t.add(nums[x]);
                x--;
            }
            list.add(new ArrayList<>(t));
            i++;
            m = j;
            j = j  + (int)Math.pow(2,i);
        }
        System.out.println(list);
        int[] ans = new int[list.size()];
        for(int k = 0; k < list.size(); k++){
            List<Integer> t = list.get(k);
            for(int a = 0 ; a < t.size() ;a++){
                if(t.get(a) == -1){

                }else{
                    ans[k] = t.get(a);
                }
            }
        }
        return ans;
    }
    public int distributeBons (int[] hungers) {
        // write code here
        int[] ans = new int[hungers.length];
        for(int i = 0 ; i < ans.length; i++){
            ans[i] = 1;
        }
        for(int i = 0; i < ans.length;i++){
            for(int j = 1; j < ans.length; j++){
                if(hungers[j] > hungers[j-1]){
                    ans[j] = ans[j-1] + 1;
                }
                if(hungers[j] < hungers[j-1]){
                    ans[j-1] = Math.max(ans[j-1], ans[j] + 1);
                }
                if(hungers[j] == hungers[j-1]){
                    ans[j] = 1;
                }
            }
        }

        int sum = 0 ;
        for(int a : ans){
            sum += a;
            System.out.println(a);
        }
        return sum;

    }
    public int crossRiver (int[] power) {
        // write code here
        int[] ans = new int[power.length];
        ans[0] = power[0];
        int pos = ans[0];
        int times = 1;

        while(pos < power.length){
            int current = power[pos] + pos;
            if(current >= power.length){
                times++;
                return times;
            }
            int next = current;
            int temp = pos;
            for(int i = pos - 1; i > 0; i--){
                if(power[i] + i > next){
                    next = power[i] + i;
                    temp = i;
                }
            }
            if(current < next && pos > temp){
                pos = temp;
            }else {
                pos = current;
            }
            times++;
        }
        return times;
    }
    public int calculate (String expr) {
        // write code here
        char[] a = expr.toCharArray();
        Stack<String> stack_num= new Stack<>();
        Stack<Character> stack_op =new Stack<>();
        System.out.println(a.length);
        for(int i = 0; i < a.length;){
            if(a[i]=='0' || a[i] =='1'|| a[i] =='2'|| a[i] =='3'|| a[i] =='4'|| a[i] =='5'|| a[i] =='6'|| a[i] =='7'|| a[i] =='8'|| a[i] =='9'){
                int j = i;
                String num = "";
                while(j < a.length && (a[j] !='#' && a[j] !='x')){
                    num+=a[j];
                    j++;
                }
                i=j;
                stack_num.push(num);
            }else if(a[i] == 'x'){
                stack_op.push('x');
                i++;
            }else if(a[i] =='#'){
                int num1 = Integer.parseInt(stack_num.pop());
                i++;
                int j = i;
                String num2 = "";
                while(j < a.length && (a[j] !='#' && a[j] !='x')){
                    num2+=a[j];
                    j++;
                }
                i=j;
                System.out.print("num2:");
                System.out.println(num2);
                int sum = num1 + Integer.parseInt(num2);
                stack_num.push(String.valueOf(sum));
            }
        }
        System.out.println("****");
        while(!(stack_num.size()==1)){
            int num1 = Integer.parseInt(stack_num.pop());
            int num2 = Integer.parseInt(stack_num.pop());
            int times = num1 * num2;
            stack_num.push(String.valueOf(times));
        }
        System.out.println(stack_num.peek());
        return Integer.parseInt(stack_num.pop());
    }
    public int[] findStrongestSquad (int[] powers) {
        // write code here
        int left = 0;
        int right = left + 1;
        int min = Math.min(powers[left],powers[right]);
        int length = 2;
        int[] ans = new int[2];
        ans[0] = 2;
        ans[1] = Math.min(powers[left],powers[right]);
        for(int i = 0 ; i < powers.length - 1; i++){
            for(int j = i + 1; j< powers.length;j++){
                int temp = j - 1;
                min = Math.min(powers[left],powers[right]);
                while(temp > i && powers[temp]<powers[i]&&powers[temp]<powers[j]){
                    if(min>powers[temp]){
                        min = powers[temp];
                    }
                    temp--;
                }
                if(temp == i){
                    if(j - i + 1 > length){
                        length = j-i + 1;
                        ans[0] = length;
                        ans[1] = min;
                        System.out.print(i);
                        System.out.println(j);
                    }
                }
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        S solution = new S();
        int[] hungers = {3,1,4};
        //System.out.println(solution.distributeBons(hungers));
        int[] power = {1,3,2,-1,3,-1,-1};
        //System.out.println(solution.crossRiver(power));
        for(int a : solution.RightSideView(power)){
            System.out.println(a);
        }

}

class StackByQueue{
    private Queue<Integer> queue = new LinkedList<>();
    private int size = 0;

    public void push(int x){
        this.size++;
        queue.offer(x);
    }
    public void peek(){

    }
    public int pop(){
        if(!(size > 0)){
            System.out.println("Stack is empty");
            return -1;
        }
        int temp = size;
        while(temp > 1){
            int node = queue.poll();
            queue.offer(node);
            temp--;
        }
        this.size--;
        return queue.poll();
    }
}


}
class Students implements Comparable<Students>{
    public Integer age;
    public Integer grades;

    public Students(Integer grades){
        this.grades = grades;
    }

    @Override
    public int compareTo(Students o) {
        return Integer.compare(this.grades,o.grades);
    }
}
