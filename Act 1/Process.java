class Process {
   int processID;
   int arrivalTime;
   int burstTime;
   int waitingTime;
   int turnAroundTime;
   Process(int processID, int arrivalTime, int burstTime) {
       this.processID = processID;
       this.arrivalTime = arrivalTime;
       this.burstTime = burstTime;
   }
}
