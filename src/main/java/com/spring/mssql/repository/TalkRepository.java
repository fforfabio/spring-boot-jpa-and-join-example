package com.spring.mssql.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.mssql.models.Talk;


@Repository
public interface TalkRepository extends JpaRepository<Talk, Long> {
	
	List<Talk> findByPublished(boolean published);
  	List<Talk> findByTitleContaining(String title);
  	
  	// Call a user defined function in the dbJoinExample database.
 	@Query(value = "SELECT * from dbo.getTutorialsWithFunction()", nativeQuery = true)
 	public List<Talk> getAllTutorialsWithFunction();
}
