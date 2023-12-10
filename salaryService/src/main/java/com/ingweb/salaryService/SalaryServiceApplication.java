package com.ingweb.salaryService;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

@Data
class Employe {
	private Long id;
	private String nom;
	private String prenom;
	private String adressMail;


}

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
class SalaryKey implements Serializable {
	Long employeId;
	Long projectId;
}

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
class Salary {
	@EmbeddedId
	private SalaryKey id;
	private Long salary;
}

@Repository
interface  SalaryRepository extends JpaRepository< Salary,SalaryKey> {

}





@RestController
class SalaryRestController {

	private  SalaryRepository slrRepository;

	public SalaryRestController (SalaryRepository slrRepository) {
		this.slrRepository =slrRepository;
	}

	@GetMapping(path="/salaries")
	public List<Salary> getAllSalary(){
		return slrRepository.findAll();
	}
}


@FeignClient(name="employeMicroService")
interface EmployeServiceClient{

	@GetMapping(path="/employes/searchByName")
	public List<Employe> findByNom (@RequestParam("name") String nom);


}

@EnableFeignClients
@SpringBootApplication
public class SalaryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalaryServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner star(SalaryRepository slrRepository,
						   EmployeServiceClient employeServiceClient) {
		return  args ->{

			employeServiceClient.findByNom("Mohamed").forEach(s-> {
				slrRepository.save(new Salary(
						new SalaryKey(s.getId(),1L),
						10000L));});


			slrRepository.findAll().forEach(s->
			{System.out.println(s.toString());});

		};
	}
}

