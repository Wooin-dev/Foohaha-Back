package com.wooin.hahahaback.common.jwt.repository;

import com.wooin.hahahaback.common.jwt.dto.TokenDto;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<TokenDto, String> {
}
