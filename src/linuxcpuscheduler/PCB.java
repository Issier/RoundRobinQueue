import java.util.Random;

/**
 * Created by Ryan on 4/11/2017.
 */
public class PCB {
    static int nextJobNum = 1;
    int jobNum;
    int jobLength;
    int startTime = 0;
    int endTime = 0;
    int startToEndTime = 0;
    int numPasses;
    boolean started = false;

    public PCB(){
        numPasses = 0;
        Random rand = new Random();
        jobLength = rand.nextInt(791) + 10;
        jobNum = nextJobNum;
        nextJobNum++;
    }

    public int getJobLength(){
        return jobLength;
    }

    public void start(int startTime){
        if(!started){
            this.startTime = startTime;
            this.started = true;
        }
    }

    public void runCycle(int timeSlice){
        jobLength -= timeSlice;
        this.numPasses++;
    }

    public void complete(int endTime){
        if(jobLength > 0){
            this.jobLength = 0;
            this.numPasses++;
        }
        this.endTime = endTime;
        this.startToEndTime = this.endTime - this.startTime;
    }
}
