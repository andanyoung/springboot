package andanyoung.springboot.shardingjdbc.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @TableName t_order_item_0
 */
public class TOrderItem implements Serializable {
    /**
     * 
     */
    private Long itemId;

    /**
     * 
     */
    private String orderNo;

    /**
     * 
     */
    private String itemName;

    /**
     * 
     */
    private BigDecimal price;

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * 
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * 
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 
     */
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
        TOrderItem other = (TOrderItem) that;
        return (this.getItemId() == null ? other.getItemId() == null : this.getItemId().equals(other.getItemId()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getItemName() == null ? other.getItemName() == null : this.getItemName().equals(other.getItemName()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getItemId() == null) ? 0 : getItemId().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getItemName() == null) ? 0 : getItemName().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", itemId=").append(itemId);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", itemName=").append(itemName);
        sb.append(", price=").append(price);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}