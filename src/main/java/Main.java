public class Main {
    public static void main(String[] args) {

        /**
         * Frechet distribution.
         * ���������� ��������� ���� ���, ��� ������� ������ ������� ��������� ���������������� ������ ������� ����.
         */
        BuildFrechetDistribution buildFrechetDistribution = new BuildFrechetDistribution();
        buildFrechetDistribution.writeToFile();

        /**
         * Ratio of Maximum
         */
        RatioOfMaximum ratioOfMaximum = new RatioOfMaximum();
        ratioOfMaximum.writeToFile();

        /**
         * Mean Excess
         */
        MeanExcess meanExcess = new MeanExcess();
        meanExcess.writeToFile();

        /**
         * Estimations
         */
        Estimations estimations = new Estimations();
        estimations.writeToFile();

    }
}
