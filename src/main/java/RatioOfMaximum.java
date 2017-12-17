import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RatioOfMaximum {

    final String fileNameOut = "/home/vsaydumarov/Studying/HeavyTails/RatioOfMaximum";
    private final String defaultDistr = "/home/vsaydumarov/Studying/HeavyTails/FrechetDistr";
    private final String ratioOfMaximumData = "/home/vsaydumarov/Studying/HeavyTails/ratioOfMaximumData";
    private final String ratioOfMaximum1 = "/home/vsaydumarov/Studying/HeavyTails/ratioOfMaximum1";
    private final String ratioOfMaximum5 = "/home/vsaydumarov/Studying/HeavyTails/ratioOfMaximum5";
    private final String ratioOfMaximum10 = "/home/vsaydumarov/Studying/HeavyTails/ratioOfMaximum10";
    private final String ratioOfMaximum20 = "/home/vsaydumarov/Studying/HeavyTails/ratioOfMaximum20";


    private List<Double> sample;
    private List<List<Double>> samples;

    public RatioOfMaximum() {
        FileWorker fileWorker = new FileWorker();
        try {
            this.sample = fileWorker.read(defaultDistr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.samples = generateNSamples();
    }

    public Double countSinglePoint(int p, List<Double> list){
        Double m = 0.0;
        Double s = 0.0;

        for (Double k : list){
            m = Math.max(Math.pow(Math.abs(k), p), m);
            k = Math.pow(Math.abs(k), p);
            s += k;
        }
        return m/s;
    }

    public List<Double> countStatisticForDifferentP(){
        List<Double> list = new ArrayList<Double>();
        for (int p = 1; p <= 20; p++){
            list.add(countSinglePoint(p, this.sample));
        }
        return list;
    }

    public List<List<Double>> generateNSamples(){
        List<List<Double>> samples = new ArrayList<List<Double>>();
        for (int n = 100; n < 1000; n += 10){
            BuildFrechetDistribution build = new BuildFrechetDistribution();
            List<Double> list = build.calc(n);
            samples.add(list);
        }
        return samples;
    }

    public List<Integer> listOfNumbers(List<List<Double>> samples){
        List<Integer> list = new ArrayList<Integer>();
        for (List<Double> sample : samples){
            list.add(sample.size());
        }
        return list;
    }

    public List<Double> generateStatisticsForDependenceFromN(int p){
        List<Double> list = new ArrayList<Double>();
        for (List<Double> singleSample : samples){
            list.add(countSinglePoint(p, singleSample));

        }
        return list;
    }

    public void writeToFile(){

        FileWorker fileWorker = new FileWorker();

        fileWorker.write(fileNameOut, countStatisticForDifferentP());

        fileWorker.write(ratioOfMaximumData, listOfNumbers(samples));
        fileWorker.write(ratioOfMaximum1, generateStatisticsForDependenceFromN(1));
        fileWorker.write(ratioOfMaximum5, generateStatisticsForDependenceFromN(5));
        fileWorker.write(ratioOfMaximum10, generateStatisticsForDependenceFromN(10));
        fileWorker.write(ratioOfMaximum20, generateStatisticsForDependenceFromN(20));


    }
}
