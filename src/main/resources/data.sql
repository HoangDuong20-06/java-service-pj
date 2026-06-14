INSERT INTO  time_slots (id, starttime, endtime)
VALUES
    (1, '06:00:00', '08:00:00'),
    (2, '08:00:00', '10:00:00'),
    (3, '10:00:00', '12:00:00'),
    (4, '14:00:00', '16:00:00'),
    (5, '18:00:00', '20:00:00');
INSERT INTO courts(id, courtname, isavailable)
VALUES
    (1, 'Sân A',  true),
    (2, 'Sân B',  true),
    (3, 'Sân C',  true);

SHOW CREATE TABLE bookings;

INSERT INTO badminton_clusters
(name, address, hotLine, manager_id)
VALUES
    (
        'San Cau Long TDT',
        'Ha Noi',
        '0123456788',
        4
    );