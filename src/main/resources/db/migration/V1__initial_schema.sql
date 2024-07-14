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

CREATE TABLE "activities"
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    adventure_name VARCHAR(255) NOT NULL,
    skill_level VARCHAR(255) NOT NULL,
    attitude VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "users" (id)
)

CREATE TABLE "activity_preferences"
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    activity_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "users" (id)
    FOREIGN KEY (activity_id) REFERENCES "activity" (id)
);