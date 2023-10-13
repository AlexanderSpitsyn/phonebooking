INSERT INTO phone_model (id, brand, device, technology, bands2g, bands3g, bands4g, updated_with_external_at, update_with_external, deleted) VALUES (2, 'Samsung', 'Galaxy S9', null, null, null, null, null, true, false);
INSERT INTO phone_model (id, brand, device, technology, bands2g, bands3g, bands4g, updated_with_external_at, update_with_external, deleted) VALUES (3, 'Samsung', 'Galaxy S8', null, null, null, null, null, true, false);
INSERT INTO phone_model (id, brand, device, technology, bands2g, bands3g, bands4g, updated_with_external_at, update_with_external, deleted) VALUES (4, 'Motorola', 'Nexus 6', null, null, null, null, null, true, false);
INSERT INTO phone_model (id, brand, device, technology, bands2g, bands3g, bands4g, updated_with_external_at, update_with_external, deleted) VALUES (5, 'Oneplus', '9', null, null, null, null, null, true, false);
INSERT INTO phone_model (id, brand, device, technology, bands2g, bands3g, bands4g, updated_with_external_at, update_with_external, deleted) VALUES (6, 'Apple', 'iPhone 13', null, null, null, null, null, true, false);
INSERT INTO phone_model (id, brand, device, technology, bands2g, bands3g, bands4g, updated_with_external_at, update_with_external, deleted) VALUES (7, 'Apple', 'iPhone 12', null, null, null, null, null, true, false);
INSERT INTO phone_model (id, brand, device, technology, bands2g, bands3g, bands4g, updated_with_external_at, update_with_external, deleted) VALUES (8, 'Apple', 'iPhone 11', null, null, null, null, null, true, false);
INSERT INTO phone_model (id, brand, device, technology, bands2g, bands3g, bands4g, updated_with_external_at, update_with_external, deleted) VALUES (9, 'iPhone', 'X', null, null, null, null, null, true, false);
INSERT INTO phone_model (id, brand, device, technology, bands2g, bands3g, bands4g, updated_with_external_at, update_with_external, deleted) VALUES (10, 'Nokia', '3310', null, null, null, null, null, true, false);

INSERT INTO phone (id, name, phone_model_id, deleted) VALUES (1, 'Samsung Galaxy S9', 2, false);
INSERT INTO phone (id, name, phone_model_id, deleted) VALUES (2, 'Samsung Galaxy S8', 3, false);
INSERT INTO phone (id, name, phone_model_id, deleted) VALUES (3, 'Samsung Galaxy S8 with Clash of Clans installed', 3, false);
INSERT INTO phone (id, name, phone_model_id, deleted) VALUES (4, 'Motorola Nexus 6', 4, false);
INSERT INTO phone (id, name, phone_model_id, deleted) VALUES (5, 'Oneplus 9', 5, false);
INSERT INTO phone (id, name, phone_model_id, deleted) VALUES (6, 'iPhone 13', 6, false);
INSERT INTO phone (id, name, phone_model_id, deleted) VALUES (7, 'iPhone 12', 7, false);
INSERT INTO phone (id, name, phone_model_id, deleted) VALUES (8, 'iPhone 11', 8, false);
INSERT INTO phone (id, name, phone_model_id, deleted) VALUES (9, 'iPhone X', 9, false);
INSERT INTO phone (id, name, phone_model_id, deleted) VALUES (10, 'Nokia 3310', 10, false);

INSERT INTO phone_booking (id, username, phone_id, startpoint, endpoint, deleted, created_at) VALUES (1, 'admin', 10, '2023-10-14 10:00:00.000000', '2023-10-14 11:00:00.000000', false, '2023-10-14 10:05:04.188425');
INSERT INTO phone_booking (id, username, phone_id, startpoint, endpoint, deleted, created_at) VALUES (2, 'admin', 10, '2023-10-15 10:00:00.000000', '2023-10-15 15:00:00.000000', false, '2023-10-14 10:05:15.778618');
INSERT INTO phone_booking (id, username, phone_id, startpoint, endpoint, deleted, created_at) VALUES (3, 'admin', 1, '2023-11-15 10:00:00.000000', '2023-11-15 15:00:00.000000', false, '2023-10-14 10:06:01.672192');
INSERT INTO phone_booking (id, username, phone_id, startpoint, endpoint, deleted, created_at) VALUES (4, 'admin', 1, '2023-11-16 10:00:00.000000', '2023-11-16 14:00:00.000000', false, '2023-10-14 10:06:14.381183');
