package com.neqrofukk.githubrepo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GitHubRepo {

    public static void main(String[] args) {
        SpringApplication.run(GitHubRepo.class, args);
    }

}
