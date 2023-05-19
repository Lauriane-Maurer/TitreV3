INSERT INTO users (username,password,enabled)
VALUES ('admin',
        '{bcrypt}$2a$10$UvEkORl/jwpd7diLipPtQ.Q4lp2Yk1.bT9EPcuJBdvZgn3yubniMu',
        1);


INSERT INTO authorities (username,authority)
VALUES ( 'admin',
         'ROLE_ADMIN' );

