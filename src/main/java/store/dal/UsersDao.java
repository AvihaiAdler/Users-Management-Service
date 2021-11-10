package store.dal;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import store.data.PersonEntity;

public interface UsersDao extends PagingAndSortingRepository<PersonEntity, String> {
	
	public List<PersonEntity> findByEmail_EndsWith(
			@Param("domain") String domain,
			Pageable page);
		
	@Query(value = "select * from users where birth_date like ?1%", nativeQuery = true)
	public List<PersonEntity> findByBirthDate_Containing(
			@Param("year") String year,
			Pageable page);
	
	
	public List<PersonEntity> findByRoles_Containing(
			@Param("role") String role,
			Pageable page);
	
	public Optional<PersonEntity> findByEmail(
			@Param("email") String email);
	
}
