package andanyoung.springboot.shardingjdbc.mapper;

import andanyoung.springboot.shardingjdbc.domain.TOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/** @Entity andyoung.springboot.shardingjdbc.domain.TOrder */
@Mapper
public interface TOrderMapper extends BaseMapper<TOrder> {

  List<TOrder> selectOrderAndItemByOrderId(TOrder tOrder);

  //  List<TOrder> selectOrderListPage(
  //      @Param("pageNo") Integer pageNo,
  //      @Param("pageSize") Integer pageSize,
  //      @Param("orderId") Long orderId);
  //
  //  List<TOrder> selectOrderListPage();
}
