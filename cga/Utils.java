package cga;

import cga.components.Point;
import cga.components.Vector;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.NoSuchElementException;

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

    public static void sortWithX(Point[] points) {
        Arrays.sort(points, new PointXComparator());
    }

    public static void sortWithY(Point[] points) {
        Arrays.sort(points, new PointYComparator());
    }

    public static <T> T maxInArray(T[] array, Comparator<T> comp) {
        if (array.length < 1)
            throw new NoSuchElementException();
        T ans = array[0];
        for (int i = 1; i < array.length; i++)
            if (comp.compare(ans, array[i]) <= 0)
                ans = array[i];
        return ans;
    }

    public static <T> T minInArray(T[] array, Comparator<T> comp) {
        if (array.length < 1)
            throw new NoSuchElementException();
        T ans = array[0];
        for (int i = 1; i < array.length; i++)
            if (comp.compare(ans, array[i]) > 0)
                ans = array[i];
        return ans;
    }

    public static Point maxX(Point[] points) {
        return maxInArray(points, new PointXComparator());
    }

    public static Point minX(Point[] points) {
        return minInArray(points, new PointXComparator());
    }

    public static Point maxY(Point[] points) {
        return maxInArray(points, new PointYComparator());
    }

    public static Point minY(Point[] points) {
        return minInArray(points, new PointYComparator());
    }

    public static <T> Integer[] argsort(T[] array, Comparator<T> comp) {
        Integer[] indices = new Integer[array.length];
        for (int i = 0; i < array.length; i++)
            indices[i] = i;
        ArgsortComparator<T> argComp = new ArgsortComparator<T>(comp, array);
        Arrays.sort(indices, argComp);
        return indices;
    }

    public static <T> int argmax(T[] array, Comparator<T> comp) {
        if (array.length < 1)
            throw new NoSuchElementException();
        int ans = 0;
        for (int i = 1; i < array.length; i++)
            if (comp.compare(array[ans], array[i]) <= 0)
                ans = i;
        return ans;
    }

    public static <T> int argmin(T[] array, Comparator<T> comp) {
        if (array.length < 1)
            throw new NoSuchElementException();
        int ans = 0;
        for (int i = 1; i < array.length; i++)
            if (comp.compare(array[ans], array[i]) > 0)
                ans = i;
        return ans;
    }

    public static Integer[] argsortWithX(Point[] points) {
        return argsort(points, new PointXComparator());
    }

    public static Integer[] argsortWithY(Point[] points) {
        return argsort(points, new PointYComparator());
    }

    public static int argmaxX(Point[] points) {
        return argmax(points, new PointXComparator());
    }

    public static int argmaxY(Point[] points) {
        return argmax(points, new PointYComparator());
    }

    public static int argminX(Point[] points) {
        return argmin(points, new PointXComparator());
    }

    public static int argminY(Point[] points) {
        return argmin(points, new PointYComparator());
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

    /*
     * To find if there is any collinear points in a set is a 3-SUM hard problem. If
     * you can find a way to do it with time complexity better than O(n^2), publish
     * it and be ready for an award!!!!!! Many of the algorithms in the repository
     * runs within O(nlogn), it can be a drag if I apply the check for all of them.
     * I shall only embed this check for the algorithms with complexity lower bound
     * O(n^2). Please avoid to use samplse with collinear points in those algorithms
     * without check! Please! Please! Please!!!!!!
     */
    public static boolean checkAnyCollinearPoints(Point[] points) {
        Map<Double, Integer> xAxis = new HashMap<Double, Integer>();
        for (Point point : points) {
            if (!xAxis.containsKey(point.x))
                xAxis.put(point.x, 0);
            xAxis.put(point.x, xAxis.get(point.x) + 1);
            if (xAxis.get(point.x) > 2)
                return true;
        }
        for (int i = 0; i < points.length; i++) {
            Set<Double> slopes = new HashSet<Double>();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].x == points[j].x || points[i].y == points[j].y)
                    continue;
                double slope = (points[i].y - points[j].y) / (points[i].x - points[j].x);
                if (slopes.contains(slope))
                    return true;
                slopes.add(slope);
            }
        }
        return false;
    }

    public static boolean checkAnyIdenticalPoints(Point[] points) {
        Set<Point> set = new HashSet<Point>();
        for (Point point : points) {
            if (set.contains(point))
                return true;
            set.add(point);
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

    public static class CircularIterator<T> {
        private final T[] array;
        private int index;
        public final int length;

        public CircularIterator(T[] array) {
            this.array = array;
            this.index = 0;
            this.length = array.length;
        }

        public int getIndex() {
            return this.index;
        }

        public T get() {
            return array[this.index];
        }

        public T next() {
            this.index++;
            if (index > this.length)
                this.index -= this.length;
            return get();
        }

        public T prev() {
            this.index--;
            if (index < 0)
                this.index += this.length;
            return get();
        }
    }

    public static <T> void swap(T[] array, int i, int j) {
        if (i == j)
            return;
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static double angle(Point p1, Point p2, Point p3) {
        Vector v1 = new Vector(p1.x - p2.x, p1.y - p2.y);
        Vector v2 = new Vector(p3.x - p2.x, p3.y - p2.y);
        return Vector.angle(v1, v2);
    }
}
