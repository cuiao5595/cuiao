package com.cuiao.cms.service;

import com.cuiao.cms.domain.User;
import com.cuiao.cms.utils.Codings;
import com.cuiao.cms.web.forms.UserForm;

public interface LoginService {
	
	//在接口中加盐
	static final String SALT = "la3h5G8l!9N0vo";
	//由一个静态方法 就是将值转码加盐

	public static String encrypt(String username,String password){
		return password=Codings.MD5Encoding(password+LoginService.SALT+username);
	}
	
	User login(UserForm uf);

	int reg(UserForm uf);

	int getCount(String username);

}
