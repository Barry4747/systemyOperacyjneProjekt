package requestFiles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import org.apache.commons.math3.distribution.BetaDistribution;


public class RequestBuilder {

    public static LinkedList<Request> buildRequests(int n, int k, double alpha, double beta, double prob, int duration){
        LinkedList<Request> requestsList = new LinkedList<>();

        BetaDistribution betaDistributionLength = new BetaDistribution(alpha, beta);

        for(int i=0; i<n; i++){
            if(prob>0.7)
                requestsList.add(new Request(k*10 +i, (int) (betaDistributionLength.sample()*100)+1, k, duration));
        }

        return requestsList;
    }


}
