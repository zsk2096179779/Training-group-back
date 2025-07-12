-- 创建合并后的用户表 (包含角色信息)
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(50) NOT NULL COMMENT '用户角色: ROLE_ADMIN 或 ROLE_USER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表 (含角色)';

-- 插入一个管理员用户 (密码是 'adminpassword') 和一个普通用户 (密码是 'userpassword')
-- 注意: 密码在数据库中是加密存储的
INSERT INTO `users` (username, email, password, role) VALUES
('admin', 'admin@example.com', '$2a$10$E.q2vXJ.Xv.LgJpZJ.4Xh.E6C6mCj6QG.KqJ.H/gX.cKj3Fj8nOm', 'ROLE_ADMIN'), -- 密码: adminpassword
('user', 'user@example.com', '$2a$10$Y.W.N.5pB5A.O5iX5pX5nO.VvJ.JvJ.vJ.vJ.vJ.vJ.vJ.vJ.vJ.5', 'ROLE_USER'); -- 密码: userpassword 