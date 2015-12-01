/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.test.query.parser;

import org.hibernate.query.parser.ConsumerContext;
import org.hibernate.sqm.domain.ExtendedMetamodel;

/**
 * @author Steve Ebersole
 */
public class ConsumerContextImpl implements ConsumerContext {
	private final ExtendedMetamodel modelMetadata;

	// false (full HQL support) by default
	private boolean strictJpaCompliance;

	public ConsumerContextImpl(ExtendedMetamodel modelMetadata) {
		this.modelMetadata = modelMetadata;
	}

	@Override
	public ExtendedMetamodel getDomainMetamodel() {
		return modelMetadata;
	}

	@Override
	public Class classByName(String name) throws ClassNotFoundException {
		return Class.forName( name );
	}

	@Override
	public boolean useStrictJpaCompliance() {
		return strictJpaCompliance;
	}

	public void enableStrictJpaCompliance() {
		strictJpaCompliance = true;
	}

	public void disableStrictJpaCompliance() {
		strictJpaCompliance = false;
	}
}
