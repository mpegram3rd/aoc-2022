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


    public void makeAMove(MovementFunction mover) {
        body[0] = mover.move(body[0]);
        for (int index = 1; index < body.length; index++) {
            double distance = calcDistance(body[index-1], body[index]);
            if (distance >= 2.0) {

                int xDistance = body[index -1].x() - body[index].x();
                int yDistance = body[index -1].y() - body[index].y();
                int newX = xDistance == 0 ? body[index].x() : body[index].x() + (xDistance / Math.abs(xDistance));
                int newY = yDistance == 0 ? body[index].y() : body[index].y() + (yDistance / Math.abs(yDistance));
                body[index] = new Point(newX, newY);
            }
        }
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

    public int getBodySize() {
        return bodySize;
    }
}
