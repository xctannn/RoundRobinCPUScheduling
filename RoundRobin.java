import java.util.ArrayList;
import java.util.Collections;
import javafx.util.Pair;

public class RoundRobin {
    public ArrayList <Pair <Integer, Integer>> findRR(ArrayList<Process> processList, ArrayList<Process> doneProcessList, int quantum){
        ArrayList <Pair <Integer, Integer>> doneProcess_TimeList = new ArrayList <Pair <Integer, Integer>>();
        Collections.sort(processList);
        int sum_Time = processList.get(0).getArrivalTime();

        while(! processList.isEmpty()){
            Collections.sort(processList);
            Process runningProcess = processList.get(0);
            
            if(runningProcess.getRemainingBurstTime() <= quantum){
                sum_Time += runningProcess.getRemainingBurstTime();
                runningProcess.setRemainingBurstTime(0);
                runningProcess.setFinishingTime(sum_Time);
                runningProcess.calculateTurnaroundTime();
                runningProcess.calculateWaitingTime();
                doneProcessList.add(runningProcess);
                processList.remove(runningProcess);
            }
            else{
                sum_Time += quantum;
                runningProcess.setRemainingBurstTime(runningProcess.getRemainingBurstTime()-quantum);
                runningProcess.setOnHoldTime(sum_Time);
            }
            doneProcess_TimeList.add(new Pair <Integer, Integer> (runningProcess.getProcessName(), sum_Time));
        }
        return doneProcess_TimeList;
    }

    // Return total Turnarround Time for the entire processes
    public float findTotal_TT(ArrayList<Process> doneProcessList){
        float total_TT = 0;
        for (int i = 0; i < doneProcessList.size(); i++){
            total_TT += doneProcessList.get(i).getTurnaroundTime();
        }
        return total_TT;
    }

    // Return total Waiting Time for the entire processes
    public float findTotal_WT(ArrayList<Process> doneProcessList){
        float total_WT = 0;
        for (int i = 0; i < doneProcessList.size(); i++){
            total_WT += doneProcessList.get(i).getWaitingTime();
        }
        return total_WT;
    }

    public float findAvg(ArrayList<Process> doneProcessList, float total){
        return total/doneProcessList.size();
    }
}

