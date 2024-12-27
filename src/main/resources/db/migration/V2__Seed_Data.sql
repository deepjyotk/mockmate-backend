-- Seed Data for Roles
INSERT INTO roles_p2p (id, name) VALUES
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_FREE_USER'),
    (3, 'ROLE_SUBSCRIBED_USER')
ON CONFLICT (id) DO NOTHING;

-- Seed Data for Interview Levels
INSERT INTO interview_levels (id, level, description) VALUES
    (1, 'Beginner', 'Covers basic concepts, ideal for entry-level candidates.'),
    (2, 'Intermediate', 'Covers moderately complex topics for mid-level candidates.'),
    (3, 'Advanced', 'Focuses on advanced and expert-level concepts.')
ON CONFLICT (id) DO NOTHING;

-- Seed Data for Interview Types
INSERT INTO interview_types (id, type, description, duration_minutes) VALUES
    (1, 'Behavioral', 'Focuses on assessing soft skills, past experiences, and cultural fit.', 45),
    (2, 'Software Engineering', 'Evaluates technical knowledge, problem-solving, and coding skills.', 60)
ON CONFLICT (id) DO NOTHING;

-- Seed Data for Interview Status
INSERT INTO interview_status (status_name) VALUES
    ('UPCOMING'),
    ('IN_PROGRESS'),
    ('END'),
    ('DID_NOT_JOIN'),
    ('INTERRUPTED')
ON CONFLICT (status_name) DO NOTHING;

-- Seed Data for Users
INSERT INTO users (id, email, password_hash, university_major, university_name, username) VALUES
    (61, 'himanshu@gmail.com', '$2a$10$aLJIKkD0WRLfauQ4MAWreeUoVMLABhSbhui.9ODjMArgawomCe2Vi', 'Computer Science', 'New York University', 'himanshu'),
    (62, 'niraj@gmail.com', '$2a$10$B7S/hjNk8J8CKjxTPtljAefYGO8EnwmU.pxMy3vLTGYmq/2mDYAQO', 'Computer Science', 'New York University', 'niraj'),
    (63, 'deepjyot@gmail.com', '$2a$10$JicjssC0QNJQx4cwcqr8TOIFOaGGEm1OjmsRr17Eg1TOFI2GlJdUu', 'Computer Science', 'New York University', 'deepjyot')
ON CONFLICT (id) DO NOTHING;

-- Seed Data for User Roles
INSERT INTO user_roles (role_id, user_id) VALUES
    (1, 61), -- Himanshu as ROLE_ADMIN
    (2, 61), -- Himanshu as ROLE_FREE_USER
    (2, 62), -- Niraj as ROLE_FREE_USER
    (3, 63), -- Deepjyot as ROLE_SUBSCRIBED_USER
    (2, 63)  -- Deepjyot also as ROLE_FREE_USER
ON CONFLICT DO NOTHING;
