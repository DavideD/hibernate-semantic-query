/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.domain;

import javax.persistence.TemporalType;
import javax.persistence.metamodel.BasicType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

/**
 * Extends the JPA Metamodel contract to expose access to BasicTypes
 * as well as access to EntityTypes by name
 *
 * @author Steve Ebersole
 */
public interface ExtendedMetamodel extends Metamodel {
	<T> BasicType<T> getBasicType(Class<T> javaType);
	<T> BasicType<T> getBasicType(Class<T> javaType, TemporalType temporalType);

	String resolveImportedName(String queryName);

	<X> EntityType<X> entity(String name);
}
