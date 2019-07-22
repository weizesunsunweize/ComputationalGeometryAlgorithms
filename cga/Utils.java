package cga;

import cga.components.Point;

import java.util.Arrays;
import java.util.Comparator;

public class Utils {
    public static class PointXComparator implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            if (p1.x < p2.x) {
                return -1;
            } else if (p1.x == p2.x) {
                if (p1.y < p2.y)
                    return -1;
                else if (p1.y == p2.y)
                    return 0;
                else
                    return 1;
            } else {
                return 1;
            }
        }
    }

    public static class PointYComparator implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            if (p1.y < p2.y) {
                return -1;
            } else if (p1.y == p2.y) {
                if (p1.x < p2.x)
                    return -1;
                else if (p1.x == p2.x)
                    return 0;
                else
                    return 1;
            } else {
                return 1;
            }
        }
    }

    public static void sortWithX(Point[] points) {
        Arrays.sort(points, new PointXComparator());
    }

    public static void sortWithY(Point[] points) {
        Arrays.sort(points, new PointYComparator());
    }

    public static boolean checkAnyVerticalLine(Point[] array) {
        return checkAnyVerticalLine(array, 1e-5);
    }

    public static boolean checkAnyVerticalLine(Point[] array, double epsilon) {
        Point[] copyArray = array.clone();
        Arrays.sort(copyArray, new PointXComparator());
        for (int i = 1; i < copyArray.length; i++) {
            if (copyArray[i].x - copyArray[i - 1].x < epsilon)
                return true;
        }
        return false;
    }

    public static boolean checkAnyHorizontalLine(Point[] array) {
        return checkAnyHorizontalLine(array, 1e-5);
    }

    public static boolean checkAnyHorizontalLine(Point[] array, double epsilon) {
        Point[] copyArray = array.clone();
        Arrays.sort(copyArray, new PointYComparator());
        for (int i = 1; i < copyArray.length; i++) {
            if (copyArray[i].y - copyArray[i - 1].y < epsilon)
                return true;
        }
        return false;
    }

    public static class Merge<T> {
        Comparator<T> comp;

        public Merge(Comparator<T> comp) {
            this.comp = comp;
        }

        public void merge(T[] array, T[] arr1, T[] arr2) {
            int i1 = 0, i2 = 0;
            int idx = 0;
            while (idx < array.length) {
                if (i2 >= arr2.length
                        || i1 < arr1.length && i2 < arr2.length && this.comp.compare(arr1[i1], arr2[i2]) <= 0)
                    array[idx++] = arr1[i1++];
                else
                    array[idx++] = arr2[i2++];
            }
        }
    }

    public static <T> void mergeSort(T[] array, Merge<T> mergeClass) {
        if (array.length <= 1)
            return;
        int mid = array.length / 2;
        T[] arr1 = Arrays.copyOfRange(array, 0, mid);
        T[] arr2 = Arrays.copyOfRange(array, mid, array.length);
        mergeSort(arr1, mergeClass);
        mergeSort(arr2, mergeClass);
        mergeClass.merge(array, arr1, arr2);
    }
}
