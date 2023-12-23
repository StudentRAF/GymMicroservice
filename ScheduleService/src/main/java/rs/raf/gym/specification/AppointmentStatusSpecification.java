package rs.raf.gym.specification;

import rs.raf.gym.model.AppointmentStatus;

public class AppointmentStatusSpecification extends BaseSpecification<AppointmentStatus> {

    public AppointmentStatusSpecification(String name) {
        addNameSpecification(name);
    }

    private void addNameSpecification(String name) {
        if (name == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(AppointmentStatus.name()),
                                                                                   name));
    }

}
