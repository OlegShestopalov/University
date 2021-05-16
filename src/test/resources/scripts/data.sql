INSERT INTO audience (number, desk)
VALUES (1, 50),
       (2, 40),
       (3, 30);
INSERT INTO class (name)
VALUES ('first'),
       ('second'),
       ('third'),
       ('fourth'),
       ('fifth'),
       ('sixth');
INSERT INTO course (name)
VALUES ('first'),
       ('second'),
       ('third'),
       ('fourth'),
       ('fifth'),
       ('sixth');
INSERT INTO day1 (day)
VALUES ('2020-09-01'),
       ('2020-09-02'),
       ('2020-09-03');
INSERT INTO faculty (name)
VALUES ('Electronics'),
       ('Programming'),
       ('Psychology');
INSERT INTO group1 (name, faculty_id, course_id)
VALUES ('AAAA', 1, 1),
       ('BBBB', 2, 2),
       ('CCCC', 3, 3);
INSERT INTO subject (name, description)
VALUES ('Subject1', 'Subject1'),
       ('Subject2', 'Subject2'),
       ('Subject3', 'Subject3');
INSERT INTO scheduleitem (class_id, subject_id, audience_id, day_id)
VALUES (1, 1, 1, 1),
       (2, 2, 2, 2),
       (3, 3, 3, 3);
INSERT INTO group_scheduleitem (group_id, scheduleitem_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);
INSERT INTO student (group_id, name, surname, sex, age, email)
VALUES (1, 'Student1', 'Student1', 'Male', 20, 'student1@gmail.com'),
       (2, 'Student2', 'Student2', 'Male', 21, 'student2@gmail.com'),
       (3, 'Student3', 'Student3', 'Male', 22, 'student3@gmail.com');
INSERT INTO subject_faculty (subject_id, faculty_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);
INSERT INTO teacher (name, surname, email)
VALUES ('Teacher1', 'Teacher1', 'teacher1@gmail.com'),
       ('Teacher2', 'Teacher2', 'teacher2@gmail.com'),
       ('Teacher3', 'Teacher3', 'teacher3@gmail.com');
INSERT INTO teacher_faculty (teacher_id, faculty_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);
INSERT INTO teacher_scheduleitem (teacher_id, scheduleitem_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);
INSERT INTO teacher_subject (teacher_id, subject_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);
INSERT INTO university (name, address, type)
VALUES ('University', 'Address', 'type');