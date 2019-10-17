package com.cuiao.cms.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cuiao.cms.dao.LoginMapper;
import com.cuiao.cms.domain.User;
import com.cuiao.cms.service.LoginService;
import com.cuiao.cms.utils.AssertUtil;
import com.cuiao.cms.web.forms.UserForm;
import com.fasterxml.jackson.databind.util.BeanUtil;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Resource
	private LoginMapper lm;

	@Override
	public User login(UserForm uf) {
		//当用户名为空时
		AssertUtil.hasLength(uf.getUsername(), "用户名不能为空");
		//当密码为空时
		AssertUtil.hasLength(uf.getPassword(), "密码不能为空");
		//通过userfrom中的username查询user对象
		int i = lm.getCountByUname(uf.getUsername());
		AssertUtil.isFalse(i<=0, "用户名不存在");
		User user= lm.getUser(uf.getUsername());
		//将user对象的密码和userform对象中的密码比对(userform密码必须先加密)
		String string = LoginService.encrypt(uf.getUsername(), uf.getPassword());
		AssertUtil.isTrue(user.getPassword().equals(string), "密码错误");
		//将user对象返回前台,放到session中
		User user2 = new User();
		BeanUtils.copyProperties(user, user2,"password");
		return user2;
	}

	@Override
	public int reg(UserForm uf) {
		AssertUtil.hasLength(uf.getUsername(), "用户名不能为空");
		AssertUtil.hasLength(uf.getPassword(), "密码不能为空");
		AssertUtil.isTrue(uf.getRePassword().equals(uf.getPassword()),"两次密码输入不一致");
		
		int i = lm.getCountByUname(uf.getUsername());
		AssertUtil.isTrue(i<=0, "用户名已存在");
		
		//创建user对象
		User user = new User();
		user.setUsername(uf.getUsername());
		user.setNickname(uf.getUsername());
		user.setLocked(false);
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		user.setGender(uf.getGender());
		user.setPassword(LoginService.encrypt(uf.getUsername(), uf.getPassword()));
		int j =lm.reg(user); 
		return j;
	}

	@Override
	public int getCount(String username) {
		int i = lm.getCountByUname(username);
		return i;
	}
	
}
