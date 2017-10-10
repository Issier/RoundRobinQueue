import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Scanner;

/**
 * Created by Ryan on 4/11/2017.
 */
public class LinuxCPUScheduler {
    public static void main(String[] args) throws IOException {
        int runTime = 0;
        boolean bandStep = false;
        boolean showUpdates = false;
        boolean loopRun = true;
        String executeReport;

        Scanner scan = new Scanner(System.in);
        while(loopRun){
            System.out.println("\t\t  LINUX CPU SCHEDULER SIMULATOR");

            System.out.println("          __    .____        ____.           __   ");
            System.out.println("          \\ \\   |   _|      |_   |           \\ \\  ");
            System.out.println("  ______   \\ \\  |  |          |  |   ______   \\ \\");
            System.out.println(" /_____/   / /  |  |          |  |  /_____/   / / ");
            System.out.println("          /_/   |  |_        _|  |           /_/  ");
            System.out.println("                |____|      |____|                ");
            System.out.println("\n\t\t  Press Enter to Begin");
            System.out.println("\t\t  -------------------------");
            scan.nextLine();

            System.out.print("Would you like to step band by band? (y or n): ");
            if(scan.next().toLowerCase().equals("y"))
                bandStep = true;
            else
                bandStep = false;

            if(bandStep == true){ //Only give option if stepping band by band
                System.out.print("Would you like to show Active Run Queue updates? (y or n): ");
                if(scan.next().toLowerCase().equals("y"))
                    showUpdates = true;
                else
                    showUpdates = false;
            }
            else{
                showUpdates = false;
            }

            PriorityArray aRQ = new PriorityArray();
            //Threading code if applicable
            executeReport = aRQ.run(bandStep, showUpdates);
            System.out.print("Would you like to print the execution information? (y or n): ");
            if(scan.next().toLowerCase().equals("y")){
                System.out.print("Please enter a name for the output file: ");
                String fileName = scan.next();
                System.out.print(executeReport);
                PrintWriter write = new PrintWriter(fileName + ".txt");
                write.write(executeReport);
                write.close();
            }
            System.out.print("Would you like to run the simulation again? (y or n): ");
            if(scan.next().toLowerCase().equals("y")) {
                loopRun = true;
                System.out.println("\n\n\n\n\n");
            }
            else {
                loopRun = false;
            }
        }
    }
}


