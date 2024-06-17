package hac.ex5.repo;

import hac.ex5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//al database operation will be handled here

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Example of a custom query method to find a user by their email address
    User findByEmail(String email);
}
