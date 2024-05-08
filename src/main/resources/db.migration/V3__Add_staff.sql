
INSERT INTO staff (phone_number, email, password, user_name)
VALUES ('1111111111', 'admin@example.com', '$2y$05$t7kco4pGkClKDnXhzgGzQeoU1IucwHOVRCcymyj1okZbSP7ICtgR6', 'admin');

INSERT INTO staff (phone_number, email, password, user_name)
VALUES ('2222222222', 'moderator@example.com', '$2y$05$62PmJd.bMTYvkbbLd.ZXUOmAJqDnYHDLgTs5umNj9izpcnbftx6qi', 'moderator');

INSERT INTO staff (phone_number, email, password, user_name)
VALUES ('3333333333', 'user@example.com', '$2y$05$tN.oszZ9DbpRXTDPMfmF4.NOcoEDDlvS/qDLys1rZJaO9XUpUylG6', 'user');

INSERT INTO staff_roles (staff_id, role_id)
VALUES ((SELECT staff_id FROM staff WHERE email = 'admin@example.com'), (SELECT role_id FROM roles WHERE name = 'ROLE_ADMIN'));

INSERT INTO staff_roles (staff_id, role_id)
VALUES ((SELECT staff_id FROM staff WHERE email = 'moderator@example.com'), (SELECT role_id FROM roles WHERE name = 'ROLE_MODERATOR'));

INSERT INTO staff_roles (staff_id, role_id)
VALUES ((SELECT staff_id FROM staff WHERE email = 'user@example.com'), (SELECT role_id FROM roles WHERE name = 'ROLE_USER'));