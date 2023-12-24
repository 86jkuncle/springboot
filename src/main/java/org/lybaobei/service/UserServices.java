package org.lybaobei.service;

import java.util.List;
import org.lybaobei.entity.Users;
import org.springframework.stereotype.Service;


public interface UserServices {

    List<Users> findAll();
}
