/*
 * Copyright (C) 2023. Lazar Dobrota and Nemanja Radovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rs.raf.gym.commons.specification;

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
