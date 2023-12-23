package rs.raf.gym.specification;

import org.springframework.data.jpa.domain.Specification;
import rs.raf.gym.model.TrainingType;

public class TrainingTypeSpecification extends BaseSpecification<TrainingType> {

    private TrainingTypeSpecification(String name) {
        addTypeSpecification(name);
    }

    private void addTypeSpecification(String name) {
        if (name == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(TrainingType.name()), name));
    }

    public static Specification<TrainingType> of(String name) {
        return new TrainingTypeSpecification(name).merge();
    }

}
