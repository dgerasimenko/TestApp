package com.danil.etl.task;

import com.danil.etl.entity.Aenaflight;
import com.danil.etl.entity.AenaflightTmp;
import com.danil.etl.utils.Stack;

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

            if (flight.getActArrDateTimeLt() != null) {
                tmpFlight.setActArrDateTimeLt(flight.getActArrDateTimeLt());
            }
            if (flight.getArrAptCodeIata() != null) {
                tmpFlight.setArrAptCodeIata(flight.getArrAptCodeIata());
            }
            if (flight.getAircraftNameScheduled() != null) {
                tmpFlight.setAircraftNameScheduled(flight.getAircraftNameScheduled());
            }
            if (flight.getArrAptNameEs() != null) {
                tmpFlight.setArrAptNameEs(flight.getArrAptNameEs());
            }

            baggageInfoStack.push(tmpFlight.getBaggageInfo());

            if (flight.getCarrierAirlineNameEn() != null) {
                tmpFlight.setCarrierAirlineNameEn(flight.getCarrierAirlineNameEn());
            }
            if (flight.getCarrierIcaoCode() != null) {
                tmpFlight.setCarrierIcaoCode(flight.getCarrierIcaoCode());
            }
            if (flight.getCarrierNumber() != null) {
                tmpFlight.setCarrierNumber(flight.getCarrierNumber());
            }

            counterStack.push(tmpFlight.getCarrierNumber());

            if (flight.getDepAptNameEs() != null) {
                tmpFlight.setDepAptNameEs(flight.getDepAptNameEs());
            }
            if (flight.getDepAptNameEs() != null) {
                tmpFlight.setDepAptNameEs(flight.getDepAptNameEs());
            }
            if (flight.getDepAptCodeIata() != null) {
                tmpFlight.setDepAptCodeIata(flight.getDepAptCodeIata());
            }
            if (flight.getEstArrDateTimeLt() != null) {
                tmpFlight.setEstArrDateTimeLt(flight.getEstArrDateTimeLt());
            }
            if (flight.getEstDepDateTimeLt() != null) {
                tmpFlight.setEstDepDateTimeLt(flight.getEstDepDateTimeLt());
            }
            if (flight.getFlightAirlineNameEn() != null) {
                tmpFlight.setFlightAirlineNameEn(flight.getFlightAirlineNameEn());
            }
            if (flight.getFlightAirlineName() != null) {
                tmpFlight.setFlightAirlineName(flight.getFlightAirlineName());
            }
            if (flight.getFlightIcaoCode() != null) {
                tmpFlight.setFlightIcaoCode(flight.getFlightIcaoCode());
            }
            if (flight.getFlightNumber() != null) {
                tmpFlight.setFlightNumber(flight.getFlightNumber());
            }
            if (flight.getFltLegSeqNo() != null) {
                tmpFlight.setFltLegSeqNo(flight.getFltLegSeqNo());
            }

            gateInfoStack.push(flight.getGateInfo());

            loungeInfoStack.push(flight.getLoungeInfo());

            if (flight.getSchdArrOnlyDateLt() != null) {
                tmpFlight.setSchdArrOnlyDateLt(flight.getSchdArrOnlyDateLt());
            }
            if (flight.getSchdArrOnlyTimeLt() != null) {
                tmpFlight.setSchdArrOnlyTimeLt(flight.getSchdArrOnlyTimeLt());
            }
            if (flight.getSourceData() != null) {
                tmpFlight.setSourceData(flight.getSourceData());
            }
            if (flight.getStatusInfo() != null) {
                tmpFlight.setStatusInfo(flight.getStatusInfo());
            }

            terminalInfoStack.push(flight.getTerminalInfo());

            arrTerminalInfoStack.push(flight.getArrTerminalInfo());

            if (flight.getActDepDateTimeLt() != null) {
                tmpFlight.setActDepDateTimeLt(flight.getActDepDateTimeLt());
            }
            if (flight.getSchdDepOnlyDateLt() != null) {
                tmpFlight.setSchdDepOnlyDateLt(flight.getSchdDepOnlyDateLt());
            }
            if (flight.getSchdDepOnlyTimeLt() != null) {
                tmpFlight.setSchdDepOnlyTimeLt(flight.getSchdDepOnlyTimeLt());
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
