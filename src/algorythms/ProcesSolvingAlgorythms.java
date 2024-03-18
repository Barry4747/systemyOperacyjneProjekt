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
    private float timePeriod;


    public ProcesSolvingAlgorythms(int duration, float timePeriod) {
        this.duration = duration;
        this.timePeriod = timePeriod;
    }

    //getters and setters
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

    public float getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(float timePeriod) {
        this.timePeriod = timePeriod;
    }

    public LinkedList<Request> fcfs(){
        LinkedList<Request> list = new LinkedList<>();
        int counter=0;

        for(int i=0; i<duration; i++){
            list.addAll(buildRequests(1, i, 2.0, 50, probabilities[i], duration));
            if(!list.isEmpty()&&counter!=list.size()){
                list.get(counter).setLength(list.get(counter).getLength()-timePeriod);
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

        LinkedList<Request> tempList = new LinkedList<>();

        for(int i=0; i<duration; i++){
            tempList = new LinkedList<>();
            tempList.addAll(buildRequests(1, i, 2.0, 50, probabilities[i], duration));
            queue.addAll(tempList);
            list.addAll(tempList);

            if(!queue.isEmpty()&&(tempRequest==null|| tempRequest.isDone())){
                tempRequest=queue.remove();
//                list.add(tempRequest);
            } else if (!queue.isEmpty()) {
                tempRequest.setLength(tempRequest.getLength()-timePeriod);
                if(tempRequest.getLength()<=0){
                    tempRequest.setEndTime(i);
                    tempRequest.setDone(true);
                }
            }
        }
//        while(!queue.isEmpty()){
//            list.add(queue.remove());
//        }
        return list;
    }

    public LinkedList<Request> rr(int k){
        LinkedList<Request> list = new LinkedList<>();

        int counter=0;

        for(int i=0; i<duration; i+=k){
            for(int j=0; j<k; j++) {
                list.addAll(buildRequests(1, i, 2.0, 50, probabilities[i], duration));
            }
            if(list.size()!=counter) {
                list.get(counter).setLength(list.get(counter).getLength() - k + 1 - timePeriod);
                if (list.get(counter).getLength() <= 0) {
                    list.get(counter).setEndTime(i);
                    list.get(counter).setDone(true);
                    counter++;
                }
            }
        }
        System.out.println("Skończone zadania: "+counter);


        return list;
    }



    public JPanel generateDiagram(double[] arr, String diagramName, String yName){
        int dataSize = arr.length;

        XYSeries series = new XYSeries(diagramName);
        for (int i = 0; i < dataSize; i++) {
            series.add(i, arr[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                diagramName,
                "Index",
                yName,
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);

        JLabel label = new JLabel(diagramName);
        JPanel frame = new JPanel();
        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.NORTH);
        frame.add(chartPanel, BorderLayout.CENTER);
        return frame;
    }
    public JPanel generateDiagram(int[] arr, String diagramName, String yName){
        int dataSize = arr.length;

        XYSeries series = new XYSeries(diagramName);
        for (int i = 0; i < dataSize; i++) {
            series.add(i, arr[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                diagramName,
                "Index",
                yName,
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);

        JLabel label = new JLabel(diagramName);
        JPanel frame = new JPanel();
        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.NORTH);
        frame.add(chartPanel, BorderLayout.CENTER);
        return frame;
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
