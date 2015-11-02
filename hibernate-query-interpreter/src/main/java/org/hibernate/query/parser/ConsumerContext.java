/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.query.parser;

import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.EntityType;

import org.hibernate.sqm.domain.EntityTypeDescriptor;
import org.hibernate.sqm.domain.ModelMetadata;
import org.hibernate.sqm.domain.TypeDescriptor;

/**
 * Contextual information related to the consumer/caller of the parser - a callback API.
 *
 * @author Steve Ebersole
 */
public interface ConsumerContext extends ModelMetadata {
	/**
	 * Resolve an entity reference by name,
	 *
	 * @param reference The referenced entity name.
	 *
	 * @return The corresponding descriptor, or {@code null} if none.
	 */
	EntityTypeDescriptor resolveEntityReference(String reference);

	/**
	 * Resolve any (potential) non-entity class reference encountered in the query.
	 *
	 * @param name The name of the class to locate
	 *
	 * @return The Class reference
	 *
	 * @throws ClassNotFoundException
	 */
	Class classByName(String name) throws ClassNotFoundException;

	/**
	 * Should constructs allowed by HQL but not allowed by JPQL result in an exception?
	 * <p/>
	 * When this returns {@code true}, detected violations lead to a
	 * {@link org.hibernate.query.parser.StrictJpaComplianceViolation} being thrown
	 *
	 * @return {@code true} indicates that parsing and validation should strictly adhere to
	 * the JPQL subset.  {@code false} indicates to allow the full HQL superset.
	 */
	boolean useStrictJpaCompliance();
}
