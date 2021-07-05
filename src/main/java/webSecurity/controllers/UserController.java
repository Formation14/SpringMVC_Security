package webSecurity.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import webSecurity.models.User;
import webSecurity.service.RoleService;
import webSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
public class UserController {

    private  UserService userService;
    private  RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    
    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/admin/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/new";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") @Valid User user,
                         @RequestParam("chooseRole") String[] chooseRole) {

        userService.chooseRole(user,chooseRole);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/edit";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         @PathVariable("id") Long id,
                         @RequestParam("chooseRole") String[] chooseRole) {

        userService.chooseRole(user,chooseRole);
        userService.updateUser(id,user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/creat")
    public String creatDefaultUsers() {
       userService.creatDefaultUser();
       return "redirect:/admin";
    }

    @GetMapping("/user/show")
    public String showUserByIdForUser(Principal principal, Model model) {
        User user = userService.loadUserByUsername(principal);
        model.addAttribute("user", user);
        return "user/showUser";
    }
}
