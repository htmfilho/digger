package digger.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import digger.model.Table;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ColumnServiceTest {

    @MockBean
    ColumnService columnService;

	@Test
	public void calculateProgressTest() {

        Table table = new Table("TABLE_A");
        assertThat(columnService.calculateProgress(table)).isEqualTo(0);
    }
}
