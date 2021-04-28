package andanyoung.springboot.shardingjdbc.domain;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.math.BigDecimal;

/** @TableName t_order_0 */
public class TOrder implements Serializable {
  /** */
  @TableId private Long orderId;

  /** */
  private String orderNo;

  /** */
  private String createName;

  /** */
  private BigDecimal price;

  private static final long serialVersionUID = 1L;

  /** */
  public Long getOrderId() {
    return orderId;
  }

  /** */
  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  /** */
  public String getOrderNo() {
    return orderNo;
  }

  /** */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /** */
  public String getCreateName() {
    return createName;
  }

  /** */
  public void setCreateName(String createName) {
    this.createName = createName;
  }

  /** */
  public BigDecimal getPrice() {
    return price;
  }

  /** */
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null) {
      return false;
    }
    if (getClass() != that.getClass()) {
      return false;
    }
    TOrder other = (TOrder) that;
    return (this.getOrderId() == null
            ? other.getOrderId() == null
            : this.getOrderId().equals(other.getOrderId()))
        && (this.getOrderNo() == null
            ? other.getOrderNo() == null
            : this.getOrderNo().equals(other.getOrderNo()))
        && (this.getCreateName() == null
            ? other.getCreateName() == null
            : this.getCreateName().equals(other.getCreateName()))
        && (this.getPrice() == null
            ? other.getPrice() == null
            : this.getPrice().equals(other.getPrice()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
    result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
    result = prime * result + ((getCreateName() == null) ? 0 : getCreateName().hashCode());
    result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    sb.append("Hash = ").append(hashCode());
    sb.append(", orderId=").append(orderId);
    sb.append(", orderNo=").append(orderNo);
    sb.append(", createName=").append(createName);
    sb.append(", price=").append(price);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }
}
