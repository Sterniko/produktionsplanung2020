package de.adventureworks.produktionsplanung;

import de.adventureworks.produktionsplanung.model.DataBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProduktionsplanungApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(ProduktionsplanungApplication.class, args);

		Object dataBean = ctx.getBean("dataBean");
		System.out.println(((DataBean)dataBean).getCustomers());
	}

}

