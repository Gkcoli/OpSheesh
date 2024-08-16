class Process {
    int processID;
    int arrivalTime;
    int burstTime;
    int waitingTime;
    int turnAroundTime;
    int completionTime;
    Process(int processID, int arrivalTime, int burstTime) {
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
 }
