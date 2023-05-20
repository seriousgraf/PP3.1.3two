package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userServiceImpl;
    @Autowired
    public AdminController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userServiceImpl.allUsers());
        return "Admin";
    }

    @GetMapping("/user-page/{id}")
    public String findUserById(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userServiceImpl.findUserById(id).get());
        return "showUser";
    }

    @GetMapping("/new")
    public String showAddUserPage(Model model) {
        model.addAttribute("user", new User());

        return "NewUser";
    }

    @PostMapping("/create")
    public String addUser(@ModelAttribute("user") User user) {
        userServiceImpl.saveUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/update/{id}")
    public String showUpdateUserPage(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userServiceImpl.findUserById(id).get());
        return "EditUser";
    }

    @PatchMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        User oldUser = userServiceImpl.findUserById(id).get();
        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(user.getPassword());
        oldUser.setEmail(user.getEmail());
        userServiceImpl.edit(id, oldUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin";
    }


}
