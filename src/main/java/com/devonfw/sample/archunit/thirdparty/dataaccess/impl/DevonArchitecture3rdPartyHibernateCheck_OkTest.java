package com.devonfw.sample.archunit.thirdparty.dataaccess.impl;

import org.hibernate.Session; // compliant
import org.hibernate.annotations.FilterDef; // compliant
import org.hibernate.envers.Audited; // compliant
import org.hibernate.validator.HibernateValidatorFactory; // compliant

public class DevonArchitecture3rdPartyHibernateCheck_OkTest {
    @Audited
    Long id;
    
    Session session;
}
