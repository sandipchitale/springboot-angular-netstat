package sandipchitale.springbootangular.netstat;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringbootAngularApplication  extends SpringBootServletInitializer {
	public static void main(String[] args) {
		launch(new SpringApplicationBuilder()).run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder) {
		return launch(springApplicationBuilder);
	}

	private static SpringApplicationBuilder launch(SpringApplicationBuilder springApplicationBuilder) {
		return springApplicationBuilder.sources(SpringbootAngularApplication .class);
	}
}
