import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {

    @Test
    @DisplayName("테스트")
    void test() {
        assertThat(1 + 1).isEqualTo(2);
    }
}
