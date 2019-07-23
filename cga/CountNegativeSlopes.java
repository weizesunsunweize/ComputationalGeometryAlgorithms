package cga;

import cga.components.Point;

import cga.Utils;

import java.util.Arrays;
import java.util.Comparator;

public class CountNegativeSlopes {
    private static class CountNegativeSlopesMerge extends Utils.Merge<Point> {
        private int count;

        public int getCount() {
            return this.count;
        }

        public CountNegativeSlopesMerge(Comparator<Point> comp) {
            super(comp);
            this.count = 0;
        }

        @Override
        public void merge(Point[] array, Point[] arr1, Point[] arr2) {
            int i1 = 0, i2 = 0;
            int idx = 0;
            while (idx < array.length) {
                if (i2 >= arr2.length
                        || i1 < arr1.length && i2 < arr2.length && this.comp.compare(arr1[i1], arr2[i2]) <= 0) {
                    array[idx++] = arr1[i1++];
                } else {
                    array[idx++] = arr2[i2++];
                    this.count += arr1.length - i1;
                }
            }
        }
    }

    public static int countNegativeSlopes(Point[] points) {
        Arrays.sort(points, new Utils.PointXComparator());
        CountNegativeSlopesMerge countNegativeSlopesMerge = new CountNegativeSlopesMerge(new Utils.PointYComparator());
        Utils.mergeSort(points, countNegativeSlopesMerge);
        return countNegativeSlopesMerge.getCount();
    }

    public static void main(String[] args) {
        Point[] points = new Point[] { new Point(4, 2), new Point(2, 1), new Point(3, 2.5), new Point(4, 3),
                new Point(5, 2) };
        int ans = countNegativeSlopes(points);
        System.out.println(ans);
    }
}
