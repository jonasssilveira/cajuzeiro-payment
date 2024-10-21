CREATE TABLE "account"
(
    "id"          SERIAL PRIMARY KEY,
    "username"    varchar   NOT NULL,
    "created_at"  timestamp NOT NULL,
    "updated_at"  timestamp NOT NULL
);

COMMENT
ON COLUMN "account"."id" IS 'Um identificador único para esta conta.';

COMMENT
ON COLUMN "account"."created_at" IS 'Data e hora da criação do usuario';

COMMENT
ON COLUMN "account"."updated_at" IS 'Data e hora da atualização do usuario';
