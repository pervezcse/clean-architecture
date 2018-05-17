package cleanarchitecture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import cleanarchitecture.core.entity.Patient.BloodType;
import cleanarchitecture.core.entity.Patient.Sex;
import lombok.Data;

@Data
public class PatientDto {

	private Long id;
	private String name;
	private String age;
	private Sex sex;
	private BloodType bloodType;
	private String address;
	@JsonProperty(access = Access.READ_ONLY)
	private String imageFileName;
}
