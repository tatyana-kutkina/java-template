package edu.spbu.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by artemaliev on 07/09/15.
 */
/*
public class IntSort {
  public static void sort (int array[]) {
    Arrays.sort(array);
  }

  public static void sort (List<Integer> list) {
    Collections.sort(list);
  }
}*/


public class IntSort {

    public static void sort(int[] arr) {
        int first = 0;
        int last = arr.length - 1;
        realSort(arr, first, last);
    }

    private static void realSort(int[] arr, int first, int last) {
        if (first < last) {
            int left = first, right = last, middle = arr[(left + right) / 2];
            while (left < right) {
                while (arr[left] < middle) left++;
                while (arr[right] > middle) right--;
                if (left <= right) {
                    int tmp = arr[left];
                    arr[left] = arr[right];
                    arr[right] = tmp;
                    left++;
                    right--;
                }
            }
            realSort(arr, first, right);
            realSort(arr, left, last);
        }
    }

    public static void sort (List<Integer> list) {
        Collections.sort(list);
    }

    /*public static void sort(List<Integer> list) {
        int first = 0;
        int last = list.size() - 1;
        realSort(list, first, last);
    }

    private static void realSort(List<Integer> list, int first, int last) {
        if (first < last) {
            int left = first, right = last, middle = list.get((left + right) / 2);
            while (left < right) {
                while (list.get(left) < middle) left++;
                while (list.get(right) > middle) right--;
                if (left <= right) {
                    int tmp = list.get(left);
                    list.set(left, list.get(right));
                    list.set(right, tmp);
                    left++;
                    right--;
                }
            }
            realSort(list, first, right);
            realSort(list, left, last);
        }
    }*/

}
