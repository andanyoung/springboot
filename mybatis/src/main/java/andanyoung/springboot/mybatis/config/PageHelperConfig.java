package andanyoung.springboot.mybatis.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class PageHelperConfig {

  /**
   * 下面几个参数都是针对默认 dialect 情况下的参数。使用自定义 dialect 实现时，下面的参数没有任何作用。
   *
   * <p>helperDialect：分页插件会自动检测当前的数据库链接，自动选择合适的分页方式。
   * 你可以配置helperDialect属性来指定分页插件使用哪种方言。配置时，可以使用下面的缩写值：
   * oracle,mysql,mariadb,sqlite,hsqldb,postgresql,db2,sqlserver,informix,h2,sqlserver2012,derby
   * 特别注意：使用 SqlServer2012 数据库时，需要手动指定为 sqlserver2012，否则会使用 SqlServer2005 的方式进行分页。 你也可以实现
   * AbstractHelperDialect，然后配置该属性为实现类的全限定名称即可使用自定义的实现方法。
   *
   * <p>offsetAsPageNum：默认值为 false，该参数对使用 RowBounds 作为分页参数时有效。 当该参数设置为 true 时，会将 RowBounds 中的 offset
   * 参数当成 pageNum 使用，可以用页码和页面大小两个参数进行分页。
   *
   * <p>rowBoundsWithCount：默认值为false，该参数对使用 RowBounds 作为分页参数时有效。 当该参数设置为true时，使用 RowBounds 分页会进行
   * count 查询。
   *
   * <p>pageSizeZero：默认值为 false，当该参数设置为 true 时，如果 pageSize=0 或者 RowBounds.limit = 0
   * 就会查询出全部的结果（相当于没有执行分页查询，但是返回结果仍然是 Page 类型）。
   *
   * <p>reasonable：分页合理化参数，默认值为false。当该参数设置为 true 时，pageNum<=0 时会查询第一页，
   * pageNum>pages（超过总数时），会查询最后一页。默认false 时，直接根据参数进行查询。
   *
   * <p>params：为了支持startPage(Object params)方法，增加了该参数来配置参数映射，用于从对象中根据属性名取值， 可以配置
   * pageNum,pageSize,count,pageSizeZero,reasonable，不配置映射的用默认值，
   * 默认值为pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero。
   *
   * <p>supportMethodsArguments：支持通过 Mapper 接口参数来传递分页参数，默认值false，分页插件会从查询方法的参数值中，自动根据上面 params
   * 配置的字段中取值，查找到合适的值时就会自动分页。 使用方法可以参考测试代码中的 com.github.pagehelper.test.basic 包下的 ArgumentsMapTest 和
   * ArgumentsObjTest。
   *
   * <p>autoRuntimeDialect：默认值为 false。设置为 true 时，允许在运行时根据多数据源自动识别对应方言的分页
   * （不支持自动选择sqlserver2012，只能使用sqlserver），用法和注意事项参考下面的场景五。
   *
   * <p>closeConn：默认值为 true。当使用运行时动态数据源或没有设置 helperDialect 属性自动获取数据库类型时，会自动获取一个数据库连接，
   * 通过该属性来设置是否关闭获取的这个连接，默认true关闭，设置为 false 后，不会关闭获取的连接，这个参数的设置要根据自己选择的数据源来决定。
   *
   * <p>aggregateFunctions(5.1.5+)：默认为所有常见数据库的聚合函数，允许手动添加聚合函数（影响行数），所有以聚合函数开头的函数，在进行 count
   * 转换时，会套一层。其他函数和列会被替换为 count(0)，其中count列可以自己配置。
   *
   * @return
   */
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
