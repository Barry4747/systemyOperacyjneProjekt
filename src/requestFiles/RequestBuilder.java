package requestFiles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import org.apache.commons.math3.distribution.BetaDistribution;


public class RequestBuilder {
    public static int number=0;

    public static LinkedList<Request> buildRequests(int n, int k, double alpha, double beta, double prob, int duration){
        LinkedList<Request> requestsList = new LinkedList<>();

        BetaDistribution betaDistributionLength = new BetaDistribution(alpha, beta);

        for(int i=0; i<n; i++){
            double probability = new Random().nextDouble(prob+0.01);
            if(probability>0.4)
                requestsList.add(new Request(number++, (int) (betaDistributionLength.sample() * 100) + 1, k, duration));

        }

        return requestsList;
    }


}
