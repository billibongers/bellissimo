package come.bila.bila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("come.bila.bila")
public class BilaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BilaApplication.class, args);
	}
}
