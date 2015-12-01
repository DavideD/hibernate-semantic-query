/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.test.sqm.domain;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.IdentifiableType;

/**
 * @author Steve Ebersole
 */
public class EntityTypeImpl extends AbstractIdentifiableType implements EntityType {
	public EntityTypeImpl(
			String typeName,
			IdentifiableType superType) {
		super( typeName, superType );
	}

	public EntityTypeImpl(
			Class javaType,
			IdentifiableType superType) {
		super( javaType, superType );
	}

	@Override
	public String getName() {
		return getTypeName();
	}

	@Override
	public BindableType getBindableType() {
		return BindableType.ENTITY_TYPE;
	}

	@Override
	public Class getBindableJavaType() {
		return getJavaType();
	}

	@Override
	public PersistenceType getPersistenceType() {
		return PersistenceType.ENTITY;
	}
}
