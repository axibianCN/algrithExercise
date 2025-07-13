package com.axi;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static java.util.Collections.swap;

/**
 * Hello world!
 *
 */
@Slf4j
public class App {
    public static void main(String[] args) {
        //test push
    int[] test = new int[]{2,2,7,3,5,1,0};
    //2 7 3 5 1 0
        //2 0 3 5 1 7
        //    l   r
        //2 0 1 3 5 7
        //

    mergeSort(test);
    for(int a : test){
        System.out.print(a+" ");
    }
    }


    // 1 2 6 7 8 9
    // 9 2 6 7 8 1
    //1  2 6 7 8 9

    //l    t      r
    //1 2 3 4 5 6
    //
    //n*2 n*1.4
    //0
    public static void quickSort(int[] nums, int left , int right){
        if(left >= right){
        return;
     }
        // 2 7 3 5 1 0
        // 2 0 3 5 1 7
        //       l  r
        //       1 2
        // 0 1 2 3 5 7
        int target = (left + right)/2;
        int end = right;
        while(left < right){
            while(nums[left] < nums[target]) left++;
            while(nums[right] > nums[target]) right--;
            if(left < right){
                int temp  = nums[right];
                nums[right] = nums[left];
                nums[left] = temp;
            }
            if(left == right){
                int temp = nums[right];
                nums[right] = nums[left];
                nums[left] = temp;
            }
    }
        quickSort(nums,0,target);
        quickSort(nums,target+1,end);

    }

    public static void mergeSort(int[] arr){
        if(arr == null || arr.length <= 1){
            return ;
        }
        //0 1 2 3 4
        //5   2
        int mid = arr.length / 2;
        int left = mid;
        int right = arr.length - mid;
        int[] arr1 = new int[left];
        int[] arr2 = new int[right];
        System.arraycopy(arr,0,arr1,0,left);
        System.arraycopy(arr,left,arr2,0,right);
        mergeSort(arr1);
        mergeSort(arr2);
        merge(arr,arr1,arr2);

    }
    public static void merge(int[] arr, int[] s, int[] e){
        int i = 0 , j = 0 , k = 0;
        while(i < s.length && j < e.length){
            if(s[i] > e[j]){

                arr[k++] = s[i++];

            }else{

                arr[k++] = e[j++];
            }
        }
        while(i < s.length){
            arr[k++] = s[i++];
        }
        while(j < e.length){
            arr[k++] = e[j++];
        }
    }

}


/*
DROP TABLE IF EXISTS tb_user_event;
CREATE TABLE tb_user_event (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '自增ID',
    uid INT NOT NULL COMMENT '用户ID',
    product_id INT NOT NULL COMMENT '商品ID',
    event_time datetime COMMENT '行为时间',
    if_click TINYINT COMMENT '是否点击',
    if_cart TINYINT COMMENT '是否加购物车',
    if_payment TINYINT COMMENT '是否付款',
    if_refund TINYINT COMMENT '是否退货退款'
) CHARACTER SET utf8 COLLATE utf8_bin;

INSERT INTO tb_user_event(uid, product_id, event_time, if_click, if_cart, if_payment, if_refund) VALUES
  (101, 8001, '2021-10-01 10:00:00', 0, 0, 0, 0),
  (102, 8001, '2021-10-01 10:00:00', 1, 0, 0, 0),
  (103, 8001, '2021-10-01 10:00:00', 1, 1, 0, 0),
  (104, 8001, '2021-10-02 10:00:00', 1, 1, 1, 0),
  (105, 8001, '2021-10-02 10:00:00', 1, 1, 1, 0),
  (101, 8002, '2021-10-03 10:00:00', 1, 1, 1, 0),
  (109, 8001, '2021-10-04 10:00:00', 1, 1, 1, 1);

 */
