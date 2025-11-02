package metrics;

public class SimpleMetrics {

    private long ops;
    private long startTime;
    private long endTime;

    public void reset(){
        ops=0;
        startTime=endTime=0;
    }

    public void start(){
        startTime=System.nanoTime();
    }

    public void stop(){
        endTime=System.nanoTime();
    }

    public void incOps(){ ops++; }

    public long getOps(){ return ops; }

    public long getTimeNs(){ return endTime-startTime; }
}
