import requestFiles.Request;

import java.util.ArrayList;

import static requestFiles.RequestBuilder.buildRequests;

public class Main {
    public static void main(String[] args) {

        ArrayList<Request> list = buildRequests(10000);
        for(Request i : list){
            System.out.println(i.toString());
        }


    }
}