package com.danil.etl.entity;

import java.util.Objects;

public final class FlightUniqueKey {
    private final String arrAptCodeIata;
    private final String depAptCodeIata;
    private final String flightIcaoCode;
    private final String flightNumber;
    private final String schdDepOnlyDateLt;
    private final String schdDepOnlyTimeLt;

    public FlightUniqueKey(Flight flight) {
        this.arrAptCodeIata = flight.getArrAptCodeIata();
        this.depAptCodeIata = flight.getDepAptCodeIata();
        this.flightIcaoCode = flight.getFlightIcaoCode();
        this.flightNumber = flight.getFlightNumber();
        this.schdDepOnlyDateLt = flight.getSchdDepOnlyDateLt();
        this.schdDepOnlyTimeLt = flight.getSchdDepOnlyTimeLt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightUniqueKey that = (FlightUniqueKey) o;
        return Objects.equals(arrAptCodeIata, that.arrAptCodeIata) &&
                Objects.equals(depAptCodeIata, that.depAptCodeIata) &&
                Objects.equals(flightIcaoCode, that.flightIcaoCode) &&
                Objects.equals(flightNumber, that.flightNumber) &&
                Objects.equals(schdDepOnlyDateLt, that.schdDepOnlyDateLt) &&
                Objects.equals(schdDepOnlyTimeLt, that.schdDepOnlyTimeLt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(arrAptCodeIata, depAptCodeIata, flightIcaoCode, flightNumber, schdDepOnlyDateLt, schdDepOnlyTimeLt);
    }
}
