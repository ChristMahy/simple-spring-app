package cmahy.simple.spring.webapp.resource.impl.adapter.security.repository;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.vo.output.OAuth2RegistrationOutputVo;

import java.util.Collection;

public interface SecurityOAuth2ClientRegistrationRepository {

    Collection<OAuth2RegistrationOutputVo> findAllAvailableRegistrations();

}
