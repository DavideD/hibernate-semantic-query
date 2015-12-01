/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.expression;

import javax.persistence.metamodel.BasicType;
import javax.persistence.metamodel.Type;

import org.hibernate.sqm.SemanticQueryWalker;

/**
 * @author Steve Ebersole
 */
public class CountStarFunction extends AbstractAggregateFunction {
	public CountStarFunction(boolean distinct, BasicType resultType) {
		super( STAR, distinct, resultType );
	}

	@Override
	public BasicType getTypeDescriptor() {
		return (BasicType) super.getTypeDescriptor();
	}

	@Override
	public BasicType getInferableType() {
		return getTypeDescriptor();
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitCountStarFunction( this );
	}

	private static Expression STAR = new Expression() {
		@Override
		public Type getTypeDescriptor() {
			return null;
		}

		@Override
		public Type getInferableType() {
			return null;
		}

		@Override
		public <T> T accept(SemanticQueryWalker<T> walker) {
			return null;
		}
	};
}
