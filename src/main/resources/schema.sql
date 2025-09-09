create table if not exists reimbursement (
    id IDENTITY NOT NULL PRIMARY KEY,
    patient_name VARCHAR(255) NOT NULL,
    identification_number VARCHAR(10) NOT NULL,
    medical_procedure VARCHAR(255) NOT NULL,
    cost NUMERIC(38,2) NOT NULL,
    status VARCHAR(8)
);

create table if not exists change_log
(
    id               IDENTITY NOT NULL PRIMARY KEY,
    change_date_time TIMESTAMP,
    old_status       VARCHAR(8),
    new_tatus        VARCHAR(8),
    user_name        VARCHAR(255),
    reimbursement_id BIGINT,
    constraint fk_change_log_reimbursement
    foreign key (reimbursement_id) references reimbursement (id)
);

create table if not exists users
(
    id         IDENTITY NOT NULL PRIMARY KEY,
    name       VARCHAR(100),
    email      VARCHAR(40),
    password   VARCHAR(30)
);
