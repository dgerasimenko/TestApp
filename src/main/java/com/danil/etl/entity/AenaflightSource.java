package com.danil.etl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "aenaflight_source")
public class AenaflightSource {
    @Id
    @Column
    private Long id;
    @Column
    private String adep;
    @Column
    private String ades;
    @Column(name = "flight_code")
    private String flightCode;
    @Column(name = "flight_number")
    private String flightNumber;
    @Column(name = "carrier_code")
    private String carrierCode;
    @Column(name = "carrier_number")
    private String carrierNumber;
    @Column(name = "status_info")
    private String statusInfo;
    @Column(name = "schd_dep_lt")
    private LocalDateTime schdDepLt;
    @Column(name = "schd_arr_lt")
    private LocalDateTime schdArrLt;
    @Column(name = "est_dep_lt")
    private LocalDateTime estDepLt;
    @Column(name = "est_arr_lt")
    private LocalDateTime estArrLt;
    @Column(name = "act_dep_lt")
    private LocalDateTime actDepLt;
    @Column(name = "act_arr_lt")
    private LocalDateTime actArrLt;
    @Column(name = "flt_leg_seq_no")
    private Integer fltLegSeqNo;
    @Column(name = "aircraft_name_scheduled")
    private String aircraftNameScheduled;
    @Column(name = "baggage_info")
    private String baggageInfo;
    @Column
    private String counter;
    @Column(name = "gate_info")
    private String gateInfo;
    @Column(name = "lounge_info")
    private String loungeInfo;
    @Column(name = "terminal_info")
    private String terminalInfo;
    @Column(name = "arr_terminal_info")
    private String arrTerminalInfo;
    @Column(name = "source_data")
    private String sourceData;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdep() {
        return adep;
    }

    public void setAdep(String adep) {
        this.adep = adep;
    }

    public String getAdes() {
        return ades;
    }

    public void setAdes(String ades) {
        this.ades = ades;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getCarrierNumber() {
        return carrierNumber;
    }

    public void setCarrierNumber(String carrierNumber) {
        this.carrierNumber = carrierNumber;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public LocalDateTime getSchdDepLt() {
        return schdDepLt;
    }

    public void setSchdDepLt(LocalDateTime schdDepLt) {
        this.schdDepLt = schdDepLt;
    }

    public LocalDateTime getSchdArrLt() {
        return schdArrLt;
    }

    public void setSchdArrLt(LocalDateTime schdArrLt) {
        this.schdArrLt = schdArrLt;
    }

    public LocalDateTime getEstDepLt() {
        return estDepLt;
    }

    public void setEstDepLt(LocalDateTime estDepLt) {
        this.estDepLt = estDepLt;
    }

    public LocalDateTime getEstArrLt() {
        return estArrLt;
    }

    public void setEstArrLt(LocalDateTime estArrLt) {
        this.estArrLt = estArrLt;
    }

    public LocalDateTime getActDepLt() {
        return actDepLt;
    }

    public void setActDepLt(LocalDateTime actDepLt) {
        this.actDepLt = actDepLt;
    }

    public LocalDateTime getActArrLt() {
        return actArrLt;
    }

    public void setActArrLt(LocalDateTime actArrLt) {
        this.actArrLt = actArrLt;
    }

    public Integer getFltLegSeqNo() {
        return fltLegSeqNo;
    }

    public void setFltLegSeqNo(Integer fltLegSeqNo) {
        this.fltLegSeqNo = fltLegSeqNo;
    }

    public String getAircraftNameScheduled() {
        return aircraftNameScheduled;
    }

    public void setAircraftNameScheduled(String aircraftNameScheduled) {
        this.aircraftNameScheduled = aircraftNameScheduled;
    }

    public String getBaggageInfo() {
        return baggageInfo;
    }

    public void setBaggageInfo(String baggageInfo) {
        this.baggageInfo = baggageInfo;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
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

    public String getSourceData() {
        return sourceData;
    }

    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AenaflightSource{" +
                "id=" + id +
                ", adep='" + adep + '\'' +
                ", ades='" + ades + '\'' +
                ", flightCode='" + flightCode + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", carrierCode='" + carrierCode + '\'' +
                ", carrierNumber='" + carrierNumber + '\'' +
                ", statusInfo='" + statusInfo + '\'' +
                ", schdDepLt=" + schdDepLt +
                ", schdArrLt=" + schdArrLt +
                ", estDepLt=" + estDepLt +
                ", estArrLt=" + estArrLt +
                ", actDepLt=" + actDepLt +
                ", actArrLt=" + actArrLt +
                ", fltLegSeqNo=" + fltLegSeqNo +
                ", aircraftNameScheduled='" + aircraftNameScheduled + '\'' +
                ", baggageInfo='" + baggageInfo + '\'' +
                ", counter='" + counter + '\'' +
                ", gateInfo='" + gateInfo + '\'' +
                ", loungeInfo='" + loungeInfo + '\'' +
                ", terminalInfo='" + terminalInfo + '\'' +
                ", arrTerminalInfo='" + arrTerminalInfo + '\'' +
                ", sourceData='" + sourceData + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
