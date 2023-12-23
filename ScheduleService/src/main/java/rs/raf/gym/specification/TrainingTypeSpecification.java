package rs.raf.gym.specification;

import rs.raf.gym.model.TrainingType;

public class TrainingTypeSpecification extends BaseSpecification<TrainingType> {

    public TrainingTypeSpecification(String name) {
        addTypeSpecification(name);
    }

    private void addTypeSpecification(String name) {
        if (name == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(TrainingType.name()), name));
    }

}
