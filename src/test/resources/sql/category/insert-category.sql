INSERT INTO amazon.categories(id, name)
VALUES ('450baeb2-2908-4998-82ac-173c5b699df4', 'cars'),
       ('19184ac3-014c-4c83-89b1-879bd5f583e2', 'furniture'),
       ('21f49af8-bca5-4d3c-b1cb-d019598c7a9b', 'toys');

INSERT INTO amazon.products(id, name, price, sku, category_id)
VALUES ('4d4b6161-dd5f-410a-83cc-41b247452f3e', 'bear', 25.24, 'UGG-BB-PUR-06', '21f49af8-bca5-4d3c-b1cb-d019598c7a9b'),
       ('a0c4e0c4-34b4-4617-b8de-4597b1bad553', 'red-doll', 126, 'UGG-BB-PUR-12', '21f49af8-bca5-4d3c-b1cb-d019598c7a9b'),
       ('b1745ff0-34d5-408e-bb86-cf9a47a10598', 'orange-doll', 12, 'UGG-BB-PUR-14', '21f49af8-bca5-4d3c-b1cb-d019598c7a9b'),
       ('ecc219bc-9408-4cab-9eda-7717edd39d4b', 'blue-doll', 11, 'UGG-BB-PUR-16', '21f49af8-bca5-4d3c-b1cb-d019598c7a9b'),
       ('461d2aa0-ceaf-4f48-80d5-aa1ae6fe10c1', 'cupboard', 345.45, 'UGG-BB-PUR-45', '19184ac3-014c-4c83-89b1-879bd5f583e2');