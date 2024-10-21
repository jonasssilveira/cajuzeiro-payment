CREATE TABLE "category"
(
    "id"            SERIAL PRIMARY KEY,
    "category_name" varchar
);

COMMENT
ON COLUMN "category"."id" IS 'Um identificador único para esta conta.';

COMMENT
ON COLUMN "category"."category_name" IS 'Nome da categoria, MEAL, FOOD e CASH';