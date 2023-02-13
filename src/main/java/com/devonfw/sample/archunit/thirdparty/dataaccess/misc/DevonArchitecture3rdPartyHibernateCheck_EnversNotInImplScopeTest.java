package com.devonfw.sample.archunit.thirdparty.dataaccess.misc;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.envers.Audited; // Noncompliant

public class DevonArchitecture3rdPartyHibernateCheck_EnversNotInImplScopeTest {
    @Id
    @GeneratedValue
    private Integer id;

    @Audited
    private String name;
}
