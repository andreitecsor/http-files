package i.learn.httpfiles.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileController {
    private static final String RESOURCE_PATH = "downloadable.txt";
    private static final String RESOURCE_NAME = "downloadable.txt"; //How we want to name the resource when downloaded

    /**
     * Calling this endpoint will download the resource, named after the endpoint path, in our case "rawfile"
     */
    @GetMapping(value = "/rawfile", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] producesOctetStream() throws IOException {
        return Files.readAllBytes(Paths.get(RESOURCE_PATH));
    }

    /**
     * Calling this endpoint will download the resource and set the resource name in Content-Disposition header
     * Note: the name can differ from the initial name
     */
    @GetMapping(value = "/file-with-header", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public HttpEntity<byte[]> producesOctetStreamV2(HttpServletResponse response) throws IOException {
        Path path = Paths.get(RESOURCE_PATH);
        byte[] bytes = Files.readAllBytes(path);

        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // Another way to the set the response header
        response.setHeader("Content-Disposition", "attachment; filename=" + RESOURCE_NAME);

        return new HttpEntity<>(bytes, headers);
    }
}
