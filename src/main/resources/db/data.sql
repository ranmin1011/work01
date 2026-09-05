INSERT INTO member_level (id, level_code, level_name, min_points, discount_rate, privileges, sort_order, status, deleted)
VALUES
(1, 'BRONZE',   '青铜会员', 0,     100.00, '["基础积分","生日祝福"]', 1, 1, 0),
(2, 'SILVER',   '白银会员', 500,   98.00,  '["基础积分","生日双倍积分","专属客服"]', 2, 1, 0),
(3, 'GOLD',     '黄金会员', 2000,  95.00,  '["基础积分","生日双倍积分","专属客服","优先发货"]', 3, 1, 0),
(4, 'PLATINUM', '铂金会员', 5000,  90.00,  '["基础积分","生日三倍积分","专属客服","优先发货","专属活动"]', 4, 1, 0),
(5, 'DIAMOND',  '钻石会员', 10000, 85.00,  '["全部权益","专属顾问","定制服务"]', 5, 1, 0)
ON DUPLICATE KEY UPDATE level_name = VALUES(level_name);
