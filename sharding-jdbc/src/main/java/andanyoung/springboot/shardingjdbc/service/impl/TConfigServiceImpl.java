package andanyoung.springboot.shardingjdbc.service.impl;

import andanyoung.springboot.shardingjdbc.domain.TConfig;
import andanyoung.springboot.shardingjdbc.mapper.TConfigMapper;
import andanyoung.springboot.shardingjdbc.service.TConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/** */
@Service
public class TConfigServiceImpl extends ServiceImpl<TConfigMapper, TConfig>
    implements TConfigService {}
