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

INSERT INTO amazon.orders(id, user_id)
VALUES ('53204f67-4563-4e48-8af0-d253d15e31c9', 'f7ef3015-1215-432a-a055-34033f01de59');

INSERT INTO amazon.order_items(id, price, quantity, product_id, order_id)
VALUES ('1d2a2330-3dee-4e20-a755-249fe2b02710', 126.2, 5, '4d4b6161-dd5f-410a-83cc-41b247452f3e', '53204f67-4563-4e48-8af0-d253d15e31c9'),
       ('dc645c4b-689a-415c-8134-ed061310f0c5', 66, 6, 'ecc219bc-9408-4cab-9eda-7717edd39d4b', '53204f67-4563-4e48-8af0-d253d15e31c9');