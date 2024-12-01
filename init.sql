DO $$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'myuser') THEN
      CREATE ROLE myuser WITH LOGIN PASSWORD 'mypassword';
   END IF;
END
$$;

-- Ensure the database is owned by the role
DO $$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'userdb') THEN
      CREATE DATABASE userdb OWNER myuser;
   END IF;
END
$$;



-- Create the roles_p2p table if it doesn't exist
DO $$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'roles_p2p') THEN
      CREATE TABLE roles_p2p (
         id BIGINT PRIMARY KEY,
         name CHARACTER VARYING(255) NOT NULL
      );
   END IF;
END
$$;

-- Insert the roles if they do not already exist
DO $$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM roles_p2p WHERE name = 'ROLE_ADMIN') THEN
      INSERT INTO roles_p2p (id, name) VALUES (1, 'ROLE_ADMIN');
   END IF;

   IF NOT EXISTS (SELECT 1 FROM roles_p2p WHERE name = 'ROLE_FREE_USER') THEN
      INSERT INTO roles_p2p (id, name) VALUES (2, 'ROLE_FREE_USER');
   END IF;

   IF NOT EXISTS (SELECT 1 FROM roles_p2p WHERE name = 'ROLE_SUBSCRIBED_USER') THEN
      INSERT INTO roles_p2p (id, name) VALUES (3, 'ROLE_SUBSCRIBED_USER');
   END IF;
END
$$;

