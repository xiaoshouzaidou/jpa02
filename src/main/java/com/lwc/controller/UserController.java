package com.lwc.controller;

import com.lwc.common.Pager;
import com.lwc.common.Respon;
import com.lwc.dao.UserDao;
import com.lwc.entity.User;
import com.lwc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2019/9/5.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @PostMapping("/list")
    @ResponseBody
    public Respon list(@RequestBody Pager pager){

        return userService.list(pager);
    }
    @GetMapping("/delete/{id}")
    @ResponseBody
    public Respon delete(@PathVariable Long id){
        return userService.delete(id);
    }

    @PostMapping("/add")
    @ResponseBody
    public Respon add(@RequestBody Pager pager){
        return userService.saveOrUpdate(pager);
    }

    @RequestMapping(value="/loginInfo",method = RequestMethod.POST)
    public String login(@Param("loginName") String loginName, @Param("password") String password, HttpServletRequest request, HttpServletResponse response){
        if(loginName==null || password == null){
            return "forward:/login";
        }
        User user = userDao.findByLoginName(loginName);
        if(user==null){
            return "forward:/login";
        }
        if(user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
            System.out.println(user.getName());
            request.getSession().setAttribute("user", user);
            return "redirect:/index";
        }else{
            return "forward:/login";
        }
    }
    @RequestMapping("/index")
    public String index(Model model,HttpServletRequest request, HttpServletResponse response){
        Object user = request.getSession().getAttribute("user");
        if(user!=null){
            model.addAttribute("user",user);
        }
        return "/index";
    }
    @RequestMapping("/login")
    public String loginHtml(){
        return "/login";
    }
}
