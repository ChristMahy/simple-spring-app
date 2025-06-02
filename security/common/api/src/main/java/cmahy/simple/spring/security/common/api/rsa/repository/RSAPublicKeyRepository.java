package cmahy.simple.spring.security.common.api.rsa.repository;

import cmahy.simple.spring.security.common.api.rsa.exception.RSAPublicKeyException;
import cmahy.simple.spring.security.common.api.rsa.vo.id.PublicKeyId;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

public interface RSAPublicKeyRepository {

    RSAPublicKey findById(PublicKeyId id) throws RSAPublicKeyException;

    Map<PublicKeyId, RSAPublicKey> findAll() throws RSAPublicKeyException;

}
