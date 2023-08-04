package com.spring.mssql.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.mssql.models.Tutorial;


@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
	
	List<Tutorial> findByPublished(boolean published);
  	List<Tutorial> findByTitleContaining(String title);
  	
  	// Call a user defined function in the dbJoinExample database.
 	@Query(value = "SELECT * from dbo.getTutorialsWithFunction()", nativeQuery = true)
 	public List<Tutorial> getAllTutorialsWithFunction();
}
