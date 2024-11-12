package com.bitbucket.diegoroberto.ocorrenciasapi.unitario.infra;

import com.bitbucket.diegoroberto.ocorrenciasapi.infra.adapters.StorageService;
import io.minio.MinioClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageServiceTest {

    @InjectMocks
    private StorageService storageService;

    @Mock
    private MinioClient minioClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve realizar upload de arquivo no bucket")
    void testUploadFile() throws Exception {
        String bucketName = "test-bucket";
        String objectName = "test-object";
        InputStream inputStream = new ByteArrayInputStream("test data".getBytes());

        assertDoesNotThrow(() -> storageService.uploadFile(bucketName, objectName, inputStream, "text/plain"));
        verify(minioClient, times(1)).putObject(any());
    }

    @Test
    @DisplayName("Deve gerar hash")
    void testGenerateHash() throws Exception {
        byte[] data = "test data".getBytes();
        String hash = storageService.generateHash(data);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] expectedHash = digest.digest(data);

        StringBuilder hexString = new StringBuilder();
        for (byte b : expectedHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        assertEquals(hexString.toString(), hash);
    }
}

