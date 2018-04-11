package com.danil.etl.loader;

import com.danil.etl.converter.FlightToDestinationData;
import com.danil.etl.dao.DestinationDataDao;
import com.danil.etl.dao.FlightDao;
import com.danil.etl.entity.DestinationData;
import com.danil.etl.entity.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Loader {
    @Autowired
    private DestinationDataDao destinationDataDao;

    @Autowired
    private FlightDao flightDao;

    public void loadDataFromSource() {
        System.out.print("Loading... ");
        final List<Flight> flights = flightDao.getAll();
        final FlightToDestinationData converter = new FlightToDestinationData();
        final List<DestinationData> destinationData = converter.convert(flights);
        destinationDataDao.persistAll(destinationData);
        System.out.println(".Done");
    }
}
