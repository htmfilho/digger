package digger.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import digger.model.Table;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class ColumnServiceTest {

    @MockBean
    ColumnService columnService;

	@Test
	public void calculateProgressTest() {

        Table table = new Table("TABLE_A");
        assertThat(columnService.calculateProgress(table)).isEqualTo(0);
    }
}
