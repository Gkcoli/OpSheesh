import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Process {
    String name;
    int burstTime;
    int remainingTime;
    
    public Process(String name, int burstTime) {
        this.name = name;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class RoundRobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean tryAgain = true;

        while (tryAgain) {
            System.out.print("Enter the number of processes: ");
            int numProcesses = sc.nextInt();

            Queue<Process> processQueue = new LinkedList<>();
            
            for (int i = 0; i < numProcesses; i++) {
                System.out.print("Enter name of process " + (i + 1) + ": ");
                String name = sc.next();
                System.out.print("Enter burst time of process " + (i + 1) + ": ");
                int burstTime = sc.nextInt();
                processQueue.add(new Process(name, burstTime));
            }

            System.out.print("Enter the time quantum: ");
            int timeQuantum = sc.nextInt();

            System.out.println("\nExecuting Round Robin Scheduling...");
            
            int currentTime = 0;
            while (!processQueue.isEmpty()) {
                Process currentProcess = processQueue.poll();
                
                if (currentProcess.remainingTime > timeQuantum) {
                    System.out.println("Process " + currentProcess.name + " executed for " + timeQuantum + " units.");
                    currentProcess.remainingTime -= timeQuantum;
                    currentTime += timeQuantum;
                    processQueue.add(currentProcess);  // Re-add to queue if not finished
                } else {
                    System.out.println("Process " + currentProcess.name + " executed for " + currentProcess.remainingTime + " units and finished.");
                    currentTime += currentProcess.remainingTime;
                    currentProcess.remainingTime = 0;  // Process is finished
                }

                System.out.println("Current time: " + currentTime);
            }

            System.out.println("\nAll processes have been completed.");

            // Prompt user if they want to try again
            System.out.print("\nDo you want to try again? (y/n): ");
            String response = sc.next();
            if (!response.equalsIgnoreCase("y")) {
                tryAgain = false;
                System.out.println("Exiting program. Goodbye!");
            }
        }
        sc.close();  // Close scanner to prevent resource leaks
    }
}
