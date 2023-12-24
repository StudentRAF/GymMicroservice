package rs.raf.gym.repository;

import org.springframework.stereotype.Repository;
import rs.raf.gym.model.TrainingAppointment;
import rs.raf.gym.model.composite.TrainingAppointmentComposite;

@Repository
public interface ITrainingAppointmentRepository extends JpaSpecificationRepository<TrainingAppointment, TrainingAppointmentComposite> {

}
