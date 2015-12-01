/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.test.sqm.domain;

import javax.persistence.metamodel.EmbeddableType;

/**
 * @author Steve Ebersole
 */
public class EmbeddableTypeImpl extends AbstractManagedType implements EmbeddableType {
	public EmbeddableTypeImpl(String typeName) {
		super( typeName );
	}

	public EmbeddableTypeImpl(Class javaType) {
		super( javaType );
	}

	@Override
	public PersistenceType getPersistenceType() {
		return PersistenceType.EMBEDDABLE;
	}
}