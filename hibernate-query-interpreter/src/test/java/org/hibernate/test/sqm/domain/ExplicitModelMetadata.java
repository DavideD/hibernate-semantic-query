/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.test.sqm.domain;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.TemporalType;
import javax.persistence.metamodel.BasicType;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.ManagedType;

import org.hibernate.sqm.domain.ExtendedMetamodel;

/**
 * @author Steve Ebersole
 */
public class ExplicitModelMetadata implements ExtendedMetamodel {
	private Map<String, String> importMap = new HashMap<String, String>();

	private Map<String, PolymorphicEntityTypeImpl> polymorphicEntityTypeMap = new HashMap<String, PolymorphicEntityTypeImpl>();

	private Map<String,EntityTypeImpl> entityTypeMap = new HashMap<String, EntityTypeImpl>();
	private Map<Class,MappedSuperclassTypeImpl> mappedSuperclassTypeMap = new HashMap<Class, MappedSuperclassTypeImpl>();
	private Map<String,EmbeddableTypeImpl> embeddableTypeMap = new HashMap<String, EmbeddableTypeImpl>();

	private Map<Class, BasicType> basicTypeMap = new HashMap<Class, BasicType>();

	public ExplicitModelMetadata() {
		// prime the basicTypeMap with the BasicTypeImpls from StandardBasicTypeDescriptors
		for ( Field field : StandardBasicTypeDescriptors.class.getDeclaredFields() ) {
			if ( BasicType.class.isAssignableFrom( field.getType() ) ) {
				try {
					final BasicType descriptor = (BasicType) field.get( StandardBasicTypeDescriptors.INSTANCE );
					basicTypeMap.put( descriptor.getJavaType(), descriptor );
				}
				catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public MappedSuperclassTypeImpl makeMappedSuperclassType(Class javaType) {
		return makeMappedSuperclassType( javaType, null );
	}

	public MappedSuperclassTypeImpl makeMappedSuperclassType(Class javaType, IdentifiableType superType) {
		return new MappedSuperclassTypeImpl( javaType, superType );
	}

	public EntityTypeImpl makeEntityType(Class javaType) {
		return makeEntityType( javaType, null );
	}

	public EntityTypeImpl makeEntityType(Class javaType, IdentifiableType superType) {
		final EntityTypeImpl entityType = new EntityTypeImpl( javaType, superType );
		entityTypeMap.put( javaType.getName(), entityType );
		importMap.put( javaType.getSimpleName(), javaType.getName() );

		return entityType;
	}

	public EntityTypeImpl makeEntityType(String entityName) {
		return makeEntityType( entityName, null );
	}

	public EntityTypeImpl makeEntityType(String entityName, IdentifiableType superType) {
		if ( entityName.contains( "." ) ) {
			final String importedName = entityName.substring( entityName.lastIndexOf( '.' ) + 1 );
			importMap.put( importedName, entityName );
		}
		final EntityTypeImpl entityType = new EntityTypeImpl( entityName, superType );
		entityTypeMap.put( entityName, entityType );

		return entityType;
	}

	public PolymorphicEntityTypeImpl makePolymorphicEntity(Class entityClass) {
		final PolymorphicEntityTypeImpl entityTypeDescriptor = new PolymorphicEntityTypeImpl( entityClass );
		importMap.put( entityClass.getSimpleName(), entityTypeDescriptor.getTypeName() );
		polymorphicEntityTypeMap.put( entityClass.getName(), entityTypeDescriptor );
		return entityTypeDescriptor;
	}

	public PolymorphicEntityTypeImpl makePolymorphicEntity(String name) {
		if ( name.contains( "." ) ) {
			final String importedName = name.substring( name.lastIndexOf( '.' ) + 1 );
			importMap.put( importedName, name );
		}
		final PolymorphicEntityTypeImpl type = new PolymorphicEntityTypeImpl( name );
		polymorphicEntityTypeMap.put( name, type );
		return type;

	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> BasicType<T> getBasicType(Class<T> javaType) {
		BasicType basicType = basicTypeMap.get( javaType );
		if ( basicType == null ) {
			basicType = new BasicTypeImpl( javaType );
			basicTypeMap.put( javaType, basicType );
		}
		return basicType;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> BasicType<T> getBasicType(Class<T> javaType, TemporalType temporalType) {
		return getBasicType( javaType );
	}


	@Override
	@SuppressWarnings("unchecked")
	public <X> ManagedType<X> managedType(Class<X> cls) {
		for ( EntityTypeImpl entityType : entityTypeMap.values() ) {
			if ( cls.equals( entityType.getJavaType() ) ) {
				return entityType;
			}
		}

		for ( MappedSuperclassTypeImpl mappedSuperclassType : mappedSuperclassTypeMap.values() ) {
			if ( cls.equals( mappedSuperclassType.getJavaType() ) ) {
				return mappedSuperclassType;
			}
		}

		for ( EmbeddableTypeImpl embeddableType : embeddableTypeMap.values() ) {
			if ( cls.equals( embeddableType.getJavaType() ) ) {
				return embeddableType;
			}
		}

		throw new IllegalArgumentException( "Per JPA spec" );
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<ManagedType<?>> getManagedTypes() {
		Set managedTypes = new HashSet();
		managedTypes.addAll( entityTypeMap.values() );
		managedTypes.addAll( mappedSuperclassTypeMap.values() );
		managedTypes.addAll( embeddableTypeMap.values() );
		return managedTypes;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <X> EmbeddableType<X> embeddable(Class<X> cls) {
		for ( EmbeddableTypeImpl embeddableType : embeddableTypeMap.values() ) {
			if ( cls.equals( embeddableType.getJavaType() ) ) {
				return embeddableType;
			}
		}

		throw new IllegalArgumentException( "Per JPA spec" );
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<EmbeddableType<?>> getEmbeddables() {
		Set embeddableTypes = new HashSet();
		embeddableTypes.addAll( embeddableTypeMap.values() );
		return embeddableTypes;
	}

	@Override
	public String resolveImportedName(String queryName) {
		final String importedName = importMap.get( queryName );
		return importedName != null ? importedName : queryName;
	}

	@Override
	@SuppressWarnings("unchecked")
	public EntityTypeImpl entity(String name) {
		EntityTypeImpl entityType = entityTypeMap.get( name );
		if ( entityType == null ) {
			entityType = polymorphicEntityTypeMap.get( name );
		}
		if ( entityType == null ) {
			throw new IllegalArgumentException( "Per JPA spec : no entity named " + name );
		}
		return entityType;
	}

	@Override
	@SuppressWarnings("unchecked")
	public EntityTypeImpl entity(Class entityClass) {
		final EntityTypeImpl entityType = entityTypeMap.get( entityClass.getName() );
		if ( entityType == null ) {
			throw new IllegalArgumentException( "Per JPA spec" );
		}
		return entityType;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<EntityType<?>> getEntities() {
		Set entityTypes = new HashSet();
		entityTypes.addAll( entityTypeMap.values() );
		return entityTypes;
	}
}
