package com.devonfw.sample.archunit.thirdparty;

import javax.persistence.EntityManager; // Noncompliant

public class DevonArchitecture3rdPartyJpaCheck_NotOkTest {
    //Noncompliant
    private EntityManager entityManager;
}
