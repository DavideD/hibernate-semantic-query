/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.test.sqm.domain;

import javax.persistence.metamodel.Type;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractTypeImpl implements Type {
	private final Class javaType;
	private final String typeName;

	public AbstractTypeImpl(String typeName) {
		this.typeName = typeName;
		this.javaType = null;
	}

	public AbstractTypeImpl(Class javaType) {
		this.typeName = javaType.getName();
		this.javaType = javaType;
	}

	@Override
	public Class getJavaType() {
		return javaType;
	}

	public String getTypeName() {
		return typeName;
	}
}
