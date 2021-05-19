package andanyoung.springboot.mybatisplus.service.impl;

import andanyoung.springboot.mybatisplus.entity.User;
import andanyoung.springboot.mybatisplus.mapper.UserMapper;
import andanyoung.springboot.mybatisplus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author andanyoung
 * @version 1.0
 * @date 2021/5/19 21:18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
