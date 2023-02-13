package com.devonfw.sample.archunit.thirdparty.dataaccess.impl;

import org.hibernate.envers.query.internal.impl.EntitiesAtRevisionQuery; // Noncompliant

public class DevonArchitecture3rdPartyHibernateCheck_UsingHibernateEnversDirectlyTest {
    //Noncompliant
    EntitiesAtRevisionQuery nonCompliantDirectQuery = new EntitiesAtRevisionQuery(null, null, getClass(), null, false);
}
