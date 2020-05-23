package digger;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DiggerApplicationTest {

	@Test
	public void mainTest() {
		DiggerApplication.main(new String[] {
			"--spring.main.web-environment=false"
		});
	}
}