INSERT INTO public.account (id, username, created_at, updated_at) VALUES (1, 'Jonas', '2024-10-16 23:28:32.000000', '2024-10-16 23:28:35.000000');

INSERT INTO public.category (id, category_name) VALUES (1, 'FOOD');
INSERT INTO public.category (id, category_name) VALUES (2, 'MEAL');
INSERT INTO public.category (id, category_name) VALUES (3, 'CASH');

INSERT INTO public.wallet (id, total_amount, account_id, category_id, created_at, updated_at) VALUES (1, 90, 1, 1, '2024-10-16 23:29:12.000000', '2024-10-16 23:29:14.000000');
INSERT INTO public.wallet (id, total_amount, account_id, category_id, created_at, updated_at) VALUES (2, 230, 1, 2, '2024-10-16 23:32:30.000000', '2024-10-16 23:32:32.000000');
INSERT INTO public.wallet (id, total_amount, account_id, category_id, created_at, updated_at) VALUES (3, 1000, 1, 3, '2024-10-17 00:44:48.000000', '2024-10-17 00:44:50.000000');
