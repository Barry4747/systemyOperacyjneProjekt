package algorythms;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import requestFiles.Request;
import requestFiles.RequestBuilder;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.List;

import static requestFiles.RequestBuilder.buildRequests;

public class ProcesSolvingAlgorythms {
    private int duration;
    int maxProcesCreated =1;
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

    public List<Request> fcfs(){
        List<Request> list = new ArrayList<>();
        int counter=0;

        for(int i=0; i<duration; i++){
            list.addAll(buildRequests(maxProcesCreated, i, 2.0, 50, probabilities[i], duration));
            if(!list.isEmpty()&&counter!=list.size()){
                list.get(counter).setLength(list.get(counter).getLength()-timePeriod);
                list.get(counter).setDuration(list.get(counter).getDuration()+1);
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

    public List<Request> sjf(){
        List<Request> list = new ArrayList<>();

        PriorityQueue<Request> queue = new PriorityQueue<>(Comparator.comparing(Request::getBeginingLength));

        Request tempRequest=null;

        List<Request> tempList = new LinkedList<>();

        for(int i=0; i<duration; i++){
            tempList = new ArrayList<>();
            tempList.addAll(buildRequests(maxProcesCreated, i, 2.0, 50, probabilities[i], duration));
            queue.addAll(tempList);
            list.addAll(tempList);

            if(!queue.isEmpty()&&(tempRequest==null|| tempRequest.isDone())){
                tempRequest=queue.remove();
//                list.add(tempRequest);
            } else if (!queue.isEmpty()) {
                tempRequest.setLength(tempRequest.getLength()-timePeriod);
                tempRequest.setDuration(tempRequest.getDuration()+1);
                if(tempRequest.getLength()<=0){
                    tempRequest.setEndTime(i);
                    tempRequest.setDone(true);
                    tempRequest=queue.remove();
                }
            }
        }
//        while(!queue.isEmpty()){
//            list.add(queue.remove());
//        }
        return list;
    }

    public List<Request> sjfw(){
        List<Request> list = new ArrayList<>();

        PriorityQueue<Request> queue = new PriorityQueue<>(Comparator.comparing(Request::getLength));

        Request tempRequest=null;

        List<Request> tempList = new LinkedList<>();

        for(int i=0; i<duration; i++){
            tempList = new ArrayList<>();
            tempList.addAll(buildRequests(maxProcesCreated, i, 2.0, 50, probabilities[i], duration));
            queue.addAll(tempList);
            list.addAll(tempList);
            if(!queue.isEmpty()){
                tempRequest=queue.remove();
                tempRequest.setLength(tempRequest.getLength()-timePeriod);
                tempRequest.setDuration(tempRequest.getDuration()+1);
                if(tempRequest.getLength()<=0){
                    tempRequest.setEndTime(i);
                    tempRequest.setDone(true);
                }else {
                    queue.add(tempRequest);
                }
            }
        }
//        while(!queue.isEmpty()){
//            list.add(queue.remove());
//        }
        return list;
    }

    public List<Request> rr(int k){
        List<Request> list = new ArrayList<>();

        int counter=0;
        int iter=0;

        for(int i=0; i<duration; i++){

            list.addAll(buildRequests(maxProcesCreated, i, 2.0, 50, probabilities[i], duration));

            if(list.size()!=iter) {
                list.get(iter).setLength(list.get(iter).getLength() - timePeriod);
                list.get(iter).setDuration(list.get(iter).getDuration()+1);
                if (list.get(iter).getLength() <= 0) {
                    list.get(iter).setDuration((int) (list.get(iter).getDuration()-list.get(iter).getLength()));
                    list.get(iter).setEndTime(i);
                    list.get(iter).setDone(true);
                    iter++;
                    counter=0;
                    if(iter==list.size()){
                        iter=0;
                    }
                } else if (counter<k-1) {
                    counter++;
                } else if (counter==k-1) {
                    counter=0;
                    iter++;
                }
            }else {
                iter=0;
                counter=0;
            }
        }


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
    public JPanel strips(double done, double started, double notStarted, String name) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setMaximumSize(new Dimension(300,300));
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();


        dataset.addValue(done, "Zakończone", " ");
        dataset.addValue(started, "Rozpoczęte, ale nieskończone", " ");
        dataset.addValue(notStarted, "Niezaczęte", " ");


        JFreeChart chart = ChartFactory.createBarChart(
                name,
                "",
                "Ilość procesów",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(BorderLayout.CENTER,chartPanel);
        return panel;
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
