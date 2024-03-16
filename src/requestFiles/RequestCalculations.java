package requestFiles;

import java.util.Random;

public class RequestCalculations {

    private static boolean probabilityCheck(int probability){
        return probability> new Random().nextInt(0,1);
    }

}
