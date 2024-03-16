package requestFiles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import org.apache.commons.math3.distribution.BetaDistribution;


public class RequestBuilder {

    public static LinkedList<Request> buildRequests(int n){
        LinkedList<Request> requestsList = new LinkedList<>();

        BetaDistribution betaDistributionLength = new BetaDistribution(2, 5);
        BetaDistribution betaDistributionArrivalTime = new BetaDistribution(1, 100);

        for(int i=0; i<n; i++){
            requestsList.add(new Request(i, (int) (betaDistributionLength.sample()*100), (int) (betaDistributionArrivalTime.sample() * 1000), 0, false));
        }

        return requestsList;
    }


}
