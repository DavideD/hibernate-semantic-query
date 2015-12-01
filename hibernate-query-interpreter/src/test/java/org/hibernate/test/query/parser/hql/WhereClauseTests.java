/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.test.query.parser.hql;

import javax.persistence.metamodel.Attribute;

import org.hibernate.query.parser.SemanticQueryInterpreter;
import org.hibernate.sqm.domain.ExtendedMetamodel;
import org.hibernate.sqm.query.SelectStatement;
import org.hibernate.sqm.query.expression.CollectionIndexFunction;
import org.hibernate.sqm.query.expression.CollectionSizeFunction;
import org.hibernate.sqm.query.expression.LiteralIntegerExpression;
import org.hibernate.sqm.query.expression.MapKeyFunction;
import org.hibernate.sqm.query.predicate.Predicate;
import org.hibernate.sqm.query.predicate.RelationalPredicate;
import org.hibernate.test.query.parser.ConsumerContextImpl;
import org.hibernate.test.sqm.domain.EntityTypeImpl;
import org.hibernate.test.sqm.domain.ExplicitModelMetadata;
import org.hibernate.test.sqm.domain.StandardBasicTypeDescriptors;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for elements of WHERE clauses.
 *
 * @author Gunnar Morling
 */
public class WhereClauseTests {

	private final ConsumerContextImpl consumerContext = new ConsumerContextImpl( buildMetamodel() );

	@Test
	public void testCollectionSizeFunction() {
		SelectStatement statement = interpret( "SELECT t FROM Trip t WHERE SIZE( t.basicCollection ) = 311" );

		Predicate predicate = statement.getQuerySpec().getWhereClause().getPredicate();
		assertThat( predicate, instanceOf( RelationalPredicate.class ) );
		RelationalPredicate relationalPredicate = ( (RelationalPredicate) predicate );

		assertThat( relationalPredicate.getType(), is( RelationalPredicate.Type.EQUAL ) );

		assertThat( relationalPredicate.getRightHandExpression(), instanceOf( LiteralIntegerExpression.class ) );
		assertThat( ( (LiteralIntegerExpression) relationalPredicate.getRightHandExpression() ).getLiteralValue(), is( 311 ) );

		assertThat( relationalPredicate.getLeftHandExpression(), instanceOf( CollectionSizeFunction.class ) );
		assertThat( ( (CollectionSizeFunction) relationalPredicate.getLeftHandExpression() ).getFromElementAlias(), is( "t" ) );
		assertThat( ( (CollectionSizeFunction) relationalPredicate.getLeftHandExpression() ).getAttributeDescriptor().getName(), is( "basicCollection" ) );
	}

	@Test
	public void testCollectionIndexFunction() {
		SelectStatement statement = interpret( "SELECT l.basicName FROM Trip t JOIN t.indexedCollectionLegs l WHERE INDEX( l ) > 2" );

		Predicate predicate = statement.getQuerySpec().getWhereClause().getPredicate();
		assertThat( predicate, instanceOf( RelationalPredicate.class ) );
		RelationalPredicate relationalPredicate = ( (RelationalPredicate) predicate );

		assertThat( relationalPredicate.getType(), is( RelationalPredicate.Type.GT ) );

		assertThat( relationalPredicate.getRightHandExpression(), instanceOf( LiteralIntegerExpression.class ) );
		assertThat( ( (LiteralIntegerExpression) relationalPredicate.getRightHandExpression() ).getLiteralValue(), is( 2 ) );

		assertThat( relationalPredicate.getLeftHandExpression(), instanceOf( CollectionIndexFunction.class ) );
		assertThat( ( (CollectionIndexFunction) relationalPredicate.getLeftHandExpression() ).getCollectionAlias(), is( "l" ) );
	}

	@Test
	public void testMapKeyFunction() {
		SelectStatement statement = interpret( "SELECT l.basicName FROM Trip t JOIN t.mapLegs l WHERE KEY( l ) = 'foo'" );

		Predicate predicate = statement.getQuerySpec().getWhereClause().getPredicate();
		assertThat( predicate, instanceOf( RelationalPredicate.class ) );
		RelationalPredicate relationalPredicate = ( (RelationalPredicate) predicate );

		assertThat( relationalPredicate.getLeftHandExpression(), instanceOf( MapKeyFunction.class ) );
		assertThat( ( (MapKeyFunction) relationalPredicate.getLeftHandExpression() ).getCollectionAlias(), is( "l" ) );
		assertThat( ( (MapKeyFunction) relationalPredicate.getLeftHandExpression() ).getMapKeyType().getJavaType().getName(), is( "java.lang.String" ) );
	}

	private SelectStatement interpret(String query) {
		return (SelectStatement) SemanticQueryInterpreter.interpret( query, consumerContext );
	}


	private ExtendedMetamodel buildMetamodel() {
		ExplicitModelMetadata metamodel = new ExplicitModelMetadata();

		EntityTypeImpl legType = metamodel.makeEntityType( "com.acme.Leg" );
		legType.makeSingularAttribute(
				"basicName",
				Attribute.PersistentAttributeType.BASIC,
				StandardBasicTypeDescriptors.INSTANCE.STRING
		);

		EntityTypeImpl tripType = metamodel.makeEntityType( "com.acme.Trip" );

		tripType.makeSetAttribute(
				"basicCollection",
				Attribute.PersistentAttributeType.ELEMENT_COLLECTION,
				StandardBasicTypeDescriptors.INSTANCE.STRING
		);
		tripType.makeListAttribute(
				"indexedCollectionLegs",
				Attribute.PersistentAttributeType.ONE_TO_MANY,
				legType
		);
		tripType.makeMapAttribute(
				"mapLegs",
				Attribute.PersistentAttributeType.ONE_TO_MANY,
				StandardBasicTypeDescriptors.INSTANCE.STRING,
				legType
		);

		return metamodel;
	}
}
