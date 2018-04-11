package com.danil.etl.sheduler;

import com.danil.etl.sheduler.loader.LoadScheduler;
import com.danil.etl.sheduler.transformer.TransformerSheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;


@Component
public class Scheduler {

@Autowired
private TransformerSheduler transformerSheduler;
@Autowired
private LoadScheduler loadScheduler;

    public void doJob() {
        System.out.println("Starting...");
        final long startTime = System.currentTimeMillis();

        transformerSheduler.schedule();
        loadScheduler.schedule();

        final long endTime = System.currentTimeMillis();
        final long totalTimeInMilis = endTime - startTime;
        System.out.println(
                String.format("Total spent time: %02d h %02d min %02d sec",
                        TimeUnit.MILLISECONDS.toHours(totalTimeInMilis),
                        TimeUnit.MILLISECONDS.toMinutes(totalTimeInMilis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalTimeInMilis)),
                        TimeUnit.MILLISECONDS.toSeconds(totalTimeInMilis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTimeInMilis))));
    }
}
