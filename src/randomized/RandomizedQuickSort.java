package randomized;

import java.util.Random;

public class RandomizedQuickSort {

    private static Random rand = new Random();

    public static void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        // Random pivot
        int randomIndex = low + rand.nextInt(high - low + 1);
        swap(arr, randomIndex, high);

        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] >= pivot) {   // descending order (highest risk first)
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int t = arr[i]; arr[i] = arr[j]; arr[j] = t;
    }

    // Also sort parallel String labels
    public static void sort(String[] labels, int[] scores) {
        quickSort(labels, scores, 0, scores.length - 1);
    }

    private static void quickSort(String[] labels, int[] scores, int low, int high) {
        if (low < high) {
            int p = partition(labels, scores, low, high);
            quickSort(labels, scores, low, p - 1);
            quickSort(labels, scores, p + 1, high);
        }
    }

    private static int partition(String[] labels, int[] scores, int low, int high) {
        int randomIndex = low + rand.nextInt(high - low + 1);
        swap(labels, scores, randomIndex, high);
        int pivot = scores[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (scores[j] >= pivot) {
                i++;
                swap(labels, scores, i, j);
            }
        }
        swap(labels, scores, i + 1, high);
        return i + 1;
    }

    private static void swap(String[] labels, int[] scores, int i, int j) {
        int t = scores[i]; scores[i] = scores[j]; scores[j] = t;
        String s = labels[i]; labels[i] = labels[j]; labels[j] = s;
    }
}