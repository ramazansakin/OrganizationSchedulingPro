
DROP TABLE IF EXISTS  event;

DROP TABLE IF EXISTS organization;


CREATE TABLE organization (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name varchar(50) NOT NULL
);

CREATE TABLE event(
    id INT AUTO_INCREMENT PRIMARY KEY,
    subject varchar(80) NOT NULL,
    duration INT DEFAULT 0,
    organization_id INT ,
    FOREIGN KEY (organization_id) REFERENCES organization(id)
);


-- ////////////////////////////////////////////////////////////////////////////
--  Sample static test values
-- ////////////////////////////////////////////////////////////////////////////
INSERT INTO organization( name )  VALUES ('My Software Conference');

INSERT INTO organization( name )  VALUES ('Test Organization');

INSERT INTO organization( name )  VALUES ('Sample');

INSERT INTO event( subject, duration, organization_id) VALUES   ('Architecting Your Codebase', 60, 1),
                                                                ('Overdoing it in Python', 45, 1 ),
                                                                ('Flavors of Concurrency in Java', 30, 1),
                                                                ('Ruby Errors from Mismatched Gem Versions', 45, 1),
                                                                ('JUnit 5 - Shaping the Future of Testing on the JVM', 45, 1),
                                                                ('Cloud Native Java', 5, 1),
                                                                ('Communicating Over Distance', 60, 1),
                                                                ('AWS Technical Essentials', 45, 1),
                                                                ('Continuous Delivery', 30, 1),
                                                                ('Monitoring Reactive Applications', 30, 1),
                                                                ('Pair Programming vs Noise', 45, 1),
                                                                ('Rails Magic', 60, 1),
                                                                ('Microservices "Just Right"', 60, 1);

