package webSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import webSecurity.dao.UserDao;
import webSecurity.model.User;
import webSecurity.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(value = "/admin")
    public String showAllUsers(ModelMap model) {
        List<User> list = userService.getAllUsers();
        model.addAttribute("users", list);
        return "users";
    }

    @GetMapping(value = "/user")
    public String user() {
        return "user";
    }

    @GetMapping(value = "/admin/adduser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "adduser";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String editUser(Model model,
                           @PathVariable("id") Integer id) {
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @PatchMapping("admin/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("admin/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(userService.getUser(id));
        return "redirect:/admin";
    }
}
