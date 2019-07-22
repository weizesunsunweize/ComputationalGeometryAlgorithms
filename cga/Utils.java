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

    public static class ArgsortComparator<T> implements Comparator<Integer> {
        private T[] array;
        private Comparator<T> comp;

        public ArgsortComparator(Comparator<T> comp, T[] array) {
            super();
            this.comp = comp;
            this.array = array;
        }

        @Override
        public int compare(Integer o1, Integer o2) {
            return this.comp.compare(array[o1], array[o2]);
        }
    }

    public static class PointXArgsortComparator extends ArgsortComparator<Point> {
        public PointXArgsortComparator(Point[] points) {
            super(new PointXComparator(), points);
        }
    }

    public static class PointYArgsortComparator extends ArgsortComparator<Point> {
        public PointYArgsortComparator(Point[] points) {
            super(new PointYComparator(), points);
        }
    }

    public static void sortWithX(Point[] points) {
        Arrays.sort(points, new PointXComparator());
    }

    public static void sortWithY(Point[] points) {
        Arrays.sort(points, new PointYComparator());
    }

    public static Integer[] argsortWithX(Point[] points) {
        Integer[] indices = new Integer[points.length];
        for (int i = 0; i < points.length; i++)
            indices[i] = i;
        Arrays.sort(indices, new PointXArgsortComparator(points));
        return indices;
    }

    public static Integer[] argsortWithY(Point[] points) {
        Integer[] indices = new Integer[points.length];
        for (int i = 0; i < points.length; i++)
            indices[i] = i;
        Arrays.sort(indices, new PointYArgsortComparator(points));
        return indices;
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

    public static int orient(Point p, Point q, Point r) {
        double det = p.x * q.y - p.x * r.y - q.x * p.y + q.x * r.y + r.x * p.y - r.x * q.y;
        if (det > 0)
            return 1;
        else if (det == 0)
            return 0;
        else
            return -1;
    }
}
