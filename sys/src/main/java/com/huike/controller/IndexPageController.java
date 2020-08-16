package com.huike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexPageController {
    @RequestMapping("/")
    public String index(){
        //如果跳转index.jsp页面之前的统计的数据查询，可以写在此处

        return "index";
    }

    @RequestMapping("/welcome.do")
    public String welcome(){
        //如果跳转index.jsp页面之前的统计的数据查询，可以写在此处

        return "welcome";
    }

    /**
     *
     * 	 * 首页
     *
     @RequestMapping("/")
     public ModelAndView index(){
     *return new ModelAndView("index");
     * 	}
     *
             *    /**
      * 	 * 首页欢迎页面
      * 	 * @return 跳转到welcome.do
      *
             * 	@RequestMapping("welcome.do")
     * 	public ModelAndView welcome(){
     * 		return new ModelAndView("welcome");
     * 	}
     *
     *
     */
}
