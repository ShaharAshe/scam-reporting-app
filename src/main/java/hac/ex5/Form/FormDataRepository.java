package hac.ex5.Form;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormDataRepository extends JpaRepository<FormData, Long> {

}
