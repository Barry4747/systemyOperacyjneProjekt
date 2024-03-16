import algorythms.ProcesSolvingAlgorythms;
import org.apache.commons.math3.geometry.partitioning.RegionFactory;
import requestFiles.Request;
import requestFiles.RequestCalculations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import static requestFiles.RequestBuilder.buildRequests;

public class Main {
    public static void main(String[] args) {
        int max=0;
        int sum=0;
        int max2=0;
        int sum2=0;

        int listSize = 10000;
        int robinK =5;
        int duration= 1000;

        ProcesSolvingAlgorythms procesSolvingAlgorythms = new ProcesSolvingAlgorythms(duration);

        LinkedList<Request> list = buildRequests(listSize);
        for(Request i : list){
            if(i.getLength()>max){
                max=i.getLength();
            }
            if(i.getArrivalTime()>max2){
                max2=i.getArrivalTime();
            }
            sum+=i.getLength();
            sum2+=i.getArrivalTime();
            System.out.println(i.toString());
        }
        list.sort(Comparator.comparingInt(Request::getArrivalTime));
        LinkedList<Request> tempList = new LinkedList<>();
        int counter=0;
        for(int i=0; i<list.get(listSize-1).getArrivalTime(); i++){
            while(list.get(counter).getArrivalTime()==i &&counter<listSize){
                tempList.add(list.get(counter));
                counter++;
            }
            tempList=procesSolvingAlgorythms.roundRobin(tempList, robinK);
        }



        System.out.println("Max length: "+max+" | Total length: "+sum+" | Average length: "+sum/10000);
        System.out.println("Max arrival time: "+max2+" | Total arrival time: "+sum2+" | Average arrival time: "+sum2/10000);
        System.out.println(tempList.size());


    }
}