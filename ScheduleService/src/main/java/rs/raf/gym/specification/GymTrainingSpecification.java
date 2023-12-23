package rs.raf.gym.specification;

import rs.raf.gym.model.Gym;
import rs.raf.gym.model.GymTraining;
import rs.raf.gym.model.Training;
import rs.raf.gym.model.composite.GymTrainingComposite;

import java.text.MessageFormat;

public class GymTrainingSpecification extends BaseSpecification<GymTraining> {

    public GymTrainingSpecification(String gym, String training, Double price, Integer minParticipants,
                                    Integer maxParticipants) {
        addGymSpecification(gym);
        addTrainingSpecification(training);
        addPriceSpecification(price);
        addMinParticipantsSpecification(minParticipants);
        addMaxParticipantsSpecification(maxParticipants);
    }

    private void addGymSpecification(String gym) {
        if (gym == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(GymTraining.composite())
                                                                                       .join(GymTrainingComposite.gym())
                                                                                       .get(Gym.name()), gym));
    }

    private void addTrainingSpecification(String training) {
        if (training == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(GymTraining.composite())
                                                                                       .join(GymTrainingComposite.training())
                                                                                       .get(Training.name()), training));
    }

    private void addPriceSpecification(Double price) {
        if (price == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(GymTraining.price()), price));
    }

    private void addMinParticipantsSpecification(Integer minParticipants) {
        if (minParticipants == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(GymTraining.minParticipants()),
                                                                                   minParticipants));
    }

    private void addMaxParticipantsSpecification(Integer maxParticipants) {
        if (maxParticipants == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(GymTraining.maxParticipants()),
                                                                                   maxParticipants));
    }

}
