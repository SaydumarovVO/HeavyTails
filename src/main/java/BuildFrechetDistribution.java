import java.util.ArrayList;
import java.util.List;

public class BuildFrechetDistribution {
    final int sampleSize = 100;
    final double gamma = 1.5;
    final String fileNameOut = "/home/vsaydumarov/Studying/HeavyTails/FrechetDistr";

    public List<Double> calc(int size){
        List<Double> pointsRandom = new ArrayList<Double>();
        List<Double> points = new ArrayList<Double>();
        for (int i = 0; i < size; i++){
            Double point = Math.random();
            pointsRandom.add(point);
        }

        for (Double point : pointsRandom){
            point = (1/gamma)*Math.pow(-Math.log(point), -gamma);
            points.add(point);
        }


        return points;
    }

    public void writeToFile(){
        FileWorker fileWorker = new FileWorker();

        fileWorker.write(fileNameOut, calc(sampleSize));
    }
}
