/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.test.sqm.domain;

import java.util.Set;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractIdentifiableType extends AbstractManagedType implements IdentifiableType {
	private final IdentifiableType superType;

	private IdentifierDescriptor identifierDescriptor;
	private SingularAttribute versionAttribute;

	public AbstractIdentifiableType(
			String typeName,
			IdentifiableType superType) {
		super( typeName );
		this.superType = superType;
	}

	public AbstractIdentifiableType(
			Class javaType,
			IdentifiableType superType) {
		super( javaType );
		this.superType = superType;
	}

	public void setIdentifierDescriptor(IdentifierDescriptor identifierDescriptor) {
		this.identifierDescriptor = identifierDescriptor;
	}

	public void setVersionAttribute(SingularAttribute versionAttribute) {
		this.versionAttribute = versionAttribute;
	}

	@Override
	public IdentifiableType getSupertype() {
		return superType;
	}

	@Override
	public boolean hasSingleIdAttribute() {
		return identifierDescriptor.hasSingleIdAttribute();
	}

	@Override
	public Type<?> getIdType() {
		return identifierDescriptor.getIdType();
	}

	@Override
	public SingularAttribute getId(Class type) {
		if ( identifierDescriptor.hasSingleIdAttribute() ) {
			return ( (SingularAttributeIdentifier) identifierDescriptor ).getIdAttribute();
		}
		else {
			throw new IllegalArgumentException( "Per JPA" );
		}
	}

	@Override
	public SingularAttribute getDeclaredId(Class type) {
		return getId( type );
	}

	@Override
	public Set<SingularAttribute> getIdClassAttributes() {
		if ( identifierDescriptor.hasSingleIdAttribute() ) {
			throw new IllegalArgumentException( "Per JPA" );
		}
		else {
			return ( (NonAggregatedCompositeIdentifierDescriptor) identifierDescriptor ).getIdClassAttributes();
		}
	}

	@Override
	public boolean hasVersionAttribute() {
		return versionAttribute != null;
	}

	@Override
	public SingularAttribute getVersion(Class type) {
		return versionAttribute;
	}

	@Override
	public SingularAttribute getDeclaredVersion(Class type) {
		return getVersion( type );
	}

	public interface IdentifierDescriptor {
		Type<?> getIdType();
		boolean hasSingleIdAttribute();
	}

	public interface SingularAttributeIdentifier {
		SingularAttribute getIdAttribute();
	}

	public static class SimpleIdentifierDescriptor implements IdentifierDescriptor, SingularAttributeIdentifier {
		private final SingularAttribute idAttribute;

		public SimpleIdentifierDescriptor(SingularAttribute idAttribute) {
			this.idAttribute = idAttribute;
		}

		@Override
		public Type<?> getIdType() {
			return idAttribute.getType();
		}

		@Override
		public boolean hasSingleIdAttribute() {
			return true;
		}

		@Override
		public SingularAttribute getIdAttribute() {
			return idAttribute;
		}
	}

	public static class EmbeddedIdentifierDescriptor implements IdentifierDescriptor, SingularAttributeIdentifier {
		private final SingularAttribute embeddedIdAttribute;

		public EmbeddedIdentifierDescriptor(SingularAttribute embeddedIdAttribute) {
			this.embeddedIdAttribute = embeddedIdAttribute;
		}

		@Override
		public EmbeddableType<?> getIdType() {
			return (EmbeddableType) embeddedIdAttribute.getType();
		}

		@Override
		public boolean hasSingleIdAttribute() {
			return true;
		}

		@Override
		public SingularAttribute getIdAttribute() {
			return embeddedIdAttribute;
		}
	}

	public static class NonAggregatedCompositeIdentifierDescriptor implements IdentifierDescriptor {
		// NOTE : JPA requires these to have an IdClass, so we work with that assumption here for testing
		private final Type idClassType;
		private final Set<SingularAttribute> idClassAttributes;

		public NonAggregatedCompositeIdentifierDescriptor(
				Type idClassType,
				Set<SingularAttribute> idClassAttributes) {
			this.idClassType = idClassType;
			this.idClassAttributes = idClassAttributes;
		}

		@Override
		public Type<?> getIdType() {
			return idClassType;
		}

		@Override
		public boolean hasSingleIdAttribute() {
			return false;
		}

		public Set<SingularAttribute> getIdClassAttributes() {
			return idClassAttributes;
		}
	}
}
