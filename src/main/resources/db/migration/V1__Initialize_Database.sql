-- Create Roles Table
CREATE TABLE IF NOT EXISTS roles_p2p (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create Other Tables as Needed
CREATE TABLE IF NOT EXISTS interview_levels (
    id BIGINT PRIMARY KEY,
    level VARCHAR(50) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS interview_types (
    id BIGINT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    description TEXT,
    duration_minutes INT NOT NULL
);

CREATE TABLE IF NOT EXISTS interview_status (
    id SERIAL PRIMARY KEY,
    status_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY,
    avatar_url TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    date_of_birth DATE,
    email VARCHAR(255) UNIQUE NOT NULL,
    email_verification_token TEXT,
    first_name VARCHAR(100),
    is_email_verified BOOLEAN DEFAULT FALSE,
    language VARCHAR(10) DEFAULT 'en',
    last_login_at TIMESTAMP,
    last_name VARCHAR(100),
    password_hash TEXT NOT NULL,
    password_reset_expires_at TIMESTAMP,
    password_reset_token TEXT,
    relevant_work_experience INT,
    timezone VARCHAR(50) DEFAULT 'UTC',
    university_major VARCHAR(100),
    university_name VARCHAR(100),
    updated_at TIMESTAMP DEFAULT NOW(),
    username VARCHAR(100) UNIQUE
);

CREATE TABLE IF NOT EXISTS user_roles (
    id SERIAL PRIMARY KEY,
    role_id BIGINT REFERENCES roles_p2p(id),
    user_id BIGINT REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS interview_type_times (
    id SERIAL PRIMARY KEY,
    time TIME NOT NULL,
    interview_type_id BIGINT REFERENCES interview_types(id)
);
