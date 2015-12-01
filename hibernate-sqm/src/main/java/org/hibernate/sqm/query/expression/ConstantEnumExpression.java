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
public class ConstantEnumExpression<T extends Enum> implements ConstantExpression<T> {
	private final T value;
	private BasicType<T> typeDescriptor;

	public ConstantEnumExpression(T value, BasicType<T> typeDescriptor) {
		this.value = value;
		this.typeDescriptor = typeDescriptor;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public BasicType<T> getTypeDescriptor() {
		return typeDescriptor;
	}

	@Override
	public Type getInferableType() {
		return getTypeDescriptor();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void impliedType(Type type) {
		if ( type != null ) {
			if ( !BasicType.class.isAssignableFrom( type.getClass() ) ) {
				throw new TypeInferenceException( "Inferred type descriptor [" + type + "] was not castable to javax.persistence.metamodel.BasicType" );
			}
			if ( !value.getClass().equals( type.getJavaType() ) ) {
				throw new TypeInferenceException( "Inferred type [" + type.getJavaType() + "] was not convertible to " + value.getClass().getName() );
			}
			this.typeDescriptor = (BasicType<T>) type;
		}
	}

	@Override
	public <X> X accept(SemanticQueryWalker<X> walker) {
		return walker.visitConstantEnumExpression( this );
	}
}
