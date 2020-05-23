package digger.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import digger.model.Datasource;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class TableServiceTest {

    @MockBean
    TableService tableService;

	@Test
	public void calculateProgressTest() {
        Datasource datasource = new Datasource();
        assertThat(tableService.calculateProgress(datasource)).isEqualTo(0);
    }
}
