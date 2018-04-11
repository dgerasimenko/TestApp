DROP TABLE IF EXISTS task_info;
DROP TABLE IF EXISTS aenaflight_source;

CREATE TABLE task_info
(
  id bigserial NOT NULL PRIMARY KEY,
  start_index bigint not null,
  end_index bigint not null,
  task_type character varying(20) not null,
  task_status character varying(20) not null,
  service_information text
);

CREATE TABLE aenaflight_source
(
  id bigserial NOT NULL PRIMARY KEY,
  adep character varying(8) NOT NULL,
  ades character varying(8) NOT NULL,
  flight_code character varying(8) NOT NULL,
  flight_number character varying(8) NOT NULL,
  carrier_code character varying(8),
  carrier_number character varying(8),
  status_info character varying(256) NOT NULL,
  schd_dep_lt timestamp without time zone NOT NULL,
  schd_arr_lt timestamp without time zone NOT NULL,
  est_dep_lt timestamp without time zone,
  est_arr_lt timestamp without time zone,
  act_dep_lt timestamp without time zone,
  act_arr_lt timestamp without time zone,
  flt_leg_seq_no integer NOT NULL,
  aircraft_name_scheduled text,
  baggage_info character varying(128),
  counter character varying(128),
  gate_info character varying(128),
  lounge_info character varying(128),
  terminal_info character varying(128),
  arr_terminal_info character varying(128),
  source_data text,
  created_at timestamp without time zone NOT NULL
);
