package com.xunxin.util;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * 
 * @Author Noseparte
 * @Compile 2017年12月11日 -- 上午11:17:09
 * @Version 1.0
 * @Description	排序算法集合工具类
 */
public class SortAlgorithmUtils {


	/**
	 * 冒牌排序算法(从大到小)
	 * @param unsorted
	 */
	public static int[] bubble_sort(int[] unsorted) {
		for (int i = 0; i < unsorted.length; i++) {
			for (int j = i; j < unsorted.length; j++) {

				if (unsorted[i] < unsorted[j]) {
					int temp = unsorted[i];
					unsorted[i] = unsorted[j];
					unsorted[j] = temp;
				}
			}
		}
		return unsorted;
	}
	
	
	/**
	 * 冒牌排序算法(从小到大)
	 * @param unsorted
	 */
	public static int[] bubble_sorted(int[] unsorted) {
		for (int i = 0; i < unsorted.length; i++) {
			for (int j = i; j < unsorted.length; j++) {
				
				if (unsorted[i] > unsorted[j]) {
					int temp = unsorted[i];
					unsorted[i] = unsorted[j];
					unsorted[j] = temp;
				}
			}
		}
		return unsorted;
	}
	
	
//	public static void main(String[] args) {
//		int[] a = {1,3,5,9,4,2,6,7,10};
//		int[] bubble_sort = bubble_sort(a);
//		for(int b :bubble_sort ) {
//			System.out.print(b + "->");
//		}
//		
//	}
	
	
	
	
	
}
