package com.boomshair.mainentrance.controller;

import com.boomshair.mainentrance.entity.Response;
import com.boomshair.mainentrance.entity.User;
import com.boomshair.mainentrance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public Mono<Response> addUser(@RequestBody User user) {
        return Mono.just(userRepository.save(user))
                .map(item -> {
                    Response response = new Response();
                    response.setErrorStatus(Response.ErrorStatus.ERROR);
                    response.setErrorNo(Response.ErrorStatus.ERROR.getErrorNo());
                    response.setMessage(item.getUsername() + "注冊成功");
                    return response;
                });
    }

    @GetMapping("/testSecurity")
    public Mono<String> java123() {
        return Mono.just("right");
    }

}
