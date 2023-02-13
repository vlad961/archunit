package com.devonfw.sample.archunit.thirdparty.dataaccess.impl;

import org.hibernate.annotations.Entity; // Noncompliant

public class DevonArchitecture3rdPartyHibernateCheck_DiscouragedAnnotationsTest {
    @Entity
    class NoncompliantInnerClass{
    }
}
