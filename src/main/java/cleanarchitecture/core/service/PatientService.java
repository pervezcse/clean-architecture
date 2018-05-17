package cleanarchitecture.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cleanarchitecture.core.entity.Patient;
import cleanarchitecture.core.entity.Patient.ImageSaver;
import cleanarchitecture.core.entity.Patient.ImageSaver.FileInfo;
import cleanarchitecture.core.repository.PatientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {
	
	private final PatientRepository patientRepo;
	private final ImageSaver imageSaver;
	
	public Patient addPatient(Patient patient, Optional<FileInfo> image) {
		if(image.isPresent())
			patient.setProfileImage(image.get(), imageSaver);
		return patientRepo.save(patient);
	}

	public Patient updatePatient(Patient patient, Optional<FileInfo> image) {
		if(image.isPresent())
			patient.setProfileImage(image.get(), imageSaver);
		return patientRepo.save(patient);
	}
	
	public List<Patient> getPatients() {
		return patientRepo.findAll();
	}
	
	public List<Patient> searchPatient(Specification<Patient> spec) {
		return patientRepo.findAll(spec);
	}
	
	public Optional<Patient> getPatient(Specification<Patient> spec) {
		return patientRepo.findOne(spec);
	}
}
