package requestFiles;

public class Request {
    private int size;
    private int prio;
    private int arrivalTime;
    private int probability;
    private boolean done;

    public Request(int size, int prio, int probability){
        this.size = size;
        this.prio = prio;
        this.probability = probability;
        done = false;
    }


    //getters and setters
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPrio() {
        return prio;
    }

    public void setPrio(int prio) {
        this.prio = prio;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Request{" +
                "size=" + size +
                ", prio=" + prio +
                ", probability=" + probability +
                ", done=" + done +
                '}';
    }

}
