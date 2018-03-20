package au.com.totp.example.security.respository;

import au.com.totp.example.security.model.SBTUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<SBTUser, String> {
}
