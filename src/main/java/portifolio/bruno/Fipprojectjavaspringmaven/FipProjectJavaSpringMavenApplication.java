package portifolio.bruno.Fipprojectjavaspringmaven;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import portifolio.bruno.Fipprojectjavaspringmaven.principal.Principal;

@SpringBootApplication
public class FipProjectJavaSpringMavenApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FipProjectJavaSpringMavenApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}


}
