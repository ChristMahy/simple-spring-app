package cmahy.simple.spring.security.client.api.webclient.repository;

import cmahy.simple.spring.security.client.api.webclient.vo.output.BasicCredentialOutputVo;

import java.util.Optional;

public interface BasicCredentialRepository {

    Optional<BasicCredentialOutputVo> getOne();

}
