import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Ryan on 4/11/2017.
 */
public class RRQueue {
    Random rand;
    int numProcesses = 0;
    boolean running = false;
    PCBNode mainNode;
    PCBNode loopNode;
    int sliceTime;
    int bufferedTime = 0; //Theoretical time since execute has been called

    public RRQueue(int sliceTime){
        this.sliceTime = sliceTime;
        rand = new Random();
        numProcesses = rand.nextInt(10) + 1;
        if(numProcesses == 1){
            mainNode = new PCBNode(new PCB());
            loopNode = null;
        }
        else if(numProcesses == 2){
            mainNode = new PCBNode(new PCB());
            loopNode = new PCBNode(new PCB());
            mainNode.setNext(loopNode);
            loopNode.setNext(mainNode);
        }
        else{
            mainNode = new PCBNode(new PCB());
            PCBNode tempEndNode = mainNode;
            PCBNode tempNewNode;
            for(int i = 1; i < numProcesses - 1; i++){
                tempNewNode = new PCBNode(new PCB());
                tempEndNode.setNext(tempNewNode);
                tempEndNode = tempNewNode;
            }
            loopNode = new PCBNode(new PCB());
            tempEndNode.setNext(loopNode);
            loopNode.setNext(mainNode);
        }
    }

    public int getNumProcesses(){
        return numProcesses;
    }

    public PCBNode getMainNode(){
        return mainNode;
    }

    public void add(PCBNode item) {

    }

    //Executes the PCBs in priority band as round robin, returning total theoretical run time when band is emptied
    public String execute(boolean step) throws IOException {
        String executeReport = "";
        this.running = true;
        PCBNode backNode = loopNode; //Keeps track of the node just before runNode for purpose of keeping links intact
        PCBNode runNode = mainNode;
        PCBNode swapNode; //For swapping out the runNode when a PCB has finished executing and is being removed

        while(numProcesses > 0 && runNode != null){
            runNode.getElement().start(bufferedTime);
            if(runNode.getElement().getJobLength() < sliceTime){ //If this is the case, job can end
                String jobReport;
                bufferedTime += runNode.getElement().getJobLength();
                runNode.getElement().complete(bufferedTime); //Tell the node that it has stopped running
                jobReport = "Job " + runNode.getElement().jobNum + " Completed " + runNode.getElement().startToEndTime + "ms (start: " + runNode.getElement().startTime + " | end: " +
                        runNode.getElement().endTime + " | Passes: " + runNode.getElement().numPasses + ")\n";
                executeReport += jobReport;
                if(step){
                    System.out.println(jobReport);
                    System.out.println("Press Enter to Execute Next Band: ");
                    System.in.read();
                }

                if(numProcesses > 2){
                    swapNode = runNode.getNext();
                    runNode = swapNode;
                    backNode.setNext(runNode);
                }
                else if(numProcesses == 2) {
                    runNode = runNode.getNext();
                }
                numProcesses--;
            }
            else{ //If this is the case, simulate completing job slice
                runNode.getElement().runCycle(sliceTime);
                bufferedTime += sliceTime;
                if(numProcesses >= 2){ //If only one process, stay on it
                    backNode = runNode;
                    runNode = backNode.getNext();
                }
            }
        }
        return executeReport;
    }
}
