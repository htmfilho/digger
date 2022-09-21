/*
 * Digger
 * Copyright (C) 2019-2022 Hildeberto Mendonca
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * A full copy of the GNU General Public License is available at:
 * https://github.com/htmfilho/digger/blob/master/LICENSE
 */

package digger.repository;

import digger.model.User;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
    Long count();
    Long countAllByEnabled(Boolean enabled);
    
    User findById(long id);
    User findByUsername(String username);

    @Query(value = "select nextval('user_id_seq')", nativeQuery = true)
    BigDecimal getSequenceNextVal();

    List<User> findAll();
    List<User> findAllByOrderByUsernameAsc();

    void save(User user);
    void deleteById(Long id);
}