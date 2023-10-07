//package com.ssap.SSAPIDE.service;
//
//import com.ssap.SSAPIDE.dto.ContainerResponseDto;
//import com.ssap.SSAPIDE.repository.ContainerRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.testcontainers.containers.MySQLContainer;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//@Testcontainers
//@DataJpaTest
//class ContainerServiceTest {
//
//    private static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:5.7")
//            .withDatabaseName("test")
//            .withUsername("test")
//            .withPassword("test");
//
//    @Autowired
//    private ContainerRepository containerRepository;
//
//    @MockBean
//    private DockerService dockerService;
//
//    @Test
//    void testCreateContainer() throws InterruptedException {
//        // Given
//        String title = "testTitle";
//        String description = "testDescription";
//        String stack = "nginx:latest";
//        String customControl = "testCustomControl";
//        String containerId = "testContainerId";
//
//        when(dockerService.createContainer(anyString(), anyString())).thenReturn(containerId);
//
//        ContainerService containerService = new ContainerService(containerRepository, dockerService);
//
//        // When
//        ContainerResponseDto container = containerService.createContainer(title, description, stack, customControl);
//
//        // Then
//        assertNotNull(container);
//        verify(dockerService, times(1)).pullImage(stack);
//        verify(dockerService, times(1)).createContainer(title, stack);
//        // runContainer is not called here, so we should not verify it
//    }
//
//    @Test
//    void testRunContainer() {
//        // Given
//        String containerId = "testContainerId";
//
//        ContainerService containerService = new ContainerService(containerRepository, dockerService);
//
//        // When
//        containerService.runContainer(containerId);
//
//        // Then
//        verify(dockerService, times(1)).runContainer(containerId);
//    }
//}
