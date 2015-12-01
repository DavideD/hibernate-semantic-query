/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.expression;

import java.util.Map;
import javax.persistence.metamodel.BasicType;
import javax.persistence.metamodel.Type;

import org.hibernate.sqm.SemanticQueryWalker;
import org.hibernate.sqm.query.from.FromElement;

/**
 * Represents the ENTRY() function for obtaining the map entries from a {@code Map}-typed association.
 *
 * @author Gunnar Morling
 * @author Steve Ebersole
 */
public class MapEntryFunction implements Expression {
	private final String collectionAlias;
	private final Type indexType;
	private final Type elementType;

	public MapEntryFunction(FromElement collectionReference, Type indexType, Type elementType) {
		this.collectionAlias = collectionReference.getAlias();
		this.indexType = indexType;
		this.elementType = elementType;
	}

	public String getCollectionAlias() {
		return collectionAlias;
	}

	public Type getMapKeyType() {
		return indexType;
	}

	public Type getMapValueType() {
		return elementType;
	}

	@Override
	public Type getTypeDescriptor() {
		return MAP_ENTRY_TYPE;
	}

	@Override
	public Type getInferableType() {
		return null;
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitMapEntryFunction( this );
	}

	static final BasicType MAP_ENTRY_TYPE = new BasicType() {
		@Override
		public PersistenceType getPersistenceType() {
			return PersistenceType.BASIC;
		}

		@Override
		public Class getJavaType() {
			return Map.Entry.class;
		}
	};
}
