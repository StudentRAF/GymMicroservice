package rs.raf.gym.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BaseSpecification<Type> {

    protected final List<Specification<Type>> specifications = new ArrayList<>();

    protected BaseSpecification() { }

    protected boolean isEmpty() {
        return specifications.isEmpty();
    }

    protected Specification<Type> get(int index) {
        return specifications.get(index);
    }

    protected int size() {
        return specifications.size();
    }

    public Specification<Type> filter() {
        if (isEmpty())
            return null;

        Specification<Type> specification = Specification.where(get(0));

        for (int index = 1; index < size(); ++index)
             specification = specification.and(get(index));

        return specification;
    }

}
