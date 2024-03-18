import algorythms.ProcesSolvingAlgorythms;
import requestFiles.Request;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        int duration=1000;
        int kwant = 4;

        //timePeriod ustawia czas wykonywania procesu w trakcie jednej sekundy, czyli jesli ustawimy 0.99 znaczy to tyle, ze 0.01 trwa przelaczanie miedzy procesem
        ProcesSolvingAlgorythms procesSolvingAlgorythms = new ProcesSolvingAlgorythms(duration, 1);
        procesSolvingAlgorythms.createDistribution(duration);
        procesSolvingAlgorythms.generateDiagram(procesSolvingAlgorythms.getProbabilities(), "Rozkład prawdopodobieństwa", "Prawdopodobieństwo");


        LinkedList<Request>[] list = new LinkedList[3];
        list[0] = procesSolvingAlgorythms.fcfs();
        list[1] =procesSolvingAlgorythms.sjf();
        list[2] = procesSolvingAlgorythms.rr(4);

        String[] name = {"fcfs", "sjf", "rr"};

        for(int j=0; j<3; j++) {
            JFrame frame = new JFrame(name[j]);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLayout(new GridLayout(2, 2));

            int averageDoneWaitingTime = 0;
            int averageWaitingTime = 0;
            int done = 0;
            int averageSize = 0;

            double[] beginingLength = new double[list[j].size()];
            int[] arrivalTime = new int[list[j].size()];
            int[] endTime = new int[list[j].size()];
            int[] totalWait = new int[list[j].size()];


            //do testowania wydajności algorytmów i przyznanych im priorytetów
            for (int i = 0; i < list[j].size(); i++) {
                list[j].get(i).calculateTotalWait();

                beginingLength[i] = list[j].get(i).getBeginingLength();
                arrivalTime[i] = list[j].get(i).getArrivalTime();
                endTime[i] = list[j].get(i).getEndTime();
                totalWait[i] = list[j].get(i).getTotalWait();
                System.out.println(list[j].get(i).toString());
                if (list[j].get(i).isDone()) {
                    averageDoneWaitingTime += list[j].get(i).getTotalWait();
                    done++;
                }
                averageWaitingTime += list[j].get(i).getTotalWait();
                averageSize += list[j].get(i).getBeginingLength();
            }

            frame.add(procesSolvingAlgorythms.generateDiagram(beginingLength, "Długość procesu", "Długość"));
            frame.add(procesSolvingAlgorythms.generateDiagram(arrivalTime, "Czas pojawienia się procesu", "Czas"));
            frame.add(procesSolvingAlgorythms.generateDiagram(endTime, "Czas zakończenia procedu", "Czas"));
            frame.add(procesSolvingAlgorythms.generateDiagram(totalWait, "Czas oczekiwania", "Czas"));


            System.out.println("Średni czas oczekiwania: " + averageWaitingTime / list[j].size() + " Średni czas oczekiwania zakończonych procesów: " + averageDoneWaitingTime / done + " Średni rozmiar: " + averageSize / list[j].size() + " Done: " + done);

            System.out.println("Utworzono: " + list[j].size());

            frame.pack();
            frame.setVisible(true);
        }


    }
}