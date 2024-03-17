package requestFiles;

public class Request {
    private int number;
    private int length;
    private int beginingLength;
    private int arrivalTime;
    private int endTime;
    private int totalWait;
    private boolean done;


    public Request(int number, int length, int arrivalTime, int totalWait) {
        this.number = number;
        this.length = length;
        this.beginingLength = length;
        this.arrivalTime = arrivalTime;
        this.endTime = totalWait;
        this.done = false;
    }
    //getters and setters
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getTotalWait() {
        return totalWait;
    }

    public void setTotalWait(int totalWait) {
        this.totalWait = totalWait;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getBeginingLength() {
        return beginingLength;
    }

    public void setBeginingLength(int beginingLength) {
        this.beginingLength = beginingLength;
    }

    @Override
    public String toString() {
        return "Request{" +
                "number=" + number +
                ", length=" + length +
                ", arrivalTime=" + arrivalTime +
                ", totalWait=" + totalWait +
                ", done=" + done +
                '}';
    }

    public void calculateTotalWait() {
        totalWait = endTime-arrivalTime;
    }
}
