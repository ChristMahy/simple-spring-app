package cmahy.simple.spring.security.webclient.api.repository;

import cmahy.simple.spring.security.webclient.api.vo.output.BasicCredentialOutputVo;

import java.util.Optional;

public interface BasicCredentialRepository {

    Optional<BasicCredentialOutputVo> getOne();

}
