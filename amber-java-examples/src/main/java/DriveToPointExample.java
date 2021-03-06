import pl.edu.agh.amber.common.AmberClient;
import pl.edu.agh.amber.drivetopoint.DriveToPointProxy;
import pl.edu.agh.amber.drivetopoint.Point;
import pl.edu.agh.amber.drivetopoint.Result;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Drive to point proxy example.
 *
 * @author Pawel Suder <pawel@suder.info>
 */
public class DriveToPointExample {

    public static void main(String[] args) {
        (new DriveToPointExample()).runDemo();
    }

    public void runDemo() {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("IP (default: 127.0.0.1): ");
        String hostname = keyboard.nextLine();

        if ("".equals(hostname)) {
            hostname = "127.0.0.1";
        }

        AmberClient client;
        try {
            client = new AmberClient(hostname, 26233);

        } catch (IOException e) {
            System.out.println("Unable to connect to robot: " + e);
            return;
        }

        DriveToPointProxy driveToPointProxy = new DriveToPointProxy(client, 0);

        try {
            List<Point> targets = Arrays.asList(new Point(0, 0, 10), new Point(500, 500, 10));
            driveToPointProxy.setTargets(targets);

            Result<Point> resultPoint = driveToPointProxy.getNextTarget();
            Point nextTarget = resultPoint.getResult();
            System.out.println(nextTarget);

            Result<List<Point>> resultPoints = driveToPointProxy.getNextTargets();
            List<Point> nextTargets = resultPoints.getResult();
            System.out.println(nextTargets);

            resultPoint = driveToPointProxy.getVisitedTarget();
            Point visitedTarget = resultPoint.getResult();
            System.out.println(visitedTarget);

            resultPoints = driveToPointProxy.getVisitedTargets();
            List<Point> visitedTargets = resultPoints.getResult();
            System.out.println(visitedTargets);

        } catch (IOException e) {
            System.out.println("Error in sending a command: " + e);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.terminate();
        }
    }
}
