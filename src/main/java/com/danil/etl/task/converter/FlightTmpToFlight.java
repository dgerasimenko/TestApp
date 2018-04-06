package com.danil.etl.task.converter;

import com.danil.etl.entity.Aenaflight;
import com.danil.etl.entity.AenaflightTmp;

public class FlightTmpToFlight {

    public Aenaflight convert(AenaflightTmp aenaflightTmp) {
        final Aenaflight aenaflight = new Aenaflight();
        aenaflight.setActArrDateTimeLt(aenaflightTmp.getActArrDateTimeLt());
        aenaflight.setArrAptCodeIata(aenaflightTmp.getArrAptCodeIata());
        aenaflight.setAircraftNameScheduled(aenaflightTmp.getAircraftNameScheduled());
        aenaflight.setArrAptNameEs(aenaflightTmp.getArrAptNameEs());
        aenaflight.setCarrierAirlineNameEn(aenaflightTmp.getCarrierAirlineNameEn());
        aenaflight.setCarrierIcaoCode(aenaflightTmp.getCarrierIcaoCode());
        aenaflight.setCarrierNumber(aenaflightTmp.getCarrierNumber());
        aenaflight.setDepAptNameEs(aenaflightTmp.getDepAptNameEs());
        aenaflight.setDepAptNameEs(aenaflightTmp.getDepAptNameEs());
        aenaflight.setDepAptCodeIata(aenaflightTmp.getDepAptCodeIata());
        aenaflight.setEstArrDateTimeLt(aenaflightTmp.getEstArrDateTimeLt());
        aenaflight.setEstDepDateTimeLt(aenaflightTmp.getEstDepDateTimeLt());
        aenaflight.setFlightAirlineNameEn(aenaflightTmp.getFlightAirlineNameEn());
        aenaflight.setFlightAirlineName(aenaflightTmp.getFlightAirlineName());
        aenaflight.setFlightIcaoCode(aenaflightTmp.getFlightIcaoCode());
        aenaflight.setFlightNumber(aenaflightTmp.getFlightNumber());
        aenaflight.setFltLegSeqNo(aenaflightTmp.getFltLegSeqNo());
        aenaflight.setSchdArrOnlyDateLt(aenaflightTmp.getSchdArrOnlyDateLt());
        aenaflight.setSchdArrOnlyTimeLt(aenaflightTmp.getSchdArrOnlyTimeLt());
        aenaflight.setSourceData(aenaflightTmp.getSourceData());
        aenaflight.setStatusInfo(aenaflightTmp.getStatusInfo());
        aenaflight.setActDepDateTimeLt(aenaflightTmp.getActDepDateTimeLt());
        aenaflight.setSchdDepOnlyDateLt(aenaflightTmp.getSchdDepOnlyDateLt());
        aenaflight.setSchdDepOnlyTimeLt(aenaflightTmp.getSchdDepOnlyTimeLt());
        aenaflight.setBaggageInfo(aenaflightTmp.getBaggageInfo());
        aenaflight.setCounter(aenaflightTmp.getCounter());
        aenaflight.setGateInfo(aenaflightTmp.getGateInfo());
        aenaflight.setLoungeInfo(aenaflightTmp.getLoungeInfo());
        aenaflight.setTerminalInfo(aenaflightTmp.getTerminalInfo());
        aenaflight.setArrTerminalInfo(aenaflightTmp.getArrTerminalInfo());
        aenaflight.setCreatedAt(aenaflightTmp.getCreatedAt());

        return aenaflight;
    }
}
