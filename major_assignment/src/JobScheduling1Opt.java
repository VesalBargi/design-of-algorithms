import java.util.Arrays;

public class JobScheduling1Opt extends BackTrackingOptimization<Integer> {
    private int n; // Number of jobs
    private int[] deadlines;
    private int[] executionTimes;

    public JobScheduling1Opt(int[] deadlines, int[] executionTimes) {
        super(new Integer[deadlines.length], new Integer[deadlines.length]);
        this.n = deadlines.length;
        this.deadlines = deadlines;
        this.executionTimes = executionTimes;
        sortJobs();
    }

    private void sortJobs() {
        int[][] jobs = new int[n][3]; // [deadline, execution time, index]
        for (int i = 0; i < n; i++) {
            jobs[i][0] = deadlines[i];
            jobs[i][1] = executionTimes[i];
            jobs[i][2] = i;
        }
        Arrays.sort(jobs, (a, b) -> Integer.compare(a[0], b[0])); // Sort by earliest deadline first
        for (int i = 0; i < n; i++) {
            deadlines[i] = jobs[i][0];
            executionTimes[i] = jobs[i][1];
        }
    }

    @Override
    protected Integer[] nodeValues(int k) {
        return new Integer[] { 1, 0 }; // 1 means job is selected, 0 means not selected
    }

    @Override
    protected double bound(int k) {
        int clock = 0;
        for (int i = 0; i <= k; i++) {
            if (x[i] == 1) {
                clock += executionTimes[i];
                if (clock > deadlines[i]) {
                    return 0; // Prune infeasible schedules
                }
            }
        }
        return cost(k); // Returns the current feasible profit
    }

    @Override
    protected double cost(int k) {
        int clock = 0;
        int currentProfit = 0;
        for (int i = 0; i <= k; i++) {
            if (x[i] == 1) {
                clock += executionTimes[i];
                if (clock <= deadlines[i]) {
                    currentProfit++;
                }
            }
        }
        return currentProfit;
    }

    @Override
    protected boolean isSolution(int k) {
        return k == n - 1;
    }

    public void report() {
        System.out.println("Selected Jobs: ");
        for (int i = 0; i < n; i++) {
            if (finalX[i] != null && finalX[i] == 1) {
                System.out.println("Job " + (i + 1) + " with execution time " + executionTimes[i] + " and deadline "
                        + deadlines[i]);
            }
        }
        System.out.println("Total Scheduled Jobs: " + finalProfit);
        System.out.println("Number of nodes generated: " + numberOfNodes);
    }
}
