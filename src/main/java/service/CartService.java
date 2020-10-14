package service;

import model.Cart;
import model.Product;

import java.util.List;

public class CartService {
    ProductService productService = new ProductService();

    public String addValidate(String sProductNo, Integer sAmount, List<Cart> sCartList, List<Product> sProductList){
        String msg = "PASS";
        //클래스의 리스트에 중복 불가
        for(Cart cartMap : sCartList){
            if(cartMap.getProductNo().equalsIgnoreCase(sProductNo) && "KLASS".equalsIgnoreCase(cartMap.getType())){
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
        cart.setAmount(sAmount);
        sCartList.add(cart);
        return sCartList;
    }
}
