package com.ingweb.employeService;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;


import java.util.List;





@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
class Employe {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String prenom;
	private String adressMail;


}

@Repository
interface EmployeRepository extends JpaRepository<Employe,Long> {

	@RestResource(path="byNom")
	public List<Employe> findByNomContains(@Param("nom") String nom);
}
/*
	public List<Employe> findByNomContains(String nom);
	public List<Employe> findByAdressMailContains (String email);*/

@CrossOrigin("*")
@RestController
class emplRestControler {
	private EmployeRepository employeRepository;
	public emplRestControler(EmployeRepository employeRepository) {
		this.employeRepository = employeRepository;
	}

	@GetMapping(path = "/employes")
	public List<Employe> getAllEmp() {
		return employeRepository.findAll();
	}

	@PostMapping(value="/employes")
	public Employe save(@RequestBody Employe emp){
		return employeRepository.save(emp);
	}

	@DeleteMapping(value="/employes/{id}")
	public void delete(@PathVariable(name="id") Long id){
		employeRepository.deleteById(id);
	}

	@PutMapping(value="/employes/{id}")
	public Employe updateEmp(@PathVariable(name="id") Long id, @RequestBody Employe emp){
		emp.setId(id);
		return employeRepository.save(emp);
	}

	@GetMapping(value="/employes/{id}")
	public Employe getEmp(@PathVariable(name="id") Long id){
		return employeRepository.findById(id).get();
	}
	//http://localhost:8081/employes/searchByName?name=mohamed

	@GetMapping(path="/employes/searchByName")
	public List<Employe> findByNom (@RequestParam("name") String nom){
		return employeRepository.findByNomContains (nom);
	}

	//http://localhost:8080/employes/search/byNom?nom=nom1

}






@SpringBootApplication
public class EmployeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner star(EmployeRepository employeRepository) {
		return  args ->{
			employeRepository.save(new Employe(null,"Aichaoui","Aicha","aicha1.aicha1@adressemail.com"));
			employeRepository.save(new Employe(null,"Mohamed","Ahmed","mohamed.ahmed@adressemail.com"));
			employeRepository.save(new Employe(null,"Gues","Bilal","gues.bilal@adressemail.com"));
			employeRepository.findAll().forEach(emp-> {System.out.println(emp.toString());});

		};
	}

}
