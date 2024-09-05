import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;

class Process {
    int processId;
    int burstTime;
    int waitingTime;
    int turnAroundTime;

    Process(int processId, int burstTime) {
        this.processId = processId;
        this.burstTime = burstTime;
        this.waitingTime = 0;
        this.turnAroundTime = 0;
    }
}

public class SJN {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // baka need nanaman to ni sir T_T

        // Input: number of processes
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        // Array to store processes
        Process[] processes = new Process[n];

        // Input: burst time for each process
        for (int i = 0; i < n; i++) {
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, burstTime);
        }

        // Sort processes by burst time (Shortest Job Next)
        Arrays.sort(processes, Comparator.comparingInt(p -> p.burstTime));

        // Calculate waiting time and turn around time
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;
        processes[0].waitingTime = 0; // First process has no waiting time (SJN NGA DIBA)

        for (int i = 1; i < n; i++) {
            processes[i].waitingTime = processes[i - 1].waitingTime + processes[i - 1].burstTime;
            totalWaitingTime += processes[i].waitingTime;
        }

        for (int i = 0; i < n; i++) {
            processes[i].turnAroundTime = processes[i].waitingTime + processes[i].burstTime;
            totalTurnAroundTime += processes[i].turnAroundTime;
        }

        // Calculate average waiting time and turn around time
        float averageWaitingTime = (float) totalWaitingTime / n;
        float averageTurnAroundTime = (float) totalTurnAroundTime / n;

        // Output: process details
        System.out.println("\nProcess\tBurst Time\tWaiting Time\tTurnaround Time");
        for (Process process : processes) {
            System.out.println("P" + process.processId + "\t\t" + process.burstTime + "\t\t" + process.waitingTime + "\t\t" + process.turnAroundTime);
        }

        // Output: average waiting time and turn around time "Pls sana tama"
        System.out.println("\nAverage Waiting Time: " + averageWaitingTime);
        System.out.println("Average Turnaround Time: " + averageTurnAroundTime);

        scanner.close();
    }
}
