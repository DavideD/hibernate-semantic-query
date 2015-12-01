/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.expression;

import javax.persistence.metamodel.Type;

import org.hibernate.sqm.SemanticQueryWalker;

/**
 * @author Steve Ebersole
 */
public class NamedParameterExpression implements ParameterExpression {
	private final String name;
	private Type typeDescriptor;

	public NamedParameterExpression(String name) {
		this.name = name;
	}

	public NamedParameterExpression(String name, Type typeDescriptor) {
		this.name = name;
		this.typeDescriptor = typeDescriptor;
	}

	@Override
	public Type getTypeDescriptor() {
		return typeDescriptor;
	}

	@Override
	public Type getInferableType() {
		return null;
	}

	@Override
	public void impliedType(Type type) {
		if ( type != null ) {
			this.typeDescriptor = type;
		}
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitNamedParameterExpression( this );
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getPosition() {
		return null;
	}
}
