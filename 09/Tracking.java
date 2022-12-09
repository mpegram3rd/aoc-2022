import java.util.HashSet;
import java.util.Set;

public class Tracking {

    private Point head;
    private Point prior;
    private Point tail;

    private Set<Point> tailPositions;

    public Tracking() {
        head = new Point(0,0);
        prior = new Point(0,0);
        tail = new Point(0,0);
        tailPositions = new HashSet<>();
        tailPositions.add(tail);
    }


    public void makeAMove(MovementFunction mover) {
        prior = head;
        head = mover.move(head);
        if (calcDistance(head, tail) >= 2.0) {
            tail = new Point(prior.x(), prior.y());
            tailPositions.add(tail);
        }
    }

    private double calcDistance(Point head, Point tail) {
        double xDistance = head.x() - tail.x();
        double yDistance = head.y() - tail.y();

        return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }

    public Set<Point> getTailPositions() {
        return tailPositions;
    }

}
