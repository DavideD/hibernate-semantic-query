/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.expression;

import javax.persistence.metamodel.BasicType;
import javax.persistence.metamodel.Type;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractAggregateFunction implements AggregateFunction {
	private final Expression argument;
	private final boolean distinct;
	private final BasicType resultType;

	public AbstractAggregateFunction(Expression argument, boolean distinct, BasicType resultType) {
		this.argument = argument;
		this.distinct = distinct;
		this.resultType = resultType;
	}

	@Override
	public Expression getArgument() {
		return argument;
	}

	@Override
	public boolean isDistinct() {
		return distinct;
	}

	@Override
	public Type getTypeDescriptor() {
		return resultType;
	}
}
