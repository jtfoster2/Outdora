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
    skiing_skill_level VARCHAR(20) NOT NULL,
    skiing_attitude VARCHAR(20) NOT NULL,
    backpacking_skill_level VARCHAR(20) NOT NULL,
    backpacking_attitude VARCHAR(20) NOT NULL,
    travel_skill_level VARCHAR(20) NOT NULL,
    travel_attitude VARCHAR(20) NOT NULL,
    hiking_skill_level VARCHAR(20) NOT NULL,
    hiking_attitude VARCHAR(20) NOT NULL,
    holidate_skill_level VARCHAR(20) NOT NULL,
    holidate_attitude VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "users" (id)
);
-- VARCHAR(20) NOT NULL -- you only get 20 chars for the enum name

--    @Enumerated(EnumType.STRING) // This tells JPA to store the enum as a string
--    var status: ArticleStatus