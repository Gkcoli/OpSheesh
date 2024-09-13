import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Process {
    String name;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int completionTime;
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
            // Get number of processes between 4 and 6
            int numProcesses = 0;
            while (numProcesses < 4 || numProcesses > 6) {
                System.out.print("Enter the number of processes (4 to 6): ");
                numProcesses = sc.nextInt();
            }

            Queue<Process> processQueue = new LinkedList<>();
            Process[] processArray = new Process[numProcesses]; // To keep track of all processes for final calculations

            for (int i = 0; i < numProcesses; i++) {
                System.out.print("Enter name of process " + (i + 1) + ": ");
                String name = sc.next();
                System.out.print("Enter arrival time of process " + (i + 1) + ": ");
                int arrivalTime = sc.nextInt();
                System.out.print("Enter burst time of process " + (i + 1) + ": ");
                int burstTime = sc.nextInt();
                Process p = new Process(name, arrivalTime, burstTime);
                processQueue.add(p);
                processArray[i] = p;
            }

            System.out.print("Enter the time quantum: ");
            int timeQuantum = sc.nextInt();

            System.out.println("\nExecuting Round Robin Scheduling...");
            
            int currentTime = 0;
            Queue<Process> readyQueue = new LinkedList<>();

            // Gantt chart variables
            StringBuilder ganttChart = new StringBuilder();
            StringBuilder timeStamps = new StringBuilder();
            timeStamps.append(currentTime).append(" | ");

            while (!processQueue.isEmpty() || !readyQueue.isEmpty()) {
                while (!processQueue.isEmpty() && processQueue.peek().arrivalTime <= currentTime) {
                    readyQueue.add(processQueue.poll());
                }
                
                if (!readyQueue.isEmpty()) {
                    Process currentProcess = readyQueue.poll();

                    if (currentProcess.remainingTime > timeQuantum) {
                        ganttChart.append(currentProcess.name).append(" | ");
                        currentTime += timeQuantum;
                        currentProcess.remainingTime -= timeQuantum;
                        timeStamps.append(currentTime).append(" | ");
                        readyQueue.add(currentProcess); // Re-add to queue if not finished
                    } else {
                        ganttChart.append(currentProcess.name).append(" | ");
                        currentTime += currentProcess.remainingTime;
                        timeStamps.append(currentTime).append(" | ");
                        currentProcess.remainingTime = 0;
                        currentProcess.completionTime = currentTime;
                    }
                } else {
                    currentTime++; // If no process is ready, increase time
                }
            }

            System.out.println("\nAll processes have been completed.");

            // Display Gantt chart
            System.out.println("\nGantt Chart:");
            System.out.println(ganttChart.toString());
            System.out.println(timeStamps.toString());

            // Calculate Turnaround Time and Waiting Time
            double totalTurnAroundTime = 0;
            double totalWaitingTime = 0;
            
            System.out.println("\nProcess\tArrival Time\tBurst Time\tExit Time\tTurnaround Time\tWaiting Time");
            for (Process p : processArray) {
                p.turnAroundTime = p.completionTime - p.arrivalTime;
                p.waitingTime = p.turnAroundTime - p.burstTime;
                totalTurnAroundTime += p.turnAroundTime;
                totalWaitingTime += p.waitingTime;

                System.out.println(p.name + "\t\t" + p.arrivalTime + "\t\t" + p.burstTime + "\t\t" + p.completionTime +
                                   "\t\t" + p.turnAroundTime + "\t\t" + p.waitingTime);
            }

            // Calculate average times
            double avgTurnAroundTime = totalTurnAroundTime / numProcesses;
            double avgWaitingTime = totalWaitingTime / numProcesses;

            System.out.println("\nAverage Turnaround Time: " + avgTurnAroundTime);
            System.out.println("Average Waiting Time: " + avgWaitingTime);

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
