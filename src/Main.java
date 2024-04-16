import algorythms.ProcesSolvingAlgorythms;
import requestFiles.Request;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int duration=100000;
        int kwant = 3;

        //timePeriod ustawia czas wykonywania procesu w trakcie jednej sekundy, czyli jesli ustawimy 0.99 znaczy to tyle, ze 0.01 trwa przelaczanie miedzy procesem
        ProcesSolvingAlgorythms procesSolvingAlgorythms = new ProcesSolvingAlgorythms(duration, 1);
        procesSolvingAlgorythms.createDistribution(duration);
        //procesSolvingAlgorythms.generateDiagram(procesSolvingAlgorythms.getProbabilities(), "Rozkład prawdopodobieństwa", "Prawdopodobieństwo");
        System.out.println("Distribution ready");


        String[] name = {"fcfs", "sjf", "sjfw", "rr"};
        String[] frameNames = {"Dlugosc procesu", "Czas pojawienia sie procesu", "czas zakonczenia procesu", "czas oczekiwania", "czas a ilosc procesow", "czas trwania", "status procesow"};

        JFrame[] frames = new JFrame[7];

        for(int i=0; i<frames.length; i++){
            frames[i] = new JFrame(frameNames[i]);
            frames[i].setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frames[i].setLayout(new GridLayout(1, 4));
        }




//        List<Request>[] list = new List[4];
//        list[0] = procesSolvingAlgorythms.fcfs();
//        System.out.println("fcfs done");
//        list[1] =procesSolvingAlgorythms.sjf();
//        System.out.println("sjf done");
//        list[3] = procesSolvingAlgorythms.rr(kwant);
//        System.out.println("rr done");
//        list[2] = procesSolvingAlgorythms.sjfw();
//        System.out.println("Done");

        for(int j=0; j< 4; j++) {
            double averageDoneWaitingTime = 0;
            double averageWaitingTime = 0;
            double done = 0;
            double averageSize = 0;

            List<Request> list = switch (j) {
                case 0 -> procesSolvingAlgorythms.fcfs();
                case 1 -> procesSolvingAlgorythms.sjf();
                case 2 -> procesSolvingAlgorythms.sjfw();
                case 3 -> procesSolvingAlgorythms.rr(kwant);
                default -> null;
            };

            System.out.println("Done");

            double size = list.size();
            double[] beginingLength = new double[list.size()];
            double[] arrivalTime = new double[list.size()];
            double[] endTime = new double[list.size()];
            double[] totalWait = new double[list.size()];
            double[] tasksPerTime = new double[duration];
            double[] durationOfProcces = new double[list.size()];
            double[] taskStatus = {0,0,0};
            for(int i=0; i<duration; i++){
                tasksPerTime[i]=0;
            }

            int starved = 0;

            for (int i = 0; i < list.size(); i++) {
                list.get(i).calculateTotalWait();

                durationOfProcces[i] = list.get(i).getDuration();
                tasksPerTime[list.get(i).getArrivalTime()] += 1;
                beginingLength[i] = list.get(i).getBeginingLength();
                arrivalTime[i] = list.get(i).getArrivalTime();
                endTime[i] = list.get(i).getEndTime();
                totalWait[i] = list.get(i).getTotalWait();
                if(!list.get(i).isDone()&&list.get(i).getTotalWait()>100){
                    list.get(i).setStarved(true);
                    starved++;
                }
//                System.out.println(list[j].get(i).toString());
                if (list.get(i).isDone()) {
                    taskStatus[0]++;
                    averageDoneWaitingTime += list.get(i).getTotalWait();
                    done++;
                } else if (list.get(i).getDuration()!=0) {
                    taskStatus[1]++;
                }else {
                    taskStatus[2]++;
                }
                averageWaitingTime += list.get(i).getTotalWait();
                averageSize += list.get(i).getBeginingLength();
            }

            frames[0].add(procesSolvingAlgorythms.generateDiagram(beginingLength, name[j], "Długość"));
            frames[1].add(procesSolvingAlgorythms.generateDiagram(arrivalTime, name[j], "Czas"));
            frames[2].add(procesSolvingAlgorythms.generateDiagram(endTime, name[j], "Czas"));
            frames[3].add(procesSolvingAlgorythms.generateDiagram(totalWait, name[j], "Czas"));
            frames[4].add(procesSolvingAlgorythms.generateDiagram(tasksPerTime, name[j], "Ilość Procesów"));
            frames[5].add(procesSolvingAlgorythms.generateDiagram(durationOfProcces, name[j], "Czas wykonywania tylko tego procesu"));
            frames[6].add(procesSolvingAlgorythms.strips(taskStatus[0], taskStatus[1], taskStatus[2], name[j]));


            System.out.println(name[j]+"|| Średni czas oczekiwania: " + averageWaitingTime / size + " Średni czas oczekiwania zakończonych procesów: " + averageDoneWaitingTime / done + " Średni rozmiar: " + averageSize / size + " Done: " + done+ " Liczba zagłodzonych procesów: "+starved);

            System.out.println("Utworzono: " + size);

            System.out.println(taskStatus[1]);
        }
        for(int i=0; i<frames.length; i++){
            frames[i].setVisible(true);
            frames[i].pack();
        }


    }
}