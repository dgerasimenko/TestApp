package com.danil.etl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aenaflight_tmp")
public class AenaflightTmp {
    @Id
    @Column
    private Long id;
    @Column(name="act_arr_date_time_lt")
    private String actArrDateTimeLt;
    @Column(name="aircraft_name_scheduled")
    private String aircraftNameScheduled;
    @Column(name="arr_apt_name_es")
    private String arrAptNameEs;
    @Column(name="arr_apt_code_iata")
    private String arrAptCodeIata;
    @Column(name="baggage_info")
    private String baggageInfo;
    @Column(name="carrier_airline_name_en")
    private String carrierAirlineNameEn;
    @Column(name="carrier_icao_code")
    private String carrierIcaoCode;
    @Column(name="carrier_number")
    private String carrierNumber;
    @Column
    private String counter;
    @Column(name="dep_apt_name_es")
    private String depAptNameEs;
    @Column(name="dep_apt_code_iata")
    private String depAptCodeIata;
    @Column(name="est_arr_date_time_lt")
    private String estArrDateTimeLt;
    @Column(name="est_dep_date_time_lt")
    private String estDepDateTimeLt;
    @Column(name="flight_airline_name_en")
    private String flightAirlineNameEn;
    @Column(name="flight_airline_name")
    private String flightAirlineName;
    @Column(name="flight_icao_code")
    private String flightIcaoCode;
    @Column(name="flight_number")
    private String flightNumber;
    @Column(name="flt_leg_seq_no")
    private String fltLegSeqNo;
    @Column(name="gate_info")
    private String gateInfo;
    @Column(name="lounge_info")
    private String loungeInfo;
    @Column(name="schd_arr_only_date_lt")
    private String schdArrOnlyDateLt;
    @Column(name="schd_arr_only_time_lt")
    private String schdArrOnlyTimeLt;
    @Column(name="source_data")
    private String sourceData;
    @Column(name="status_info")
    private String statusInfo;
    @Column(name="terminal_info")
    private String terminalInfo;
    @Column(name="arr_terminal_info")
    private String arrTerminalInfo;
    @Column(name="created_at")
    private Long createdAt;
    @Column(name="act_dep_date_time_lt")
    private String actDepDateTimeLt;
    @Column(name="schd_dep_only_date_lt")
    private String schdDepOnlyDateLt;
    @Column(name="schd_dep_only_time_lt")
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

    public String getBaggageInfo() {
        return baggageInfo;
    }

    public void setBaggageInfo(String baggageInfo) {
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

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
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

    public String getGateInfo() {
        return gateInfo;
    }

    public void setGateInfo(String gateInfo) {
        this.gateInfo = gateInfo;
    }

    public String getLoungeInfo() {
        return loungeInfo;
    }

    public void setLoungeInfo(String loungeInfo) {
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

    public String getTerminalInfo() {
        return terminalInfo;
    }

    public void setTerminalInfo(String terminalInfo) {
        this.terminalInfo = terminalInfo;
    }

    public String getArrTerminalInfo() {
        return arrTerminalInfo;
    }

    public void setArrTerminalInfo(String arrTerminalInfo) {
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

    @Override
    public String toString() {
        return "Aenaflight{" +
                "id=" + id +
                ", actArrDateTimeLt='" + actArrDateTimeLt + '\'' +
                ", aircraftNameScheduled='" + aircraftNameScheduled + '\'' +
                ", arrAptNameEs='" + arrAptNameEs + '\'' +
                ", arrAptCodeIata='" + arrAptCodeIata + '\'' +
                ", baggageInfo='" + baggageInfo + '\'' +
                ", carrierAirlineNameEn='" + carrierAirlineNameEn + '\'' +
                ", carrierIcaoCode='" + carrierIcaoCode + '\'' +
                ", carrierNumber='" + carrierNumber + '\'' +
                ", counter='" + counter + '\'' +
                ", depAptNameEs='" + depAptNameEs + '\'' +
                ", depAptCodeIata='" + depAptCodeIata + '\'' +
                ", estArrDateTimeLt='" + estArrDateTimeLt + '\'' +
                ", estDepDateTimeLt='" + estDepDateTimeLt + '\'' +
                ", flightAirlineNameEn='" + flightAirlineNameEn + '\'' +
                ", flightAirlineName='" + flightAirlineName + '\'' +
                ", flightIcaoCode='" + flightIcaoCode + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", fltLegSeqNo='" + fltLegSeqNo + '\'' +
                ", gateInfo='" + gateInfo + '\'' +
                ", loungeInfo='" + loungeInfo + '\'' +
                ", schdArrOnlyDateLt='" + schdArrOnlyDateLt + '\'' +
                ", schdArrOnlyTimeLt='" + schdArrOnlyTimeLt + '\'' +
                ", sourceData='" + sourceData + '\'' +
                ", statusInfo='" + statusInfo + '\'' +
                ", terminalInfo='" + terminalInfo + '\'' +
                ", arrTerminalInfo='" + arrTerminalInfo + '\'' +
                ", createdAt=" + createdAt +
                ", actDepDateTimeLt='" + actDepDateTimeLt + '\'' +
                ", schdDepOnlyDateLt='" + schdDepOnlyDateLt + '\'' +
                ", schdDepOnlyTimeLt='" + schdDepOnlyTimeLt + '\'' +
                '}';
    }
}
