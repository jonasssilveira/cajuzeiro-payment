CREATE TABLE "transaction"
(
    "id"         SERIAL PRIMARY KEY,
    "amount"     float   NOT NULL,
    "account_id" integer NOT NULL,
    "merchant"   varchar,
    "mcc"        varchar(4),
    "created_at" timestamp
);

COMMENT
ON COLUMN "transaction"."id" IS 'Um identificador único para esta transação.';

COMMENT
ON COLUMN "transaction"."amount" IS 'O valor a ser debitado de um saldo.';

COMMENT
ON COLUMN "transaction"."account_id" IS 'Um identificador para a conta.';

COMMENT
ON COLUMN "transaction"."merchant" IS 'O nome do estabelecimento.';

COMMENT
ON COLUMN "transaction"."mcc" IS 'Um código numérico de 4 dígitos que classifica os estabelecimentos
comerciais de acordo com o tipo de produto vendido ou serviço prestado.';

COMMENT
ON COLUMN "transaction"."created_at" IS 'Data e hora da criação da transação';