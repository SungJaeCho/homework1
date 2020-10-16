package service;

import common.SoldOutException;
import model.Cart;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartService {
    ProductService productService = new ProductService();

    public String addValidate(String sProductNo, Integer sAmount, List<Cart> sCartList, List<Product> sProductList){
        String msg = "PASS";
        Product product = productService.selectOne(sProductNo, sProductList);

        if(product.getProductNo() == null){
            msg = "리스트에 없는 상품입니다. 다시 주문하여 주십시요.";
            return msg;
        }
        if(product.getInventoryCnt() == 0){
            msg = "해당 물품의 재고가 없습니다.";
            return msg;
        }
        if("KLASS".equalsIgnoreCase(product.getType()) && sAmount > 1){
            msg = "동일한 클래스는 하나만 주문이 가능합니다.";
            return msg;
        }
        for(Cart cartMap : sCartList){
            if("KLASS".equalsIgnoreCase(cartMap.getType()) && cartMap.getProductNo().equalsIgnoreCase(sProductNo)){
                msg = "동일한 클래스는 하나만 주문이 가능합니다.";
                break;
            }
        }
        return msg;
    }

    public List<Cart> addCart(String sProductNo, Integer sAmount, List<Cart> sCartList, List<Product> sProductList){
        Product product = productService.selectOne(sProductNo, sProductList);
        for(Cart cartMap : sCartList){
            if(cartMap.getProductNo().equalsIgnoreCase(product.getProductNo())){
                cartMap.setAmount(cartMap.getAmount()+sAmount);
                return sCartList;
            }
        }
        Cart cart = new Cart();
        cart.setProductNo(product.getProductNo());
        cart.setType(product.getType());
        cart.setProductNm(product.getProductNm());
        cart.setPrice(product.getPrice());
        cart.setAmount(sAmount);
        sCartList.add(cart);
        return sCartList;
    }

    public Map<String, Object> sumReqAmt(List<Product> sProductList, List<Cart> sCartList, Integer targetAmt, Integer shippingFee) {
        //키드상품의 경우 주문금액 5만원 미만시 배송료 5천원 부가
        //클래스와 키트 같이 주문시 배송료 없음
        Map<String, Object> resultMap = new HashMap<>();
        List<Cart> completeList = new ArrayList<>();

        boolean shippingFeeFlag = true;
        boolean klassFlag = false;
        boolean kitFlag = false;
        Integer sumReqAmt = 0;

        for(Cart cartMap : sCartList){
            if("KLASS".equalsIgnoreCase(cartMap.getType())){
                klassFlag = true;
            }
            else {
                try {
                    checkInventory(cartMap, sProductList);
                } catch (SoldOutException e) {
                    e.printStackTrace();
                    //롤백진행
                    productService.rollbackAmount(completeList, sProductList);
                    resultMap.put("result", e.getValue());
                    resultMap.put("msg", e.getMessage());
                    return resultMap;
                }
                Integer resultCnt = productService.updateAmount(cartMap.getProductNo(), cartMap.getAmount(), sProductList);
                if(resultCnt == 1){
                    completeList.add(cartMap);
                }
                kitFlag = true;
            }
            sumReqAmt += cartMap.getPrice() * cartMap.getAmount();
        }

        if(sumReqAmt > targetAmt || (klassFlag && kitFlag)){
            resultMap.put("sumReqAmt", sumReqAmt);
            resultMap.put("shippingFee", 0);
        } else {
            resultMap.put("sumReqAmt", sumReqAmt);
            resultMap.put("shippingFee", shippingFee);
        }
        resultMap.put("result", "COMPLETE");
        resultMap.put("msg", "계산 완료");
        return resultMap;
    }

    public void checkInventory (Cart sCart, List<Product> sProductList) throws SoldOutException {
        Product product = productService.selectOne(sCart.getProductNo(), sProductList);
        if(product.getInventoryCnt() - sCart.getAmount() < 0) {
            throw new SoldOutException("SOLD OUT", "[" + sCart.getProductNm() + "]의 물품 재고가 부족합니다.");
        }
    }

}
