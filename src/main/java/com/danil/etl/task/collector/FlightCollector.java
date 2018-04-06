package com.danil.etl.task.collector;

import com.danil.etl.entity.Aenaflight;
import com.danil.etl.entity.AenaflightTmp;
import com.danil.etl.utils.Stack;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class FlightCollector {

    public AenaflightTmp collect(List<Aenaflight> flights) {
        final AenaflightTmp tmpFlight = new AenaflightTmp();
        tmpFlight.setStartIndex(flights.get(0).getId());
        tmpFlight.setEndIndex(flights.get(flights.size() - 1).getId());

        final Stack<String> baggageInfoStack = new Stack<>();
        final Stack<String> counterStack = new Stack<>();
        final Stack<String> gateInfoStack = new Stack<>();
        final Stack<String> loungeInfoStack = new Stack<>();
        final Stack<String> terminalInfoStack = new Stack<>();
        final Stack<String> arrTerminalInfoStack = new Stack<>();

        for(Aenaflight flight : flights) {

            if (StringUtils.isNotEmpty(flight.getActArrDateTimeLt())) {
                tmpFlight.setActArrDateTimeLt(flight.getActArrDateTimeLt());
            }
            if (StringUtils.isNotEmpty(flight.getArrAptCodeIata())) {
                tmpFlight.setArrAptCodeIata(flight.getArrAptCodeIata());
            }
            if (StringUtils.isNotEmpty(flight.getAircraftNameScheduled())) {
                tmpFlight.setAircraftNameScheduled(flight.getAircraftNameScheduled());
            }
            if (StringUtils.isNotEmpty(flight.getArrAptNameEs())) {
                tmpFlight.setArrAptNameEs(flight.getArrAptNameEs());
            }

            baggageInfoStack.push(flight.getBaggageInfo());

            if (StringUtils.isNotEmpty(flight.getCarrierAirlineNameEn())) {
                tmpFlight.setCarrierAirlineNameEn(flight.getCarrierAirlineNameEn());
            }
            if (StringUtils.isNotEmpty(flight.getCarrierIcaoCode())) {
                tmpFlight.setCarrierIcaoCode(flight.getCarrierIcaoCode());
            }
            if (StringUtils.isNotEmpty(flight.getCarrierNumber())) {
                tmpFlight.setCarrierNumber(flight.getCarrierNumber());
            }

            counterStack.push(flight.getCarrierNumber());

            if (StringUtils.isNotEmpty(flight.getDepAptNameEs())) {
                tmpFlight.setDepAptNameEs(flight.getDepAptNameEs());
            }
            if (StringUtils.isNotEmpty(flight.getDepAptNameEs())) {
                tmpFlight.setDepAptNameEs(flight.getDepAptNameEs());
            }
            if (StringUtils.isNotEmpty(flight.getDepAptCodeIata())) {
                tmpFlight.setDepAptCodeIata(flight.getDepAptCodeIata());
            }
            if (StringUtils.isNotEmpty(flight.getEstArrDateTimeLt())) {
                tmpFlight.setEstArrDateTimeLt(flight.getEstArrDateTimeLt());
            }
            if (StringUtils.isNotEmpty(flight.getEstDepDateTimeLt())) {
                tmpFlight.setEstDepDateTimeLt(flight.getEstDepDateTimeLt());
            }
            if (StringUtils.isNotEmpty(flight.getFlightAirlineNameEn())) {
                tmpFlight.setFlightAirlineNameEn(flight.getFlightAirlineNameEn());
            }
            if (StringUtils.isNotEmpty(flight.getFlightAirlineName())) {
                tmpFlight.setFlightAirlineName(flight.getFlightAirlineName());
            }
            if (StringUtils.isNotEmpty(flight.getFlightIcaoCode())) {
                tmpFlight.setFlightIcaoCode(flight.getFlightIcaoCode());
            }
            if (StringUtils.isNotEmpty(flight.getFlightNumber())) {
                tmpFlight.setFlightNumber(flight.getFlightNumber());
            }
            if (StringUtils.isNotEmpty(flight.getFltLegSeqNo())) {
                tmpFlight.setFltLegSeqNo(flight.getFltLegSeqNo());
            }

            gateInfoStack.push(flight.getGateInfo());

            loungeInfoStack.push(flight.getLoungeInfo());

            if (StringUtils.isNotEmpty(flight.getSchdArrOnlyDateLt())) {
                tmpFlight.setSchdArrOnlyDateLt(flight.getSchdArrOnlyDateLt());
            }
            if (StringUtils.isNotEmpty(flight.getSchdArrOnlyTimeLt())) {
                tmpFlight.setSchdArrOnlyTimeLt(flight.getSchdArrOnlyTimeLt());
            }
            if (StringUtils.isNotEmpty(flight.getSourceData())) {
                tmpFlight.setSourceData(flight.getSourceData());
            }
            if (StringUtils.isNotEmpty(flight.getStatusInfo())) {
                tmpFlight.setStatusInfo(flight.getStatusInfo());
            }

            terminalInfoStack.push(flight.getTerminalInfo());

            arrTerminalInfoStack.push(flight.getArrTerminalInfo());

            if (StringUtils.isNotEmpty(flight.getActDepDateTimeLt())) {
                tmpFlight.setActDepDateTimeLt(flight.getActDepDateTimeLt());
            }
            if (StringUtils.isNotEmpty(flight.getSchdDepOnlyDateLt())) {
                tmpFlight.setSchdDepOnlyDateLt(flight.getSchdDepOnlyDateLt());
            }
            if (StringUtils.isNotEmpty(flight.getSchdDepOnlyTimeLt())) {
                tmpFlight.setSchdDepOnlyTimeLt(flight.getSchdDepOnlyTimeLt());
            }
            if (flight.getCreatedAt() != null) {
                tmpFlight.setCreatedAt(flight.getCreatedAt());
            }
        }

        tmpFlight.setBaggageInfo(baggageInfoStack.toString());
        tmpFlight.setCounter(counterStack.toString());
        tmpFlight.setGateInfo(gateInfoStack.toString());
        tmpFlight.setLoungeInfo(loungeInfoStack.toString());
        tmpFlight.setTerminalInfo(terminalInfoStack.toString());
        tmpFlight.setArrTerminalInfo(arrTerminalInfoStack.toString());
        return tmpFlight;
    }
}
