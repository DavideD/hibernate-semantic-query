/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.test.sqm.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractManagedType extends AbstractTypeImpl implements ManagedType {
	private Set<Attribute> attributes;

	public AbstractManagedType(String typeName) {
		super( typeName );
	}

	public AbstractManagedType(Class javaType) {
		super( javaType );
	}

	public SingularAttributeImpl makeSingularAttribute(
			String name,
			Attribute.PersistentAttributeType attributeType,
			Type type) {
		final SingularAttributeImpl attr = new SingularAttributeImpl(
				this,
				name,
				attributeType,
				type
		);
		addAttribute( attr );
		return attr;
	}

	public MapAttributeImpl makeMapAttribute(
			String name,
			Attribute.PersistentAttributeType attributeType,
			Type keyType,
			Type elementType) {
		final MapAttributeImpl attr = new MapAttributeImpl(
				this,
				name,
				attributeType,
				keyType,
				elementType
		);
		addAttribute( attr );
		return attr;
	}

	public ListAttributeImpl makeListAttribute(
			String name,
			Attribute.PersistentAttributeType attributeType,
			Type elementType) {
		final ListAttributeImpl attr = new ListAttributeImpl(
				this,
				name,
				attributeType,
				elementType
		);
		addAttribute( attr );
		return attr;
	}

	public SetAttributeImpl makeSetAttribute(
			String name,
			Attribute.PersistentAttributeType attributeType,
			Type elementType) {
		final SetAttributeImpl attr = new SetAttributeImpl(
				this,
				name,
				attributeType,
				elementType
		);
		addAttribute( attr );
		return attr;
	}

	protected void addAttribute(Attribute attribute) {
		if ( attributes == null ) {
			attributes = new HashSet<Attribute>();
		}
		attributes.add( attribute );
	}

	@Override
	public Set<Attribute> getAttributes() {
		return attributes == null ? Collections.<Attribute>emptySet() : attributes;
	}

	@Override
	public Set<Attribute> getDeclaredAttributes() {
		return attributes;
	}

	@Override
	public Attribute getAttribute(String name) {
		if ( attributes != null ) {
			for ( Attribute attribute : attributes ) {
				if ( attribute.getName().equals( name ) ) {
					return attribute;
				}
			}
		}

		throw new IllegalArgumentException( "Per JPA spec, the named attribute does not exist : " + getTypeName() + " -> " + name );
	}

	@Override
	public Attribute getDeclaredAttribute(String name) {
		return getAttribute( name );
	}

	@Override
	public Set<SingularAttribute> getSingularAttributes() {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public Set<SingularAttribute> getDeclaredSingularAttributes() {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public Set<PluralAttribute> getPluralAttributes() {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public Set<PluralAttribute> getDeclaredPluralAttributes() {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public SingularAttribute getSingularAttribute(String name) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public SingularAttribute getDeclaredSingularAttribute(String name) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public CollectionAttribute getCollection(String name) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public CollectionAttribute getDeclaredCollection(String name) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public SetAttribute getSet(String name) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public SetAttribute getDeclaredSet(String name) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public ListAttribute getList(String name) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public ListAttribute getDeclaredList(String name) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public MapAttribute getMap(String name) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public MapAttribute getDeclaredMap(String name) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public MapAttribute getDeclaredMap(String name, Class keyType, Class valueType) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public MapAttribute getMap(String name, Class keyType, Class valueType) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public ListAttribute getDeclaredList(String name, Class elementType) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public ListAttribute getList(String name, Class elementType) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public SetAttribute getDeclaredSet(String name, Class elementType) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public SetAttribute getSet(String name, Class elementType) {
		return null;
	}

	@Override
	public CollectionAttribute getDeclaredCollection(String name, Class elementType) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public CollectionAttribute getCollection(String name, Class elementType) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public SingularAttribute getDeclaredSingularAttribute(String name, Class type) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}

	@Override
	public SingularAttribute getSingularAttribute(String name, Class type) {
		throw new UnsupportedOperationException( "Only #getAttributes, #getDeclaredAttributes, #getAttribute and #getDeclaredAttribute supported" );
	}
}
