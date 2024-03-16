package requestFiles;

import java.util.ArrayList;
import java.util.Random;

public class RequestBuilder {

    public static ArrayList<Request> buildRequests(int n){
        ArrayList<Request> requestsList = new ArrayList<>();

        for(int i=0; i<n; i++){
            requestsList.add(new Request(new Random().nextInt(100)+1, 0, new Random().nextInt(1+ new Random().nextInt(50))+1));
        }

        return requestsList;
    }


}
