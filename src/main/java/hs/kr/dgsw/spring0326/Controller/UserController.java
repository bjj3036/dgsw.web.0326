package hs.kr.dgsw.spring0326.Controller;

import hs.kr.dgsw.spring0326.Domain.User;
import hs.kr.dgsw.spring0326.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/listUser")
    public List<User> listUser(){
        return this.userService.listUser();
    }

    @GetMapping("/findUser/{id}")
    public User findUser(@PathVariable Long id){
        return this.userService.findUser(id);
    }

    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User user){
        return this.userService.updateUser(user);
    }

    @DeleteMapping("/deleteUser/{id}")
    public boolean deleteUser(@PathVariable Long id){
        return this.userService.deleteUser(id);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user){
        return this.userService.login(user);
    }
}
