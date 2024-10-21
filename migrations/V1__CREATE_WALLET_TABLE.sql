CREATE TABLE "wallet"
(
    "id"           SERIAL PRIMARY KEY,
    "total_amount" float     NOT NULL,
    "account_id"   integer   NOT NULL,
    "category_id"  integer,
    "created_at"   timestamp NOT NULL,
    "updated_at"   timestamp NOT NULL
);

COMMENT
ON COLUMN "wallet"."total_amount" IS 'Saldo disponivel';

COMMENT
ON COLUMN "wallet"."category_id" IS 'id da categoria';