package andanyoung.springboot.mybatis;

import com.github.pagehelper.PageSerializable;
import lombok.Data;

import java.util.List;

@Data
public class PageVo<T> extends PageSerializable<T> {
  private static final long serialVersionUID = -9124041110647055654L;

  public PageVo(List<T> list) {
    super(list);
  }
  //  // 当前页
  //  private int pageNum;
  //  // 当前页大小
  //  private int pageSize;
  //  // 记录总数
  //  private long totalSize;
  //  // 页码总数
  //  private int totalPages;
  //  private T list;

  //  PageVo(PageInfo<T> pageInfo) {
  //    this.list = pageInfo.getList();
  //    this.totalSize = pageInfo.getTotal();
  //    this.pageNum = pageInfo.getPageNum();
  //    this.pageSize = pageInfo.getPageSize();
  //    this.totalPages = totalSize / pageSize + 1;
  //  }
}
