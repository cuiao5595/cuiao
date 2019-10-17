package com.cuiao.cms.web.controllers;

import java.lang.ProcessBuilder.Redirect;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cuiao.cms.core.CMSRuntimeException;
import com.cuiao.cms.dao.LoginMapper;
import com.cuiao.cms.domain.User;
import com.cuiao.cms.service.LoginService;
import com.cuiao.cms.web.Constant;
import com.cuiao.cms.web.forms.UserForm;

@Controller
public class LoginController {

	@Resource
	private LoginService service;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("userForm", new UserForm());
		return "passport/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, UserForm uf, HttpServletRequest request) {
		model.addAttribute("userForm", new UserForm());
		// 获取user Form对象
		// 将user对象放入session中
		try {
			User u = service.login(uf);
			Constant.setLoginUser(request, u);
			return "redirect:home";
			} catch (CMSRuntimeException e) {
				model.addAttribute("message", e.getMessage());
			}
			return "passport/login";
		
	}

	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String reg(Model model) {
		model.addAttribute("userForm", new UserForm());
		return "passport/reg";
	}
	
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String reg(Model model,UserForm uf,RedirectAttributes attr) {
		// 获取前台信息给注册提供数据
		try {
			int i =service.reg(uf);
			attr.addFlashAttribute("message", "注册成功");
			//将成功的信息展示出来
			return "redirect:login";
		} catch (CMSRuntimeException e) {
			model.addAttribute("message", e.getMessage());
		}
		return "passport/reg";
	}

	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request){
		request.getSession().invalidate();
		return "redirect:home";
	}
	
}
