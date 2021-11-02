package store.dal;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import store.data.PersonEntity;

public interface UsersDao extends PagingAndSortingRepository<PersonEntity, String> {
	
	//?
	public List<PersonEntity> findAllByEmailEndsWith_domain(
			@Param("domain") String domain,
			Pageable page);
	
	//?
	public List<PersonEntity> findAllByBirthDateContaining_year(
			@Param("year") String year,
			Pageable page);
	
	//?
	public List<PersonEntity> findAllByRolesContaining_role(
			@Param("role") String role,
			Pageable page);
	
	public Optional<PersonEntity> findByEmail(
			@Param("email") String email);
	
}
