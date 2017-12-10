package org.mihy.gowma.repository;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.model.File;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FileRepository extends BaseRepository {

    public static final String BUCKET_NAME = "gowma";
    public static final String S3_FILE_PATH_TEMPLATE = "https://s3.ap-south-1.amazonaws.com/gowma/%s";
    private final String INSERT_SQL = "INSERT INTO file(file_name,file__path,file__size) VALUES(:name,:path,:size)";
    private final String UPDATE_BY_ID_SQL = "UPDATE FILE SET file_name=:name,file__path=:path,file__size=:size" +
            " WHERE id=:id";
    AWSCredentials credentials = new BasicAWSCredentials(
            "AKIAJJJL5HP2MCKE44EQ",
            "01N7xZKPhork3LbpeupNV7uW21zPOyvpyNFM/q7Z"
    );
    AmazonS3 s3client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.AP_SOUTH_1)
            .build();


    @Transactional
    public List<File> upload(MultipartFile[] files) {

        List<File> filesToReturn = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            File file = new File();
            file.setName(multipartFile.getOriginalFilename());
            file.setPath(multipartFile.getOriginalFilename());
            file.setSize(multipartFile.getSize());
            file = create(file);
            String fileKey = file.getId() + "/" + multipartFile.getOriginalFilename();

            InputStream is = null;
            try {
                is = multipartFile.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            s3client.putObject(new PutObjectRequest(BUCKET_NAME, fileKey, is, new ObjectMetadata()));

            file.setPath(String.format(S3_FILE_PATH_TEMPLATE, fileKey));
            file = update(file);
            filesToReturn.add(file);
        }
        return filesToReturn;
    }


    public File create(File file) {
        super.insert(file, INSERT_SQL, new BeanPropertySqlParameterSource(file));
        return file;
    }

    public File update(File file) {
        BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(file);
        namedParameterJdbcTemplate.update(UPDATE_BY_ID_SQL, paramSource);
        return file;
    }


}
