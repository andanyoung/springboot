## 1. MyBatis 介绍

大家都知道，MyBatis 框架是一个持久层框架，是 Apache 下的顶级项目。Mybatis 可以让开发者的主要精力放在 sql 上，通过 Mybatis 提供的映射方式，自由灵活的生成满足需要的 sql 语句。使用简单的 XML
或注解来配置和映射原生信息，将接口和 Java 的 POJOs 映射成数据库中的记录，在国内可谓是占据了半壁江山。本节课程主要通过两种方式来对 Spring Boot 集成 MyBatis
做一讲解。重点讲解一下基于注解的方式。因为实际项目中使用注解的方式更多一点，更简洁一点，省去了很多 xml 配置（这不是绝对的，有些项目组中可能也在使用 xml 的方式）。

## 2. MyBatis 的配置

## 2.1 依赖导入

Spring Boot 集成 MyBatis，需要导入 `mybatis-spring-boot-starter` 和 mysql 的依赖，这里我们使用的版本时 1.3.2，如下：

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.4</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

我们点开 `mybatis-spring-boot-starter` 依赖，可以看到我们之前使用 Spring 时候熟悉的依赖，就像我在课程的一开始介绍的那样，Spring Boot 致力于简化编码，使用 starter
系列将相关依赖集成在一起，开发者不需要关注繁琐的配置，非常方便。

```
<!-- 省去其他 -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
</dependency>
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
</dependency>
```

## 2.2 properties.yml配置

我们再来看一下，集成 MyBatis 时需要在 properties.yml 配置文件中做哪些基本配置呢？

```
# 服务端口号
server:
  port: 8080

spring:
  datasource: # 数据库配置
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/admin4j?useSSL=false&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&maxReconnects=10
    username: root
    password: 123456

mybatis:
  # 指定别名设置的包为所有entity
  configuration:
    map-underscore-to-camel-case: true # 驼峰命名规范
  mapper-locations: # mapper映射文件位置
    - classpath:mapper/*.xml
  type-aliases-package: andanyoung.springboot.mybatis.entity
```

我们来简单介绍一下上面的这些配置：关于数据库的相关配置，我就不详细的解说了，这点相信大家已经非常熟练了，配置一下用户名、密码、数据库连接等等，这里使用的连接池是 Spring Boot 自带的
hikari，感兴趣的朋友可以去百度或者谷歌搜一搜，了解一下。

这里说明一下 `map-underscore-to-camel-case: true`， 用来开启驼峰命名规范，这个比较好用，比如数据库中字段名为：`user_name`， 那么在实体类中可以定义属性为 `userName`
（甚至可以写成 `username`，也能映射上），会自动匹配到驼峰属性，如果不这样配置的话，针对字段名和属性名不同的情况，会映射不到。

## 3. 基于 xml 的整合

使用原始的 xml 方式，需要新建 UserMapper.xml 文件，在上面的 application.yml 配置文件中，我们已经定义了 xml 文件的路径：`classpath:mapper/*.xml`，所以我们在
resources 目录下新建一个 mapper 文件夹，然后创建一个 UserMapper.xml 文件。

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="andanyoung.springboot.mybatis.dao.UserMapper">
    <resultMap id="BaseResultMap" type="andanyoung.springboot.mybatis.entity.User">

        <id column="uid" jdbcType="INTEGER" property="uid"/>
        <result column="name" jdbcType="VARCHAR" property="name"/>
        <result column="password" jdbcType="VARCHAR" property="password"/>
    </resultMap>

    <select id="getUserByName" resultType="User" parameterType="String">
        select *
        from sys_user
        where `name` = #{username}
    </select>
</mapper>
```

这和整合 Spring 一样的，namespace 中指定的是对应的 Mapper， `<resultMap>` 中指定对应的实体类，即 User。然后在内部指定表的字段和实体的属性相对应即可。这里我们写一个根据用户名查询用户的 sql。

实体类中有 id，username 和 password，我不在这贴代码，大家可以下载源码查看。UserMapper.java 文件中写一个接口即可：

```
User getUserByName(String username);
```

中间省略 service 的代码，我们写一个 Controller 来测试一下：

```
@RestController
public class TestController {

    @Resource
    private UserService userService;
    
    @RequestMapping("/getUserByName/{name}")
    public User getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }
}
```

启动项目，在浏览器中输入：`http://localhost:8080/getUserByName/admin` 即可查询到数据库表中用户名为 admin的用户信息（事先搞两个数据进去即可）：

```
{"id":1,"name":"admin","password":"123456"}
```

这里需要注意一下：Spring Boot 如何知道这个 Mapper 呢？一种方法是在上面的 mapper 层对应的类上面添加 `@Mapper` 注解即可，但是这种方法有个弊端，当我们有很多个 mapper
时，那么每一个类上面都得添加 `@Mapper` 注解。另一种比较简便的方法是在 Spring Boot 启动类上添加`@MaperScan` 注解，来扫描一个包下的所有 mapper。如下：

```
@SpringBootApplication
@MapperScan("andanyoung.springboot.mybatis.dao")
public class MybatisApplication {

  public static void main(String[] args) {
    SpringApplication.run(MybatisApplication.class, args);
  }
}
```

这样的话，`com.itcodai.course10.dao` 包下的所有 mapper 都会被扫描到了。

## 4. 基于注解的整合

基于注解的整合就不需要 xml 配置文件了，MyBatis 主要提供了 `@Select`， `@Insert`， `@Update`， `Delete` 四个注解。这四个注解是用的非常多的，也很简单，注解后面跟上对应的 sql
语句即可，我们举个例子：

```
@Select("select * from sys_user where id = #{id}")
User getUser(Long id);
```

这跟 xml 文件中写 sql 语句是一样的，这样就不需要 xml 文件了，但是有个问题，有人可能会问，如果是两个参数呢？如果是两个参数，我们需要使用 `@Param` 注解来指定每一个参数的对应关系，如下：

```
@Select("select * from user sys_user  id = #{id} and name=#{name}")
User getUserByIdAndName(@Param("id") Long id, @Param("name") String username);
```

可以看出，`@Param` 指定的参数应该要和 sql 中 `#{}` 取的参数名相同，不同则取不到。可以在 controller 中自行测试一下，接口都在源码中，文章中我就不贴测试代码和结果了。

有个问题需要注意一下，一般我们在设计表字段后，都会根据自动生成工具生成实体类，这样的话，基本上实体类是能和表字段对应上的，最起码也是驼峰对应的，由于在上面配置文件中开启了驼峰的配置，所以字段都是能对的上的。但是，万一有对不上的呢？我们也有解决办法，使用 `@Results`
注解来解决。

```
@Select("select * from sys_user where id = #{id}")
@Results({
        @Result(property = "name", column = "name"),
        @Result(property = "password", column = "password")
})
User getUser(Long id);
```

`@Results` 中的 `@Result` 注解是用来指定每一个属性和字段的对应关系，这样的话就可以解决上面说的这个问题了。

当然了，我们也可以 xml 和注解相结合使用，目前我们实际的项目中也是采用混用的方式，因为有时候 xml 方便，有时候注解方便，比如就上面这个问题来说，如果我们定义了上面的这个
UserMapper.xml，那么我们完全可以使用 `@ResultMap` 注解来替代 `@Results` 注解，如下：

```
@Select("select * from sys_user where id = #{id}")
@ResultMap("BaseResultMap")
User getUser(Long id);
```

`@ResultMap` 注解中的值从哪来呢？对应的是 UserMapper.xml 文件中定义的 `<resultMap>` 时对应的 id 值：

```
<resultMap id="BaseResultMap" type="com.itcodai.course10.entity.User">
```

这种 xml 和注解结合着使用的情况也很常见，而且也减少了大量的代码，因为 xml 文件可以使用自动生成工具去生成，也不需要人为手动敲，所以这种使用方式也很常见。

## 5. 总结

本节课主要系统的讲解了 Spring Boot 集成 MyBatis 的过程，分为基于 xml 形式和基于注解的形式来讲解，通过实际配置手把手讲解了 Spring Boot 中 MyBatis
的使用方式，并针对注解方式，讲解了常见的问题已经解决方式，有很强的实战意义。在实际项目中，建议根据实际情况来确定使用哪种方式，一般 xml 和注解都在用。

## 6 分页

```
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.3.0</version>
</dependency>
```

### 配置

### java配置

```
@Configuration
public class PageHelperConfig {

  @Bean
  public PageHelper pageHelper() {
    PageHelper pageHelper = new PageHelper();
    Properties properties = new Properties();
    // ：默认值为 false，该参数对使用 RowBounds 作为分页参数时有效。 当该参数设置为 true 时，会将 RowBounds 中的 offset 参数当成 pageNum
    // 使用，可以用页码和页面大小两个参数进行分页。
    properties.setProperty("offsetAsPageNum", "true");
    // 默认值为false，该参数对使用 RowBounds 作为分页参数时有效。 当该参数设置为true时，使用 RowBounds 分页会进行 count 查询。
    properties.setProperty("rowBoundsWithCount", "true");
    properties.setProperty("reasonable", "true");
    // 配置mysql数据库的方言
    properties.setProperty("dialect", "mysql");
    pageHelper.setProperties(properties);
    return pageHelper;
  }
}
```

### **aplication.yml添加配置**

```
#分页pageHelper
pagehelper:
  helper-dialect: mysql
  reasonable: true
  support-methods-arguments: true
```

### 配置参数说明

> **下面几个参数都是针对默认 dialect 情况下的参数。使用自定义 dialect 实现时，下面的参数没有任何作用。**
>
> 1. `helperDialect`：分页插件会自动检测当前的数据库链接，自动选择合适的分页方式。 你可以配置`helperDialect`属性来指定分页插件使用哪种方言。配置时，可以使用下面的缩写值：
     >    `oracle`,`mysql`,`mariadb`,`sqlite`,`hsqldb`,`postgresql`,`db2`,`sqlserver`,`informix`,`h2`,`sqlserver2012`,`derby`
     >    **特别注意：**使用 SqlServer2012 数据库时，需要手动指定为 `sqlserver2012`，否则会使用 SqlServer2005 的方式进行分页。
     >    你也可以实现 `AbstractHelperDialect`，然后配置该属性为实现类的全限定名称即可使用自定义的实现方法。
> 2. `offsetAsPageNum`：默认值为 `false`，该参数对使用 `RowBounds` 作为分页参数时有效。 当该参数设置为 `true` 时，会将 `RowBounds` 中的 `offset` 参数当成 `pageNum` 使用，可以用页码和页面大小两个参数进行分页。
> 3. `rowBoundsWithCount`：默认值为`false`，该参数对使用 `RowBounds` 作为分页参数时有效。 当该参数设置为`true`时，使用 `RowBounds` 分页会进行 count 查询。
> 4. `pageSizeZero`：默认值为 `false`，当该参数设置为 `true` 时，如果 `pageSize=0` 或者 `RowBounds.limit = 0` 就会查询出全部的结果（相当于没有执行分页查询，但是返回结果仍然是 `Page` 类型）。
> 5. `reasonable`：分页合理化参数，默认值为`false`。当该参数设置为 `true` 时，`pageNum<=0` 时会查询第一页， `pageNum>pages`（超过总数时），会查询最后一页。默认`false` 时，直接根据参数进行查询。
> 6. `params`：为了支持`startPage(Object params)`方法，增加了该参数来配置参数映射，用于从对象中根据属性名取值， 可以配置 `pageNum,pageSize,count,pageSizeZero,reasonable`，不配置映射的用默认值， 默认值为`pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero`。
> 7. `supportMethodsArguments`：支持通过 Mapper 接口参数来传递分页参数，默认值`false`，分页插件会从查询方法的参数值中，自动根据上面 `params` 配置的字段中取值，查找到合适的值时就会自动分页。 使用方法可以参考测试代码中的 `com.github.pagehelper.test.basic` 包下的 `ArgumentsMapTest` 和 `ArgumentsObjTest`。
> 8. `autoRuntimeDialect`：默认值为 `false`。设置为 `true` 时，允许在运行时根据多数据源自动识别对应方言的分页 （不支持自动选择`sqlserver2012`，只能使用`sqlserver`），用法和注意事项参考下面的**场景五**。
> 9. `closeConn`：默认值为 `true`。当使用运行时动态数据源或没有设置 `helperDialect` 属性自动获取数据库类型时，会自动获取一个数据库连接， 通过该属性来设置是否关闭获取的这个连接，默认`true`关闭，设置为 `false` 后，不会关闭获取的连接，这个参数的设置要根据自己选择的数据源来决定。
> 10. `aggregateFunctions`(5.1.5+)：默认为所有常见数据库的聚合函数，允许手动添加聚合函数（影响行数），所有以聚合函数开头的函数，在进行 count 转换时，会套一层。其他函数和列会被替换为 count(0)，其中count列可以自己配置。

**重要提示：**

当 `offsetAsPageNum=false` 的时候，由于 `PageNum` 问题，`RowBounds`查询的时候 `reasonable` 会强制为 `false`。使用 `PageHelper.startPage`
方法不受影响。

#### 4. 如何选择配置这些参数

单独看每个参数的说明可能是一件让人不爽的事情，这里列举一些可能会用到某些参数的情况。

##### 场景一

如果你仍然在用类似ibatis式的命名空间调用方式，你也许会用到`rowBoundsWithCount`， 分页插件对`RowBounds`支持和 MyBatis 默认的方式是一致，默认情况下不会进行 count
查询，如果你想在分页查询时进行 count 查询， 以及使用更强大的 `PageInfo` 类，你需要设置该参数为 `true`。

**注：** `PageRowBounds` 想要查询总数也需要配置该属性为 `true`。

##### 场景二

如果你仍然在用类似ibatis式的命名空间调用方式，你觉得 `RowBounds` 中的两个参数 `offset,limit` 不如 `pageNum,pageSize` 容易理解， 你可以使用 `offsetAsPageNum`
参数，将该参数设置为 `true` 后，`offset`会当成 `pageNum` 使用，`limit` 和 `pageSize` 含义相同。

##### 场景三

如果觉得某个地方使用分页后，你仍然想通过控制参数查询全部的结果，你可以配置 `pageSizeZero` 为 `true`， 配置后，当 `pageSize=0` 或者 `RowBounds.limit = 0` 就会查询出全部的结果。

##### 场景四

如果你分页插件使用于类似分页查看列表式的数据，如新闻列表，软件列表， 你希望用户输入的页数不在合法范围（第一页到最后一页之外）时能够正确的响应到正确的结果页面， 那么你可以配置 `reasonable` 为 `true`
，这时如果 `pageNum<=0` 会查询第一页，如果 `pageNum>总页数` 会查询最后一页。

##### 场景五

如果你在 Spring 中配置了动态数据源，并且连接不同类型的数据库，这时你可以配置 `autoRuntimeDialect` 为 `true`，这样在使用不同数据源时，会使用匹配的分页进行查询。
这种情况下，你还需要特别注意 `closeConn` 参数，由于获取数据源类型会获取一个数据库连接，所以需要通过这个参数来控制获取连接后，是否关闭该连接。 默认为 `true`
，有些数据库连接关闭后就没法进行后续的数据库操作。而有些数据库连接不关闭就会很快由于连接数用完而导致数据库无响应。所以在使用该功能时，特别需要注意你使用的数据源是否需要关闭数据库连接。

当不使用动态数据源而只是自动获取 `helperDialect` 时，数据库连接只会获取一次，所以不需要担心占用的这一个连接是否会导致数据库出错，但是最好也根据数据源的特性选择是否关闭连接。

### 分页常规使用

```
PageHelper.startPage(page, size);
List<UserLoginRecord> userLoginRecords = userLoginRecordMapper.selectList();
PageVo<UserLoginRecord> userLoginRecordPageInfo = new PageVo<>(userLoginRecords);
return userLoginRecordPageInfo;
```

### 代码中其他方式使用

> 分页插件支持以下几种调用方式：

```
//第一种，RowBounds方式的调用
List<User> list = sqlSession.selectList("x.y.selectIf", null, new RowBounds(0, 10));

//第二种，Mapper接口方式的调用，推荐这种使用方式。
PageHelper.startPage(1, 10);
List<User> list = userMapper.selectIf(1);

//第三种，Mapper接口方式的调用，推荐这种使用方式。
PageHelper.offsetPage(1, 10);
List<User> list = userMapper.selectIf(1);

//第四种，参数方法调用
//存在以下 Mapper 接口方法，你不需要在 xml 处理后两个参数
public interface CountryMapper {
    List<User> selectByPageNumSize(
            @Param("user") User user,
            @Param("pageNum") int pageNum, 
            @Param("pageSize") int pageSize);
}
//配置supportMethodsArguments=true
//在代码中直接调用：
List<User> list = userMapper.selectByPageNumSize(user, 1, 10);

//第五种，参数对象
//如果 pageNum 和 pageSize 存在于 User 对象中，只要参数有值，也会被分页
//有如下 User 对象
public class User {
    //其他fields
    //下面两个参数名和 params 配置的名字一致
    private Integer pageNum;
    private Integer pageSize;
}
//存在以下 Mapper 接口方法，你不需要在 xml 处理后两个参数
public interface CountryMapper {
    List<User> selectByPageNumSize(User user);
}
//当 user 中的 pageNum!= null && pageSize!= null 时，会自动分页
List<User> list = userMapper.selectByPageNumSize(user);

//第六种，ISelect 接口方式
//jdk6,7用法，创建接口
Page<User> page = PageHelper.startPage(1, 10).doSelectPage(new ISelect() {
    @Override
    public void doSelect() {
        userMapper.selectGroupBy();
    }
});
//jdk8 lambda用法
Page<User> page = PageHelper.startPage(1, 10).doSelectPage(()-> userMapper.selectGroupBy());

//也可以直接返回PageInfo，注意doSelectPageInfo方法和doSelectPage
pageInfo = PageHelper.startPage(1, 10).doSelectPageInfo(new ISelect() {
    @Override
    public void doSelect() {
        userMapper.selectGroupBy();
    }
});
//对应的lambda用法
pageInfo = PageHelper.startPage(1, 10).doSelectPageInfo(() -> userMapper.selectGroupBy());

//count查询，返回一个查询语句的count数
long total = PageHelper.count(new ISelect() {
    @Override
    public void doSelect() {
        userMapper.selectLike(user);
    }
});
//lambda
total = PageHelper.count(()->userMapper.selectLike(user));
```

### 官网

- https://github.com/pagehelper/Mybatis-PageHelper

使用文档：

- https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md

mybatis教程 ：

- https://www.w3cschool.cn/mybatis/