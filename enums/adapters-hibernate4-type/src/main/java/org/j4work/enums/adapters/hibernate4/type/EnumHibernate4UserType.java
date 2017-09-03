package org.j4work.enums.adapters.hibernate4.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.EnhancedUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * http://learningviacode.blogspot.gr/2011/09/creating-hibernate-custom-type-4.html
 * https://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/ch06.html#types-custom
 * https://docs.jboss.org/hibernate/orm/4.3/javadocs/org/hibernate/usertype/EnhancedUserType.html
 * http://blog.xebia.com/understanding-and-writing-hibernate-user-types/
 */
public class EnumHibernate4UserType implements EnhancedUserType {

    @Override
    public String objectToSQLString(Object o) {
        return null;
    }

    @Override
    public String toXMLString(Object o) {
        return null;
    }

    @Override
    public Object fromXMLString(String s) {
        return null;
    }

    @Override
    public int[] sqlTypes() {
        return new int[0];
    }

    @Override
    public Class returnedClass() {
        return null;
    }

    @Override
    public boolean equals(Object o, Object o1)
        throws HibernateException {
        return false;
    }

    @Override
    public int hashCode(Object o)
        throws HibernateException {
        return 0;
    }

    @Override
    public Object nullSafeGet(
        ResultSet resultSet, String[] strings, SessionImplementor sessionImplementor, Object o
    )
        throws HibernateException, SQLException {
        return null;
    }

    @Override
    public void nullSafeSet(
        PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor
    )
        throws HibernateException, SQLException {

    }

    @Override
    public Object deepCopy(Object o)
        throws HibernateException {
        return null;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o)
        throws HibernateException {
        return null;
    }

    @Override
    public Object assemble(Serializable serializable, Object o)
        throws HibernateException {
        return null;
    }

    @Override
    public Object replace(Object o, Object o1, Object o2)
        throws HibernateException {
        return null;
    }
}
