package cmahy.simple.spring.security.common.api.rsa.repository;

import cmahy.simple.spring.security.common.api.rsa.exception.RSAPrivateKeyException;
import cmahy.simple.spring.security.common.api.rsa.vo.id.PrivateKeyId;

import java.security.interfaces.RSAPrivateKey;
import java.util.Map;

public interface RSAPrivateKeyRepository {

    RSAPrivateKey findById(PrivateKeyId id) throws RSAPrivateKeyException;

    Map<PrivateKeyId, RSAPrivateKey> findAll() throws RSAPrivateKeyException;

}
