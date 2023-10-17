package com.wooin.hahahaback.common.jwt.repository;

import com.wooin.hahahaback.common.jwt.entity.TokenInfo;
import org.springframework.data.repository.CrudRepository;

public interface TokenInfoRepository extends CrudRepository<TokenInfo, String> {
}
