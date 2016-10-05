package com.test.testapp.repository;

import com.test.testapp.domain.Standard;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Standard entity.
 */
@SuppressWarnings("unused")
public interface StandardRepository extends JpaRepository<Standard,Long> {

}
