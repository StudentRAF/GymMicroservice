package rs.raf.gym.specification;

import rs.raf.gym.model.AppointmentStatus;
import rs.raf.gym.model.Gym;
import rs.raf.gym.model.GymTraining;
import rs.raf.gym.model.Training;
import rs.raf.gym.model.TrainingAppointment;
import rs.raf.gym.model.composite.GymTrainingComposite;
import rs.raf.gym.model.composite.TrainingAppointmentComposite;

import java.time.LocalDate;
import java.time.LocalTime;

public class TrainingAppointmentSpecification extends BaseSpecification<TrainingAppointment> {

    public TrainingAppointmentSpecification(String gym, String training, LocalDate date, LocalTime time,
                                            Integer duration, String status) {
        addGymSpecification(gym);
        addTrainingSpecification(training);
        addDateSpecification(date);
        addTimeSpecification(time);
        addDurationSpecification(duration);
        addStatusSpecification(status);
    }

    private void addGymSpecification(String gym) {
        if (gym == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(TrainingAppointment.composite())
                                                                                       .join(TrainingAppointmentComposite.gymTraining())
//                                                                                       .join(GymTraining.composite())
                                                                                       .join(GymTrainingComposite.gym())
                                                                                       .get(Gym.name()),
                                                                                   gym));
    }

    private void addTrainingSpecification(String training) {
        if (training == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(TrainingAppointment.composite())
                                                                                       .join(TrainingAppointmentComposite.gymTraining())
//                                                                                       .join(GymTraining.composite())
                                                                                       .join(GymTrainingComposite.training())
                                                                                       .get(Training.name()),
                                                                                   training));
    }

    private void addDateSpecification(LocalDate date) {
        if (date == null)
            return;
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(TrainingAppointment.composite())
                                                                                       .get(TrainingAppointmentComposite.date()),
                                                                                   date));
    }

    private void addTimeSpecification(LocalTime time) {
        if (time == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(TrainingAppointment.composite())
                                                                                       .get(TrainingAppointmentComposite.time()),
                                                                                   time));
    }

    private void addDurationSpecification(Integer duration) {
        if (duration == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(TrainingAppointment.duration()),
                                                                                   duration));
    }

    private void addStatusSpecification(String status) {
        if (status == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(TrainingAppointment.status())
                                                                                       .get(AppointmentStatus.name()),
                                                                                   status));
    }

}
