package com.danil.etl.sheduler;

import com.danil.etl.loader.Loader;
import com.danil.etl.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;


@Component
public class Scheduler {

@Autowired
private Transformer transformer;
@Autowired
private Loader loader;

    public void doJob() {
        System.out.println("Starting...");
        final long startTime = System.currentTimeMillis();
        transformer.extractAndTransform();
        System.out.print("Loading...");
        loader.loadDataFromSource();
        System.out.println(".Done");

        final long endTime = System.currentTimeMillis();
        final long totalTimeInMilis = endTime - startTime;
        System.out.println(
                String.format("Total spent time: %02d min %02d sec",
                        TimeUnit.MILLISECONDS.toMinutes(totalTimeInMilis),
                        TimeUnit.MILLISECONDS.toSeconds(totalTimeInMilis) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTimeInMilis))));
    }
}
