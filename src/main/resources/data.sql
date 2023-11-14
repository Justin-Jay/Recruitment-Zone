-- File: src/main/resources/data.sql

-- Fetch distinct authorities from Spring Security's authorities table
INSERT INTO employee_authorities (username, authority)
SELECT DISTINCT u.username, a.authority
FROM employee e
JOIN authorities a ON e.username = a.username;
