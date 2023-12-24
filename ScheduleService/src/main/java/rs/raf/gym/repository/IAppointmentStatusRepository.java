package rs.raf.gym.repository;

import org.springframework.stereotype.Repository;
import rs.raf.gym.model.AppointmentStatus;

import java.util.Optional;

@Repository
public interface IAppointmentStatusRepository extends JpaSpecificationRepository<AppointmentStatus, Long> {

    Optional<AppointmentStatus> findByName(String name);

}
