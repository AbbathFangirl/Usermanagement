package de.vh.usermanagementdb.controller;

import de.vh.usermanagementdb.model.User;
import de.vh.usermanagementdb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController  {


    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/all")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "allusers";
    }

    @GetMapping("/user/add")
    public String showAddUser(Model model) {
        model.addAttribute("user", new User());
        return "newuser";
    }

    @PostMapping("/user/add")
    public String processAddUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        userRepository.save(user);
        return "redirect:/user/all";
    }

    @GetMapping("/user/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "edituser";
    }

    @PostMapping("/user/edit/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "redirect:/user/all";
        }
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/all";
    }

    @GetMapping("/user/delete/{id}")
    public String showDeleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "redirect:/user/all";
    }

    @PostMapping("/user/delete/{id}")
    public String processDeleteUser(@PathVariable("id") Integer id, @Valid User user,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "redirect:/user/all";
        }
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/all";
    }








}
