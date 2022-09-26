package com.valex.repository;

import com.valex.domain.model.User;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

  User findByEmail (String email);

  @NotNull Optional<User> findById(@NotNull Long id);

//  @Query ("select u from User u left join fetch u.cards where u.id = :id")
//  User findByIdFetchCards (Long id);
}
