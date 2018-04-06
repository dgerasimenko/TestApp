package com.danil.etl.task.converter;

import com.danil.etl.entity.Aenaflight;
import com.danil.etl.entity.DestinationData;
import com.danil.etl.utils.DateTimeUtils;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FlightToDestinationData {

    public DestinationData convert(Aenaflight flight) {
        final DestinationData destinationData = new DestinationData();

        destinationData.setAdep(flight.getDepAptCodeIata());
        destinationData.setAdes(flight.getArrAptCodeIata());
        destinationData.setFlightCode(flight.getFlightIcaoCode());
        destinationData.setFlightNumber(flight.getFlightNumber());
        destinationData.setCarrierCode(flight.getCarrierIcaoCode());
        destinationData.setCarrierNumber(flight.getCarrierNumber());
        destinationData.setStatusInfo(flight.getStatusInfo());

        if (StringUtils.isNotEmpty(flight.getSchdDepOnlyDateLt()) && StringUtils.isNotEmpty(flight.getSchdDepOnlyTimeLt())) {
            final LocalDate schdDepOnlyDateLt = DateTimeUtils.parseDate(flight.getSchdDepOnlyDateLt());
            final LocalTime schdDepOnlyTimeLt = DateTimeUtils.parseTime(flight.getSchdDepOnlyTimeLt());
            destinationData.setSchdDepLt(LocalDateTime.of(schdDepOnlyDateLt, schdDepOnlyTimeLt));
        }

        if (StringUtils.isNotEmpty(flight.getSchdArrOnlyDateLt()) && StringUtils.isNotEmpty(flight.getSchdArrOnlyTimeLt())) {
            final LocalDate schdArrOnlyDateLt = DateTimeUtils.parseDate(flight.getSchdArrOnlyDateLt());
            final LocalTime schdArrOnlyTimeLt = DateTimeUtils.parseTime(flight.getSchdArrOnlyTimeLt());
            destinationData.setSchdArrLt(LocalDateTime.of(schdArrOnlyDateLt, schdArrOnlyTimeLt));
        }
        if (StringUtils.isNotEmpty(flight.getEstDepDateTimeLt())) {
            destinationData.setEstDepLt(DateTimeUtils.parseDateTime(flight.getEstDepDateTimeLt()));
        }
        if (StringUtils.isNotEmpty(flight.getEstArrDateTimeLt())) {
            destinationData.setEstArrLt(DateTimeUtils.parseDateTime(flight.getEstArrDateTimeLt()));
        }
        if (StringUtils.isNotEmpty(flight.getActDepDateTimeLt())) {
            destinationData.setActDepLt(DateTimeUtils.parseDateTime(flight.getActDepDateTimeLt()));
        }
        if (StringUtils.isNotEmpty(flight.getActArrDateTimeLt())) {
            destinationData.setActArrLt(DateTimeUtils.parseDateTime(flight.getActArrDateTimeLt()));
        }
        if (StringUtils.isNotEmpty(flight.getFltLegSeqNo())) {
            destinationData.setFltLegSeqNo(Integer.valueOf(flight.getFltLegSeqNo()));
        }
        destinationData.setAircraftNameScheduled(flight.getAircraftNameScheduled());
        destinationData.setBaggageInfo(flight.getBaggageInfo());
        destinationData.setCounter(flight.getCounter());
        destinationData.setGateInfo(flight.getGateInfo());
        destinationData.setLoungeInfo(flight.getLoungeInfo());
        destinationData.setTerminalInfo(flight.getTerminalInfo());
        destinationData.setSourceData(flight.getSourceData());
        if (flight.getCreatedAt() != null) {
            destinationData.setCreatedAt(DateTimeUtils.fromTimestampMilis(flight.getCreatedAt()));
        }

        return destinationData;
    }
}
