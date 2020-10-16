import common.SoldOutException;
import model.Cart;
import model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import service.CartService;
import service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestCartService {

    @Test
    public void 카트_롤백_테스트() {
        ProductService productService = new ProductService();
        CartService cartService = new CartService();
        List<Product> sProductList = new ArrayList<>();
        List<Cart> sCartList = new ArrayList<>();
        List<Cart> completeList = new ArrayList<>();

        sProductList = productService.createProduct();
        sProductList = sProductList.stream().filter( e -> "KIT".equalsIgnoreCase(e.getType()))
        .collect(Collectors.toList());
        sProductList.forEach(System.out::println);

        sCartList.add(new Cart("42031", "KIT", "나만의 음악을 만들기 위한 장비 패키지", 209000, 2));
        sCartList.add(new Cart("97166", "KIT", "이렇게 멋진 수채화 키트,어때요? 클래스101과 고넹이화방이 기획한 3가지 수채화 키트", 96900, 6));

        for(Cart cartMap : sCartList){
            if("KIT".equalsIgnoreCase(cartMap.getType())){
                try {
                    cartService.checkInventory(cartMap, sProductList);
                } catch (SoldOutException e) {
                    e.printStackTrace();
                    //롤백진행
                    productService.rollbackAmount(completeList, sProductList);
                    break;
                }
                Integer resultCnt = productService.updateAmount(cartMap.getProductNo(), cartMap.getAmount(), sProductList);
                if(resultCnt == 1){
                    completeList.add(cartMap);
                }
            }
        }
        sProductList.forEach(System.out::println);
    }

}
