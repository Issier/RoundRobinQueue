import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Ryan on 4/11/2017.
 */
public class PriorityArray {
    final int REALTIME_SLICE = 200;
    final int PROGRAM_SLICE  = 10;
    final int NUM_LEVELS_RT   = 8;
    final int NUM_LEVELS_PRGRM = 4;
    int nr_active = 0;
    boolean[] bitmap;
    boolean jobStep = false;
    String currentPrioArray;
    ArrayList<RRQueue> queue;
    Scanner scan = new Scanner(System.in);

    public PriorityArray() throws IOException {
        queue = new ArrayList<RRQueue>();
        bitmap = new boolean[NUM_LEVELS_RT + NUM_LEVELS_PRGRM];

        for(int i = 0; i < NUM_LEVELS_RT; i++){
            queue.add(new RRQueue(REALTIME_SLICE));
        }
        for(int i = 0; i < NUM_LEVELS_PRGRM; i++){
            queue.add(new RRQueue(PROGRAM_SLICE));
        }

        Arrays.fill(bitmap, true);
        printPrioArray();
        System.out.println("Press Enter to Begin Execution: ");
        System.in.read();
    }

    public void printPrioArray(){
        this.currentPrioArray = "";
        int prioNum = 1;
        for(RRQueue i : queue){
            String bandHeader = prioNum + "\t" + i.getNumProcesses() + ": ";
            System.out.print(bandHeader);
            this.currentPrioArray += bandHeader;

            PCBNode temp = i.getMainNode();
            String job;
            for(int n = 0; n < i.getNumProcesses(); n++){
                if(temp != null) {
                    job = "[" + temp.getElement().getJobLength() + "]" + "->";
                    System.out.print(job);
                    this.currentPrioArray += job;
                    temp = temp.getNext();
                }
            }
            prioNum++;
            System.out.println("\n");
            this.currentPrioArray += "\n";
        }
    }

    public String run(boolean step, boolean showArrayUpdates) throws IOException {
        String partialReport;
        String fullReport = currentPrioArray + "\n";
        int runBand = 0;
        for(boolean i : bitmap){
            if(i){
                partialReport = "Priority " + (runBand + 1) + " Band Completed:\n" + queue.get(runBand).execute(jobStep) + "\n\n";
                fullReport += partialReport;
                System.out.println(partialReport);
                if(showArrayUpdates){
                    System.out.println("Press Enter to Show Priority Array Progress");
                    System.out.println("-----------------------------------");
                    System.in.read();
                    printPrioArray();
                }
                if(step){
                    System.out.println("Press Enter to Execute Next Band: ");
                    System.out.println("-----------------------------------");
                    System.in.read();
                }
            }
            runBand++;

            if(runBand == bitmap.length){
                //All jobs done, do something
            }
        }
        return fullReport;
    }
}
