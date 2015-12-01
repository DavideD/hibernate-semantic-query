/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.test.sqm.domain;

import java.lang.reflect.Member;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;

/**
 * @author Steve Ebersole
 */
public class SingularAttributeImpl implements SingularAttribute {
	private final AbstractManagedType declaringType;
	private final String name;
	private final PersistentAttributeType attributeType;
	private final Type type;

	private boolean isId;
	private boolean isVersion;

	public SingularAttributeImpl(
			AbstractManagedType declaringType,
			String name,
			PersistentAttributeType attributeType,
			Type type) {
		this.declaringType = declaringType;
		this.name = name;
		this.attributeType = attributeType;
		this.type = type;
	}

	@Override
	public ManagedType getDeclaringType() {
		return declaringType;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public PersistentAttributeType getPersistentAttributeType() {
		return attributeType;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Class getJavaType() {
		return getType().getJavaType();
	}

	@Override
	public boolean isId() {
		return isId;
	}

	@Override
	public boolean isVersion() {
		return isVersion;
	}

	@Override
	public boolean isOptional() {
		return false;
	}

	@Override
	public boolean isAssociation() {
		return getPersistentAttributeType() == PersistentAttributeType.MANY_TO_ONE
				|| getPersistentAttributeType() == PersistentAttributeType.ONE_TO_ONE;
	}

	@Override
	public boolean isCollection() {
		return false;
	}

	@Override
	public BindableType getBindableType() {
		return BindableType.SINGULAR_ATTRIBUTE;
	}

	@Override
	public Class getBindableJavaType() {
		return getJavaType();
	}

	@Override
	public Member getJavaMember() {
		throw new UnsupportedOperationException( "Not supported" );
	}
}
