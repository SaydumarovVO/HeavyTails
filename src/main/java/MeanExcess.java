import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeanExcess {
    private final String defaultDistr = "/home/vsaydumarov/Studying/HeavyTails/FrechetDistr";
    private final String meanExcessFile = "/home/vsaydumarov/Studying/HeavyTails/MeanExcess";
    private final String meanExcessFileData = "/home/vsaydumarov/Studying/HeavyTails/MeanExcessData";
    private final String meanExcessFileFrechet = "/home/vsaydumarov/Studying/HeavyTails/MeanExcessFrechet";
    private final String meanExcessFileDataFrechet = "/home/vsaydumarov/Studying/HeavyTails/MeanExcessDataFrechet";


    private List<Double> sample;
    private List<Double> data;
    private List<Double> frechetData;
    private final int n;

    public MeanExcess(){
        FileWorker fileWorker = new FileWorker();
        try {
            this.sample = fileWorker.read(defaultDistr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        n = sample.size();
        this.data = generateData();
        this.frechetData = generateFrechet();
    }

    public List<Double> meanExcess(List<Double> data){
        List<Double> list = new ArrayList<Double>();

        for (Double point : data){
            Double numerator = 0.0;
            Double denominator = 0.0;
            for (int i = 1; i <= n; i++){
                numerator += ((sample.get(i-1) - point)*indicator(point, sample.get(i-1)));
                denominator += indicator(point, sample.get(i-1));
            }
            list.add(numerator/denominator);
        }

        return list;
    }


    private List<Double> generateData(){
        List<Double> list = new ArrayList<Double>();
        for (Double k : sample){
            Double l = 1000*Math.random();
            list.add(l);
        }
        Collections.sort(list);
        return list;
    }

    private List<Double> generateFrechet(){
        BuildFrechetDistribution buildFrechetDistribution = new BuildFrechetDistribution();
        return buildFrechetDistribution.calc(1000);
    }

    private int indicator(Double point, Double pointFromDistr){
        return pointFromDistr > point ? 1 : 0;
    }

    public void writeToFile(){
        FileWorker fileWorker = new FileWorker();
        fileWorker.write(meanExcessFile, meanExcess(data));
        fileWorker.write(meanExcessFileData, data);
        fileWorker.write(meanExcessFileFrechet, meanExcess(frechetData));
        fileWorker.write(meanExcessFileDataFrechet, frechetData);
    }
}
