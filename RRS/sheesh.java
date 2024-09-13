import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Process {
    String name;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int exitTime;
    int turnAroundTime;
    int waitingTime;

    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
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
                System.out.print("Enter arrival time of process " + (i + 1) + ": ");
                int arrivalTime = sc.nextInt();
                System.out.print("Enter burst time of process " + (i + 1) + ": ");
                int burstTime = sc.nextInt();
                processQueue.add(new Process(name, arrivalTime, burstTime));
            }

            System.out.print("Enter the time quantum: ");
            int timeQuantum = sc.nextInt();

            // Output table and Gantt chart setup
            StringBuilder ganttChart = new StringBuilder();
            StringBuilder timeLine = new StringBuilder();

            System.out.println("\nExecuting Round Robin Scheduling...");
            
            int currentTime = 0;
            int completedProcesses = 0;
            double totalTurnaroundTime = 0;
            double totalWaitingTime = 0;
            
            Queue<Process> waitingQueue = new LinkedList<>();

            while (completedProcesses < numProcesses) {
                for (Process process : processQueue) {
                    if (process.arrivalTime <= currentTime && process.remainingTime > 0) {
                        waitingQueue.add(process);
                    }
                }

                if (!waitingQueue.isEmpty()) {
                    Process currentProcess = waitingQueue.poll();

                    if (currentProcess.remainingTime > timeQuantum) {
                        currentProcess.remainingTime -= timeQuantum;
                        currentTime += timeQuantum;
                        ganttChart.append(currentProcess.name).append(" | ");
                        timeLine.append(currentTime).append(" | ");
                        waitingQueue.add(currentProcess);
                    } else {
                        currentTime += currentProcess.remainingTime;
                        currentProcess.exitTime = currentTime;
                        currentProcess.remainingTime = 0;
                        ganttChart.append(currentProcess.name).append(" | ");
                        timeLine.append(currentTime).append(" | ");

                        currentProcess.turnAroundTime = currentProcess.exitTime - currentProcess.arrivalTime;
                        currentProcess.waitingTime = currentProcess.turnAroundTime - currentProcess.burstTime;

                        totalTurnaroundTime += currentProcess.turnAroundTime;
                        totalWaitingTime += currentProcess.waitingTime;

                        completedProcesses++;
                    }
                } else {
                    currentTime++;
                }
            }

            // Display process table
            System.out.println("\nProcess  Arrival Time  Burst Time  Exit Time  Turnaround Time  Waiting Time");
            for (Process process : processQueue) {
                System.out.printf("%-8s %-13d %-11d %-10d %-15d %-13d\n",
                        process.name, process.arrivalTime, process.burstTime, process.exitTime,
                        process.turnAroundTime, process.waitingTime);
            }

            // Display Gantt chart
            System.out.println("\nGantt Chart:");
            System.out.println(ganttChart);
            System.out.println(timeLine);

            // Calculate and display averages
            double avgTurnaroundTime = totalTurnaroundTime / numProcesses;
            double avgWaitingTime = totalWaitingTime / numProcesses;
            System.out.printf("\nAverage Turnaround Time: %.2f\n", avgTurnaroundTime);
            System.out.printf("Average Waiting Time: %.2f\n", avgWaitingTime);

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
