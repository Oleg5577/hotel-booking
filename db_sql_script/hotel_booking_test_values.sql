INSERT INTO `hotel_booking_db`.`role` (`role_name`) VALUES ('admin');
INSERT INTO `hotel_booking_db`.`role` (`role_name`) VALUES ('user');

INSERT INTO `hotel_booking_db`.`user` (`email`, `password`, `name`, `surname`, `phone_number`, `fk_role_id`) VALUES ('ivanov@gmail.com', '123456', 'Иван', 'Иванов', '+37512345', 1);
INSERT INTO `hotel_booking_db`.`user` (`email`, `password`, `name`, `surname`, `phone_number`, `fk_role_id`) VALUES ('petrov@gmail.com', '111111', 'Петр', 'Петров', '+375124445', 1);
INSERT INTO `hotel_booking_db`.`user` (`email`, `password`, `name`, `surname`, `phone_number`, `fk_role_id`) VALUES ('sidorov@gmail.com', '222222', 'Сидр', 'Сидорович', '+37662345', 2);
INSERT INTO `hotel_booking_db`.`user` (`email`, `password`, `name`, `surname`, `phone_number`, `fk_role_id`) VALUES ('jhon@gmail.com', '333333', 'Jhon', 'Brown', '+117512345', 2);
INSERT INTO `hotel_booking_db`.`user` (`email`, `password`, `name`, `surname`, `phone_number`, `fk_role_id`) VALUES ('jhony@gmail.com', '323232', 'Johnny', 'Green', '+37512345', 2);
INSERT INTO `hotel_booking_db`.`user` (`email`, `password`, `name`, `surname`, `phone_number`, `fk_role_id`) VALUES ('vasya@gmail.com', '123123', 'Vasya', 'Vasilyev', '+75537512345', 2);
INSERT INTO `hotel_booking_db`.`user` (`email`, `password`, `name`, `surname`, `phone_number`, `fk_role_id`) VALUES ('johnson@gmail.com', '1234321', 'Anna', 'Johnson', '+235512345', 2);
INSERT INTO `hotel_booking_db`.`user` (`email`, `password`, `name`, `surname`, `phone_number`, `fk_role_id`) VALUES ('williams@gmail.com', '12341234', 'Sophia', 'Williams', '+1177512345', 2);
INSERT INTO `hotel_booking_db`.`user` (`email`, `password`, `name`, `surname`, `phone_number`, `fk_role_id`) VALUES ('miller@gmail.com', '12344321', 'Ryan', 'Miller', '+37512345', 2);
INSERT INTO `hotel_booking_db`.`user` (`email`, `password`, `name`, `surname`, `phone_number`, `fk_role_id`) VALUES ('zayceva@gmail.com', '7676764', 'Мария', 'Зайцева', '+37512345', 2);

INSERT INTO `hotel_booking_db`.`room_type` (`type_name`) VALUES ('standard');
INSERT INTO `hotel_booking_db`.`room_type` (`type_name`) VALUES ('semilux');
INSERT INTO `hotel_booking_db`.`room_type` (`type_name`) VALUES ('lux');
INSERT INTO `hotel_booking_db`.`room_type` (`type_name`) VALUES ('president');

INSERT INTO `hotel_booking_db`.`room` (`number`, `size`, `price`, `fk_room_type_id`) VALUES (101, 1, 40, 1);
INSERT INTO `hotel_booking_db`.`room` (`number`, `size`, `price`, `fk_room_type_id`) VALUES (102, 2, 50, 1);
INSERT INTO `hotel_booking_db`.`room` (`number`, `size`, `price`, `fk_room_type_id`) VALUES (103, 1, 40, 1);
INSERT INTO `hotel_booking_db`.`room` (`number`, `size`, `price`, `fk_room_type_id`) VALUES (104, 2, 50, 1);
INSERT INTO `hotel_booking_db`.`room` (`number`, `size`, `price`, `fk_room_type_id`) VALUES (201, 1, 60, 2);
INSERT INTO `hotel_booking_db`.`room` (`number`, `size`, `price`, `fk_room_type_id`) VALUES (203, 2, 70, 2);
INSERT INTO `hotel_booking_db`.`room` (`number`, `size`, `price`, `fk_room_type_id`) VALUES (204, 2, 70, 2);
INSERT INTO `hotel_booking_db`.`room` (`number`, `size`, `price`, `fk_room_type_id`) VALUES (206, 1, 80, 3);
INSERT INTO `hotel_booking_db`.`room` (`number`, `size`, `price`, `fk_room_type_id`) VALUES (207, 2, 100, 3);
INSERT INTO `hotel_booking_db`.`room` (`number`, `size`, `price`, `fk_room_type_id`) VALUES (210, 2, 200, 4);

INSERT INTO `hotel_booking_db`.`room_request` (`check_in`, `check_out`, `room_size`, `request_status`, `fk_user_id`, `fk_room_type_id`) VALUES ('2017-07-22', '2017-07-24', 2, 'in_progress', 3, 1);
INSERT INTO `hotel_booking_db`.`room_request` (`check_in`, `check_out`, `room_size`, `request_status`, `fk_user_id`, `fk_room_type_id`) VALUES ('2017-07-25', '2017-07-28', 1, 'confirmed', 4, 2);
INSERT INTO `hotel_booking_db`.`room_request` (`check_in`, `check_out`, `room_size`, `request_status`, `fk_user_id`, `fk_room_type_id`) VALUES ('2017-07-24', '2017-07-27', 1, 'confirmed', 5, 4);
INSERT INTO `hotel_booking_db`.`room_request` (`check_in`, `check_out`, `room_size`, `request_status`, `fk_user_id`, `fk_room_type_id`) VALUES ('2017-07-21', '2017-08-01', 2, 'denied', 6, 3);
INSERT INTO `hotel_booking_db`.`room_request` (`check_in`, `check_out`, `room_size`, `request_status`, `fk_user_id`, `fk_room_type_id`) VALUES ('2017-07-26', '2017-07-29', 2, 'confirmed', 7, 2);
INSERT INTO `hotel_booking_db`.`room_request` (`check_in`, `check_out`, `room_size`, `request_status`, `fk_user_id`, `fk_room_type_id`) VALUES ('2017-07-24', '2017-07-28', 2, 'confirmed', 8, 1);
INSERT INTO `hotel_booking_db`.`room_request` (`check_in`, `check_out`, `room_size`, `request_status`, `fk_user_id`, `fk_room_type_id`) VALUES ('2017-08-02', '2017-08-06', 1, 'confirmed', 9, 1);
INSERT INTO `hotel_booking_db`.`room_request` (`check_in`, `check_out`, `room_size`, `request_status`, `fk_user_id`, `fk_room_type_id`) VALUES ('2017-08-06', '2017-08-11', 2, 'in_progress', 3, 1);
INSERT INTO `hotel_booking_db`.`room_request` (`check_in`, `check_out`, `room_size`, `request_status`, `fk_user_id`, `fk_room_type_id`) VALUES ('2017-08-10', '2017-08-15', 2, 'in_progress', 4, 2);
INSERT INTO `hotel_booking_db`.`room_request` (`check_in`, `check_out`, `room_size`, `request_status`, `fk_user_id`, `fk_room_type_id`) VALUES ('2017-08-12', '2017-09-14', 2, 'in_progress', 5, 3);

INSERT INTO `hotel_booking_db`.`order` (`check_in`, `check_out`, `amount`, `fk_room_id`, `fk_user_id`, `is_paid`, `order_status`) VALUES ('2017-06-12', '2017-06-15', 120, 3, 1, TRUE, 'checked_out');
INSERT INTO `hotel_booking_db`.`order` (`check_in`, `check_out`, `amount`, `fk_room_id`, `fk_user_id`, `is_paid`, `order_status`) VALUES ('2017-07-13', '2017-07-16', 120, 4, 1, TRUE, 'checked_out');
INSERT INTO `hotel_booking_db`.`order` (`check_in`, `check_out`, `amount`, `fk_room_id`, `fk_user_id`, `is_paid`, `order_status`) VALUES ('2017-06-13', '2017-06-14', 40, 2, 2, TRUE, 'expect_guest_arrival');
INSERT INTO `hotel_booking_db`.`order` (`check_in`, `check_out`, `amount`, `fk_room_id`, `fk_user_id`, `is_paid`) VALUES ('2017-06-13', '2017-06-15', 100, 4, 3, TRUE);
INSERT INTO `hotel_booking_db`.`order` (`check_in`, `check_out`, `amount`, `fk_room_id`, `fk_user_id`, `is_paid`, `order_status`) VALUES ('2017-06-15', '2017-06-18', 180, 5, 4, TRUE, 'checked_out');
INSERT INTO `hotel_booking_db`.`order` (`check_in`, `check_out`, `amount`, `fk_room_id`, `fk_user_id`, `is_paid`, `order_status`) VALUES ('2017-07-16', '2017-07-18', 180, 5, 4, FALSE, 'checked_in');
INSERT INTO `hotel_booking_db`.`order` (`check_in`, `check_out`, `amount`, `fk_room_id`, `fk_user_id`, `is_paid`, `order_status`) VALUES ('2017-06-22', '2017-06-23', 120, 3, 5, TRUE, 'checked_out');
INSERT INTO `hotel_booking_db`.`order` (`check_in`, `check_out`, `amount`, `fk_room_id`, `fk_user_id`, `is_paid`, `order_status`) VALUES ('2017-06-23', '2017-06-28', 300, 2, 6, FALSE, 'checked_in');
INSERT INTO `hotel_booking_db`.`order` (`check_in`, `check_out`, `amount`, `fk_room_id`, `fk_user_id`, `is_paid`, `order_status`) VALUES ('2017-06-25', '2017-06-27', 140, 7, 7, TRUE, 'canceled');
INSERT INTO `hotel_booking_db`.`order` (`check_in`, `check_out`, `amount`, `fk_room_id`, `fk_user_id`, `is_paid`, `order_status`) VALUES ('2017-06-28', '2017-06-30', 160, 8, 8, FALSE, 'checked_in');
INSERT INTO `hotel_booking_db`.`order` (`check_in`, `check_out`, `amount`, `fk_room_id`, `fk_user_id`, `is_paid`, `order_status`) VALUES ('2017-07-01', '2017-07-05', 400, 3, 9, TRUE, 'expect_guest_arrival');
INSERT INTO `hotel_booking_db`.`order` (`check_in`, `check_out`, `amount`, `fk_room_id`, `fk_user_id`, `is_paid`, `order_status`) VALUES ('2017-07-11', '2017-07-14', 600, 10, 10, FALSE, 'checked_out');
