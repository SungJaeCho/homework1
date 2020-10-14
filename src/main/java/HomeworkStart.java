import model.Cart;
import model.Product;
import service.CartService;
import service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HomeworkStart{

    public  static List<Product> productList = null;
    public  static List<Cart> cartList = null;
    public  static boolean whileFlag01 = true;
    public  static boolean whileFlag02 = true;
    public  static String switchStr01 = null;
    public  static String switchStr02 = "";

    public static void main(String[] args) {
        //상품생성
        ProductService productService = new ProductService();
        productList = productService.createProduct();
        CartService cartService = new CartService();

        Scanner sc = new Scanner(System.in);
        cartList = new ArrayList<>();

        String sProductNo;
        Integer sAmount;
        String msg;

        while (whileFlag01){
            System.out.print("입력(o[order] : 주문, q[quit]: 종료) :");
            switchStr01 = sc.next();
            switch (switchStr01){
                case "o": //진입
                    whileFlag02 = true;
                    System.out.println("상품번호  " + " 종류               "+ "상품명             " + "판매가격(원)         " + "재고수");
                    for(Product pro : productList){
                        System.out.println(pro.getProductNo() + "  "+ pro.getType()+ "  "+ pro.getProductNm()+ "  " + pro.getPrice()+ "  " + pro.getInventoryCnt());
                    }

                    while (whileFlag02){
                        switch (switchStr02){
                            case " ": //계산
                                System.out.println("주문내역 :");
                                System.out.println("--------------------------------------------------------");
                                for(Cart printCart : cartList){
                                    System.out.println(printCart);
                                }
                                System.out.println("--------------------------------------------------------");
                                System.out.println("주문금액 :");
                                System.out.println("배송비 :");
                                System.out.println("--------------------------------------------------------");
                                System.out.println("지불금액 :");
                                System.out.println("--------------------------------------------------------");

                                whileFlag02 = false;
                                switchStr02 = "";
                                break;
                            default:
                                Scanner sc1 = new Scanner(System.in);
                                System.out.print("상품번호 :");
                                switchStr02 = sc1.nextLine();
                                sProductNo = switchStr02;
                                if(" ".equals(sProductNo)){
                                    switchStr02 = " ";
                                    break;
                                }
                                System.out.print("수량 :");
                                sAmount = sc1.nextInt();
                                msg = cartService.addValidate(sProductNo, sAmount, cartList, productList);
                                if("PASS".equalsIgnoreCase(msg)){
                                    cartList = cartService.addCart(sProductNo, sAmount, cartList, productList);
                                } else {
                                    System.out.println("--------------------------------------------------------");
                                    System.out.println(msg);
                                    System.out.println("--------------------------------------------------------");
                                }
                                break;
                        }
                    }
                    break;

                case "q": //종료
                    System.out.println("종료");
                    whileFlag01 = false;
                    break;
                default: System.out.println("다시 입력해 주십시요.");
                    break;
            }

        }

    }

}
