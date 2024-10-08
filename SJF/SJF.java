import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;

class Process {
    int processId;
    int arrivalTime;
    int burstTime;
    int waitingTime;
    int turnAroundTime;
    boolean isCompleted = false;

    Process(int processId, int arrivalTime, int burstTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitingTime = 0;
        this.turnAroundTime = 0;
    }
}

public class SJFNonPreemptive {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: number of processes
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        // Array to store processes
        Process[] processes = new Process[n];

        // Input: arrival time and burst time for each process
        for (int i = 0; i < n; i++) {
            System.out.println("Enter arrival time and burst time for process " + (i + 1) + ":");
            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }

        // SJF Non-Preemptive Scheduling
        int currentTime = 0;
        int completed = 0;
        float totalWaitingTime = 0;
        float totalTurnAroundTime = 0;

        // Sort processes by arrival time first
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        while (completed != n) {
            int idx = -1;
            int minBurstTime = Integer.MAX_VALUE;

            // Find process with the minimum burst time among the arrived processes
            for (int i = 0; i < n; i++) {
                if (processes[i].arrivalTime <= currentTime && !processes[i].isCompleted) {
                    if (processes[i].burstTime < minBurstTime) {
                        minBurstTime = processes[i].burstTime;
                        idx = i;
                    }
                }
            }

            // If no process is found, increment the current time
            if (idx == -1) {
                currentTime++;
            } else {
                // Process the selected job
                processes[idx].waitingTime = currentTime - processes[idx].arrivalTime;
                processes[idx].turnAroundTime = processes[idx].waitingTime + processes[idx].burstTime;

                totalWaitingTime += processes[idx].waitingTime;
                totalTurnAroundTime += processes[idx].turnAroundTime;

                currentTime += processes[idx].burstTime;
                processes[idx].isCompleted = true;
                completed++;
            }
        }

        // Output process details
        System.out.println("\nProcess\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time");
        for (Process process : processes) {
            System.out.println("P" + process.processId + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" + process.waitingTime + "\t\t" + process.turnAroundTime);
        }

        // Output average waiting time and turnaround time
        System.out.println("\nAverage Waiting Time: " + (totalWaitingTime / n));
        System.out.println("Average Turnaround Time: " + (totalTurnAroundTime / n));

        scanner.close();
    }
}

