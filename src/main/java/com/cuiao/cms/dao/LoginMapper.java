package com.cuiao.cms.dao;

import com.cuiao.cms.domain.User;

public interface LoginMapper {

	User getUser(String username);

	int getCountByUname(String username);

	int reg(User user);

}
