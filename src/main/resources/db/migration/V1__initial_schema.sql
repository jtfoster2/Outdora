CREATE TABLE "users"
(
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE "provider_details"
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    provider_name VARCHAR(255) NOT NULL,
    provider_user_id INT NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES "users" (id)
);

-- Made this separate so that it could be created separately from the user creation.
CREATE TABLE "profile"
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    description TEXT,
    FOREIGN KEY (user_id) REFERENCES "users" (id)
);

CREATE TABLE "activity_preferences"
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    skiing BOOLEAN NOT NULL,
    hiking BOOLEAN NOT NULL,
    backpacking BOOLEAN NOT NULL,
    travel BOOLEAN NOT NULL,
    holidate BOOLEAN NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "users" (id)
);

CREATE TABLE chat
(
    id BIGSERIAL PRIMARY KEY,
    user_a_id BIGINT NOT NULL,
    user_b_id BIGINT NOT NULL,
    chat TEXT NOT NULL,
    FOREIGN KEY (user_a_id) REFERENCES users (id),
    FOREIGN KEY (user_b_id) REFERENCES users (id)
);