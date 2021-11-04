package store.logic;

import store.data.PersonEntity;
import store.users.PersonBoundary;
import store.users.PersonBoundaryWithoutPwd;

public interface PersonEntityConverter {
	public PersonBoundaryWithoutPwd toBoundary(PersonEntity entity);
	
	public PersonEntity toEntity(PersonBoundary boundary);
}
