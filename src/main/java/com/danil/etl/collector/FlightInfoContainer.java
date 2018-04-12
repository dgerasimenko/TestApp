package com.danil.etl.collector;

import com.danil.etl.entity.Flight;

public class FlightInfoContainer {
    private Flight flight;
    private long duplicates;

    public FlightInfoContainer(Flight flight, long duplicates) {
        this.flight = flight;
        this.duplicates = duplicates;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public long getDuplicates() {
        return duplicates;
    }

    public void incrementDuplicates(long duplicates) {
        this.duplicates += duplicates;
    }
}
