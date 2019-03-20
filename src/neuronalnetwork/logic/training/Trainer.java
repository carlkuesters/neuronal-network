package neuronalnetwork.logic.training;

/**

 @author Carl
 */
public class Trainer {

    private Training training;
    private int batchSize;
    private int runs;
    private boolean isPaused;

    public Trainer(Training training, int batchSize) {
        this.training = training;
        this.batchSize = batchSize;
    }

    public void trainEndless(double statisticsKeepRate) {
        TestUtil.setRandomWeights(training.getNetwork());
        runs = 0;
        while (true) {
            for (int i = 0; i < batchSize; i++) {
                while (isPaused) {
                    try {
                        Thread.sleep(1000 / 60);
                    } catch (InterruptedException ex) {
                    }
                }
                training.trainRandomTestCase();
                runs++;
            }
            training.updateStatistics(statisticsKeepRate);
        }
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public Training getTraining() {
        return training;
    }

    public int getRuns() {
        return runs;
    }
}
