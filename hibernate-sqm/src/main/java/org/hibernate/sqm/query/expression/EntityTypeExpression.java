/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.expression;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Type;

import org.hibernate.sqm.SemanticQueryWalker;

/**
 * Represents an reference to an entity type
 *
 * @author Steve Ebersole
 */
public class EntityTypeExpression implements Expression {
	private final EntityType entityTypeDescriptor;

	public EntityTypeExpression(EntityType entityTypeDescriptor) {
		this.entityTypeDescriptor = entityTypeDescriptor;
	}

	@Override
	public EntityType getTypeDescriptor() {
		return entityTypeDescriptor;
	}

	@Override
	public Type getInferableType() {
		return entityTypeDescriptor;
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitEntityTypeExpression( this );
	}
}
