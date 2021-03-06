DROP TABLE IF EXISTS group_scheduleItem;
DROP TABLE IF EXISTS teacher_scheduleItem;
DROP TABLE IF EXISTS teacher_subject;
DROP TABLE IF EXISTS teacher_faculty;
DROP TABLE IF EXISTS subject_faculty;
DROP TABLE IF EXISTS scheduleItem;
DROP TABLE IF EXISTS day1;
DROP TABLE IF EXISTS lesson;
DROP TABLE IF EXISTS subject;
DROP TABLE IF EXISTS audience;
DROP TABLE IF EXISTS teacher;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS group1;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS faculty;
DROP TABLE IF EXISTS university;

-- DROP SCHEMA IF EXISTS public;
-- CREATE SCHEMA public AUTHORIZATION maintainer;
COMMENT
    ON SCHEMA public IS 'standard public schema';
GRANT ALL
    ON SCHEMA public TO PUBLIC;
GRANT ALL
    ON SCHEMA public TO maintainer;

CREATE TABLE university
(
    id      INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    type    VARCHAR(100) NOT NULL
);

CREATE TABLE faculty
(
    id   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE course
(
    id   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE day1
(
    id  INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    day DATE NOT NULL
);

CREATE TABLE group1
(
    id         INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    faculty_id INT          NOT NULL,
    course_id  INT          NOT NULL,
    FOREIGN KEY (faculty_id) REFERENCES faculty (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE student
(
    id       INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    group_id INT          NOT NULL,
    name     VARCHAR(100) NOT NULL,
    surname  VARCHAR(100) NOT NULL,
    sex      VARCHAR(100) NOT NULL,
    age      INT          NOT NULL,
    email    VARCHAR(100) NOT NULL,
    FOREIGN KEY (group_id) REFERENCES group1 (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE teacher
(
    id      INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    email   VARCHAR(100) NOT NULL
);

CREATE TABLE audience
(
    id     INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    number INT NOT NULL,
    desk   INT NOT NULL
);

CREATE TABLE subject
(
    id          INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    description VARCHAR(1024) NOT NULL
);

CREATE TABLE lesson
(
    id   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(32) NOT NULL
);

CREATE TABLE scheduleItem
(
    id          INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    lesson_id   INT NOT NULL,
    subject_id  INT NOT NULL,
    audience_id INT NOT NULL,
    day_id      INT NOT NULL,
    FOREIGN KEY (lesson_id) REFERENCES lesson (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subject (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (audience_id) REFERENCES audience (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (day_id) REFERENCES day1 (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE group_scheduleItem
(
    id              INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    group_id        INT NOT NULL,
    scheduleItem_id INT NOT NULL,
    FOREIGN KEY (group_id) REFERENCES group1 (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (scheduleItem_id) REFERENCES scheduleItem (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE teacher_scheduleItem
(
    id              INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    teacher_id      INT NOT NULL,
    scheduleItem_id INT NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teacher (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (scheduleItem_id) REFERENCES scheduleItem (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE teacher_subject
(
    id         INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    teacher_id INT NOT NULL,
    subject_id INT NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teacher (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subject (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE teacher_faculty
(
    id         INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    teacher_id INT NOT NULL,
    faculty_id INT NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teacher (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (faculty_id) REFERENCES faculty (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE subject_faculty
(
    id         INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    subject_id INT NOT NULL,
    faculty_id INT NOT NULL,
    FOREIGN KEY (subject_id) REFERENCES subject (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (faculty_id) REFERENCES faculty (id) ON DELETE CASCADE ON UPDATE CASCADE
);
