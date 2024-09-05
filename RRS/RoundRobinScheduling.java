import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobinScheduling {

    // Define a Process class to store process information
    static class Process {
        int id;             // Process ID
        int burstTime;      // Time required by the process to complete
        int remainingTime;  // Time remaining for the process to complete

        // Constructor to initialize process details
        Process(int id, int burstTime) {
            this.id = id;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the number of processes
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        // Input the time quantum
        System.out.print("Enter the time quantum: ");
        int timeQuantum = scanner.nextInt();

        // Create a queue to manage the processes
        Queue<Process> queue = new LinkedList<>();

        // Input burst times for each process
        for (int i = 1; i <= n; i++) {
            System.out.print("Enter burst time for Process " + i + ": ");
            int burstTime = scanner.nextInt();
            queue.add(new Process(i, burstTime)); // Add the process to the queue
        }

        int currentTime = 0; // To keep track of the current time

        // Process the queue using Round Robin scheduling
        while (!queue.isEmpty()) {
            Process currentProcess = queue.poll(); // Get the next process from the queue

            // Check if the process can complete in the given time quantum
            if (currentProcess.remainingTime > timeQuantum) {
                currentTime += timeQuantum;
                currentProcess.remainingTime -= timeQuantum;
                queue.add(currentProcess); // Re-add the process to the queue
                System.out.println("Process " + currentProcess.id + " executed for " + timeQuantum + " units.");
            } else {
                currentTime += currentProcess.remainingTime;
                System.out.println("Process " + currentProcess.id + " executed for " + currentProcess.remainingTime + " units and completed.");
                currentProcess.remainingTime = 0;
            }

            System.out.println("Current time: " + currentTime + " units.");
        }

        System.out.println("All processes completed.");
        scanner.close();
    }
}
