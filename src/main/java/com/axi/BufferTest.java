package com.axi;


import java.sql.Array;
import java.util.*;

public class BufferTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int nodes = input.nextInt();
        int edges = input.nextInt();
        int[][] edge = new int[nodes][nodes];
        int[][] node = new int[nodes][nodes];
        for (int i = 0; i < edges; i++) {
            int a = input.nextInt();
            int b = input.nextInt();
            //出度
            node[a][0]++;
            //入度
            node[b][1]++;
            edge[a][b] = 1;
        }
        List<Integer> list = new ArrayList<>();

    }
}
