/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.expression;

import javax.persistence.metamodel.BasicType;

import org.hibernate.sqm.SemanticQueryWalker;

/**
 * @author Steve Ebersole
 */
public class AvgFunction extends AbstractAggregateFunction implements AggregateFunction {
	public AvgFunction(Expression argument, boolean distinct, BasicType resultType) {
		super( argument, distinct, resultType );
	}

	@Override
	public BasicType getInferableType() {
		return getTypeDescriptor();
	}

	@Override
	public BasicType getTypeDescriptor() {
		return (BasicType) super.getTypeDescriptor();
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitAvgFunction( this );
	}
}
