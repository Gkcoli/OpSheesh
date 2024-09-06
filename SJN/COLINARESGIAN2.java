import java.util.Scanner;

class Process {
   int processId;
   int arrivalTime;
   int burstTime;
   int waitingTime;
   int turnAroundTime;
   int exitTime;  
   boolean isCompleted = false;
   Process(int processId, int arrivalTime, int burstTime) {
       this.processId = processId;
       this.arrivalTime = arrivalTime;
       this.burstTime = burstTime;
       this.waitingTime = 0;
       this.turnAroundTime = 0;
       this.exitTime = 0;
   }
}
public class COLINARESGIAN2 {
   public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean tryAgain;
       do {
        // Input: number of processes
        int n;
        do {
            System.out.print("Enter the number of processes 3 and 5: ");
            n = scanner.nextInt();
            if (n < 3 || n > 5) {
                System.out.println("Please enter a valid number of processes (minimum 3 and maximum 5 only).");
            }
        } while (n < 3 || n > 5);
        // Array to store processes
        Process[] processes = new Process[n];
      
        for (int i = 0; i < n; i++) {
            System.out.println("Enter arrival time and burst time for process " + (i + 1) + ":");
            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }
     
        int currentTime = 0;
        int completed = 0;
        float totalWaitingTime = 0;
        float totalTurnAroundTime = 0;
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
               processes[idx].waitingTime = currentTime - processes[idx].arrivalTime;  // Calculate waiting time
               processes[idx].turnAroundTime = processes[idx].waitingTime + processes[idx].burstTime;
               processes[idx].exitTime = currentTime + processes[idx].burstTime;  // Calculate exit time
               totalWaitingTime += processes[idx].waitingTime;
               totalTurnAroundTime += processes[idx].turnAroundTime;
               currentTime += processes[idx].burstTime;
               processes[idx].isCompleted = true;
               completed++;
           }
        }
       
        System.out.println("\nProcess\tArrival Time\tBurst Time\tExit Time\tWaiting Time\tTurnaround Time");
        for (Process process : processes) {
           System.out.println("P" + process.processId + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" + process.exitTime + "\t\t" + process.waitingTime + "\t\t" + process.turnAroundTime);
        }
        // Output average waiting time and turnaround time
        System.out.println("\nAverage Waiting Time: " + (totalWaitingTime / n));
        System.out.println("Average Turnaround Time: " + (totalTurnAroundTime / n));
        System.out.print("\nWould you like to try again? (yes/no): ");
        String userInput = scanner.next();
       
        tryAgain = userInput.equalsIgnoreCase("yes");
    } while (tryAgain);
    scanner.close();
}
}
