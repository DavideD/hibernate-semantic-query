/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.from;

import org.hibernate.sqm.SemanticQueryWalker;
import org.hibernate.sqm.query.JoinType;
import org.hibernate.sqm.domain.EntityTypeDescriptor;
import org.hibernate.sqm.query.predicate.Predicate;

/**
 * @author Steve Ebersole
 */
public class QualifiedEntityJoinFromElement
		extends AbstractJoinedFromElement
		implements QualifiedJoinedFromElement {
	private final String entityName;

	private Predicate onClausePredicate;

	public QualifiedEntityJoinFromElement(
			FromElementSpace fromElementSpace,
			String alias,
			EntityTypeDescriptor entityTypeDescriptor,
			JoinType joinType) {
		super( fromElementSpace, alias, entityTypeDescriptor, joinType );
		this.entityName = entityTypeDescriptor.getTypeName();
	}

	public String getEntityName() {
		return entityName;
	}

	@Override
	public EntityTypeDescriptor getTypeDescriptor() {
		return (EntityTypeDescriptor) super.getTypeDescriptor();
	}

	@Override
	public Predicate getOnClausePredicate() {
		return onClausePredicate;
	}

	public void setOnClausePredicate(Predicate predicate) {
		this.onClausePredicate = predicate;
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitQualifiedEntityJoinFromElement( this );
	}
}
