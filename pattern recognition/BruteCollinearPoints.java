/******************************************************************************
 *  Name:    Eugene Kotyashov
 *  NetID:   euk
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: class searching sets of 4 collinear points via brute force
 *  examination of all possible 4-point combinations
 *
******************************************************************************/
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;


public class BruteCollinearPoints {
    private class EndPoints {
        public Point start;
        public Point end;
        public EndPoints(Point st, Point ed) {
            start = st;
            end = ed;
        }
    }
    private ArrayList<LineSegment> segs = null;
    
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        segs = new ArrayList<LineSegment>();
        ArrayList<EndPoints> endPts = new ArrayList<EndPoints>();
        int t = 0;
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                for (int k = j+1; k < points.length; k++) {
                    for (int m = k+1; m < points.length; m++) {
                        //check that all integers are different
                        if ((i == j) || (i == k) || (i == m) || (j == k) || 
                            (j == m) || (k == m)) {
                            continue;
                        }
                        
                        t++;
                        if ((points[i].slopeOrder().compare(
                            points[j], points[k]) == 0) &&
                            (points[i].slopeOrder().compare(
                            points[k], points[m]) == 0)) {
                            Point[] pts = new Point[4];
                            pts[0] = points[i];
                            pts[1] = points[j];
                            pts[2] = points[k];
                            pts[3] = points[m];
                            Point start = pts[0];
                            Point end = pts[0];
                            for (int ip = 1; ip < 4; ip++) {
                                if (less(start, pts[ip])) start = pts[ip];
                                if (less(pts[ip], end)) end = pts[ip];
                            }
                            boolean duplicateFound = false;
                            for (int ind = 0; ind < endPts.size(); ind++) {
                                duplicateFound =
                                (endPts.get(ind).start.compareTo(start) == 0) &&
                                (endPts.get(ind).end.compareTo(end) == 0);
                                if (duplicateFound) break;
                            }
                            if (!duplicateFound) { 
                                endPts.add(new EndPoints(start, end));
                                segs.add(new LineSegment(start, end));
                            }
                        }                            
                    }
                }
            }
        }
        StdOut.println(t);    
    }
    
    private static boolean less(Point p1, Point p2) {
        return p1.compareTo(p2) < 0;
    }
    
    public int numberOfSegments() {   // the number of line segments
        return segs.size();        
    }
    public LineSegment[] segments() {            // the line segments
        LineSegment[] res = new LineSegment[segs.size()];
        return segs.toArray(res);
    }
       public static void main(String[] args) {
       
       // read the n points from a file
       In in = new In(args[0]);
       int n = in.readInt();
       Point[] points = new Point[n];
       for (int i = 0; i < n; i++) {
           int x = in.readInt();
           int y = in.readInt();
           points[i] = new Point(x, y);
       }
       
       // draw the points
       StdDraw.enableDoubleBuffering();
       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);
       for (Point p : points) {
           p.draw();
       }
       StdDraw.show();
       
       // print and draw the line segments
       BruteCollinearPoints collinear = new BruteCollinearPoints(points);
       for (LineSegment segment : collinear.segments()) {
           StdOut.println(segment);
           segment.draw();
       }
       StdDraw.show();
   }
}
