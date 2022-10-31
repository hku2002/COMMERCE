insert into member (activated, created_at, address, address_detail, zip_code, email, password, phone, user_id, name, id) values (true, now(), '서울시 강남구 테헤란로 427', '아이파크몰 test 호', '00111', 'gildong@commerce.com', '$2a$10$3.VKQZR7sEYeGwIT.l7Jpebwt.yU.updmGVNU8waHFd4.juq/S36G', '01012341234', 'test01', '홍길동', 1);
insert into member (activated, created_at, address, address_detail, zip_code, email, password, phone, user_id, name, id) values (true, now(), '서울시 강남구 테스트로 123', '행복아파트 101-101 호', '00112', 'test02@commerce.com', '$2a$10$3.VKQZR7sEYeGwIT.l7Jpebwt.yU.updmGVNU8waHFd4.juq/S36G', '01011112222', 'test02', '김테스', 2);
insert into member (activated, created_at, address, address_detail, zip_code, email, password, phone, user_id, name, id) values (true, now(), '서울시 강남구 행복로 123', '즐거운아파트 102-102 호', '00123', 'test03@test.com', '$2a$10$3.VKQZR7sEYeGwIT.l7Jpebwt.yU.updmGVNU8waHFd4.juq/S36G', '01011113333', 'test03', '박행복', 3);

insert into item (id, name, img_path, default_price, sale_price, discount_price, discount_rate, discount_method, supply_price, stock_quantity, activated, created_at) values (1, '이쁜 텀블러 파랑', 'https://img.test.com/item.jpg', 10000, 8000, 2000, 0, 'PRICE', 6000, 50, true, now());
insert into item (id, name, img_path, default_price, sale_price, discount_price, discount_rate, discount_method, supply_price, stock_quantity, activated, created_at) values (2, '이쁜 텀블러 노랑', 'https://img.test.com/item2.jpg', 10500, 8500, 2000, 0, 'PRICE', 6500, 50, true, now());
insert into item (id, name, img_path, default_price, sale_price, discount_price, discount_rate, discount_method, supply_price, stock_quantity, activated, created_at) values (3, '이쁜 티셔트 L 파랑티', 'https://img.test.com/tsblue.jpg', 10000, 9000, 0, 10, 'RATE', 2000, 100, true, now());
insert into item (id, name, img_path, default_price, sale_price, discount_price, discount_rate, discount_method, supply_price, stock_quantity, activated, created_at) values (4, '이쁜 티셔트 L 노랑티', 'https://img.test.com/tsyellow.jpg', 10000, 9000, 0, 10, 'RATE', 2000, 100, true, now());
insert into item (id, name, img_path, default_price, sale_price, discount_price, discount_rate, discount_method, supply_price, stock_quantity, activated, created_at) values (5, '이쁜 티셔트 M 파랑티', 'https://img.test.com/tsybluem.jpg', 10000, 8000, 0, 20, 'RATE', 2000, 100, true, now());
insert into item (id, name, img_path, default_price, sale_price, discount_price, discount_rate, discount_method, supply_price, stock_quantity, activated, created_at) values (6, '이쁜 티셔트 M 노랑티', 'https://img.test.com/tsyyellowm.jpg', 10000, 8000, 0, 20, 'RATE', 2000, 100, true, now());
insert into item (id, name, img_path, default_price, sale_price, discount_price, discount_rate, discount_method, supply_price, stock_quantity, activated, created_at) values (7, '맛난 김밥', 'https://img.test.com/kimboop.jpg', 3000, 3000, 0, 0, 'NO_DISCOUNT', 2000, 50, true, now());

insert into product (id, name, img_path, default_price, sale_price, discount_price, discount_rate, discount_method, status, composition_type, main_item_id, activated, created_at)  values (1, '이쁜 텀블러', 'https://img.test.com/item.jpg', 10000, 8000, 2000, 0, 'PRICE', 'DISPLAY', 'SINGLE', 1, true, now());
insert into product (id, name, img_path, default_price, sale_price, discount_price, discount_rate, discount_method, status, composition_type, main_item_id, activated, created_at)  values (2, '이쁜 티셔트', 'https://img.test.com/item.jpg', 10000, 8000, 0, 20, 'RATE', 'DISPLAY', 'SINGLE', 5, true, now());
insert into product (id, name, img_path, default_price, sale_price, discount_price, discount_rate, discount_method, status, composition_type, main_item_id, activated, created_at)  values (3, '맛난 김밥 세트', 'https://img.test.com/kimboop.jpg', 3000, 3000, 0, 0, 'NO_DISCOUNT', 'DISPLAY', 'VARIETY_SET', 7, true, now());

insert into option (id, product_id, item_id, item_product_mapping_id, name, stage, parent_id, item_used_quantity, activated, created_at) values (1, 1, 1, 1, '파랑', 1, null, 1, true, now());
insert into option (id, product_id, item_id, item_product_mapping_id, name, stage, parent_id, item_used_quantity, activated, created_at) values (2, 1, 2, 1, '노랑', 1, null, 1, true, now());
insert into option (id, product_id, item_id, item_product_mapping_id, name, stage, parent_id, item_used_quantity, activated, created_at) values (3, 2, 3, 1, 'L', 1, null, 1, true, now());
insert into option (id, product_id, item_id, item_product_mapping_id, name, stage, parent_id, item_used_quantity, activated, created_at) values (4, 2, 3, 1, '파랑티', 2, 3, 1, true, now());
insert into option (id, product_id, item_id, item_product_mapping_id, name, stage, parent_id, item_used_quantity, activated, created_at) values (5, 2, 4, 1, '노랑티', 2, 4, 1, true, now());

insert into option (id, product_id, item_id, item_product_mapping_id, name, stage, parent_id, item_used_quantity, activated, created_at) values (6, 2, 5, 1, 'M', 1, null, 1, true, now());
insert into option (id, product_id, item_id, item_product_mapping_id, name, stage, parent_id, item_used_quantity, activated, created_at) values (7, 2, 5, 1, '파랑티', 2, 5, 1, true, now());
insert into option (id, product_id, item_id, item_product_mapping_id, name, stage, parent_id, item_used_quantity, activated, created_at) values (8, 2, 6, 1, '노랑티', 2, 6, 1, true, now());

insert into option (id, product_id, item_id, item_product_mapping_id, name, stage, parent_id, item_used_quantity, activated, created_at) values (9, 3, 7, 1, '3개 세트', 1, null, 3, true, now());
insert into option (id, product_id, item_id, item_product_mapping_id, name, stage, parent_id, item_used_quantity, activated, created_at) values (10, 3, 7, 1, '10개 세트', 1, null, 10, true, now());

insert into item_product_mapping (id, item_id, product_id, item_used_quantity, activated, created_at) values (1, 1, 1, 1, true, now());
insert into item_product_mapping (id, item_id, product_id, item_used_quantity, activated, created_at) values (2, 2, 1, 1, true, now());
insert into item_product_mapping (id, item_id, product_id, item_used_quantity, activated, created_at) values (3, 3, 2, 1, true, now());
insert into item_product_mapping (id, item_id, product_id, item_used_quantity, activated, created_at) values (4, 4, 2, 1, true, now());
insert into item_product_mapping (id, item_id, product_id, item_used_quantity, activated, created_at) values (5, 5, 2, 1, true, now());
insert into item_product_mapping (id, item_id, product_id, item_used_quantity, activated, created_at) values (6, 6, 2, 1, true, now());
insert into item_product_mapping (id, item_id, product_id, item_used_quantity, activated, created_at) values (7, 7, 3, 3, true, now());
insert into item_product_mapping (id, item_id, product_id, item_used_quantity, activated, created_at) values (8, 7, 3, 10, true, now());
