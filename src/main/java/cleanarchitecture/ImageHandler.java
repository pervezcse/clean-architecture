package cleanarchitecture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import cleanarchitecture.core.entity.Patient.ImageSaver;

@Component
public class ImageHandler implements WebMvcConfigurer, ImageSaver {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final static String PARTIAL_IMAGE_FOLDER_PATH = "resources/static/image";
	private final String IMAGE_FOLDER_PATH = getExecutionPath() + "/" + PARTIAL_IMAGE_FOLDER_PATH + "/";
	
	@Override
	public Optional<FileInfo> save(FileInfo image) {
		String originalFilename = image.getFileName();
		InputStream inStream = image.getData();
		File destinationFile = new File(this.IMAGE_FOLDER_PATH + originalFilename);
		FileInfo result = null;
		try(OutputStream outStream = new FileOutputStream(destinationFile)) {
			byte[] buffer = new byte[inStream.available()];
			inStream.read(buffer);
			outStream.write(buffer);
			result = new FileInfo();
			result.setFileName("/" + PARTIAL_IMAGE_FOLDER_PATH + "/" + originalFilename);
			result.setFileSize(image.getFileSize());
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
		}
		return Optional.ofNullable(result);
	}

	private int indexOf(String s, Pattern pattern) {
		Matcher matcher = pattern.matcher(s);
		return matcher.find() ? matcher.start() : -1;
	}

	private String getExecutionPath() {
	    String absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
	    int index = indexOf(absolutePath, Pattern.compile("(/[^/]*jar)"));
	    if(index==-1)
	    	index = absolutePath.lastIndexOf("/");
	    absolutePath = absolutePath.substring(0, index);
	    try {
			absolutePath = URLDecoder.decode(absolutePath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
	    return absolutePath;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/" + ImageHandler.PARTIAL_IMAGE_FOLDER_PATH + "/**")
				.addResourceLocations("file:" + this.IMAGE_FOLDER_PATH);
	}
}
