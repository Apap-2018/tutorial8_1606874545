package com.apap.tutorial8.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial8.model.PasswordModel;
import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user, Model model) {
		Boolean passValidation= validatePassword(user.getPassword());
		if(passValidation==true) {
			userService.addUser(user);
		}
		else {
			model.addAttribute("message", "Password Too Weak");
		}	
		
		
		
		return "home";
	}
	
	@RequestMapping(value="/updatePassword", method=RequestMethod.POST)
	private String updatePassword(@ModelAttribute PasswordModel passModel, Authentication auth, Model model) {
		
		Boolean status = userService.cekPassword(auth.getName(),passModel);
		if(status==true) {
			Boolean passValidation= validatePassword(passModel.getPasswordBaru());
			if(passValidation==true) {
				userService.updatePassword(auth.getName(),passModel);
			}
			else {
				model.addAttribute("message", "Password Too Weak");
			}	
		}
		else {
			model.addAttribute("message", "Password Salah");
			return "home";
			
		}
		return "home";
	}
	
	public boolean validatePassword(String password) {
		if (password.length()>=8 && Pattern.compile("[0-9]").matcher(password).find() &&  Pattern.compile("[a-zA-Z]").matcher(password).find())  {
			return true;
		}
		else {
			return false;
		}
	}
	
}
