--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5 (Ubuntu 14.5-2.pgdg22.04+2)
-- Dumped by pg_dump version 15.0 (Ubuntu 15.0-1.pgdg22.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
-- SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: owner; Type: TABLE DATA; Schema: public; Owner: -
--

SET SESSION AUTHORIZATION DEFAULT;

VACUUM ;

BEGIN ;

ALTER TABLE "public"."owner" DISABLE TRIGGER ALL;

INSERT INTO "public"."owner" ("id", "address", "address_info", "city", "email", "first_name", "housenumber", "lastname", "phonenumber", "searchindex", "uuid", "zipcode") VALUES (1, 'Laerheidestr.', 'Appartment C 011', 'Bochum', 'thomas.woehlke@gmail.com', 'Thomas', '8', 'Wöhlke', '+4923452007953', NULL, '598b2050-3e40-4d52-ba3d-a651c49d9613', '44799');
INSERT INTO "public"."owner" ("id", "address", "address_info", "city", "email", "first_name", "housenumber", "lastname", "phonenumber", "searchindex", "uuid", "zipcode") VALUES (3, 'Hill Drive', 'Auf dem Schrottplatz von Titus Jonas', 'Rocky Beach', 'thomas.woehlke@rub.de', 'Justus', '128', 'Jonas', '+4923452007953', NULL, 'ad4b4bdf-0406-4642-bb59-c1800e73378b', '99777');


ALTER TABLE "public"."owner" ENABLE TRIGGER ALL;

--
-- Data for Name: owner_pet_pettype; Type: TABLE DATA; Schema: public; Owner: -
--

ALTER TABLE "public"."owner_pet_pettype" DISABLE TRIGGER ALL;

INSERT INTO "public"."owner_pet_pettype" ("id", "name", "searchindex", "uuid") VALUES (1, 'Mouse', 'Mouse ', '3ba50441-d02c-4320-bd92-ef751ffe488a');
INSERT INTO "public"."owner_pet_pettype" ("id", "name", "searchindex", "uuid") VALUES (2, 'Dog', 'Dog ', '3698d5f5-2ee8-474e-9cba-8ab6fb995d25');
INSERT INTO "public"."owner_pet_pettype" ("id", "name", "searchindex", "uuid") VALUES (4, 'Guinea pig', 'Guinea pig ', '065e13e3-e0b3-46ce-95d2-4041f27e14d1');
INSERT INTO "public"."owner_pet_pettype" ("id", "name", "searchindex", "uuid") VALUES (5, 'Whale', 'Whale ', '59ce000a-e5c7-48c7-afeb-8e1e6253547a');


ALTER TABLE "public"."owner_pet_pettype" ENABLE TRIGGER ALL;

--
-- Data for Name: owner_pet; Type: TABLE DATA; Schema: public; Owner: -
--

ALTER TABLE "public"."owner_pet" DISABLE TRIGGER ALL;

INSERT INTO "public"."owner_pet" ("id", "birth_date", "name", "searchindex", "uuid", "owner_id", "owner_pet_pettype_id") VALUES (1, '2021-01-03', 'Waldmar', NULL, '3c103d8f-ce4b-464a-a029-e9de9fd97142', 1, 4);
INSERT INTO "public"."owner_pet" ("id", "birth_date", "name", "searchindex", "uuid", "owner_id", "owner_pet_pettype_id") VALUES (2, '2022-03-14', 'Roger', NULL, '4316205b-2aee-40c0-a9a1-6cb77056bc4b', 1, 1);


ALTER TABLE "public"."owner_pet" ENABLE TRIGGER ALL;

--
-- Data for Name: owner_pet_visit; Type: TABLE DATA; Schema: public; Owner: -
--

ALTER TABLE "public"."owner_pet_visit" DISABLE TRIGGER ALL;

INSERT INTO "public"."owner_pet_visit" ("id", "visit_date", "description", "searchindex", "uuid", "owner_pet_id") VALUES (1, '2022-09-12', 'Routine KO', NULL, '4095767a-3102-46a4-a964-443f45116099', 2);
INSERT INTO "public"."owner_pet_visit" ("id", "visit_date", "description", "searchindex", "uuid", "owner_pet_id") VALUES (2, '2022-11-07', 'Routine ASDQWER JOLO', NULL, '578e70e1-e513-4fb8-a0c3-a1511c89f7d9', 1);


ALTER TABLE "public"."owner_pet_visit" ENABLE TRIGGER ALL;

--
-- Data for Name: specialty; Type: TABLE DATA; Schema: public; Owner: -
--

ALTER TABLE "public"."specialty" DISABLE TRIGGER ALL;

INSERT INTO "public"."specialty" ("id", "name", "searchindex", "uuid") VALUES (1, 'Neurosurgeon', 'Neurosurgeon ', '42f5aaad-c2e3-4f19-a9cf-67cc66ff3043');
INSERT INTO "public"."specialty" ("id", "name", "searchindex", "uuid") VALUES (2, 'Cardiologist', 'Cardiologist ', 'c35f7862-f9b8-43cb-902f-673220f08c27');
INSERT INTO "public"."specialty" ("id", "name", "searchindex", "uuid") VALUES (3, 'Oncologist', 'Oncologist ', '903c0e18-cf5f-4cff-95de-21dc1f750f9b');
INSERT INTO "public"."specialty" ("id", "name", "searchindex", "uuid") VALUES (4, 'Anesthetist', 'Anesthetist ', 'cf2ab994-8c05-4689-abfa-b88f1cfaceca');


ALTER TABLE "public"."specialty" ENABLE TRIGGER ALL;

--
-- Data for Name: vet; Type: TABLE DATA; Schema: public; Owner: -
--

ALTER TABLE "public"."vet" DISABLE TRIGGER ALL;

INSERT INTO "public"."vet" ("id", "first_name", "lastname", "searchindex", "uuid") VALUES (1, 'Marie', 'Curie', 'Marie Curie Neurosurgeon Oncologist Anesthetist Cardiologist ', '2c7dae8a-1246-489e-956e-ec331c0bcde5');
INSERT INTO "public"."vet" ("id", "first_name", "lastname", "searchindex", "uuid") VALUES (2, 'Walter', 'von der Vogelweide', 'Walter von der Vogelweide Anesthetist ', 'ea05c3d7-2629-4dd5-a590-50371c7e849e');


ALTER TABLE "public"."vet" ENABLE TRIGGER ALL;

--
-- Data for Name: vet_specialties; Type: TABLE DATA; Schema: public; Owner: -
--

ALTER TABLE "public"."vet_specialties" DISABLE TRIGGER ALL;

INSERT INTO "public"."vet_specialties" ("vet_id", "specialty_id") VALUES (1, 1);
INSERT INTO "public"."vet_specialties" ("vet_id", "specialty_id") VALUES (1, 2);
INSERT INTO "public"."vet_specialties" ("vet_id", "specialty_id") VALUES (1, 3);
INSERT INTO "public"."vet_specialties" ("vet_id", "specialty_id") VALUES (1, 4);
INSERT INTO "public"."vet_specialties" ("vet_id", "specialty_id") VALUES (2, 4);


ALTER TABLE "public"."vet_specialties" ENABLE TRIGGER ALL;

--
-- Name: owner_pet_pettype_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."owner_pet_pettype_seq"', 100, true);


--
-- Name: owner_pet_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."owner_pet_seq"', 50, true);


--
-- Name: owner_pet_visit_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."owner_pet_visit_seq"', 50, true);


--
-- Name: owner_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."owner_seq"', 50, true);


--
-- Name: specialty_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."specialty_seq"', 50, true);


--
-- Name: vet_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"public"."vet_seq"', 50, true);


COMMIT ;

--
-- PostgreSQL database dump complete
--

