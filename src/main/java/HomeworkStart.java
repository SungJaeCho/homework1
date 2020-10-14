import model.Cart;
import model.Product;
import service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HomeworkStart{



    public static void main(String[] args) {
        //상품생성
        ProductService productService = new ProductService();
        List<Product> productList = productService.createProduct();

        boolean flag = true;
        String inputStr = null;

        Scanner sc = new Scanner(System.in);
        Cart cart = new Cart();

        while (flag){
            System.out.print("입력(o[order] : 주문, q[quit]: 종료) :");
            inputStr = sc.next();
            switch (inputStr){
                case "o": //진입
                    System.out.println("진입");
                    break;
                case "q": //종료
                    System.out.println("종료");
                    flag = false;
                    break;
                case " ": //계산
                    System.out.println("계산");
                    break;
                default: System.out.println("다시 입력해 주십시요.");
                    break;
            }

        }




        System.out.println("상품번호  " + " 종류               "+ "상품명             " + "판매가격(원)         " + "재고수");
        for(Product pro : productList){
            System.out.println(pro.getProductNo() + "  "+ pro.getType()+ "  "+ pro.getProductNm()+ "  " + pro.getPrice()+ "  " + pro.getInventoryCnt());
        }


    }
}
