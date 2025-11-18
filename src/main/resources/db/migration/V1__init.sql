CREATE TABLE IF NOT EXISTS reimbursement (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    patient_name VARCHAR(255) NOT NULL,
    identification_number VARCHAR(20) NOT NULL,
    medical_procedure VARCHAR(255) NOT NULL,
    cost NUMERIC(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_reimbursement_ident_number
    ON reimbursement (identification_number);


CREATE TABLE IF NOT EXISTS change_log (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    change_date_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    old_status VARCHAR(20),
    new_status VARCHAR(20),
    username VARCHAR(100),
    description VARCHAR(255),
    reimbursement_id BIGINT NOT NULL,
    CONSTRAINT fk_change_log_reimbursement
    FOREIGN KEY (reimbursement_id) REFERENCES reimbursement (id)
);

CREATE INDEX IF NOT EXISTS idx_change_log_reimbursement
    ON change_log (reimbursement_id);


CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    password VARCHAR(200) NOT NULL,
    CONSTRAINT uq_users_email UNIQUE (email)
);

CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);