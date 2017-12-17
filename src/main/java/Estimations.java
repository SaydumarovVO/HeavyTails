import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Estimations {

    private final String defaultDistr = "/home/vsaydumarov/Studying/HeavyTails/FrechetDistr";

    private final String hillsEstimatorFile = "/home/vsaydumarov/Studying/HeavyTails/Estimators/HillsEstimator";
    private final String ratioEstimatorFile = "/home/vsaydumarov/Studying/HeavyTails/Estimators/RatioEstimator";
    private final String ratioEstimatorDataFile = "/home/vsaydumarov/Studying/HeavyTails/Estimators/RatioEstimatorData";
    private final String momentEstimatorFile = "/home/vsaydumarov/Studying/HeavyTails/Estimators/MomentEstimator";
    private final String uhEstimatorFile = "/home/vsaydumarov/Studying/HeavyTails/Estimators/UHEstimator";
    private final String pickandsEstimatorFile = "/home/vsaydumarov/Studying/HeavyTails/Estimators/PickandsEstimator";

    private List<Double> sample;
    private final int n;
    private List<Double> points = new ArrayList<Double>();

    public Estimations(){
        FileWorker fileWorker = new FileWorker();
        try {
            this.sample = fileWorker.read(defaultDistr);
            Collections.sort(sample);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.n = sample.size();

        Double x1 = sample.get(0);
        Double xn = sample.get(n-1);
        Double diapason = xn - x1;
        Double step = diapason/n;

        for (int i = 1; i < n; i++){
            this.points.add(x1 + i*step);
        }
    }

    public List<Double> hillsEstimator(){
        List<Double> list = new ArrayList<Double>();
        for (int k = 1; k < n; k++){
            Double gamma = -Math.log(sample.get(n-k-1));
            for (int i = 1; i <= k; i++){
                gamma += (1/(k))*(Math.log(sample.get(n-i)));
            }
            list.add(gamma);
        }
        return list;
    }

    public List<Double> ratioEstimator(){
        List<Double> list = new ArrayList<Double>();


        for (Double point : points){
            Double numerator = 0.0;
            Double denominator = 0.0;
            for (int i = 0; i < n; i++){
                numerator += Math.log(sample.get(i)/point)*indicator(point, sample.get(i));
                denominator += indicator(point, sample.get(i));
            }
            list.add(numerator/denominator);
        }
        return list;
    }

    public List<Double> momentEstimator(){
        List<Double> hills = hillsEstimator();
        List<Double> list = new ArrayList<Double>();
        for (int k = 1; k < n; k++){
            Double gamma = hills.get(k-1) + 1 - 0.5*Math.pow(1 - (Math.pow(hills.get(k-1), 2)/snk(k)) ,-1);
            list.add(gamma);
        }
        return list;
    }

    public List<Double> uhEstimator(){
        List<Double> list = new ArrayList<Double>();
        List<Double> hills = hillsEstimator();
        for (int k = 1; k < n; k++){
            Double gamma = -Math.log(sample.get(n-k)*hills.get(k-1));
            for (int i = 1; i <= k; i++){
                gamma += (1/(k))*(Math.log(sample.get(n-i-1)*hills.get(i-1)));
            }
            list.add(gamma);
        }
        return list;
    }

    public List<Double> pickandsEstimator(){
        List<Double> list = new ArrayList<Double>();
        for (int k = 1; k <= n/4; k++){
            Double gamma = Math.pow(Math.log(2), -1)*Math.log((sample.get(n-k) - sample.get(n-2*k))/(sample.get(n-2*k) - sample.get(n-4*k)));
            list.add(gamma);
        }
        return list;
    }

    public void writeToFile(){
        FileWorker fileWorker = new FileWorker();

        fileWorker.write(hillsEstimatorFile, hillsEstimator());
        fileWorker.write(ratioEstimatorDataFile, points);
        fileWorker.write(ratioEstimatorFile, ratioEstimator());
        fileWorker.write(momentEstimatorFile, momentEstimator());
        fileWorker.write(uhEstimatorFile, uhEstimator());
        fileWorker.write(pickandsEstimatorFile, pickandsEstimator());
    }

    private int indicator(Double point, Double pointFromDistr){
        return pointFromDistr > point ? 1 : 0;
    }

    private Double snk(int k){
        Double snk = 0.0;

        for (int i = 1; i <= k; i++){
            snk += (1/k)*Math.pow(Math.log(sample.get(n-i)) - Math.log(sample.get(n-k-1)),2);
        }
        return snk;
    }
}
