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
        Point prior = body[0];
        body[0] = mover.move(body[0]);
        for (int index = 1; index < body.length; index++) {
            if (calcDistance(body[index-1], body[index]) >= 2.0) {
                Point tmp = body[index];
                body[index] = new Point(prior.x(), prior.y());
                if (index == body.length - 1) {
                    System.out.println("!!! Moved Tail !!! " + tmp + "->" + body[index]);
                }
                prior = tmp;
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
}
