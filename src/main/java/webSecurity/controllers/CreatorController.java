package webSecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import webSecurity.model.User;
import webSecurity.service.RoleService;
import webSecurity.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class CreatorController {


    private UserService userService;
    private RoleService roleService;

    @Autowired
    public CreatorController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }


    @GetMapping("/admin")
    public String allUsers(Model model) {
        model.addAttribute("people", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/admin/adduser")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/adduser";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") @Valid User user, @RequestParam("selectedRole") String[] selectedRole,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "admin/adduser";

        for (String role : selectedRole
        ) {
            if (role.contains("ROLE_USER")) {
                user.getRoles().add(roleService.getUserRole());
            } else if (role.contains("ROLE_ADMIN")) {
                user.getRoles().add(roleService.getAdminRole());
            }
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/edit";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") Integer id,
                         @RequestParam("selectedRole") String[] selectedRole) {
        if (bindingResult.hasErrors())
            return "admin/edit";

        for (String role : selectedRole) {
            if (role.contains("USER")) {
                user.getRoles().add(roleService.getUserRole());
            } else if (role.contains("ADMIN")) {
                user.getRoles().add(roleService.getAdminRole());
            }
        }
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    //Создаем пользователей по умолчанию admin, user
    @GetMapping("/creatDefaultUsers")
    public String creatDefaultUsers() {
        roleService.setRolesDefault();

        User admin = new User();
        admin.setEmail("admin@email.com");
        admin.setName("admin");
        admin.setPassword("admin");
        admin.getRoles().add(roleService.getAdminRole());

        userService.addUser(admin);

        return "redirect:/admin";
    }

    @GetMapping("/user/show")
    public String showUserByIdForUser(Principal principal, Model model) {
        User user = userService.loadUserByUsername(principal);
        model.addAttribute("user", user);
        return "user/showUser";
    }
}
