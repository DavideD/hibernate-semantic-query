/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.test.sqm.domain;

import java.lang.reflect.Member;
import java.util.Map;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.Type;

/**
 * @author Steve Ebersole
 */
public class MapAttributeImpl implements MapAttribute {
	private final AbstractManagedType declaringType;
	private final String name;
	private final PersistentAttributeType attributeType;
	private final Type keyType;
	private final Type elementType;

	public MapAttributeImpl(
			AbstractManagedType declaringType,
			String name,
			PersistentAttributeType attributeType,
			Type keyType,
			Type elementType) {
		this.declaringType = declaringType;
		this.name = name;
		this.attributeType = attributeType;
		this.keyType = keyType;
		this.elementType = elementType;
	}

	@Override
	public boolean isCollection() {
		return true;
	}

	@Override
	public CollectionType getCollectionType() {
		return CollectionType.MAP;
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
	public Type getKeyType() {
		return keyType;
	}

	@Override
	public Class getKeyJavaType() {
		return getKeyType().getJavaType();
	}

	@Override
	public Type getElementType() {
		return elementType;
	}

	@Override
	public Class getJavaType() {
		return Map.class;
	}

	@Override
	public boolean isAssociation() {
		return getPersistentAttributeType() == PersistentAttributeType.ONE_TO_MANY
				|| getPersistentAttributeType() == PersistentAttributeType.MANY_TO_MANY;
	}

	@Override
	public BindableType getBindableType() {
		return BindableType.PLURAL_ATTRIBUTE;
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