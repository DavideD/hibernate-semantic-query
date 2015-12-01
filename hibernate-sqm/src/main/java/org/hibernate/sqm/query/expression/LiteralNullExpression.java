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
public class LiteralNullExpression implements LiteralExpression<Void> {
	@Override
	public Void getLiteralValue() {
		return null;
	}

	@Override
	public BasicType<Void> getTypeDescriptor() {
		return NULL_TYPE;
	}

	@Override
	public Type getInferableType() {
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void impliedType(Type type) {
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitLiteralNullExpression( this );
	}

	private static BasicType<Void> NULL_TYPE = new BasicType<Void>() {
		@Override
		public PersistenceType getPersistenceType() {
			return PersistenceType.BASIC;
		}

		@Override
		public Class<Void> getJavaType() {
			return void.class;
		}
	};
}
