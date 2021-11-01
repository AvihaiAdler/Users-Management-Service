package store.dal;

import org.springframework.data.repository.PagingAndSortingRepository;
import store.data.PersonEntity;

public interface UsersDao extends PagingAndSortingRepository<PersonEntity, String> {
	
	
}
