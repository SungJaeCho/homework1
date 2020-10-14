package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String productNo;
    private String type;
    private String productNm;
    private Integer price;
    private Integer inventoryCnt;

    @Override
    public String toString() {
        return "Product [productNo=" + productNo + ", type=" + type + ", productNm=" + productNm + ", price=" + price
                + ", inventoryCnt=" + inventoryCnt + "]";
    }
}
