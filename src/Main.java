import algorythms.ProcesSolvingAlgorythms;
import requestFiles.Request;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        int duration=1000;

        ProcesSolvingAlgorythms procesSolvingAlgorythms = new ProcesSolvingAlgorythms(duration);
        procesSolvingAlgorythms.createDistribution(duration);
        procesSolvingAlgorythms.generateDiagram(procesSolvingAlgorythms.getProbabilities(), "Rozkład prawdopodobieństwa", "Prawdopodobieństwo");


        LinkedList<Request> list = procesSolvingAlgorythms.sjf();

        int averageDoneWaitingTime=0;
        int averageWaitingTime=0;
        int done=0;
        int averageSize=0;

        int[] beginingLength = new int[list.size()];
        int[] arrivalTime = new int[list.size()];
        int[] endTime = new int[list.size()];
        int[] totalWait = new int[list.size()];


        //do testowania wydajności algorytmów i przyznanych im priorytetów
        for(int i=0; i<list.size(); i++){
            list.get(i).calculateTotalWait();

            beginingLength[i]=list.get(i).getBeginingLength();
            arrivalTime[i]=list.get(i).getArrivalTime();
            endTime[i]=list.get(i).getEndTime();
            totalWait[i]=list.get(i).getTotalWait();
            System.out.println(list.get(i).toString());
            if(list.get(i).isDone()){
                averageDoneWaitingTime+=list.get(i).getTotalWait();
                done++;
            }
            averageWaitingTime+=list.get(i).getTotalWait();
            averageSize+=list.get(i).getBeginingLength();
        }

        procesSolvingAlgorythms.generateDiagram(beginingLength, "Długość procesu", "Długość");
        procesSolvingAlgorythms.generateDiagram(arrivalTime, "Czas pojawienia się procesu", "Czas");
        procesSolvingAlgorythms.generateDiagram(endTime, "Czas zakończenia procedu", "Czas");
        procesSolvingAlgorythms.generateDiagram(totalWait, "Czas oczekiwania", "Czas");


        System.out.println("Średni czas oczekiwania: "+averageWaitingTime/list.size()+" Średni czas oczekiwania zakończonych procesów: "+averageDoneWaitingTime/done+" Średni rozmiar: "+averageSize/list.size()+" Done: "+done);

        System.out.println("Utworzono: "+list.size());



    }
}