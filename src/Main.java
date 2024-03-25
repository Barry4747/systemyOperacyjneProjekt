import algorythms.ProcesSolvingAlgorythms;
import requestFiles.Request;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        int duration=100000;
        int kwant = 3;

        //timePeriod ustawia czas wykonywania procesu w trakcie jednej sekundy, czyli jesli ustawimy 0.99 znaczy to tyle, ze 0.01 trwa przelaczanie miedzy procesem
        ProcesSolvingAlgorythms procesSolvingAlgorythms = new ProcesSolvingAlgorythms(duration, 1);
        procesSolvingAlgorythms.createDistribution(duration);
        procesSolvingAlgorythms.generateDiagram(procesSolvingAlgorythms.getProbabilities(), "Rozkład prawdopodobieństwa", "Prawdopodobieństwo");


        LinkedList<Request>[] list = new LinkedList[4];
        list[0] = procesSolvingAlgorythms.fcfs();
        list[1] =procesSolvingAlgorythms.sjf();
        list[3] = procesSolvingAlgorythms.rr(kwant);
        list[2] = procesSolvingAlgorythms.sjfw();
        System.out.println("Done");

        String[] name = {"fcfs", "sjf", "sjfw", "rr"};
        String[] frameNames = {"Dlugosc procesu", "Czas pojawienia sie procesu", "czas zakonczenia procesu", "czas oczekiwania", "czas a ilosc procesow", "czas trwania", "status procesow"};

        JFrame[] frames = new JFrame[7];

        for(int i=0; i<frames.length; i++){
            frames[i] = new JFrame(frameNames[i]);
            frames[i].setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frames[i].setLayout(new GridLayout(1, list.length));
        }

        for(int j=0; j< list.length; j++) {

            int averageDoneWaitingTime = 0;
            int averageWaitingTime = 0;
            int done = 0;
            int averageSize = 0;

            double[] beginingLength = new double[list[j].size()];
            int[] arrivalTime = new int[list[j].size()];
            int[] endTime = new int[list[j].size()];
            int[] totalWait = new int[list[j].size()];
            int[] tasksPerTime = new int[duration];
            int[] durationOfProcces = new int[list[j].size()];
            int[] taskStatus = {0,0,0};
            for(int i=0; i<duration; i++){
                tasksPerTime[i]=0;
            }



            for (int i = 0; i < list[j].size(); i++) {
                list[j].get(i).calculateTotalWait();

                durationOfProcces[i] = list[j].get(i).getDuration();
                tasksPerTime[list[j].get(i).getArrivalTime()] += 1;
                beginingLength[i] = list[j].get(i).getBeginingLength();
                arrivalTime[i] = list[j].get(i).getArrivalTime();
                endTime[i] = list[j].get(i).getEndTime();
                totalWait[i] = list[j].get(i).getTotalWait();
//                System.out.println(list[j].get(i).toString());
                if (list[j].get(i).isDone()) {
                    taskStatus[0]++;
                    averageDoneWaitingTime += list[j].get(i).getTotalWait();
                    done++;
                } else if (list[j].get(i).getDuration()!=0) {
                    taskStatus[1]++;
                }else {
                    taskStatus[2]++;
                }
                averageWaitingTime += list[j].get(i).getTotalWait();
                averageSize += list[j].get(i).getBeginingLength();
            }

            frames[0].add(procesSolvingAlgorythms.generateDiagram(beginingLength, name[j], "Długość"));
            frames[1].add(procesSolvingAlgorythms.generateDiagram(arrivalTime, name[j], "Czas"));
            frames[2].add(procesSolvingAlgorythms.generateDiagram(endTime, name[j], "Czas"));
            frames[3].add(procesSolvingAlgorythms.generateDiagram(totalWait, name[j], "Czas"));
            frames[4].add(procesSolvingAlgorythms.generateDiagram(tasksPerTime, name[j], "Ilość Procesów"));
            frames[5].add(procesSolvingAlgorythms.generateDiagram(durationOfProcces, name[j], "Czas wykonywania tylko tego procesu"));
            frames[6].add(procesSolvingAlgorythms.strips(taskStatus[0], taskStatus[1], taskStatus[2], name[j]));


            System.out.println(name[j]+"|| Średni czas oczekiwania: " + averageWaitingTime / list[j].size() + " Średni czas oczekiwania zakończonych procesów: " + averageDoneWaitingTime / done + " Średni rozmiar: " + averageSize / list[j].size() + " Done: " + done);

            System.out.println("Utworzono: " + list[j].size());

            System.out.println(taskStatus[1]);
        }
        for(int i=0; i<frames.length; i++){
            frames[i].setVisible(true);
            frames[i].pack();
        }


    }
}