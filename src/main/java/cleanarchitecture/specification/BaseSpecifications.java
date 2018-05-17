package cleanarchitecture.specification;

import org.springframework.data.jpa.domain.Specification;

abstract class BaseSpecifications<T> {

	private final String wildcard = "%";

	Specification<T> attributeContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}
			return cb.like(cb.lower(root.get(attribute)), toLowerCase(value));
		};
	}

	Specification<T> attributeEquals(String attribute, Object value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}
			return cb.equal(root.get(attribute), value);
		};
	}
	
//	private Specification<User> addressAttributeContains(String attribute, String value) {
//        return (root, query, cb) -> {
//            if(value == null) {
//                return null;
//            }
//            ListJoin<User, Address> addresses = root.joinList("addresses", JoinType.INNER);
//            return cb.like(
//                cb.lower(addresses.get(attribute)),
//                containsLowerCase(value)
//            );
//        };
//    }

	String toLowerCase(String searchField) {
		return wildcard + searchField.toLowerCase() + wildcard;
	}
}
