package jdbcDemo.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserDao userDao;

    @Autowired
    public UserController(UserDao userDao){
        this.userDao = userDao;
    }

    @PostMapping("/add")
    public String adduser(@RequestParam String name, @RequestParam String email){
        User user = new User(name, email);

        boolean isInserted = userDao.insertUser(user);

        if(isInserted){
            return "Inserted Successfully";
        }else{
            return "fail";
        }
    }
}
