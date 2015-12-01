/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.expression;

import javax.persistence.metamodel.Type;

import org.hibernate.sqm.SemanticQueryWalker;
import org.hibernate.sqm.query.QuerySpec;

/**
 * @author Steve Ebersole
 */
public class SubQueryExpression implements Expression {
	private final QuerySpec querySpec;
	private final Type type;

	public SubQueryExpression(QuerySpec querySpec, Type type) {
		this.querySpec = querySpec;
		this.type = type;
	}

	@Override
	public Type getTypeDescriptor() {
		return type;
	}

	@Override
	public Type getInferableType() {
		return type;
	}

	public QuerySpec getQuerySpec() {
		return querySpec;
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitSubQueryExpression( this );
	}
}
