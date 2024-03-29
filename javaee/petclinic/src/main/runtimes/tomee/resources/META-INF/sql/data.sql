--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4 (Ubuntu 14.4-1.pgdg22.04+1)
-- Dumped by pg_dump version 14.4 (Ubuntu 14.4-1.pgdg22.04+1)

--
-- Data for Name: owner; Type: TABLE DATA; Schema: public; Owner: petclinic_jakartaee
--

INSERT INTO public.owner VALUES (23, 'Glatzer Str.', NULL, 'Berlin', 'thomas.woehlke@gmail.com', 'Thomas', '5a', 'Wöhlke', '+493052007953', 'Thomas W hlke Glatzer Str 5a 10247 Berlin  493052007953 thomas woehlke gmail com ', '6ee91567-a8eb-4c53-91fe-c4bd88ca3f11', '10247');
INSERT INTO public.owner VALUES (24, 'Hill Drive', 'Auf dem Schrottplatz von Titus Jonas', 'Rocky Beach', 'thomas.woehlke@rub.de', 'Justus', '128', 'Jonas', '+4923452007953', 'Auf dem Schrottplatz von Titus Jonas Justus Jonas Hill Drive 128 99777 Rocky Beach  4923452007953 thomas woehlke rub de ', '0349af29-16f3-4ffc-b3ab-e23daea5a90b', '99777');


--
-- Data for Name: owner_pet_type; Type: TABLE DATA; Schema: public; Owner: petclinic_jakartaee
--

INSERT INTO public.owner_pet_type VALUES (1, 'Dog', 'Dog ', 'f0d464c6-2149-4c6c-b477-bec0766b8052');
INSERT INTO public.owner_pet_type VALUES (2, 'Cat', 'Cat ', 'b88b6449-b44b-4cc4-b30a-311a3d1c3361');
INSERT INTO public.owner_pet_type VALUES (3, 'Mouse', 'Mouse ', '052ccd24-43e4-4181-8b32-ad3244b93199');
INSERT INTO public.owner_pet_type VALUES (4, 'Hamster', 'Hamster ', 'ee0149cf-5efb-420c-8e81-93143b97dfea');
INSERT INTO public.owner_pet_type VALUES (5, 'Rabbit', 'Rabbit ', '3f593756-7350-4580-8300-1b1e943dc026');
INSERT INTO public.owner_pet_type VALUES (6, 'Fish', 'Fish ', '4fd9a619-d9a5-43c4-b725-ca17ab60159f');
INSERT INTO public.owner_pet_type VALUES (8, 'Guinea pig', 'Guinea pig ', '3389ac8c-a541-4565-9577-3e93bdfc3ad1');
INSERT INTO public.owner_pet_type VALUES (9, 'Pigeon', 'Pigeon ', '91cbd075-d8cc-4b92-a5a6-0db340ddf019');
INSERT INTO public.owner_pet_type VALUES (10, 'Snake', 'Snake ', 'a23a4023-0c57-44a4-bc16-7949460b980e');
INSERT INTO public.owner_pet_type VALUES (11, 'Spider', 'Spider ', '070aacf3-08ca-4820-a6ee-bd011dff4e21');
INSERT INTO public.owner_pet_type VALUES (7, 'Pony', 'Pony ', 'd76b2627-af8d-4f7f-88e7-0fcee289f3ce');
INSERT INTO public.owner_pet_type VALUES (12, 'Donkey', 'Donkey ', '3417d73c-c8a3-4034-b37b-c31ebcd4cb49');


--
-- Data for Name: owner_pet; Type: TABLE DATA; Schema: public; Owner: petclinic_jakartaee
--

INSERT INTO public.owner_pet VALUES (25, '2018-02-10', 'Roger', 'Roger ', '2fa7e4f8-4009-48eb-b175-54a3bd5d9e3a', 24, 5);
INSERT INTO public.owner_pet VALUES (26, '2021-08-15', 'Jerry', 'Jerry ', '7cf6da17-52f0-401e-a8f8-8d7f3d63ea16', 24, 3);
INSERT INTO public.owner_pet VALUES (27, '2020-06-10', 'Tom', 'Tom ', 'a553f6aa-ac28-440d-8f28-981cddafec86', 24, 2);
INSERT INTO public.owner_pet VALUES (28, '2020-06-14', 'Python', 'Python ', '0b8c7059-063f-4c55-bd22-0c8f9bc3b9c5', 23, 10);
INSERT INTO public.owner_pet VALUES (29, '2022-02-18', 'Tarantula', 'Tarantula ', 'faf15ccf-383a-4b90-9155-e41e6107d658', 23, 11);


--
-- Data for Name: owner_pet_visit; Type: TABLE DATA; Schema: public; Owner: petclinic_jakartaee
--

INSERT INTO public.owner_pet_visit VALUES (30, '2022-08-09', 'Routine', '2022 08 08T22 00 00Z Routine ', 'a9affdd1-7eeb-4baa-b52d-46c3711396c4', 25);


--
-- Data for Name: specialty; Type: TABLE DATA; Schema: public; Owner: petclinic_jakartaee
--

INSERT INTO public.specialty VALUES (13, 'Cardiologist', 'Cardiologist ', 'b1f1c1ec-c823-4ff2-b8de-9905ce802041');
INSERT INTO public.specialty VALUES (14, 'Radiologist', 'Radiologist ', 'dfe23ba1-9fc7-428a-aae0-99cd69887286');
INSERT INTO public.specialty VALUES (15, 'Rescue Doctor', 'Rescue Doctor ', '2ff652fb-df03-48af-aa77-f7b47e155604');
INSERT INTO public.specialty VALUES (16, 'Surgeon', 'Surgeon ', '4247ab00-93f8-4593-9c8e-bea2b5f56d2f');
INSERT INTO public.specialty VALUES (17, 'Neurosurgeon', 'Neurosurgeon ', 'e6643c99-7e95-410c-9184-a3b0c1edb597');
INSERT INTO public.specialty VALUES (18, 'Anesthetist', 'Anesthetist ', 'd021c584-feda-4f79-80e6-c2e22cfdc880');
INSERT INTO public.specialty VALUES (19, 'Shaman', 'Shaman ', 'd9814cff-01e7-4f3d-b1b1-46b6df125641');


--
-- Data for Name: vet; Type: TABLE DATA; Schema: public; Owner: petclinic_jakartaee
--

INSERT INTO public.vet VALUES (21, 'Marie', 'Curie', 'Marie Curie Neurosurgeon Rescue Doctor Shaman Cardiologist Radiologist Anesthetist Surgeon ', '8f05d625-6bcb-4762-a500-46df2f8ebdb5');
INSERT INTO public.vet VALUES (22, 'Walther', 'von der Vogelweide', 'Walther von der Vogelweide Shaman ', '7c3620bd-8c04-41b3-818c-db32434f8fea');


--
-- Data for Name: vet_specialties; Type: TABLE DATA; Schema: public; Owner: petclinic_jakartaee
--

INSERT INTO public.vet_specialties VALUES (21, 17);
INSERT INTO public.vet_specialties VALUES (21, 15);
INSERT INTO public.vet_specialties VALUES (21, 19);
INSERT INTO public.vet_specialties VALUES (21, 13);
INSERT INTO public.vet_specialties VALUES (21, 14);
INSERT INTO public.vet_specialties VALUES (21, 18);
INSERT INTO public.vet_specialties VALUES (21, 16);
INSERT INTO public.vet_specialties VALUES (22, 19);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: petclinic_jakartaee
--

SELECT pg_catalog.setval('public.hibernate_sequence', 30, true);


--
-- PostgreSQL database dump complete
--

