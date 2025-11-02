package graph;

public class SimpleMetrics implements Metrics {
    private long startTime;
    private long elapsed;
    private long ops = 0;

    @Override
    public void start() {
        this.startTime = System.nanoTime();
    }

    @Override
    public void stop() {
        this.elapsed = System.nanoTime() - startTime;
    }

    @Override
    public void incOps() {
        ops++;
    }

    @Override
    public long getTime() {
        return elapsed;
    }

    @Override
    public long getOps() {
        return ops;
    }
}
