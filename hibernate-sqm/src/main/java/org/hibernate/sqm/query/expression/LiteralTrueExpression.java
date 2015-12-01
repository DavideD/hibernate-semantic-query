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
public class LiteralTrueExpression extends AbstractLiteralExpressionImpl<Boolean> {
	public LiteralTrueExpression(BasicType<Boolean> booleanTypeDescriptor) {
		super( Boolean.TRUE, booleanTypeDescriptor );
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitLiteralTrueExpression( this );
	}

	@Override
	protected void validateInferredType(Class javaType) {
		if ( !Boolean.class.equals( javaType ) ) {
			throw new TypeInferenceException( "Inferred type [" + javaType + "] was not convertible to Boolean" );
		}
	}
}
