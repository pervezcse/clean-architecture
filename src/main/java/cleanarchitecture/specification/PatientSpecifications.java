package cleanarchitecture.specification;

import org.springframework.data.jpa.domain.Specification;
import static org.springframework.data.jpa.domain.Specification.where;
import org.springframework.stereotype.Component;
import cleanarchitecture.core.entity.Patient;
import cleanarchitecture.dto.PatientDto;

@Component
public class PatientSpecifications extends BaseSpecifications<Patient> {

	public Specification<Patient> search(PatientDto dto) {
		return (root, query, cb) -> {
			query.distinct(true);
			return where(nameContains(dto.getName())).and(addressContains(dto.getAddress()))
					.and(bloodTypeEquals(dto.getBloodType())).and(sexEquals(dto.getSex())).toPredicate(root, query, cb);
		};
	}

	public Specification<Patient> getById(Long id) {
		return (root, query, cb) -> {
			query.distinct(true);
			return where(idEquals(id)).toPredicate(root, query, cb);
		};
	}

	private Specification<Patient> idEquals(Long id) {
		return attributeEquals("id", id);
	}

	private Specification<Patient> nameContains(String firstName) {
		return attributeContains("name", firstName);
	}

	private Specification<Patient> addressContains(String address) {
		return attributeContains("address", address);
	}

	private Specification<Patient> bloodTypeEquals(Object bloodType) {
		return attributeEquals("bloodType", bloodType);
	}

	private Specification<Patient> sexEquals(Object sex) {
		return attributeEquals("sex", sex);
	}
}
