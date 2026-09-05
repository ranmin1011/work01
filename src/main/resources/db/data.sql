INSERT INTO member_level (id, level_code, level_name, min_points, discount_rate, privileges, sort_order, status, deleted)
VALUES
(1, 'BRONZE',   '青铜会员', 0,     100.00, '["基础积分","生日祝福"]', 1, 1, 0),
(2, 'SILVER',   '白银会员', 500,   98.00,  '["基础积分","生日双倍积分","专属客服"]', 2, 1, 0),
(3, 'GOLD',     '黄金会员', 2000,  95.00,  '["基础积分","生日双倍积分","专属客服","优先发货"]', 3, 1, 0),
(4, 'PLATINUM', '铂金会员', 5000,  90.00,  '["基础积分","生日三倍积分","专属客服","优先发货","专属活动"]', 4, 1, 0),
(5, 'DIAMOND',  '钻石会员', 10000, 85.00,  '["全部权益","专属顾问","定制服务"]', 5, 1, 0)
ON DUPLICATE KEY UPDATE level_name = VALUES(level_name);

INSERT INTO coupon (id, coupon_code, coupon_name, coupon_type, threshold_amount, discount_amount, total_count, claimed_count, per_member_limit, valid_from, valid_to, status, deleted)
VALUES
(1, 'NEW100', '������100��10', 'FULL_REDUCTION', 100.00, 10.00, 1000, 0, 1, NOW(), DATE_ADD(NOW(), INTERVAL 90 DAY), 1, 0),
(2, 'CASH5',  '5Ԫ����ȯ',     'CASH',           0.00,   5.00,  500,  0, 1, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), 1, 0)
ON DUPLICATE KEY UPDATE coupon_name = VALUES(coupon_name);
