DELETE FROM amazon.order_items WHERE id IS NOT NULL;
DELETE FROM amazon.orders WHERE id IS NOT NULL;
DELETE FROM amazon.users WHERE id IS NOT NULL;
DELETE FROM amazon.products WHERE id IS NOT NULL;
DELETE FROM amazon.categories WHERE id IS NOT NULL;