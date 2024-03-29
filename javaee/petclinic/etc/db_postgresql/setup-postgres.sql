-- Role: java

-- DROP ROLE java;

CREATE ROLE petclinic_jakartaee LOGIN
    PASSWORD 'petclinic_jakartaeepwd'
    SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION;

GRANT pg_monitor TO petclinic_jakartaee;
GRANT pg_read_all_settings TO petclinic_jakartaee;
GRANT pg_read_all_stats TO petclinic_jakartaee;
GRANT pg_signal_backend TO petclinic_jakartaee;
GRANT pg_stat_scan_tables TO petclinic_jakartaee;

CREATE TABLESPACE tablespace_petclinic_jakartaee
    OWNER tw
    LOCATION '/opt/postgresql/tablespace_petclinic_jakartaee';

ALTER TABLESPACE tablespace_petclinic_jakartaee
    OWNER TO petclinic_jakartaee;


-- Database: petclinic_jakartaee

-- DROP DATABASE petclinic_jakartaee;
-- CONNECTION LIMIT = -1;

CREATE DATABASE petclinic_jakartaee_liberty
    WITH OWNER = petclinic_jakartaee
    ENCODING = 'UTF8'
    TABLESPACE = tablespace_petclinic_jakartaee
    CONNECTION LIMIT = -1;

CREATE DATABASE petclinic_jakartaee_wildfly
    WITH OWNER = petclinic_jakartaee
    ENCODING = 'UTF8'
    TABLESPACE = tablespace_petclinic_jakartaee
    CONNECTION LIMIT = -1;

CREATE DATABASE petclinic_jakartaee_glassfish
    WITH OWNER = petclinic_jakartaee
    ENCODING = 'UTF8'
    TABLESPACE = tablespace_petclinic_jakartaee
    CONNECTION LIMIT = -1;

CREATE DATABASE petclinic_jakartaee_tomee
    WITH OWNER = petclinic_jakartaee
    ENCODING = 'UTF8'
    TABLESPACE = tablespace_petclinic_jakartaee
    CONNECTION LIMIT = -1;

CREATE DATABASE petclinic_jakartaee_payara
    WITH OWNER = petclinic_jakartaee
    ENCODING = 'UTF8'
    TABLESPACE = tablespace_petclinic_jakartaee
    CONNECTION LIMIT = -1;
