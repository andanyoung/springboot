package andanyoung.springboot.mybatisplus.mapper;

import andanyoung.springboot.mybatisplus.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @author andanyoung
 * @version 1.0
 * @date 2021/5/19 20:43
 */

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }

    @Test
    public void test3() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name", "email").likeRight("name", "黄");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    // 按照直属上级进行分组，查询每组的平均年龄，最大年龄，最小年龄

    /**
     * select avg(age) avg_age ,min(age) min_age, max(age) max_age from user group by manager_id having sum(age) < 500;
     **/

    @Test
    public void test4() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("manager_id", "avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id").having("sum(age) < {0}", 500);
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    @Test
    public void test5() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("name", "id").like("name", "黄");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }
}