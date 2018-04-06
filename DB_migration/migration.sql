ALTER TABLE aenaflight_2017_01
  ALTER COLUMN baggage_info TYPE TEXT;
ALTER TABLE aenaflight_2017_01
  ALTER COLUMN counter TYPE TEXT;
ALTER TABLE aenaflight_2017_01
  ALTER COLUMN gate_info TYPE TEXT;
ALTER TABLE aenaflight_2017_01
  ALTER COLUMN lounge_info TYPE TEXT;
ALTER TABLE aenaflight_2017_01
  ALTER COLUMN terminal_info TYPE TEXT;
ALTER TABLE aenaflight_2017_01
  ALTER COLUMN arr_terminal_info TYPE TEXT;

DROP TABLE IF EXISTS destination_data;
DROP TABLE IF EXISTS aenaflight_tmp;

CREATE TABLE aenaflight_tmp
(
  id                      SERIAL NOT NULL,
  start_index             BIGINT NOT NULL,
  end_index               BIGINT NOT NULL,
  act_arr_date_time_lt    CHARACTER VARYING(64) COLLATE pg_catalog."default",
  aircraft_name_scheduled TEXT COLLATE pg_catalog."default",
  arr_apt_name_es         CHARACTER VARYING(128) COLLATE pg_catalog."default",
  arr_apt_code_iata       CHARACTER VARYING(8) COLLATE pg_catalog."default",
  baggage_info            TEXT COLLATE pg_catalog."default",
  carrier_airline_name_en CHARACTER VARYING(128) COLLATE pg_catalog."default",
  carrier_icao_code       CHARACTER VARYING(8) COLLATE pg_catalog."default",
  carrier_number          CHARACTER VARYING(8) COLLATE pg_catalog."default",
  counter                 TEXT COLLATE pg_catalog."default",
  dep_apt_name_es         CHARACTER VARYING(128) COLLATE pg_catalog."default",
  dep_apt_code_iata       CHARACTER VARYING(8) COLLATE pg_catalog."default",
  est_arr_date_time_lt    CHARACTER VARYING(64) COLLATE pg_catalog."default",
  est_dep_date_time_lt    CHARACTER VARYING(64) COLLATE pg_catalog."default",
  flight_airline_name_en  CHARACTER VARYING(128) COLLATE pg_catalog."default",
  flight_airline_name     CHARACTER VARYING(128) COLLATE pg_catalog."default",
  flight_icao_code        CHARACTER VARYING(8) COLLATE pg_catalog."default",
  flight_number           CHARACTER VARYING(8) COLLATE pg_catalog."default",
  flt_leg_seq_no          CHARACTER VARYING(8) COLLATE pg_catalog."default",
  gate_info               TEXT COLLATE pg_catalog."default",
  lounge_info             TEXT COLLATE pg_catalog."default",
  schd_arr_only_date_lt   CHARACTER VARYING(32) COLLATE pg_catalog."default",
  schd_arr_only_time_lt   CHARACTER VARYING(32) COLLATE pg_catalog."default",
  source_data             TEXT COLLATE pg_catalog."default",
  status_info             CHARACTER VARYING(128) COLLATE pg_catalog."default",
  terminal_info           TEXT COLLATE pg_catalog."default",
  arr_terminal_info       TEXT COLLATE pg_catalog."default",
  created_at              BIGINT,
  act_dep_date_time_lt    CHARACTER VARYING(64) COLLATE pg_catalog."default",
  schd_dep_only_date_lt   CHARACTER VARYING(32) COLLATE pg_catalog."default",
  schd_dep_only_time_lt   CHARACTER VARYING(32) COLLATE pg_catalog."default",
  CONSTRAINT aenaflight_tmp_pkey PRIMARY KEY (id)
);
CREATE TABLE destination_data
(
  id                      SERIAL                                              NOT NULL,
  adep                    CHARACTER VARYING(8) COLLATE pg_catalog."default"   NOT NULL,
  ades                    CHARACTER VARYING(8) COLLATE pg_catalog."default"   NOT NULL,
  flight_code             CHARACTER VARYING(8) COLLATE pg_catalog."default"   NOT NULL,
  flight_number           CHARACTER VARYING(8) COLLATE pg_catalog."default"   NOT NULL,
  carrier_code            CHARACTER VARYING(8) COLLATE pg_catalog."default",
  carrier_number          CHARACTER VARYING(8) COLLATE pg_catalog."default",
  status_info             CHARACTER VARYING(256) COLLATE pg_catalog."default" NOT NULL,
  schd_dep_lt             TIMESTAMP WITHOUT TIME ZONE                         NOT NULL,
  schd_arr_lt             TIMESTAMP WITHOUT TIME ZONE                         NOT NULL,
  est_dep_lt              TIMESTAMP WITHOUT TIME ZONE,
  est_arr_lt              TIMESTAMP WITHOUT TIME ZONE,
  act_dep_lt              TIMESTAMP WITHOUT TIME ZONE,
  act_arr_lt              TIMESTAMP WITHOUT TIME ZONE,
  flt_leg_seq_no          INTEGER                                             NOT NULL,
  aircraft_name_scheduled TEXT COLLATE pg_catalog."default",
  baggage_info            TEXT COLLATE pg_catalog."default",
  counter                 TEXT COLLATE pg_catalog."default",
  gate_info               TEXT COLLATE pg_catalog."default",
  lounge_info             TEXT COLLATE pg_catalog."default",
  terminal_info           TEXT COLLATE pg_catalog."default",
  arr_terminal_info       TEXT COLLATE pg_catalog."default",
  source_data             TEXT COLLATE pg_catalog."default",
  created_at              TIMESTAMP WITHOUT TIME ZONE                         NOT NULL,
  CONSTRAINT destination_data_pkey PRIMARY KEY (id)
);