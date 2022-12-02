public class Process implements Comparable<Process>{
    private int processName;
    private int arrivalTime;
    private int onHoldTime;
    private int burstTime;
    private int priority;
    private int remainingBurstTime;
    private int finishingTime;
    private int turnaroundTime;
    private int waitingTime;

    public Process(){}

    public Process(int processName, int arrivalTime, int burstTime, int priority){
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingBurstTime = this.burstTime;
        this.onHoldTime = this.arrivalTime;
    }

    public int getProcessName(){
        return processName;
    }

    public int getArrivalTime(){
        return arrivalTime;
    }

    public int getOnHoldTime(){
        return onHoldTime;
    }

    public int getBurstTime(){
        return burstTime;
    }

    public int getPriority(){
        return priority;
    }

    public int getRemainingBurstTime(){
        return remainingBurstTime;
    }

    public int getTurnaroundTime(){
        return turnaroundTime;
    }

    public int getWaitingTime(){
        return waitingTime;
    }

    public int getFinishingTime(){
        return finishingTime;
    }

    public int setProcessName(){
        return processName;
    }

    public void setArrivalTime(int arrivalTime){
        this.arrivalTime = arrivalTime;
    }

    public void setOnHoldTime(int onHoldTime){
        this.onHoldTime = onHoldTime;
    }

    public void setBurstTime(int burstTime){
        this.burstTime = burstTime;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }

    public void setRemainingBurstTime(int rem_BurstTime){
        this.remainingBurstTime = rem_BurstTime;
    }

    public void setFinishingTime(int finishingTime){
        this.finishingTime = finishingTime;
    }

    public void calculateTurnaroundTime(){
        this.turnaroundTime = this.finishingTime - this.arrivalTime;
    }

    public void calculateWaitingTime(){
        this.waitingTime = this.turnaroundTime - this.burstTime;
    }

    @Override
    public int compareTo(Process compareProcess){
        if(this.onHoldTime - compareProcess.getOnHoldTime() == 0){
            if(this.burstTime == this.remainingBurstTime)
                return this.priority - compareProcess.getPriority();
            else
                return compareProcess.getArrivalTime() - this.arrivalTime;
        }
        else
            return this.onHoldTime - compareProcess.getOnHoldTime();
    }
    
    @Override
    public String toString(){
        return ("P: " + getProcessName() + ", AT: " + getArrivalTime() + ", BT:  " + getBurstTime());
    }
}
