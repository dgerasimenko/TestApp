package com.danil.etl.dto;

import com.danil.etl.utils.Stack;

public class FlightDto {

    private Long id;

    private String actArrDateTimeLt;

    private String aircraftNameScheduled;

    private String arrAptNameEs;

    private String arrAptCodeIata;

    private Stack<String> baggageInfo;

    private String carrierAirlineNameEn;

    private String carrierIcaoCode;

    private String carrierNumber;

    private Stack<String> counter;

    private String depAptNameEs;

    private String depAptCodeIata;

    private String estArrDateTimeLt;

    private String estDepDateTimeLt;

    private String flightAirlineNameEn;

    private String flightAirlineName;

    private String flightIcaoCode;

    private String flightNumber;

    private String fltLegSeqNo;

    private Stack<String> gateInfo;

    private Stack<String> loungeInfo;

    private String schdArrOnlyDateLt;

    private String schdArrOnlyTimeLt;

    private String sourceData;

    private String statusInfo;

    private Stack<String> terminalInfo;

    private Stack<String> arrTerminalInfo;

    private Long createdAt;

    private String actDepDateTimeLt;

    private String schdDepOnlyDateLt;

    private String schdDepOnlyTimeLt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActArrDateTimeLt() {
        return actArrDateTimeLt;
    }

    public void setActArrDateTimeLt(String actArrDateTimeLt) {
        this.actArrDateTimeLt = actArrDateTimeLt;
    }

    public String getAircraftNameScheduled() {
        return aircraftNameScheduled;
    }

    public void setAircraftNameScheduled(String aircraftNameScheduled) {
        this.aircraftNameScheduled = aircraftNameScheduled;
    }

    public String getArrAptNameEs() {
        return arrAptNameEs;
    }

    public void setArrAptNameEs(String arrAptNameEs) {
        this.arrAptNameEs = arrAptNameEs;
    }

    public String getArrAptCodeIata() {
        return arrAptCodeIata;
    }

    public void setArrAptCodeIata(String arrAptCodeIata) {
        this.arrAptCodeIata = arrAptCodeIata;
    }

    public Stack<String> getBaggageInfo() {
        return baggageInfo;
    }

    public void setBaggageInfo(Stack<String> baggageInfo) {
        this.baggageInfo = baggageInfo;
    }

    public String getCarrierAirlineNameEn() {
        return carrierAirlineNameEn;
    }

    public void setCarrierAirlineNameEn(String carrierAirlineNameEn) {
        this.carrierAirlineNameEn = carrierAirlineNameEn;
    }

    public String getCarrierIcaoCode() {
        return carrierIcaoCode;
    }

    public void setCarrierIcaoCode(String carrierIcaoCode) {
        this.carrierIcaoCode = carrierIcaoCode;
    }

    public String getCarrierNumber() {
        return carrierNumber;
    }

    public void setCarrierNumber(String carrierNumber) {
        this.carrierNumber = carrierNumber;
    }

    public Stack<String> getCounter() {
        return counter;
    }

    public void setCounter(Stack<String> counter) {
        this.counter = counter;
    }

    public String getDepAptNameEs() {
        return depAptNameEs;
    }

    public void setDepAptNameEs(String depAptNameEs) {
        this.depAptNameEs = depAptNameEs;
    }

    public String getDepAptCodeIata() {
        return depAptCodeIata;
    }

    public void setDepAptCodeIata(String depAptCodeIata) {
        this.depAptCodeIata = depAptCodeIata;
    }

    public String getEstArrDateTimeLt() {
        return estArrDateTimeLt;
    }

    public void setEstArrDateTimeLt(String estArrDateTimeLt) {
        this.estArrDateTimeLt = estArrDateTimeLt;
    }

    public String getEstDepDateTimeLt() {
        return estDepDateTimeLt;
    }

    public void setEstDepDateTimeLt(String estDepDateTimeLt) {
        this.estDepDateTimeLt = estDepDateTimeLt;
    }

    public String getFlightAirlineNameEn() {
        return flightAirlineNameEn;
    }

    public void setFlightAirlineNameEn(String flightAirlineNameEn) {
        this.flightAirlineNameEn = flightAirlineNameEn;
    }

    public String getFlightAirlineName() {
        return flightAirlineName;
    }

    public void setFlightAirlineName(String flightAirlineName) {
        this.flightAirlineName = flightAirlineName;
    }

    public String getFlightIcaoCode() {
        return flightIcaoCode;
    }

    public void setFlightIcaoCode(String flightIcaoCode) {
        this.flightIcaoCode = flightIcaoCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFltLegSeqNo() {
        return fltLegSeqNo;
    }

    public void setFltLegSeqNo(String fltLegSeqNo) {
        this.fltLegSeqNo = fltLegSeqNo;
    }

    public Stack<String> getGateInfo() {
        return gateInfo;
    }

    public void setGateInfo(Stack<String> gateInfo) {
        this.gateInfo = gateInfo;
    }

    public Stack<String> getLoungeInfo() {
        return loungeInfo;
    }

    public void setLoungeInfo(Stack<String> loungeInfo) {
        this.loungeInfo = loungeInfo;
    }

    public String getSchdArrOnlyDateLt() {
        return schdArrOnlyDateLt;
    }

    public void setSchdArrOnlyDateLt(String schdArrOnlyDateLt) {
        this.schdArrOnlyDateLt = schdArrOnlyDateLt;
    }

    public String getSchdArrOnlyTimeLt() {
        return schdArrOnlyTimeLt;
    }

    public void setSchdArrOnlyTimeLt(String schdArrOnlyTimeLt) {
        this.schdArrOnlyTimeLt = schdArrOnlyTimeLt;
    }

    public String getSourceData() {
        return sourceData;
    }

    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public Stack<String> getTerminalInfo() {
        return terminalInfo;
    }

    public void setTerminalInfo(Stack<String> terminalInfo) {
        this.terminalInfo = terminalInfo;
    }

    public Stack<String> getArrTerminalInfo() {
        return arrTerminalInfo;
    }

    public void setArrTerminalInfo(Stack<String> arrTerminalInfo) {
        this.arrTerminalInfo = arrTerminalInfo;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getActDepDateTimeLt() {
        return actDepDateTimeLt;
    }

    public void setActDepDateTimeLt(String actDepDateTimeLt) {
        this.actDepDateTimeLt = actDepDateTimeLt;
    }

    public String getSchdDepOnlyDateLt() {
        return schdDepOnlyDateLt;
    }

    public void setSchdDepOnlyDateLt(String schdDepOnlyDateLt) {
        this.schdDepOnlyDateLt = schdDepOnlyDateLt;
    }

    public String getSchdDepOnlyTimeLt() {
        return schdDepOnlyTimeLt;
    }

    public void setSchdDepOnlyTimeLt(String schdDepOnlyTimeLt) {
        this.schdDepOnlyTimeLt = schdDepOnlyTimeLt;
    }
}
