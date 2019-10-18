package digger.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import digger.model.Datasource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TableServiceTest {

    @MockBean
    TableService tableService;

	@Test
	public void calculateProgressTest() {

        Datasource datasource = new Datasource();
        assertThat(tableService.calculateProgress(datasource)).isEqualTo(0);
    }
}
