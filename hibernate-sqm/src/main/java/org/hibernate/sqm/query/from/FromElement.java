/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.from;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.EntityType;

import org.hibernate.sqm.SemanticQueryWalker;
import org.hibernate.sqm.path.AttributePathPart;

/**
 * @author Steve Ebersole
 */
public interface FromElement extends AttributePathPart {
	FromElementSpace getContainingSpace();

	Bindable getBindableModelDescriptor();
	String getAlias();

	Attribute resolveAttribute(String attributeName);

	void addTreatedAs(EntityType typeDescriptor);

	<T> T accept(SemanticQueryWalker<T> walker);
}
