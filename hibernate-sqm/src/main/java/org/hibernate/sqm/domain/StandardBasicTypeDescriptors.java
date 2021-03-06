/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author Steve Ebersole
 */
public class StandardBasicTypeDescriptors {
	private static final Map<Class,BasicTypeDescriptor> DESCRIPTOR_MAP = new HashMap<Class, BasicTypeDescriptor>();

	/**
	 * Singleton access
	 */
	public static final StandardBasicTypeDescriptors INSTANCE = new StandardBasicTypeDescriptors();

	private StandardBasicTypeDescriptors() {
	}

	public final BasicTypeDescriptor NULL = new BasicTypeDescriptorImpl( Void.class );

	public final BasicTypeDescriptor BOOLEAN = new BasicTypeDescriptorImpl( Boolean.class );

	public final BasicTypeDescriptor CHAR = new BasicTypeDescriptorImpl( Character.class );
	public final BasicTypeDescriptor STRING = new BasicTypeDescriptorImpl( String.class );

	public final BasicTypeDescriptor BYTE = new BasicTypeDescriptorImpl( Byte.class );
	public final BasicTypeDescriptor SHORT = new BasicTypeDescriptorImpl( Short.class );
	public final BasicTypeDescriptor INTEGER = new BasicTypeDescriptorImpl( Integer.class );
	public final BasicTypeDescriptor LONG = new BasicTypeDescriptorImpl( Long.class );
	public final BasicTypeDescriptor BIG_INTEGER = new BasicTypeDescriptorImpl( BigInteger.class );
	public final BasicTypeDescriptor FLOAT = new BasicTypeDescriptorImpl( Float.class );
	public final BasicTypeDescriptor DOUBLE = new BasicTypeDescriptorImpl( Double.class );
	public final BasicTypeDescriptor BIG_DECIMAL = new BasicTypeDescriptorImpl( BigDecimal.class );

	public final BasicTypeDescriptor UUID = new BasicTypeDescriptorImpl( java.util.UUID.class );

	public final BasicTypeDescriptor BLOB = new BasicTypeDescriptorImpl( Blob.class );
	public final BasicTypeDescriptor CLOB = new BasicTypeDescriptorImpl( Clob.class );
	public final BasicTypeDescriptor NCLOB = new BasicTypeDescriptorImpl( NClob.class );

	public final BasicTypeDescriptor DATE = new BasicTypeDescriptorImpl( java.util.Date.class );
	public final BasicTypeDescriptor JDBC_DATE = new BasicTypeDescriptorImpl( Date.class );
	public final BasicTypeDescriptor JDBC_TIME = new BasicTypeDescriptorImpl( Time.class );
	public final BasicTypeDescriptor JDBC_TIMESTAMP = new BasicTypeDescriptorImpl( Timestamp.class );
	public final BasicTypeDescriptor CALENDAR = new BasicTypeDescriptorImpl( Calendar.class );
	public final BasicTypeDescriptor TIMEZONE = new BasicTypeDescriptorImpl( TimeZone.class );

	public final BasicTypeDescriptor CLASS = new BasicTypeDescriptorImpl( Class.class );

	public final BasicTypeDescriptor LOCALE = new BasicTypeDescriptorImpl( Locale.class );
	public final BasicTypeDescriptor CURRENCY = new BasicTypeDescriptorImpl( Currency.class );

	public final BasicTypeDescriptor URL = new BasicTypeDescriptorImpl( java.net.URL.class );

	public final BasicTypeDescriptor MAP = new BasicTypeDescriptorImpl( Map.class );
	public final BasicTypeDescriptor LIST = new BasicTypeDescriptorImpl( List.class );
	public final BasicTypeDescriptor SET = new BasicTypeDescriptorImpl( Set.class );

	/**
	 * todo : Ultimately it would be better to delegate this to the ConsumerContext
	 * <p/>
	 * Delegating to ConsumerContext has a huge `paradigm mismatch` though in the difference
	 * between Class and org.hibernate.type.Type
	 */
	public BasicTypeDescriptor standardDescriptorForType(Class javaType) {
		return DESCRIPTOR_MAP.get( javaType );
	}

	private static class BasicTypeDescriptorImpl implements BasicTypeDescriptor {
		private final Class javaType;

		private BasicTypeDescriptorImpl(Class javaType) {
			this.javaType = javaType;
			DESCRIPTOR_MAP.put( javaType, this );
		}

		@Override
		public String getTypeName() {
			return javaType.getName();
		}

		@Override
		public AttributeDescriptor getAttributeDescriptor(String attributeName) {
			return null;
		}

		@Override
		public Class getCorrespondingJavaType() {
			return javaType;
		}
	}

}
