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
            Tracking tracking = new Tracking(10); // Problem 1 size = 2, Problem 2 size = 10
            String line = garbIn.readLine();

            while (line != null) {
                System.out.println("Processing: " + line);
                tracking = processMove(tracking, line);
                line = garbIn.readLine();
            }
            garbIn.close();
            System.out.println("Total Unique Positions for Tail: "+ tracking.getTailPositions().size());
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
