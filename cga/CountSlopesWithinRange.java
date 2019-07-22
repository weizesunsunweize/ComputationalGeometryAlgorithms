package cga;

import cga.components.Point;

import cga.CountNegativeSlopes;

public class CountSlopesWithinRange {
    public static Point[] convert(Point[] points, double minSlope, double maxSlope) {
        Point[] convertedPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            convertedPoints[i] = new Point(points[i].x * minSlope - points[i].y, points[i].x * maxSlope - points[i].y);
        return convertedPoints;
    }

    public static int countSlopesWithinRange(Point[] points, double minSlope, double maxSlope) {
        if (minSlope >= maxSlope)
            return 0;
        Point[] convertedPoints = convert(points, minSlope, maxSlope);
        return CountNegativeSlopes.countNegativeSlopes(convertedPoints);
    }

    public static void main(String[] args) {
        Point[] points = new Point[] { new Point(4, 2), new Point(2, 1), new Point(5, 2), new Point(5, 10) };
        int ans = countSlopesWithinRange(points, 7.99999, 1000);
        System.out.println(ans);
    }
}
