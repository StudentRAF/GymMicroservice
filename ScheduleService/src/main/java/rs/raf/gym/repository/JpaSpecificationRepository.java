package rs.raf.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaSpecificationRepository<Type, ID> extends JpaRepository<Type, ID>, JpaSpecificationExecutor<Type> {

}
