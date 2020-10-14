package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private String productNo;
    private String type;
    private String productNm;
    private Integer amount;

    @Override
    public String toString() {
        return "Cart [productNo=" + productNo + ", type=" + type + ", productNm=" + productNm + ", amount=" + amount +"]";
    }
}
