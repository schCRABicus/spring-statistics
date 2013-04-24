package com.gmail.schcrabicus.spring.stats.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.DateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, Boolean> enabled;
	public static volatile SingularAttribute<User, Boolean> accountNonExpired;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, byte[]> photo;
	public static volatile SingularAttribute<User, Integer> version;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SetAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, String> login;
	public static volatile SingularAttribute<User, DateTime> birthDate;
	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, Boolean> credentialsNonExpired;
	public static volatile SingularAttribute<User, Boolean> accountNonLocked;

}

