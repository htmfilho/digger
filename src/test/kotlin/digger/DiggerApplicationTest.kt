package digger

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DiggerApplicationTest {

	@Test
	fun mainTest() {
		main(arrayOf("--spring.main.web-environment=false"))
	}
}