/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package co.vedantscheduler.cpuscheduler;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author norway
 */
public class CPUAlgorithms {
    
    static void findWaitingTimeFCFS(String processes[], int n, int bt[], int wt[], int at[]){
        
    int service_time[] = new int[n];
    service_time[0] = at[0];
    wt[0] = 0;
 
    for (int i = 1; i < n ; i++)
    {
        int wasted=0;
        service_time[i] = service_time[i-1] + bt[i-1];
 
        wt[i] = service_time[i] - at[i];
        if (wt[i] < 0) {
            wasted = Math.abs(wt[i]);
            wt[i] = 0;
        }
        service_time[i] = service_time[i] + wasted;
    }
}
 
    static void findTurnAroundTimeFCFS(String processes[], int n, int bt[], int wt[], int tat[]) {
        for (int i = 0; i < n ; i++)
            tat[i] = bt[i] + wt[i];
    }
 
    static float[] findavgTimeFCFS(String processes[], int n, int bt[], int at[]) {
        int wt[] = new int[n], tat[] = new int[n];
        float results[] = {0f,0f};

        findWaitingTimeFCFS(processes, n, bt, wt, at);

        findTurnAroundTimeFCFS(processes, n, bt, wt, tat);

        int total_wt = 0, total_tat = 0;
        for (int i = 0 ; i < n ; i++)
        {
            total_wt = total_wt + wt[i];
            total_tat = total_tat + tat[i];
        } 
        results[0] = (float)total_wt/(float)n;
        results[1] = (float)total_tat / (float)n;
        
        return results;
    }

    static DefaultTableModel findTableFCFS(String processes[], int n, int bt[], int at[]) {
        int[] wt = new int[n], tat = new int[n];
            findWaitingTimeFCFS(processes, n, bt, wt, at);
            findTurnAroundTimeFCFS(processes, n, bt, wt, tat);

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Process");
            model.addColumn("Burst Time");
            model.addColumn("Arrival Time");
            model.addColumn("Turnaround Time");
            model.addColumn("Waiting Time");

            for (int i = 0 ; i < n ; i++) {
                Object[] row = {processes[i], bt[i], at[i], tat[i], wt[i]};
                model.addRow(row);
            }

            return model;
    }
    
    static void findWaitingTimeSJF(String processes[], int n, int bt[], int wt[], int at[]) {
    int rt[] = new int[n];
    for (int i = 0; i < n; i++) {
        rt[i] = bt[i];
    }

    int complete = 0, t = 0, minm = Integer.MAX_VALUE;
    int shortest = 0, finish_time;
    boolean check = false;

    while (complete != n) {
        for (int j = 0; j < n; j++) {
            if ((at[j] <= t) && (rt[j] < minm) && rt[j] > 0) {
                minm = rt[j];
                shortest = j;
                check = true;
            }
        }

        if (check == false) {
            t++;
            continue;
        }

        // Decrease remaining time of shortest job
        rt[shortest]--;

        // Update minimum
        minm = rt[shortest];
        if (minm == 0)
            minm = Integer.MAX_VALUE;

        // If a job is completed
        if (rt[shortest] == 0) {
            // Increment complete
            complete++;

            // Update finish time
            finish_time = t + 1;

            // Calculate waiting time
            wt[shortest] = finish_time - bt[shortest] - at[shortest];

            if (wt[shortest] < 0)
                wt[shortest] = 0;
        }
        // Increment time
        t++;
    }
}

    static void findTurnAroundTimeSJF(String processes[], int n, int bt[], int wt[], int tat[]) {
        for (int i = 0; i < n; i++)
            tat[i] = bt[i] + wt[i];
    }

    static float[] findavgTimeSJF(String processes[], int n, int bt[], int at[]) {
        int wt[] = new int[n], tat[] = new int[n];
        float results[] = {0f, 0f};

        findWaitingTimeSJF(processes, n, bt, wt, at);

        findTurnAroundTimeSJF(processes, n, bt, wt, tat);

        int total_wt = 0, total_tat = 0;
        for (int i = 0; i < n; i++) {
            total_wt = total_wt + wt[i];
            total_tat = total_tat + tat[i];
        } 
        results[0] = (float)total_wt/(float)n;
        results[1] = (float)total_tat/(float)n;

        return results;
    }
    
    static DefaultTableModel findTableSJF(String processes[], int n, int bt[], int at[]) {
        int[] wt = new int[n], tat = new int[n];
            findWaitingTimeSJF(processes, n, bt, wt, at);
            findTurnAroundTimeSJF(processes, n, bt, wt, tat);

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Process");
            model.addColumn("Burst Time");
            model.addColumn("Arrival Time");
            model.addColumn("Turnaround Time");
            model.addColumn("Waiting Time");

            for (int i = 0 ; i < n ; i++) {
                Object[] row = {processes[i], bt[i], at[i], tat[i], wt[i]};
                model.addRow(row);
            }
            
            return model;
    }
    
    static void findWaitingTimeSRTF(String processes[], int n, int bt[], int wt[], int at[]) {
    int rt[] = new int[n];
    for (int i = 0; i < n; i++) {
        rt[i] = bt[i];
    }

    int complete = 0, t = 0, minm = Integer.MAX_VALUE, shortest = 0, finish_time;
    boolean check = false;

    while (complete != n) {
        for (int j = 0; j < n; j++) {
            if ((at[j] <= t) && (rt[j] < minm) && (rt[j] > 0)) {
                minm = rt[j];
                shortest = j;
                check = true;
            }
        }
        if (check == false) {
            t++;
            continue;
        }
        rt[shortest]--;
        minm = rt[shortest];
        if (minm == 0) {
            minm = Integer.MAX_VALUE;
        }
        if (rt[shortest] == 0) {
            complete++;
            check = false;
            finish_time = t + 1;
            wt[shortest] = finish_time - bt[shortest] - at[shortest];
            if (wt[shortest] < 0) {
                wt[shortest] = 0;
            }
        }
        t++;
    }
}

    static void findTurnAroundTimeSRTF(String processes[], int n, int bt[], int wt[], int tat[]) {
        for (int i = 0; i < n; i++) {
            tat[i] = bt[i] + wt[i];
        }
    }

    static float[] findavgTimeSRTF(String processes[], int n, int bt[], int at[]) {
        int wt[] = new int[n], tat[] = new int[n];
        float results[] = {0f, 0f};

        findWaitingTimeSRTF(processes, n, bt, wt, at);
        findTurnAroundTimeSRTF(processes, n, bt, wt, tat);

        int total_wt = 0, total_tat = 0;
        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
        }

        results[0] = (float) total_wt / (float) n;
        results[1] = (float) total_tat / (float) n;

        return results;
    }
    
    static DefaultTableModel findTableSRTF(String processes[], int n, int bt[], int at[]) {
        int[] wt = new int[n], tat = new int[n];
            findWaitingTimeSRTF(processes, n, bt, wt, at);
            findTurnAroundTimeSRTF(processes, n, bt, wt, tat);

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Process");
            model.addColumn("Burst Time");
            model.addColumn("Arrival Time");
            model.addColumn("Turnaround Time");
            model.addColumn("Waiting Time");

            for (int i = 0 ; i < n ; i++) {
                Object[] row = {processes[i], bt[i], at[i], tat[i], wt[i]};
                model.addRow(row);
            }

            return model;
    }
    
    static void findWaitingTimeRR(String processes[], int n, int bt[], int wt[], int at[], int quantum) {
    // Make a copy of burst times bt[] to store remaining
    // burst times.
    int rem_bt[] = new int[n];
    for (int i = 0 ; i < n ; i++)
        rem_bt[i] = bt[i];
 
    int t = 0; // Current time
 
    // Keep traversing processes in round robin manner
    // until all of them are not done.
    while(true)
    {
        boolean done = true;
 
        // Traverse all processes one by one repeatedly
        for (int i = 0 ; i < n; i++)
        {
            // If a process is not done
            if (rem_bt[i] > 0)
            {
                done = false; // There is a pending process
 
                if (rem_bt[i] > quantum && at[i] <= t)
                {
                    // Process will run for a quantum time
                    t += quantum;
                    rem_bt[i] -= quantum;
                }
                else
                {
                    // Process will run for a shorter time
                    if (at[i] <= t) {
                        t += rem_bt[i];
                        wt[i] = t - bt[i] - at[i];
                        rem_bt[i] = 0;
                    }
                    else {
                        // Process has not arrived yet
                        wt[i] = t - at[i];
                    }
                }
            }
        }
 
        // If all processes are done
        if (done == true)
          break;
    }
}
 
    static void findTurnAroundTimeRR(String processes[], int n, int bt[], int wt[], int tat[]) {
        for (int i = 0; i < n ; i++)
            tat[i] = bt[i] + wt[i];
    }
 
    static float[] findavgTimeRR(String processes[], int n, int bt[], int at[], int quantum) {
        int wt[] = new int[n], tat[] = new int[n];
        float results[] = {0f,0f};

        findWaitingTimeRR(processes, n, bt, wt, at, quantum);

        findTurnAroundTimeRR(processes, n, bt, wt, tat);

        int total_wt = 0, total_tat = 0;
        for (int i = 0 ; i < n ; i++)
        {
            total_wt = total_wt + wt[i];
            total_tat = total_tat + tat[i];
        }

        results[0] = (float)total_wt/(float)n;
        results[1] = (float)total_tat/(float)n;
        
        return results;
    }
    
    static DefaultTableModel findTableRR(String processes[], int n, int bt[], int at[], int quantum) {
        int[] wt = new int[n], tat = new int[n];
            findWaitingTimeRR(processes, n, bt, wt, at, quantum);
            findTurnAroundTimeRR(processes, n, bt, wt, tat);

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Process");
            model.addColumn("Burst Time");
            model.addColumn("Arrival Time");
            model.addColumn("Turnaround Time");
            model.addColumn("Waiting Time");

            for (int i = 0 ; i < n ; i++) {
                Object[] row = {processes[i], bt[i], at[i], tat[i], wt[i]};
                model.addRow(row);
            }

            return model;
    }
}
