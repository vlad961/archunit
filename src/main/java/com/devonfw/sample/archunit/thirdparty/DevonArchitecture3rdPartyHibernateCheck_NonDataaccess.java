package com.devonfw.sample.archunit.thirdparty;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session; // Noncompliant
import org.hibernate.annotations.Entity; // Noncompliant
import org.hibernate.annotations.OrderBy; // Noncompliant
import org.hibernate.annotations.Cascade; // Noncompliant
import org.hibernate.annotations.FilterDef; // Noncompliant
public class DevonArchitecture3rdPartyHibernateCheck_NonDataaccess {
    @Entity
    class NoncompliantInnerClass{
        @OrderBy(clause = "NAME DESC")
        Set<String> taskList = new HashSet<>();
    }

}
