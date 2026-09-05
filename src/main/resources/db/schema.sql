CREATE TABLE IF NOT EXISTS member (
    id              BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_no       VARCHAR(32)  NOT NULL,
    username        VARCHAR(64)  NOT NULL,
    password        VARCHAR(128) NOT NULL,
    nickname        VARCHAR(64)  DEFAULT NULL,
    mobile          VARCHAR(20)  DEFAULT NULL,
    email           VARCHAR(128) DEFAULT NULL,
    avatar          VARCHAR(255) DEFAULT NULL,
    gender          TINYINT      DEFAULT 0,
    birthday        DATE         DEFAULT NULL,
    status          TINYINT      NOT NULL DEFAULT 1,
    level_id        BIGINT       NOT NULL DEFAULT 1,
    points          INT          NOT NULL DEFAULT 0,
    total_points    INT          NOT NULL DEFAULT 0,
    register_source VARCHAR(32)  DEFAULT 'web',
    last_login_at   DATETIME     DEFAULT NULL,
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT      NOT NULL DEFAULT 0,
    UNIQUE KEY uk_member_no (member_no),
    UNIQUE KEY uk_username (username),
    UNIQUE KEY uk_mobile (mobile)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS member_level (
    id              BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    level_code      VARCHAR(32)  NOT NULL,
    level_name      VARCHAR(64)  NOT NULL,
    min_points      INT          NOT NULL DEFAULT 0,
    discount_rate   DECIMAL(5,2) NOT NULL DEFAULT 100.00,
    privileges      VARCHAR(512) DEFAULT NULL,
    sort_order      INT          NOT NULL DEFAULT 0,
    status          TINYINT      NOT NULL DEFAULT 1,
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT      NOT NULL DEFAULT 0,
    UNIQUE KEY uk_level_code (level_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS points_record (
    id              BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id       BIGINT       NOT NULL,
    change_type     VARCHAR(32)  NOT NULL,
    change_amount   INT          NOT NULL,
    balance_after   INT          NOT NULL,
    biz_no          VARCHAR(64)  DEFAULT NULL,
    remark          VARCHAR(255) DEFAULT NULL,
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_points_member (member_id),
    KEY idx_points_biz (biz_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS member_sign_in (
    id              BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id       BIGINT       NOT NULL,
    sign_date       DATE         NOT NULL,
    continuous_days INT          NOT NULL DEFAULT 1,
    points_earned   INT          NOT NULL DEFAULT 0,
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_member_sign_date (member_id, sign_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS oper_log (
    id              BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id       BIGINT       DEFAULT NULL,
    module          VARCHAR(64)  DEFAULT NULL,
    operation       VARCHAR(128) DEFAULT NULL,
    method          VARCHAR(255) DEFAULT NULL,
    request_uri     VARCHAR(255) DEFAULT NULL,
    request_params  TEXT         DEFAULT NULL,
    ip              VARCHAR(64)  DEFAULT NULL,
    success         TINYINT      NOT NULL DEFAULT 1,
    error_msg       VARCHAR(512) DEFAULT NULL,
    cost_ms         BIGINT       DEFAULT NULL,
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_oper_member (member_id),
    KEY idx_oper_created (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
