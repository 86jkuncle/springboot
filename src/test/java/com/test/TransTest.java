package com.test;

import org.junit.runner.RunWith;
import org.lybaobei.Main;
import org.lybaobei.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class TransTest {

    private UserServices userServices;

    @Autowired
    public TransTest(UserServices userServices){
        this.userServices = userServices;
    }
}
