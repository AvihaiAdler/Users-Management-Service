package store.dal;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import store.data.PersonEntity;

public interface UsersDao extends PagingAndSortingRepository<PersonEntity, String> {
	
	public List<PersonEntity> findAllByDomain(
			@Param("domain") String domain,
			Sort sort);
	
	public Optional<PersonEntity> findAllByEmail(
			@Param("Email") String email);
}
