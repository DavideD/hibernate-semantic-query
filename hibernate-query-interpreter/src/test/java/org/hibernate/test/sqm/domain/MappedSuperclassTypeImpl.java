/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.test.sqm.domain;

import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.MappedSuperclassType;

/**
 * @author Steve Ebersole
 */
public class MappedSuperclassTypeImpl extends AbstractIdentifiableType implements MappedSuperclassType {
	public MappedSuperclassTypeImpl(Class javaType, IdentifiableType superType) {
		super( javaType, superType );
	}

	@Override
	public PersistenceType getPersistenceType() {
		return PersistenceType.MAPPED_SUPERCLASS;
	}
}
