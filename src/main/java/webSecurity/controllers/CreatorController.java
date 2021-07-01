package webSecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import webSecurity.model.Role;
import webSecurity.model.User;
import webSecurity.service.RoleService;
import webSecurity.service.UserService;

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
        model.addAttribute("roles", roleService.getAllRoles());
        return "creator/adduser";
    }


//    @RequestMapping(value = "/creat", method = RequestMethod.POST)
//    public String addUserBd(@ModelAttribute("user") User user,
//                            @RequestParam(value = "roles", required = false) String[] role) {
//        Set<Role> rol = new HashSet<>();
//        for (String s : role) {
//            if (s.equals("ADMIN")) {
//                rol.add(roleService.getAllRoles().get(0));
//            } else if (s.equals("USER")) {
//                rol.add(roleService.getAllRoles().get(1));
//            }
//        }
//
//        user.setRoles(rol);
//        userService.addUser(user);
//        return "redirect:/creat/users";
//    }
    @Transactional
    @RequestMapping(value = "/creat", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam(value = "roles", required = false) String[] role) {
        Set<Role> rol = new HashSet<>();
        for (String s : role) {
            if (s.equals("ADMIN")) {
                rol.add(roleService.getAllRoles().get(0));
            } else if (s.equals("USER")) {
                rol.add(roleService.getAllRoles().get(1));
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

    @PatchMapping(value = "/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "roles", required = false) String[] role) {

        userService.update(user, role);
        return "redirect:/creator/users";
    }
}
