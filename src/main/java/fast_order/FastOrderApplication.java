package fast_order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class FastOrderApplication {
	public static void main(String[] args) {
		SpringApplication.run(FastOrderApplication.class, args);
	}
}
