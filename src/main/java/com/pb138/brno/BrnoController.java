package com.pb138.brno;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BrnoController {
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
