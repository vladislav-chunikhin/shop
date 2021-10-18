INSERT INTO amazon.categories(id, name)
VALUES ('450baeb2-2908-4998-82ac-173c5b699df4', 'toys'),
       ('19184ac3-014c-4c83-89b1-879bd5f583e2', 'furniture'),
       ('21f49af8-bca5-4d3c-b1cb-d019598c7a9b', 'car');

INSERT INTO amazon.products(id, name, price, sku, category_id)
VALUES ('4d4b6161-dd5f-410a-83cc-41b247452f3e', 'bear', 25.24, 'UGG-BB-PUR-06', '450baeb2-2908-4998-82ac-173c5b699df4'),
       ('a0c4e0c4-34b4-4617-b8de-4597b1bad553', 'red-doll', 126, 'UGG-BB-PUR-12', '450baeb2-2908-4998-82ac-173c5b699df4'),
       ('b1745ff0-34d5-408e-bb86-cf9a47a10598', 'orange-doll', 12, 'UGG-BB-PUR-14', '450baeb2-2908-4998-82ac-173c5b699df4'),
       ('ecc219bc-9408-4cab-9eda-7717edd39d4b', 'blue-doll', 11, 'UGG-BB-PUR-16', '450baeb2-2908-4998-82ac-173c5b699df4'),
       ('461d2aa0-ceaf-4f48-80d5-aa1ae6fe10c1', 'cupboard', 345.45, 'UGG-BB-PUR-45', '19184ac3-014c-4c83-89b1-879bd5f583e2');

INSERT INTO amazon.users (id, full_name)
VALUES ('f7ef3015-1215-432a-a055-34033f01de59', 'Vladislav Chunikhin'),
       ('c41ac77a-2449-43d5-818c-d00fd03668ba', 'Maxim Azarov'),
       ('485da2e8-df14-4331-88bf-9b33aea18f86', 'Zera Wood');

INSERT INTO amazon.orders(id, user_id, total_amount, created)
VALUES ('53204f67-4563-4e48-8af0-d253d15e31c9', 'f7ef3015-1215-432a-a055-34033f01de59', 36, '2021-10-17 06:48:46.427165'),
       ('cfcd8281-8c2f-4181-bd15-57d16d6cf4bd', 'f7ef3015-1215-432a-a055-34033f01de59', 38, '2021-10-17 06:48:46.427165'),
       ('5879cb93-d63a-464b-89ab-90fc94d77012', 'f7ef3015-1215-432a-a055-34033f01de59', 58, '2021-10-14 06:48:46.427165'),
       ('8718be0a-fca0-42ce-b72d-7c5e76705715', 'f7ef3015-1215-432a-a055-34033f01de59', 58, '2021-10-16 06:48:46.427165'),
       ('777acc1f-a93c-4d4f-9733-a9650b8a62f0', 'f7ef3015-1215-432a-a055-34033f01de59', 58, '2021-10-12 06:48:46.427165');

INSERT INTO amazon.order_items(id, price, quantity, product_id, order_id)
VALUES ('1d2a2330-3dee-4e20-a755-249fe2b02710', 10, 1, '4d4b6161-dd5f-410a-83cc-41b247452f3e', '53204f67-4563-4e48-8af0-d253d15e31c9'),
       ('dc645c4b-689a-415c-8134-ed061310f0c5', 12, 1, 'ecc219bc-9408-4cab-9eda-7717edd39d4b', '53204f67-4563-4e48-8af0-d253d15e31c9'),
       ('ca883e83-d2fa-4c4d-8093-36f88c94f955', 14, 1, 'a0c4e0c4-34b4-4617-b8de-4597b1bad553', '53204f67-4563-4e48-8af0-d253d15e31c9'),
       ('ba8f57cf-f7e3-4dcb-b179-c83250e50e6e', 10, 1, '4d4b6161-dd5f-410a-83cc-41b247452f3e', 'cfcd8281-8c2f-4181-bd15-57d16d6cf4bd'),
       ('8d58a257-ac71-44e1-97ac-456cd70fca1c', 12, 1, 'ecc219bc-9408-4cab-9eda-7717edd39d4b', 'cfcd8281-8c2f-4181-bd15-57d16d6cf4bd'),
       ('315ae6be-cf11-4e8a-b092-1c87bab29ca9', 16, 1, 'a0c4e0c4-34b4-4617-b8de-4597b1bad553', 'cfcd8281-8c2f-4181-bd15-57d16d6cf4bd'),
       ('7752af32-8aad-4ec8-8923-fd8462a5ad24', 10, 1, '4d4b6161-dd5f-410a-83cc-41b247452f3e', '5879cb93-d63a-464b-89ab-90fc94d77012'),
       ('4aaa4841-ebc3-472f-bae1-15d5e437fd28', 24, 1, 'ecc219bc-9408-4cab-9eda-7717edd39d4b', '5879cb93-d63a-464b-89ab-90fc94d77012'),
       ('69a1e841-ea65-470c-b4bb-240cb853e63d', 24, 1, 'a0c4e0c4-34b4-4617-b8de-4597b1bad553', '5879cb93-d63a-464b-89ab-90fc94d77012'),
       ('71173b86-2d7c-4064-93a9-e62d8f25d2bb', 10, 1, '4d4b6161-dd5f-410a-83cc-41b247452f3e', '8718be0a-fca0-42ce-b72d-7c5e76705715'),
       ('17a6daf4-9f38-4946-b5fe-8083e73e458a', 24, 1, 'ecc219bc-9408-4cab-9eda-7717edd39d4b', '8718be0a-fca0-42ce-b72d-7c5e76705715'),
       ('fdb85e3a-da00-4dd6-b523-c8ca2a233c01', 24, 1, 'a0c4e0c4-34b4-4617-b8de-4597b1bad553', '8718be0a-fca0-42ce-b72d-7c5e76705715'),
       ('4611d7e1-150a-4afd-8277-bbbd5c82a6ad', 10, 1, '4d4b6161-dd5f-410a-83cc-41b247452f3e', '777acc1f-a93c-4d4f-9733-a9650b8a62f0'),
       ('33b4d33b-bb73-4b85-896e-41d5982087d5', 24, 1, 'ecc219bc-9408-4cab-9eda-7717edd39d4b', '777acc1f-a93c-4d4f-9733-a9650b8a62f0'),
       ('2e4cc507-a335-455f-9781-de2bb75887be', 24, 1, 'a0c4e0c4-34b4-4617-b8de-4597b1bad553', '777acc1f-a93c-4d4f-9733-a9650b8a62f0');