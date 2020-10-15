import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

public class TestCartService {

    @Test
    public void 카트_롤백_테스트() {
        int a = 0;
        int b = 1;
        Assertions.assertEquals(a, b);
    }
}
