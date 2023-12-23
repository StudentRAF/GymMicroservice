package rs.raf.gym.repository;

import org.springframework.stereotype.Repository;
import rs.raf.gym.model.AppointmentStatus;

@Repository
public interface IAppointmentStatusRepository extends JpaSpecificationRepository<AppointmentStatus, String> {

}
