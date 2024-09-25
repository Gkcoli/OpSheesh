import java.util.Scanner;

class Banker {
    int numProcesses;
    int numResources;
    int[] available; // Available resources
    int[][] maximum; // Maximum resource demand of each process
    int[][] allocation; // Resources currently allocated to each process
    int[][] need; // Remaining resource need of each process

    public Banker(int numProcesses, int numResources) {
        this.numProcesses = numProcesses;
        this.numResources = numResources;
        available = new int[numResources];
        maximum = new int[numProcesses][numResources];
        allocation = new int[numProcesses][numResources];
        need = new int[numProcesses][numResources];
    }

    // Calculate the need matrix based on the maximum and allocation matrices
    public void calculateNeed() {
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                need[i][j] = maximum[i][j] - allocation[i][j];
            }
        }
    }

    // Check if the system is in a safe state
    public boolean isSafe() {
        int[] work = available.clone();
        boolean[] finish = new boolean[numProcesses];

        System.out.println("\nSafe Sequence: ");
        int count = 0;
        while (count < numProcesses) {
            boolean found = false;

            for (int i = 0; i < numProcesses; i++) {
                if (!finish[i]) {
                    boolean canAllocate = true;
                    for (int j = 0; j < numResources; j++) {
                        if (need[i][j] > work[j]) {
                            canAllocate = false;
                            break;
                        }
                    }

                    if (canAllocate) {
                        for (int j = 0; j < numResources; j++) {
                            work[j] += allocation[i][j];
                        }
                        System.out.print("P" + i + " ");
                        finish[i] = true;
                        found = true;
                        count++;
                    }
                }
            }

            if (!found) {
                System.out.println("\nThe system is not in a safe state.");
                return false;
            }
        }
        System.out.println("\nThe system is in a safe state.");
        return true;
    }

    // Request resources for a process
    public boolean requestResources(int process, int[] request) {
        // Check if request is valid
        for (int i = 0; i < numResources; i++) {
            if (request[i] > need[process][i]) {
                System.out.println("Error: Process has exceeded its maximum claim.");
                return false;
            }
            if (request[i] > available[i]) {
                System.out.println("Error: Resources are not available.");
                return false;
            }
        }

        // Pretend to allocate resources
        for (int i = 0; i < numResources; i++) {
            available[i] -= request[i];
            allocation[process][i] += request[i];
            need[process][i] -= request[i];
        }

        // Check if system remains safe after allocation
        if (isSafe()) {
            System.out.println("Resources allocated successfully to process P" + process);
            return true;
        } else {
            // Rollback if system is unsafe
            for (int i = 0; i < numResources; i++) {
                available[i] += request[i];
                allocation[process][i] -= request[i];
                need[process][i] += request[i];
            }
            System.out.println("Request denied. System would be unsafe.");
            return false;
        }
    }
}

public class BankersAlgorithm {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean tryAgain = true;

        while (tryAgain) {
            System.out.print("Enter the number of processes: ");
            int numProcesses = sc.nextInt();
            System.out.print("Enter the number of resources: ");
            int numResources = sc.nextInt();

            Banker banker = new Banker(numProcesses, numResources);

            // Input available resources
            System.out.println("Enter available resources:");
            for (int i = 0; i < numResources; i++) {
                System.out.print("Resource " + (i + 1) + ": ");
                banker.available[i] = sc.nextInt();
            }

            // Input maximum demand for each process
            System.out.println("\nEnter the maximum demand for each process:");
            for (int i = 0; i < numProcesses; i++) {
                System.out.println("Process P" + i + ":");
                for (int j = 0; j < numResources; j++) {
                    System.out.print("Resource " + (j + 1) + ": ");
                    banker.maximum[i][j] = sc.nextInt();
                }
            }

            // Input allocated resources for each process
            System.out.println("\nEnter the allocation for each process:");
            for (int i = 0; i < numProcesses; i++) {
                System.out.println("Process P" + i + ":");
                for (int j = 0; j < numResources; j++) {
                    System.out.print("Resource " + (j + 1) + ": ");
                    banker.allocation[i][j] = sc.nextInt();
                }
            }

            // Calculate the need matrix
            banker.calculateNeed();

            // Check if the system is in a safe state initially
            if (!banker.isSafe()) {
                System.out.println("Initial state is not safe. Exiting.");
                break;
            }

            // Resource request phase
            boolean requestMore = true;
            while (requestMore) {
                System.out.print("\nEnter process number to request resources (or -1 to exit): ");
                int process = sc.nextInt();
                if (process == -1) {
                    requestMore = false;
                    continue;
                }

                int[] request = new int[numResources];
                System.out.println("Enter the resources requested by process P" + process + ":");
                for (int i = 0; i < numResources; i++) {
                    System.out.print("Resource " + (i + 1) + ": ");
                    request[i] = sc.nextInt();
                }

                banker.requestResources(process, request);
            }

            // Prompt user if they want to try again
            System.out.print("\nDo you want to try again? (y/n): ");
            String response = sc.next();
            if (!response.equalsIgnoreCase("y")) {
                tryAgain = false;
                System.out.println("Exiting program. Goodbye!");
            }
        }
        sc.close();
    }
}
