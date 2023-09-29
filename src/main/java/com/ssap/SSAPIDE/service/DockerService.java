package com.ssap.SSAPIDE.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;

import com.github.dockerjava.netty.NettyDockerCmdExecFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DockerService {

    private final DockerClient dockerClient;

    public DockerService() {
        String dockerHost = System.getenv("DOCKER_HOST_URL"); // TODO: 2023-09-25   AWS에 배포시, 환경 변수 설정을 통해 AWS 도커 데몬 주소로 설정
        if(dockerHost == null || dockerHost.isEmpty()) {
            dockerHost = "tcp://localhost:2375"; // Default Docker host URL
        }

        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerHost) // 환경 변수를 사용하여 도커 호스트 URL 가져오기
                .build();

        this.dockerClient = DockerClientImpl.getInstance(config)
                .withDockerCmdExecFactory(new NettyDockerCmdExecFactory());

    }

    public void pullImage(String stack) throws InterruptedException {
        // DockerHub에서 이미지 가져오기
        dockerClient.pullImageCmd(stack)
                .exec(new PullImageResultCallback())
                .awaitCompletion();
    }

    public String createContainer(String title, String stack) {
        //TODO: 추후에 user 별 컨테이너 이름 중복이 발생할 경우, 에러를 리턴하도록 처리할 것.
        //TODO: 사용자별 컨테이너 어떻게 생성하고 관리할지 고민 필요
        // 컨테이너 이름에 유일성을 보장하는 접미사를 추가합니다.
        String uniqueTitle = title + "_" + System.currentTimeMillis();

        // 컨테이너 생성
        String containerId = dockerClient.createContainerCmd(stack)
                .withName(uniqueTitle)
                .exec()
                .getId();

        // 컨테이너 상세 정보를 가져와 ID를 검증
        String verifiedContainerId = dockerClient.inspectContainerCmd(containerId)
                .exec()
                .getId();

        if (!containerId.equals(verifiedContainerId)) {
            throw new RuntimeException("Container ID mismatch");
        }

        return containerId;
    }

    public void deleteContainer(String containerId) {
        // Docker 컨테이너 중지
        dockerClient.stopContainerCmd(containerId).exec();
        // Docker 컨테이너 삭제
        dockerClient.removeContainerCmd(containerId).exec();
    }

    public void runContainer(String containerId) {
        // 컨테이너 실행
        dockerClient.startContainerCmd(containerId).exec();
    }
}