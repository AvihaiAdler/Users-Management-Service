package store.logic;

import store.data.PersonEntity;
import store.users.PersonBoundary;

public interface PersonEntityConverter {
	public PersonBoundary toBoundary(PersonEntity entity);
	
	public PersonEntity toEntity(PersonBoundary boundary);
}
