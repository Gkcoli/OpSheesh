import java.util.Scanner;
public class App {
   public static void main(String[] args) throws Exception {
       Scanner scanner = new Scanner(System.in);
       String choice;
       do {
           System.out.print("Enter the number of the process: ");
           int n = scanner.nextInt();
           Process[] processes = new Process[n];
           for (int i = 0; i < n; i++) {
               System.out.print("Enter arrival time for Process " + (i + 1) + ": ");
               int arrivalTime = scanner.nextInt();
               System.out.print("Enter burst time for Process " + (i + 1) + ": ");
               int burstTime = scanner.nextInt();
               processes[i] = new Process(i + 1, arrivalTime, burstTime);
           }
           int currentTime = 0;
           for (int i = 0; i < n; i++) {
               Process currentProcess = processes[i];
               if (currentTime < currentProcess.arrivalTime) {
                   currentTime = currentProcess.arrivalTime;
               }
               currentProcess.waitingTime = currentTime - currentProcess.arrivalTime;
               currentProcess.turnAroundTime = currentProcess.waitingTime + currentProcess.burstTime;
               currentProcess.completionTime = currentTime + currentProcess.burstTime;
               currentTime += currentProcess.burstTime;
           }
           int totalWaitTime = 0, totalTurnAroundTime = 0;
           for (Process process : processes) {
               totalWaitTime += process.waitingTime;
               totalTurnAroundTime += process.turnAroundTime;
           }
           double averageWaitTime = (double) totalWaitTime / n;
           double averageTurnAroundTime = (double) totalTurnAroundTime / n;
           // Throughput Calculation
           double throughput = (double) n / currentTime;
           System.out.println("\nProcess\t\tArrival Time\tBurst Time\tWait Time\tTurn Around Time\tCompletion Time");
           for (Process process : processes) {
               System.out.println("P" + process.processID + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" + process.waitingTime + "\t\t" + process.turnAroundTime + "\t\t\t" + process.completionTime);
           }
           System.out.println("\nTotal Wait Time: " + totalWaitTime);
           System.out.println("Average Wait Time: " + averageWaitTime);
           System.out.println("Total Turn Around Time: " + totalTurnAroundTime);
           System.out.println("Average Turn Around Time: " + averageTurnAroundTime);
           // Output the throughput
           System.out.println("Throughput: " + throughput + " processes per unit time");
           System.out.print("\nWould you like to run the program again? (yes/no): ");
           choice = scanner.next();
       } while (choice.equalsIgnoreCase("yes"));
       System.out.println("Program terminated.");
       scanner.close();
   }
}
