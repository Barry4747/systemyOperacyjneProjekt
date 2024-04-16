package requestFiles;

public class Request {
    private int number;
    private double length;
    private double beginingLength;
    private int arrivalTime;
    private int endTime;
    private int totalWait;
    private int duration;
    private boolean done;
    private boolean isStarved;


    public Request(int number, float length, int arrivalTime, int endTime) {
        this.number = number;
        this.length = length;
        this.beginingLength = length;
        this.arrivalTime = arrivalTime;
        this.endTime = endTime;
        this.done = false;
        this.duration=0;
        this.isStarved=false;
    }
    //getters and setters
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
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

    public double getBeginingLength() {
        return beginingLength;
    }

    public void setBeginingLength(double beginingLength) {
        this.beginingLength = beginingLength;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isStarved() {
        return isStarved;
    }

    public void setStarved(boolean starved) {
        isStarved = starved;
    }

    @Override
    public String toString() {
        return "Request{" +
                "number=" + number +
                ", length=" + length +
                ", beginingLength=" + beginingLength +
                ", arrivalTime=" + arrivalTime +
                ", endTime=" + endTime +
                ", totalWait=" + totalWait +
                ", done=" + done +
                '}';
    }

    public void calculateTotalWait() {
        totalWait = endTime-arrivalTime;
    }
}
