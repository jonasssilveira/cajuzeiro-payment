ALTER TABLE "transaction"
    ADD FOREIGN KEY ("account_id") REFERENCES "account" ("id");

ALTER TABLE "wallet"
    ADD FOREIGN KEY ("category_id") REFERENCES "category" ("id");

ALTER TABLE "wallet"
    ADD FOREIGN KEY ("account_id") REFERENCES "account" ("id");