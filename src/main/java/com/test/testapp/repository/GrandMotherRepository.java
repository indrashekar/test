package com.test.testapp.repository;

import com.test.testapp.domain.GrandMother;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GrandMother entity.
 */
@SuppressWarnings("unused")
public interface GrandMotherRepository extends JpaRepository<GrandMother,Long> {

}
