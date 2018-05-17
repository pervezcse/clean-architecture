package cleanarchitecture.controller;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cleanarchitecture.core.entity.Patient;
import cleanarchitecture.core.entity.Patient.ImageSaver.FileInfo;
import cleanarchitecture.core.service.PatientService;
import cleanarchitecture.dto.PatientDto;
import cleanarchitecture.specification.PatientSpecifications;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final PatientService patientService;
	private final ModelMapper modelMapper;
	private final PatientSpecifications patientSpecs;

	@RequestMapping(method = RequestMethod.POST)
	public PatientDto addPatient(@Valid PatientDto patientDto, @RequestParam("file") MultipartFile uploadFile) {
		FileInfo fileInfo = null;
		if (uploadFile != null) {
			try (InputStream image = uploadFile.getInputStream()) {
				fileInfo = new FileInfo();
				fileInfo.setData(image);
				fileInfo.setFileName(uploadFile.getOriginalFilename());
				fileInfo.setFileSize(uploadFile.getSize());
				Patient patient = modelMapper.map(patientDto, Patient.class);
				Patient result = patientService.addPatient(patient, Optional.of(fileInfo));
				return modelMapper.map(result, PatientDto.class);
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		Patient patient = modelMapper.map(patientDto, Patient.class);
		Patient result = patientService.addPatient(patient, Optional.ofNullable(fileInfo));
		return modelMapper.map(result, PatientDto.class);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<PatientDto> getPatients() {
		List<Patient> patients = patientService.getPatients();
		Type listType = new TypeToken<List<PatientDto>>() {}.getType();
		return modelMapper.map(patients, listType);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/searchresults")
	public List<PatientDto> searchPatients(@Valid @RequestBody PatientDto patientDto) {
		List<Patient> patients = patientService.searchPatient(patientSpecs.search(patientDto));
		Type listType = new TypeToken<List<PatientDto>>() {}.getType();
		return modelMapper.map(patients, listType);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public PatientDto getPatient(@PathVariable Long id) {
		Optional<Patient> patient = patientService.getPatient(patientSpecs.getById(id));
		if(patient.isPresent())
			return modelMapper.map(patient.get(), PatientDto.class);
		return null;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public PatientDto updatePatient(@PathVariable Long id, @Valid PatientDto patientDto,
			@RequestParam("file") MultipartFile uploadFile) {
		patientDto.setId(id);
		FileInfo fileInfo = null;
		if (uploadFile != null) {
			try (InputStream image = uploadFile.getInputStream()) {
				fileInfo = new FileInfo();
				fileInfo.setData(image);
				fileInfo.setFileName(uploadFile.getOriginalFilename());
				fileInfo.setFileSize(uploadFile.getSize());
				Patient patient = modelMapper.map(patientDto, Patient.class);
				Patient result = patientService.updatePatient(patient, Optional.of(fileInfo));
				return modelMapper.map(result, PatientDto.class);
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		Patient patient = modelMapper.map(patientDto, Patient.class);
		Patient result = patientService.updatePatient(patient, Optional.ofNullable(fileInfo));
		return modelMapper.map(result, PatientDto.class);
	}
}