/******************************************************************************
 *  Name:    Eugene Kotyashov
 *  NetID:   euk
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: class searching sets of 4 or more collinear points via sortig
 *
******************************************************************************/
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Arrays;


public class FastCollinearPoints {
    private ArrayList<LineSegment> segs = null;
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        segs = new ArrayList<LineSegment>();
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
           Point[] tmp = Arrays.copyOfRange(points, i+1, points.length);
           Arrays.sort(tmp, points[i].slopeOrder());
           int j = 0;
           while (j < tmp.length-1) {
               int k = 1; 
               while ((j+k < tmp.length) &&
               (points[i].slopeOrder().compare(tmp[j], tmp[j+k]) == 0)) {
                //points[i].slopeTo(tmp[j]) == points[i].slopeTo(tmp[j+k]))) {
                   k++;
               }
               if (k >= 3) {
                   segs.add(new LineSegment(points[i], tmp[j+k-1]));
               }
               j = j + k;
           }
        }        
    }
    
   // the number of line segments
    public int numberOfSegments() {
        return segs.size();
    }
    
    public LineSegment[] segments() {
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
       FastCollinearPoints collinear = new FastCollinearPoints(points);
       StdOut.println(collinear.numberOfSegments());
       for (LineSegment segment : collinear.segments()) {
           StdOut.println(segment);
           segment.draw();
       }
       StdDraw.show();
    }
}