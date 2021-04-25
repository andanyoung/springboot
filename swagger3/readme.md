# Swagger介绍

对于 Rest API 来说很重要的一部分内容就是文档，Swagger 为我们提供了一套通过代码和注解自动生成文档的方法，这一点对于保证 API 文档的及时性将有很大的帮助。

Swagger 是一套基于 OpenAPI 规范（OpenAPI Specification，OAS）构建的开源工具，可以帮助我们设计、构建、记录以及使用 Rest API。

OAS本身是一个API规范，它用于描述一整套API接口，包括一个接口是哪种请求方式、哪些参数、哪些header等，都会被包括在这个文件中。它在设计的时候通常是YAML格式，这种格式书写起来比较方便，而在网络中传输时又会以json形式居多，因为json的通用性比较强。

Swagger 主要包含了以下三个部分：

- Swagger Editor：基于浏览器的编辑器，我们可以使用它编写我们 OpenAPI 规范。

- Swagger UI：它会将我们编写的 OpenAPI 规范呈现为交互式的 API 文档，后文我将使用浏览器来查看并且操作我们的 Rest API。

- Swagger Codegen：它可以通过为 OpenAPI（以前称为 Swagger）规范定义的任何 API 生成服务器存根和客户端 SDK 来简化构建过程。

## **springfox介绍**

由于Spring的流行，Marty Pitt编写了一个基于Spring的组件swagger-springmvc，用于将swagger集成到springmvc中来，而springfox则是从这个组件发展而来。

通常SpringBoot项目整合swagger需要用到两个依赖：springfox-swagger2和springfox-swagger-ui，用于自动生成swagger文档。

- springfox-swagger2：这个组件的功能用于帮助我们自动生成描述API的json文件

- springfox-swagger-ui：就是将描述API的json文件解析出来，用一种更友好的方式呈现出来。

## **SpringFox 3.0.0 发布**

官方说明：

- SpringFox 3.0.0 发布了，SpringFox 的前身是 swagger-springmvc，是一个开源的 API doc 框架，可以将 Controller 的方法以文档的形式展现。

此版本的亮点：

- Spring5，Webflux支持（仅支持请求映射，尚不支持功能端点）。

- Spring Integration支持（非常感谢反馈）。

- SpringBoot支持springfox Boot starter依赖性（零配置、自动配置支持）。

- 具有自动完成功能的文档化配置属性。

- 更好的规范兼容性与2.0。

- 支持OpenApi 3.0.3。

- 零依赖。几乎只需要spring-plugin，swagger-core ，现有的swagger2注释将继续工作并丰富openapi3.0规范。

兼容性说明：

- 需要Java 8

- 需要Spring5.x（未在早期版本中测试）

- 需要SpringBoot 2.2+（未在早期版本中测试）

注意：

应用主类增加注解@EnableOpenApi，删除之前版本的SwaggerConfig.java。

启动项目，访问地址：http://localhost:8080/swagger-ui/index.html，注意2.x版本中访问的地址的为http://localhost:8080/swagger-ui.html

# 一、**整合使用**

添加依赖和`spring-boot-starter-parent`的版本有关，自动引入的`spring-plugin-core`包版本不一致会导致项目跑不起来，这里是个大坑。

## 1.1、2.1.x版本

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
    <exclusions>
        <exclusion>
            <artifactId>spring-plugin-core</artifactId>
            <groupId>org.springframework.plugin</groupId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.plugin</groupId>
    <artifactId>spring-plugin-core</artifactId>
    <version>2.0.0.RELEASE</version>
</dependency>
```

## 1.2、2.3.x版本

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency> 
```

# 二、配置

## 2.1、启动类

启动类添加`@EnableOpenApi`注解，然并卵，经过测试不加也可以(黑人问号脸.jpg)，到底加还是不加，看你心情吧。

```java
@EnableOpenApi
@SpringBootApplication
public class Swagger3DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Swagger3DemoApplication.class, args);
    }
}
```

## 2.2、swagger配置

可以根据`Environment`和`Profiles`对象来控制不同环境文档地址是否对外暴漏

```java
@Configuration
public class SwaggerConfig {

  public static final String VERSION = "1.0.0";

  @Bean
  public Docket createRestApi() {

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
        .paths(PathSelectors.any()) // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("admin4j接口文档")
        .description("接口文档")
        .contact(new Contact("andanyoung", "#", "1218853253@qq.com"))
        .version(VERSION)
        .build();
  }
}

```

# 三、常用注解

## 3.1、类

`@Api()`：表示这个类是 swagger 资源

- tags：表示说明内容，只写 tags 就可以省略属性名
- value：同样是说明，不过会被 tags 替代，没卵用

## 3.2、方法上

`@ApiOperation()` ：对方法的说明，注解位于方法上

- value：方法的说明
- notes：额外注释说明
- response：返回的对象
- tags：这个方法会被单独摘出来，重新分组，若没有，所有的方法会在一个Controller分组下

## 3.3、方法入参

`@ApiParam() `：对方法参数的具体说明，`用在方法入参括号里`，该注解在post请求参数时，参数名不显示

- name：参数名
- value：参数说明
- required：是否必填



`@ApiImplicitParam`对方法参数的具体说明，`用在方法上@ApiImplicitParams之内`，该注解在get,post请求参数时，参数名均正常显示

- name 参数名称
- value 参数的简短描述
- required 是否为必传参数
- dataType 参数类型，可以为类名，也可以为基本类型（String，int、boolean等）指定也不起作用，没卵用
- paramType 参数的传入（请求）类型，可选的值有path, query, body, header or form。



## 3.4、实体

`@ApiModel`描述一个Model的信息（一般用在请求参数无法使用`@ApiImplicitParam`注解进行描述的时候）

- value model的别名，默认为类名
- description model的详细描述

`@ApiModelProperty`描述一个model的属性

- value 属性简短描述
- example 属性的示例值
- required 是否为必须值

## 3.5、header参数

```java
@ApiImplicitParams({      @ApiImplicitParam(paramType="header",name="USERTOKEN",dataType="String",required=true,value="用户token")
    })
```
## 3.6、file入参

需要使用`@RequestPart` 注解

```java
@ApiOperation(value = "上传文件接口",notes = "上传文件接口")
@ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "上传人")
})
@PostMapping(value = "/uploadFile")
public String uploadFile(@RequestParam("name") String name,@RequestPart("file") MultipartFile file){
    
}
```



# 四、拦截器放行

若项目中有使用拦截器，放行以下路径

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    TokenInterceptor tokenInterceptor;

    /**
     * 拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册token拦截器
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/swagger-ui/**")
                .excludePathPatterns("/**/swagger-resources/**")
                .excludePathPatterns("/**/v3/**")
        ;
    }
}
```

# 五、文档访问地址

http://ip:port/context-path/swagger-ui/

http://ip:port/context-path/swagger-ui/index.html

