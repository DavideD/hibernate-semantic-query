/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.expression;

import org.hibernate.sqm.SemanticQueryWalker;
import org.hibernate.sqm.domain.EntityTypeDescriptor;

/**
 * Represents an reference to an entity type
 *
 * @author Steve Ebersole
 */
public class EntityTypeExpression implements Expression {
	private final EntityTypeDescriptor entityTypeDescriptor;

	public EntityTypeExpression(EntityTypeDescriptor entityTypeDescriptor) {
		this.entityTypeDescriptor = entityTypeDescriptor;
	}

	@Override
	public EntityTypeDescriptor getTypeDescriptor() {
		return entityTypeDescriptor;
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitEntityTypeExpression( this );
	}
}
