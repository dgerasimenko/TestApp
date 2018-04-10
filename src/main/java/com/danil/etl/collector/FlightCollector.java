package com.danil.etl.collector;

import com.danil.etl.entity.Flight;
import com.danil.etl.entity.FlightUniqueKey;
import com.danil.etl.task.TaskServiceInfo;
import com.danil.etl.utils.Stack;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightCollector {
    private static final char SEPARATOR = ',';

    public TaskServiceInfo orderBy(List<Flight> flights) {

        final Map<FlightUniqueKey, Flight> sourceData2oneRecord = new HashMap<>();
        final List<Long> idsToBeDeleted = new ArrayList<>(flights.size() / 2);
        for (Flight tmpFlight : flights) {
            final FlightUniqueKey key = new FlightUniqueKey(tmpFlight);
            final Flight flight = sourceData2oneRecord.get(key);

            if (flight == null) {
                sourceData2oneRecord.put(key, tmpFlight);
            } else {
                idsToBeDeleted.add(tmpFlight.getId());
                mergeFlight(tmpFlight, flight);
            }

        }
        return new TaskServiceInfo(sourceData2oneRecord, idsToBeDeleted);
    }


    private void mergeFlight(Flight src, Flight res) {

        res.setActArrDateTimeLt(src.getActArrDateTimeLt());
        res.setArrAptCodeIata(src.getArrAptCodeIata());
        res.setAircraftNameScheduled(src.getAircraftNameScheduled());
        res.setArrAptNameEs(src.getArrAptNameEs());
        res.setCarrierAirlineNameEn(src.getCarrierAirlineNameEn());
        res.setCarrierIcaoCode(src.getCarrierIcaoCode());
        res.setCarrierNumber(src.getCarrierNumber());
        res.setDepAptNameEs(src.getDepAptNameEs());
        res.setDepAptNameEs(src.getDepAptNameEs());
        res.setDepAptCodeIata(src.getDepAptCodeIata());
        res.setEstArrDateTimeLt(src.getEstArrDateTimeLt());
        res.setEstDepDateTimeLt(src.getEstDepDateTimeLt());
        res.setFlightAirlineNameEn(src.getFlightAirlineNameEn());
        res.setFlightAirlineName(src.getFlightAirlineName());
        res.setFlightIcaoCode(src.getFlightIcaoCode());
        res.setFlightNumber(src.getFlightNumber());
        res.setFltLegSeqNo(src.getFltLegSeqNo());
        res.setSchdArrOnlyDateLt(src.getSchdArrOnlyDateLt());
        res.setSchdArrOnlyTimeLt(src.getSchdArrOnlyTimeLt());
        res.setSourceData(src.getSourceData());
        res.setStatusInfo(src.getStatusInfo());
        res.setActDepDateTimeLt(src.getActDepDateTimeLt());
        res.setSchdDepOnlyDateLt(src.getSchdDepOnlyDateLt());
        res.setSchdDepOnlyTimeLt(src.getSchdDepOnlyTimeLt());

        if (StringUtils.isNotEmpty(src.getBaggageInfo())) {
            final Stack<String> baggageInfoStack = new Stack<>();
            if (StringUtils.isNotEmpty(res.getBaggageInfo())) {
                baggageInfoStack.pushAll(StringUtils.split(res.getBaggageInfo(), SEPARATOR));
            }
            baggageInfoStack.pushAll(StringUtils.split(src.getBaggageInfo(), SEPARATOR));
            res.setBaggageInfo(baggageInfoStack.toString());
        }
        if (StringUtils.isNotEmpty(src.getCounter())) {
            final Stack<String> counterStack = new Stack<>();
            if (StringUtils.isNotEmpty(res.getCounter())) {
                counterStack.pushAll(StringUtils.split(res.getCounter(), SEPARATOR));
            }
            counterStack.pushAll(StringUtils.split(src.getCounter(), SEPARATOR));
            res.setCounter(counterStack.toString());
        }
        if (StringUtils.isNotEmpty(src.getGateInfo())) {
            final Stack<String> gateInfoStack = new Stack<>();
            if (StringUtils.isNotEmpty(res.getGateInfo())) {
                gateInfoStack.pushAll(StringUtils.split(res.getGateInfo(), SEPARATOR));
            }
            gateInfoStack.pushAll(StringUtils.split(src.getGateInfo(), SEPARATOR));
            res.setGateInfo(gateInfoStack.toString());
        }
        if (StringUtils.isNotEmpty(src.getLoungeInfo())) {
            final Stack<String> loungeInfoStack = new Stack<>();
            if (StringUtils.isNotEmpty(res.getLoungeInfo())) {
                loungeInfoStack.pushAll(StringUtils.split(res.getLoungeInfo(), SEPARATOR));
            }
            loungeInfoStack.pushAll(StringUtils.split(src.getLoungeInfo(), SEPARATOR));
            res.setLoungeInfo(loungeInfoStack.toString());
        }
        if (StringUtils.isNotEmpty(src.getTerminalInfo())) {
            final Stack<String> terminalInfoStack = new Stack<>();
            if (StringUtils.isNotEmpty(res.getTerminalInfo())) {
                terminalInfoStack.pushAll(StringUtils.split(res.getTerminalInfo(), SEPARATOR));
            }
            terminalInfoStack.pushAll(StringUtils.split(src.getTerminalInfo(), SEPARATOR));
            res.setTerminalInfo(terminalInfoStack.toString());
        }
        if (StringUtils.isNotEmpty(src.getArrTerminalInfo())) {
            final Stack<String> arrTerminalInfoStack = new Stack<>();
            if (StringUtils.isNotEmpty(res.getArrTerminalInfo())) {
                arrTerminalInfoStack.pushAll(StringUtils.split(res.getArrTerminalInfo(), SEPARATOR));
            }
            arrTerminalInfoStack.pushAll(StringUtils.split(src.getArrTerminalInfo(), SEPARATOR));
            res.setArrTerminalInfo(arrTerminalInfoStack.toString());
        }

        res.setCreatedAt(src.getCreatedAt());
    }
}
