package cleanarchitecture.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cleanarchitecture.core.entity.Patient;


public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {

//	Page<Patient> findAll(Pageable pageable);
//	@Query("FROM Patient p " + "where (:#{#patient.name} is NULL or p.name like %:#{#patient.name}%) "
//			+ "and (:#{#patient.sex} is NULL or p.sex = :#{#patient.sex}) "
//			+ "and (:#{#patient.bloodType} is NULL or p.bloodType = :#{#patient.bloodType}) "
//			+ "and (:#{#patient.address} is NULL or p.address like %:#{#patient.address}%)")
//	Page<Patient> search(@Param("patient") Patient patient, Pageable pageable);
}
