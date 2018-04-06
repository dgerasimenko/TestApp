ALTER TABLE aenaflight_2017_01 ALTER COLUMN baggage_info TYPE text;
ALTER TABLE aenaflight_2017_01 ALTER COLUMN counter TYPE text;
ALTER TABLE aenaflight_2017_01 ALTER COLUMN gate_info TYPE text;
ALTER TABLE aenaflight_2017_01 ALTER COLUMN lounge_info TYPE text;
ALTER TABLE aenaflight_2017_01 ALTER COLUMN terminal_info TYPE text;
ALTER TABLE aenaflight_2017_01 ALTER COLUMN arr_terminal_info TYPE text;

DROP TABLE  IF EXISTS destination_data;
DROP TABLE  IF EXISTS aenaflight_tmp;

CREATE TABLE aenaflight_tmp
(
    id SERIAL NOT NULL,
    start_index bigint NOT NULL,
    end_index bigint NOT NULL,
    act_arr_date_time_lt character varying(64) COLLATE pg_catalog."default",
    aircraft_name_scheduled text COLLATE pg_catalog."default",
    arr_apt_name_es character varying(128) COLLATE pg_catalog."default",
    arr_apt_code_iata character varying(8) COLLATE pg_catalog."default",
    baggage_info text COLLATE pg_catalog."default",
    carrier_airline_name_en character varying(128) COLLATE pg_catalog."default",
    carrier_icao_code character varying(8) COLLATE pg_catalog."default",
    carrier_number character varying(8) COLLATE pg_catalog."default",
    counter text COLLATE pg_catalog."default",
    dep_apt_name_es character varying(128) COLLATE pg_catalog."default",
    dep_apt_code_iata character varying(8) COLLATE pg_catalog."default",
    est_arr_date_time_lt character varying(64) COLLATE pg_catalog."default",
    est_dep_date_time_lt character varying(64) COLLATE pg_catalog."default",
    flight_airline_name_en character varying(128) COLLATE pg_catalog."default",
    flight_airline_name character varying(128) COLLATE pg_catalog."default",
    flight_icao_code character varying(8) COLLATE pg_catalog."default",
    flight_number character varying(8) COLLATE pg_catalog."default",
    flt_leg_seq_no character varying(8) COLLATE pg_catalog."default",
    gate_info text COLLATE pg_catalog."default",
    lounge_info text COLLATE pg_catalog."default",
    schd_arr_only_date_lt character varying(32) COLLATE pg_catalog."default",
    schd_arr_only_time_lt character varying(32) COLLATE pg_catalog."default",
    source_data text COLLATE pg_catalog."default",
    status_info character varying(128) COLLATE pg_catalog."default",
    terminal_info text COLLATE pg_catalog."default",
    arr_terminal_info text COLLATE pg_catalog."default",
    created_at bigint,
    act_dep_date_time_lt character varying(64) COLLATE pg_catalog."default",
    schd_dep_only_date_lt character varying(32) COLLATE pg_catalog."default",
    schd_dep_only_time_lt character varying(32) COLLATE pg_catalog."default",
    CONSTRAINT aenaflight_tmp_pkey PRIMARY KEY (id)
);
CREATE TABLE destination_data
(
    id SERIAL NOT NULL,
    adep character varying(8) COLLATE pg_catalog."default" NOT NULL,
    ades character varying(8) COLLATE pg_catalog."default" NOT NULL,
    flight_code character varying(8) COLLATE pg_catalog."default" NOT NULL,
    flight_number character varying(8) COLLATE pg_catalog."default" NOT NULL,
    carrier_code character varying(8) COLLATE pg_catalog."default",
    carrier_number character varying(8) COLLATE pg_catalog."default",
    status_info character varying(256) COLLATE pg_catalog."default" NOT NULL,
    schd_dep_lt timestamp without time zone NOT NULL,
    schd_arr_lt timestamp without time zone NOT NULL,
    est_dep_lt timestamp without time zone,
    est_arr_lt timestamp without time zone,
    act_dep_lt timestamp without time zone,
    act_arr_lt timestamp without time zone,
    flt_leg_seq_no integer NOT NULL,
    aircraft_name_scheduled text COLLATE pg_catalog."default",
    baggage_info text COLLATE pg_catalog."default",
    counter text COLLATE pg_catalog."default",
    gate_info text COLLATE pg_catalog."default",
    lounge_info text COLLATE pg_catalog."default",
    terminal_info text COLLATE pg_catalog."default",
    arr_terminal_info text COLLATE pg_catalog."default",
    source_data text COLLATE pg_catalog."default",
    created_at timestamp without time zone NOT NULL,
    CONSTRAINT destination_data_pkey PRIMARY KEY (id)
);