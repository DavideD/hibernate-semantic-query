/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.test.query.parser.hql;

import org.hibernate.query.parser.internal.hql.antlr.HqlParser;
import org.hibernate.query.parser.internal.ImplicitAliasGenerator;
import org.hibernate.query.parser.internal.hql.HqlParseTreeBuilder;
import org.hibernate.query.parser.internal.hql.phase1.FromClauseProcessor;
import org.hibernate.sqm.query.JoinType;
import org.hibernate.sqm.query.from.FromClause;
import org.hibernate.sqm.query.from.FromElement;
import org.hibernate.sqm.query.from.FromElementSpace;
import org.hibernate.sqm.query.from.QualifiedAttributeJoinFromElement;
import org.hibernate.sqm.query.from.RootEntityFromElement;

import org.hibernate.test.query.parser.ParsingContextImpl;
import org.junit.Test;

import org.antlr.v4.runtime.tree.ParseTreeWalker;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Initial work on a "from clause processor"
 *
 * @author Steve Ebersole
 */
public class HqlFromClauseProcessorPocTest {
	@Test
	public void testSimpleFrom() throws Exception {
		final HqlParser parser = HqlParseTreeBuilder.INSTANCE.parseHql( "select a.b from Something a" );
		final FromClauseProcessor fromClauseProcessor = processFromClause( parser );

		assertEquals( 1, fromClauseProcessor.getFromClauseIndex().getFromClauseStackNodeList().size() );
		final FromClause fromClause1 = fromClauseProcessor.getFromClauseIndex().getFromClauseStackNodeList().get( 0 ).getFromClause();
		assertNotNull( fromClause1 );
		assertEquals( 1, fromClause1.getFromElementSpaces().size() );
		FromElementSpace space1 = fromClause1.getFromElementSpaces().get( 0 );
		assertNotNull( space1 );
		assertNotNull( space1.getRoot() );
		assertEquals( 0, space1.getJoins().size() );
		RootEntityFromElement root = space1.getRoot();
		assertNotNull( root );
		assertThat( root.getAlias(), is("a") );
	}

	private FromClauseProcessor processFromClause(HqlParser parser) {
		final FromClauseProcessor explicitFromClauseIndexer = new FromClauseProcessor( new ParsingContextImpl() );
		ParseTreeWalker.DEFAULT.walk( explicitFromClauseIndexer, parser.statement() );
		return explicitFromClauseIndexer;
	}

	@Test
	public void testMultipleSpaces() throws Exception {
		final HqlParser parser = HqlParseTreeBuilder.INSTANCE.parseHql( "select a.b from Something a, SomethingElse b" );
		final FromClauseProcessor fromClauseProcessor = processFromClause( parser );

		assertEquals( 1, fromClauseProcessor.getFromClauseIndex().getFromClauseStackNodeList().size() );
		final FromClause fromClause1 = fromClauseProcessor.getFromClauseIndex().getFromClauseStackNodeList().get( 0 ).getFromClause();
		assertNotNull( fromClause1 );
//		assertEquals( 0, fromClause1.getChildFromClauses().size() );
		assertEquals( 2, fromClause1.getFromElementSpaces().size() );
		FromElementSpace space1 = fromClause1.getFromElementSpaces().get( 0 );
		FromElementSpace space2 = fromClause1.getFromElementSpaces().get( 1 );
		assertNotNull( space1.getRoot() );
		assertEquals( 0, space1.getJoins().size() );
		assertNotNull( space2.getRoot() );
		assertEquals( 0, space2.getJoins().size() );

		assertNotNull( space1.getRoot() );
		assertThat(space1.getRoot().getAlias(), is("a")  );

		assertNotNull( space2.getRoot() );
		assertThat(space2.getRoot().getAlias(), is("b")  );
	}

	@Test
	public void testImplicitAlias() throws Exception {
		final HqlParser parser = HqlParseTreeBuilder.INSTANCE.parseHql( "select b from Something" );
		final FromClauseProcessor fromClauseProcessor = processFromClause( parser );

		assertEquals( 1, fromClauseProcessor.getFromClauseIndex().getFromClauseStackNodeList().size() );
		final FromClause fromClause1 = fromClauseProcessor.getFromClauseIndex().getFromClauseStackNodeList().get( 0 ).getFromClause();
		assertNotNull( fromClause1 );
//		assertEquals( 0, fromClause1.getChildFromClauses().size() );
		assertEquals( 1, fromClause1.getFromElementSpaces().size() );
		FromElementSpace space1 = fromClause1.getFromElementSpaces().get( 0 );
		assertNotNull( space1 );
		assertNotNull( space1.getRoot() );
		assertEquals( 0, space1.getJoins().size() );
		assertTrue( ImplicitAliasGenerator.isImplicitAlias( space1.getRoot().getAlias() ) );
	}

	@Test
	public void testCrossJoin() throws Exception {
		final HqlParser parser = HqlParseTreeBuilder.INSTANCE.parseHql( "select a.b from Something a cross join SomethingElse b" );
		final FromClauseProcessor fromClauseProcessor = processFromClause( parser );

		assertEquals( 1, fromClauseProcessor.getFromClauseIndex().getFromClauseStackNodeList().size() );
		final FromClause fromClause1 = fromClauseProcessor.getFromClauseIndex().getFromClauseStackNodeList().get( 0 ).getFromClause();
		assertNotNull( fromClause1 );
//		assertEquals( 0, fromClause1.getChildFromClauses().size() );
		assertEquals( 1, fromClause1.getFromElementSpaces().size() );
		FromElementSpace space1 = fromClause1.getFromElementSpaces().get( 0 );
		assertNotNull( space1 );
		assertNotNull( space1.getRoot() );
		assertEquals( 1, space1.getJoins().size() );
	}

	@Test
	public void testSimpleImplicitInnerJoin() throws Exception {
		simpleJoinAssertions(
				HqlParseTreeBuilder.INSTANCE.parseHql( "select a.basic from Something a join a.entity c" ),
				JoinType.INNER
		);
	}

	private void simpleJoinAssertions(HqlParser parser, JoinType joinType) {
		final FromClauseProcessor fromClauseProcessor = processFromClause( parser );

		assertEquals( 1, fromClauseProcessor.getFromClauseIndex().getFromClauseStackNodeList().size() );
		final FromClause fromClause1 = fromClauseProcessor.getFromClauseIndex().getFromClauseStackNodeList().get( 0 ).getFromClause();
		assertNotNull( fromClause1 );
//		assertEquals( 0, fromClause1.getChildFromClauses().size() );
		assertEquals( 1, fromClause1.getFromElementSpaces().size() );
		FromElementSpace space1 = fromClause1.getFromElementSpaces().get( 0 );
		assertNotNull( space1 );
		assertNotNull( space1.getRoot() );
		assertEquals( 1, space1.getJoins().size() );
	}

	@Test
	public void testSimpleExplicitInnerJoin() throws Exception {
		simpleJoinAssertions(
				HqlParseTreeBuilder.INSTANCE.parseHql( "select a.basic from Something a inner join a.entity c" ),
				JoinType.INNER
		);
	}

	@Test
	public void testSimpleExplicitOuterJoin() throws Exception {
		simpleJoinAssertions(
				HqlParseTreeBuilder.INSTANCE.parseHql( "select a.basic from Something a outer join a.entity c" ),
				JoinType.LEFT
		);
	}

	@Test
	public void testSimpleExplicitLeftOuterJoin() throws Exception {
		simpleJoinAssertions(
				HqlParseTreeBuilder.INSTANCE.parseHql( "select a.basic from Something a left outer join a.entity c" ),
				JoinType.LEFT
		);
	}

	@Test
	public void testAttributeJoinWithOnClause() throws Exception {
		final HqlParser parser = HqlParseTreeBuilder.INSTANCE.parseHql( "select a from Something a left outer join a.entity c on c.basic1 > 5 and c.basic2 < 20 " );
		final FromClauseProcessor fromClauseProcessor = processFromClause( parser );

		assertEquals( 1, fromClauseProcessor.getFromClauseIndex().getFromClauseStackNodeList().size() );
		final FromClause fromClause1 = fromClauseProcessor.getFromClauseIndex().getFromClauseStackNodeList().get( 0 ).getFromClause();
		assertNotNull( fromClause1 );
//		assertEquals( 0, fromClause1.getChildFromClauses().size() );
		assertEquals( 1, fromClause1.getFromElementSpaces().size() );
		FromElementSpace space1 = fromClause1.getFromElementSpaces().get( 0 );
		assertNotNull( space1 );
		assertNotNull( space1.getRoot() );
		assertEquals( 1, space1.getJoins().size() );
	}
}
