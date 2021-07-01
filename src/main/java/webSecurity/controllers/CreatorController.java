package webSecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import webSecurity.model.Role;
import webSecurity.model.User;
import webSecurity.service.RoleService;
import webSecurity.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/creat")
public class CreatorController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @GetMapping()
    public String showAllUsers(ModelMap model) {
        List<User> list = userService.getAllUsers();
        model.addAttribute("users", list);
        return "creator/users";
    }

//    @GetMapping(value = "/")
//    public String user() {
//        return "user";
//    }

    @GetMapping(value = "/adduser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllDefRoles());
        return "creator/adduser";
    }

    @PostMapping("/creat")
    public String create(@ModelAttribute("user") @Valid User user,
                         @RequestParam("chooseRole") String[] role) {
        Set<Role> rol = new HashSet<>();
        for (String s : role) {
            if (s.equals("ADMIN")) {
                rol.add(roleService.getAdminRole());
            } else if (s.equals("USER")) {
                rol.add(roleService.getUserRole());
            }
        }
        user.setRoles(rol);
        userService.addUser(user);
        return "redirect:/creat/";
    }

    @GetMapping("/creat/{id}/edit")
    public String edit(@PathVariable("id") Integer id, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.getUser(id));
        modelMap.addAttribute("roles", roleService.getAllRoles());
        return "/creator/edit";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         @PathVariable("id") Integer id, @RequestParam("chooseRole") String[] selectedRole) {

        for (String role : selectedRole) {
            if (role.contains("USER")) {
                user.getRoles().add(roleService.getUserRole());
            } else if (role.contains("ADMIN")) {
                user.getRoles().add(roleService.getAdminRole());
            }
        }
        userService.updateUser(id, user);
        return "redirect:/creat";
    }
}
