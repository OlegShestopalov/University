INSERT INTO audience (id, number, desk) VALUES (1, 1, 50), (2, 2, 40), (3, 3, 30);
INSERT INTO class (id, name) VALUES (1, 'first'), (2, 'second'), (3, 'third'), (4, 'fourth'), (5, 'fifth'), (6, 'sixth');
INSERT INTO course (id, name) VALUES (1, 'first'), (2, 'second'), (3, 'third'), (4, 'fourth'), (5, 'fifth'), (6, 'sixth');
INSERT INTO day1 (id, day) VALUES (1, '2020-09-01'), (2, '2020-09-02'), (3, '2020-09-03');
INSERT INTO faculty (id, name) VALUES (1, 'Electronics'), (2, 'Programming'), (3, 'Psychology');
INSERT INTO group1 (id, name, faculty_id, course_id) VALUES (1, 'AAAA', 1, 1), (2, 'BBBB', 2, 2), (3, 'CCCC', 3, 3);
INSERT INTO subject (id, name, description)
VALUES (1, 'Subject1', 'Subject1'),
       (2, 'Subject2', 'Subject2'),
       (3, 'Subject3', 'Subject3');
INSERT INTO scheduleitem (id, class_id, subject_id, audience_id, day_id) VALUES (1, 1, 1, 1, 1), (2, 2, 2, 2, 2), (3, 3, 3, 3, 3);
INSERT INTO group_scheduleitem (id, group_id, scheduleitem_id) VALUES (1, 1, 1), (2, 2, 2), (3, 3, 3);
INSERT INTO student (id, group_id, name, surname, sex, age, email)
VALUES (1, 1, 'Student1', 'Student1', 'Male', 20, 'student1@gmail.com'),
       (2, 2, 'Student2', 'Student2', 'Male', 21, 'student2@gmail.com'),
       (3, 3, 'Student3', 'Student3', 'Male', 22, 'student3@gmail.com');
INSERT INTO subject_faculty (id, subject_id, faculty_id) VALUES (1, 1, 1), (2, 2, 2), (3, 3, 3);
INSERT INTO teacher (id, name, surname, email)
VALUES (1, 'Teacher1', 'Teacher1', 'teacher1@gmail.com'),
       (2, 'Teacher2', 'Teacher2', 'teacher2@gmail.com'),
       (3, 'Teacher3', 'Teacher3', 'teacher3@gmail.com');
INSERT INTO teacher_faculty (id, teacher_id, faculty_id) VALUES (1, 1, 1), (2, 2, 2), (3, 3, 3);
INSERT INTO teacher_scheduleitem (id, teacher_id, scheduleitem_id) VALUES (1, 1, 1), (2, 2, 2), (3, 3, 3);
INSERT INTO teacher_subject (id, teacher_id, subject_id) VALUES (1, 1, 1), (2, 2, 2), (3, 3, 3);
INSERT INTO university (id, name, address, type) VALUES (1, 'University', 'Address', 'type');