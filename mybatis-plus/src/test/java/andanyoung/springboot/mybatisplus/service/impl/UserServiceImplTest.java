package andanyoung.springboot.mybatisplus.service.impl;

import andanyoung.springboot.mybatisplus.entity.User;
import andanyoung.springboot.mybatisplus.mapper.UserMapper;
import andanyoung.springboot.mybatisplus.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author andanyoung
 * @version 1.0
 * @date 2021/5/19 21:20
 */
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void testGetOne() {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        wrapper.gt(User::getAge, 28);
        User one = userService.getOne(wrapper, false); // 第二参数指定为false,使得在查到了多行记录时,不抛出异常,而返回第一条记录
        System.out.println(one);
    }

    @Test
    public void testChain() {
        List<User> list = userService.lambdaQuery()
                .gt(User::getAge, 39)
                .likeRight(User::getName, "王")
                .list();
        list.forEach(System.out::println);
    }

    @Test
    public void testPage() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(User::getAge, 28);
        // 设置分页信息, 查第3页, 每页2条数据
        Page<User> page = new Page<>(3, 2);
        // 执行分页查询
        Page<User> userPage = userMapper.selectPage(page, wrapper);
        System.out.println("总记录数 = " + userPage.getTotal());
        System.out.println("总页数 = " + userPage.getPages());
        System.out.println("当前页码 = " + userPage.getCurrent());
        // 获取分页查询结果
        List<User> records = userPage.getRecords();
        records.forEach(System.out::println);
    }
}