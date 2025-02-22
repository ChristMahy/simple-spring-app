package cmahy.simple.spring.webapp.resource.impl.adapter.security.request.userinfo.vo.output;

public interface OAuth2Profile {

    String sub();

    String name();

    String email();

    String given_name();

    String family_name();
}
