package andanyoung.springboot.shardingjdbc.service.impl;

import andanyoung.springboot.shardingjdbc.domain.TOrderItem;
import andanyoung.springboot.shardingjdbc.mapper.TOrderItemMapper;
import andanyoung.springboot.shardingjdbc.service.TOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/** */
@Service
public class TOrderItemServiceImpl extends ServiceImpl<TOrderItemMapper, TOrderItem>
    implements TOrderItemService {}
