import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solution {
    private static final MovementFunction left = (Point p) -> new Point(p.x()-1, p.y());
    private static final MovementFunction right = (Point p) -> new Point(p.x()+1, p.y());
    private static final MovementFunction up = (Point p) -> new Point(p.x(), p.y()+1);
    private static final MovementFunction down = (Point p) -> new Point(p.x(), p.y()-1);

    public static void main(String[] args) {
        try {
            BufferedReader garbIn = new BufferedReader(new FileReader("input.txt"));
            Tracking tracking1 = new Tracking(2);
            Tracking tracking2 = new Tracking(10);
            String line = garbIn.readLine();

            while (line != null) {
                System.out.println("Processing: " + line);
                tracking1 = processMove(tracking1, line);
                tracking2 = processMove(tracking2, line);
                line = garbIn.readLine();
            }
            garbIn.close();
            System.out.println("Problem 1: Total Unique Positions for Tail (length=" + tracking1.getBodySize() + "): "
                    + tracking1.getTailPositions().size());
            System.out.println("Problem 2: Total Unique Positions for Tail (length=" + tracking1.getBodySize() + "): "
                    + tracking2.getTailPositions().size());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static Tracking processMove(Tracking tracking, String line) {
        String [] values = line.split(" ", 2);
        MovementFunction mover = getMover(values[0].charAt(0));
        int moveCount = Integer.parseInt(values[1]);
        for (int count = 0; count < moveCount; count++) {
            tracking.makeAMove(mover);
        }
        return tracking;
    }

    private static MovementFunction getMover(char direction) {
        switch(direction) {
            case 'L': return left;
            case 'R': return right;
            case 'U': return up;
            case 'D': return down;
            default: return null;
        }
    }
}
