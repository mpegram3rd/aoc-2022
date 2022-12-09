import java.util.HashSet;
import java.util.Set;

public class Tracking {

    private int bodySize = 1;
    private Set<Point> tailPositions;

    private Point[] body;

    public Tracking(int bodySize) {
        // Build the body
        this.bodySize = bodySize;
        body = new Point[bodySize];
        for (int index = 0; index < bodySize; index++) {
            body[index] = new Point(0,0);
        }

        tailPositions = new HashSet<>();
        tailPositions.add(getTail());
    }


    // Notes:
    // If in same row or column and preceding segment then just needs to
    // move 1 in the direction (left or right)
    // If it is in neither the same row or column then must move diaganol
    public void makeAMove(MovementFunction mover) {
        Point prior = body[0];
        body[0] = mover.move(body[0]);
        for (int index = 1; index < body.length; index++) {
            if (calcDistance(body[index-1], body[index]) >= 2.0) {
                Point tmp = body[index];
                body[index] = new Point(prior.x(), prior.y());
                if (index == body.length - 1) {
                    System.out.println("Head is at " + body[0] + " !!! Moved Tail !!! " + tmp + "->" + body[index]);
                }
                prior = tmp;
            }
        }
        writeGrid();
        System.out.println("------------");
        tailPositions.add(getTail());
}

    private double calcDistance(Point head, Point tail) {
        double xDistance = head.x() - tail.x();
        double yDistance = head.y() - tail.y();

        return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }

    public Set<Point> getTailPositions() {
        return tailPositions;
    }

    public Point getTail() {
        return body[bodySize - 1];
    }

    public Point getHead() {
        return body[0];
    }

    public Point[] getBody() {
        return body;
    }

    private void writeGrid() {
        char[][] grid = new char[6][6];
        for (int y = grid.length - 1; y >= 0; y--) {
            for (int x = 0; x < grid[y].length; x++) {
                grid[y][x] = '.';
            }
        }
        int count = 0;
        for (Point point : getBody()) {
            if (grid[point.y()][point.x()] == '.') {
                grid[point.y()][point.x()] = (char) ('0' + count);
            }
            count++;
        }
        for (int y = grid.length - 1; y >= 0; y--) {
            for (int x = 0; x < grid[y].length; x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println();
        }
    }

}
