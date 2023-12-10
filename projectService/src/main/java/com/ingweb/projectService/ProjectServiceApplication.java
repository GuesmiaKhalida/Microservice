package com.ingweb.projectService;

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
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;

}

@RepositoryRestResource
interface ProjectRepository extends JpaRepository<Project, Long> {

}
@SpringBootApplication
public class ProjectServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ProjectRepository projectRepository) {
		return  args ->{

			projectRepository.save(new Project(null,"Project1"));
			projectRepository.save(new Project(null,"Project2"));
			projectRepository.save(new Project(null,"Project3"));
			projectRepository.findAll().forEach(p-> {System.out.println(p.toString());});

		};
	}
}
