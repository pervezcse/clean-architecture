package cleanarchitecture.core.entity;

import java.io.InputStream;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import cleanarchitecture.core.entity.Patient.ImageSaver.FileInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(of = { "id" })
@Entity
public class Patient {

	public enum Sex {
		MALE, FEMALE
	}

	public enum BloodType {
		A_POSITIVE("A+"), A_NEGATIVE("A-"), B_POSITIVE("B+"), B_NEGATIVE("B-"), AB_POSITIVE("AB+"), AB_NEGATIVE(
				"AB-"), O_NEGATIVE("O-"), O_POSITIVE("O+");

		private final String label;

		private BloodType(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return label;
		}
	}

	public interface ImageSaver {

		@Data
		public static class FileInfo {
			private String fileName;
			private long fileSize;
			private InputStream data;
		}
		Optional<FileInfo> save(FileInfo image);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String age;
	@Enumerated(EnumType.STRING)
	private Sex sex;
	@Enumerated(EnumType.STRING)
	private BloodType bloodType;
	private String address;
	private String imageFileName;

	public Boolean setProfileImage(FileInfo imageInfo, ImageSaver imageSaver) {
		Optional<FileInfo> savedImageInfo = imageSaver.save(imageInfo);
		if(savedImageInfo.isPresent()) {
			this.imageFileName = savedImageInfo.get().getFileName();
			return true;
		}
		return false;
	}
}
