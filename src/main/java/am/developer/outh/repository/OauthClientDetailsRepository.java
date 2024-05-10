package am.developer.outh.repository;

import am.developer.outh.model.OauthClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetail, String> {
}
