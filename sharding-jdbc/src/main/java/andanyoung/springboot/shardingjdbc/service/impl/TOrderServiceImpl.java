package andanyoung.springboot.shardingjdbc.service.impl;

import andanyoung.springboot.shardingjdbc.domain.TOrder;
import andanyoung.springboot.shardingjdbc.mapper.TOrderMapper;
import andanyoung.springboot.shardingjdbc.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/** */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {}
