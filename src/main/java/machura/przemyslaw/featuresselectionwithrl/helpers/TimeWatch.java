/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.helpers;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author Przemek
 */
public class TimeWatch {

    long starts = 0;
    long totalTime = 0;

    public TimeWatch() {

    }

    public TimeWatch start() {
        starts = System.nanoTime();
        return this;
    }

    public TimeWatch reset() {
        totalTime = 0;
        return this;
    }
    
    public TimeWatch restart(){
        totalTime = 0;
        starts = System.nanoTime();
        return this;
    }
    
    public void addToTotalTime(){
        totalTime += elapsedTime();
    }

    public long elapsedTime() {
        long ends = System.nanoTime();
        return ends - starts;
    }

    public long time(TimeUnit unit) {
        return unit.convert(elapsedTime(), TimeUnit.NANOSECONDS);
    }

    public String toMinuteSeconds(){
        return String.format("%d min, %d sec", time(TimeUnit.MINUTES),
                time(TimeUnit.SECONDS) - time(TimeUnit.MINUTES));
    }
    
    public long totalTime(TimeUnit unit){
        return unit.convert(totalTime, TimeUnit.NANOSECONDS);
    }
    
    public String totalTimeToMinuteSeconds(){
        return String.format("%d min, %d sec", totalTime(TimeUnit.MINUTES),
                totalTime(TimeUnit.SECONDS) - (totalTime(TimeUnit.MINUTES))*60);
    }
    
    public Long totalTimeInSeconds(){
        return totalTime(TimeUnit.SECONDS);
    }
}
