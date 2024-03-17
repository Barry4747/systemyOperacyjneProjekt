package algorythms;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import requestFiles.Request;
import requestFiles.RequestBuilder;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.Registry;
import java.util.*;

import static requestFiles.RequestBuilder.buildRequests;

public class ProcesSolvingAlgorythms {
    private int duration;
    private double[] probabilities;


    public ProcesSolvingAlgorythms(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double[] getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(double[] probabilities) {
        this.probabilities = probabilities;
    }

    public LinkedList<Request> fcfs(){
        LinkedList<Request> list = new LinkedList<>();
        int counter=0;
        Random random = new Random();
        for(int i=0; i<duration; i++){
            list.addAll(buildRequests(1, i, 2.0, 50, probabilities[i], duration));
            if(!list.isEmpty()&&counter!=list.size()){
                list.get(counter).setLength(list.get(counter).getLength()-1);
                if(list.get(counter).getLength()<=0){
                    list.get(counter).setDone(true);
                    list.get(counter).setEndTime(i);
                    counter++;
                }
            }
        }
        System.out.println("Skończone zadania: "+counter);

        return list;
    }

    public LinkedList<Request> sjf(){
        LinkedList<Request> list = new LinkedList<>();

        PriorityQueue<Request> queue = new PriorityQueue<>(Comparator.comparing(Request::getBeginingLength));

        Request tempRequest=null;

        for(int i=0; i<duration; i++){
            queue.addAll(buildRequests(1, i, 2.0, 50, probabilities[i], duration));
            if(!queue.isEmpty()&&(tempRequest==null|| tempRequest.isDone())){
                tempRequest=queue.remove();
                list.add(tempRequest);
            } else if (!queue.isEmpty()) {
                tempRequest.setLength(tempRequest.getLength()-1);
                if(tempRequest.getLength()<=0){
                    tempRequest.setEndTime(i);
                    tempRequest.setDone(true);
                }
            }
        }
        while(!queue.isEmpty()){
            list.add(queue.remove());
        }
        return list;
    }



    public void generateDiagram(double[] arr, String diagramName, String yName){
        int dataSize = arr.length;

        XYSeries series = new XYSeries(diagramName);
        for (int i = 0; i < dataSize; i++) {
            series.add(i, arr[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                diagramName,
                "Index",
                yName,
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);

        JFrame frame = new JFrame(diagramName);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
    public void generateDiagram(int[] arr, String diagramName, String yName){
        int dataSize = arr.length;

        XYSeries series = new XYSeries(diagramName);
        for (int i = 0; i < dataSize; i++) {
            series.add(i, arr[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                diagramName,
                "Index",
                yName,
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);

        JFrame frame = new JFrame(diagramName);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void createDistribution(int dataSize){
        double alpha = 1.5;
        double beta = 2.0;

        BetaDistribution betaDistribution = new BetaDistribution(alpha, beta);

        probabilities = new double[dataSize];

        for (int i = 0; i < dataSize; i++) {
            double value = betaDistribution.density((double)  i/dataSize);
            probabilities[i] =  (value/3)+0.2;
        }
    }

}
