package algorythms;

import requestFiles.Request;

import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ProcesSolvingAlgorythms {
    int duration;


    public ProcesSolvingAlgorythms(int duration) {
        this.duration = duration;
    }

    public LinkedList<Request> fcfs(LinkedList<Request> list){
        if(list.size()<0){
            return new LinkedList<>();
        }
        Queue<Request> queue = new LinkedList<>();
        for(Request i : list){
            queue.add(i);
        }
        for(int i=0; i<duration; i++){
            queue.peek().getLength();
        }
        return new LinkedList<>();
    }


    public LinkedList<Request> roundRobin(LinkedList<Request> list, int k){

        if(list.size()==0){
            return new LinkedList<>();
        }

        Queue<Request> queue = new LinkedList<>();
        for(Request i : list){
            queue.add(i);
        }
        for(int i=0; i<duration; i+=k){
            Request tempRequest = queue.remove();
            tempRequest.setLength(tempRequest.getLength() - k);
            if(tempRequest.getLength()>0)
                queue.add(tempRequest);
            else {
                i += tempRequest.getLength();
                if (queue.size()==0)
                    duration-=i;
                    break;
            }
        }
        return (LinkedList<Request>) queue;
    }

}
