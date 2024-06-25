package hac.ex5.repo;

import hac.ex5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for handling the database operations for User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their email address.
     * @param email The email to search for.
     * @return User with the specified email.
     */
    User findByEmail(String email);

    /**
     * Find a user by their username.
     * @param username The username to search for.
     * @return User with the specified username.
     */
    User findByUsername(String username);

    /**
     * Count all users by their email status and containing a specific role.
     * @param role The role to check in the users.
     * @return count of users.
     */
    long countAllByEmailIsTrueAndRoleContaining(String role);
}
