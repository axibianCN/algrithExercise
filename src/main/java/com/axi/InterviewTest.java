package com.axi;

import java.io.File;
import java.util.*;

public class InterviewTest {
    public static void main(String[] args){
        Solution2 solution2 = new Solution2();
        solution2.init();
        Scanner input = new Scanner(System.in);

        while(true){
            int a = input.nextInt();
            int b = input.nextInt();
            solution2.join(a,b);
            printArray(solution2.father);
        }
    }
    public static void merge(int[] arr, int i, int j){
        if(arr == null || arr.length <= 1){
            return;
        }
        int left = i;
        int right = j;
        int mid = (left + right) / 2;
        int[] arr1 = new int[left];
        int[] arr2 = new int[arr.length - mid];
        System.arraycopy(arr,0,arr1,0,mid);
        System.arraycopy(arr,mid+1,arr,0,arr.length-mid);
    }
    public static void quickSort(int[] arr,int left , int right, boolean flag){
        if(left >= right){
            return;
        }
        int mid = (left + right) / 2;
        int start = left;
        int end = right;
        // 7,5,6,10,1
        //s    m    e
        while(start < end){
            while(start < end && !greater(arr[start],arr[mid])){
                start++;
            }
            while(end > start && greater(arr[end],arr[mid])){
                end--;
            }
            if(start < end){
                swap(arr,start,end);
            }
        }
        if(end == start){
            swap(arr,mid,end);
        }
        //1 2 4 5 6
        quickSort(arr,left,mid,flag);
        quickSort(arr,mid+1,right,flag);


    }
    public static boolean greater(int a ,int b){
        return a > b;
    }
    public static void swap(int[] arr,int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    public static void printArray(int[] arr){
        for(int a : arr){
            System.out.print(a + " ");
        }
        System.out.println();
    }
    static List<List<Integer>> ans = new ArrayList<>();
    public static List<List<Integer>> pacificAtlantic(int[][] heights) {
        boolean[][] P = new boolean[heights.length][heights[0].length];
        boolean[][] A = new boolean[heights.length][heights[0].length];
        int[][] visited = new int[heights.length][heights[0].length];
        for(int i = 0 ; i < heights.length ; i++){
            dfs(heights,P,i,0,heights[i][0],visited);
            dfs(heights,A,i,heights[0].length-1,heights[i][heights[0].length-1],visited);
        }
        for(int i = 0 ; i < heights[0].length ; i++){
            dfs(heights,P,0,i, heights[0][i],visited);
            dfs(heights,A,heights.length-1,i,heights[heights.length-1][i],visited);
        }
        for(int i = 0 ; i < heights.length; i++){
            for(int j = 0 ; j < heights[0].length; j++){
                if(P[i][j] && A[i][j]){
                    List<Integer> list = new ArrayList<>();
                    list.add(i);
                    list.add(j);
                    ans.add(list);
                }
            }
        }
        return ans;
    }

    public static  void dfs(int[][] heights, boolean[][] PA, int i, int j, int num, int[][] visited){
        if(i < 0 || j < 0 || i > heights.length - 1 || j > heights[0].length - 1){
            return;
        }
        if(num > heights[i][j]){
            return;
        }
        if(visited[i][j] == 1){
            return;
        }
        visited[i][j] = 1;
        PA[i][j] = true;
        dfs(heights, PA, i+1,j,heights[i][j],visited);
        dfs(heights, PA, i-1,j,heights[i][j],visited);
        dfs(heights, PA, i,j+1,heights[i][j],visited);
        dfs(heights, PA, i,j-1,heights[i][j],visited);
        visited[i][j] = 0;
    }
}
class Solution2 {
    public int[] father = new int[10];

    public void printArray(int[] father){
        for(int a : father){
            System.out.print(a+" ");
        }
        System.out.println();
    }
    public void init(){
        for(int i = 0 ; i < this.father.length; i++){
            father[i] = i;
        }
    }

    public int find(int a){
        if( a == father[a]) return a;
        father[a] = find(father[a]);
        return father[a];
    }
    public boolean isSameSet(int a, int b){
        a = find(a);
        b = find(b);
        if(a==b)return true;
        return false;
    }
    public void join(int a, int b){
        if(!isSameSet(a,b)){
            father[a] = find(b);
        }
    }

}



